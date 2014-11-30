package opti_fret_courly.controleur;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import opti_fret_courly.modele.Destinataire;
import opti_fret_courly.modele.Entrepot;
import opti_fret_courly.modele.Horaire;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.modele.Tournee;
import opti_fret_courly.outil.Lecteur;
import opti_fret_courly.outil.exception.SemantiqueException;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueZone;

import org.jdom2.Element;
import org.jdom2.JDOMException;

/**
 * Cette commande permet de charger les demandes de livraison à partir d'un
 * fichier
 * 
 * @author Elody Catinel
 * 
 */
public class CmdChargeDmd extends CmdChargement {

	/**
	 * Vue graphique associée à la zone
	 */
	private VueZone vueZone;
	
	/**
	 * Vue représentant le détail de l'élément sélectionné
	 */
	private VueDetailNoeud vueDetailNoeud;

	/**
	 * Fichier XML contenant les demandes de livraison
	 */
	private String fichier;

	/**
	 * Nom de la balise représentant un la liste des plages horaires d'une
	 * tournée dans le fichier
	 */
	private static final String N_PLAGES = "PlagesHoraires";
	/**
	 * Nom de la balise représentant une <code>Plage</code> dans le fichier
	 */
	private static final String N_PLAGE = "Plage";
	/**
	 * Nom de la balise représentant un <code>Entrepot</code> dans le fichier
	 */
	private static final String N_ENTREPOT = "Entrepot";
	/**
	 * Nom de la balise représentant la liste des livraisons d'une plage horaire
	 * dans le fichier
	 */
	private static final String N_LIVRAISONS = "Livraisons";
	/**
	 * Nom de la balise représentant une <code>Livraisoneud</code> dans le
	 * fichier
	 */
	private static final String N_LIVRAISON = "Livraison";

	/**
	 * Map permettant de stocker les indices des noeuds occupés par des
	 * livraisons ou l'entrepot
	 */
	private Set<Integer> positionsLivraisons;

	/**
	 * Constructeur
	 * 
	 * @param vueZone la vue graphique assoiée à la zone
	 * @param vueDetailNoeud la vue détaillée des elements sélectionnés
	 * @param fichier le fichier contenant les demandes de livraisons
	 */
	public CmdChargeDmd(VueZone vueZone,VueDetailNoeud vueDetailNoeud, String fichier) {
		this.vueZone = vueZone;
		this.fichier = fichier;
		this.vueDetailNoeud = vueDetailNoeud;
		this.positionsLivraisons = new HashSet<Integer>();
	}

	/**
	 * Lance la lecture du fichier de demandes de livraisons
	 */
	public void executer() throws SemantiqueException, IOException,
			JDOMException {

		if (vueZone == null) {
			throw new IllegalArgumentException("La vue de la zone est nulle");
		}

		Element journeeType = Lecteur.lireFichierXML(fichier);
		Tournee tournee = new Tournee();

		/* Entrepot */
		Element entrepotXML = journeeType.getChild(N_ENTREPOT);
		Entrepot entrepot = construireEntrepot(entrepotXML);
		tournee.setEntrepot(entrepot);

		/* Plages */
		Element groupePlagesXML = journeeType.getChild(N_PLAGES);
		List<Element> plagesXML = groupePlagesXML.getChildren(N_PLAGE);

		if (plagesXML != null) {
			for (int i = 0; i < plagesXML.size(); i++) {
				Element plageXML = plagesXML.get(i);

				/* Si la plage ne peut pas etre ajoutee, on arrete */
				Calendar dateTournee = tournee.getDate();
				Plage plage = construirePlage(plageXML, dateTournee);
				if( plage.estVide()){
					System.out.println("La plage " + plage + " a ete ignoree car elle est vide.");
				}
				else if (!tournee.ajouterPlage(plage)){
					throw new SemantiqueException("La plage " + plage
							+ " n'a pas pu etre ajoutee, car elle existe deja "
							+ "ou s'entrecroise avec une autre");
				}
			}

			/* Actualisation de zone */
			vueZone.getZone().setTournee(tournee);

		}
		vueDetailNoeud.setDateTournee(tournee.getDate());
		vueZone.actualiserVue();
		vueZone.repaint();

	}

	/**
	 * Construit un plage a partir d'un element XML representant une plage
	 * 
	 * @param plageXML la balise XML representant la plage
	 * @param dateTournee date de la tournee
	 * @return la plage horaire construite
	 * @throws SemantiqueException si l'heure de début de la plage horaire est
	 *             supérieure à l'heure de fin de celle-ci
	 * @throws IllegalArgumentException si la les heures de la plage horaire
	 *             n'ont pas le format attendu, ou si les attributs des
	 *             livraisons de la plage n'ont pas le format attendu
	 */

	private Plage construirePlage(Element plageXML, Calendar dateTournee)
			throws SemantiqueException, IllegalArgumentException {
		Plage plage = null;
		String hd = plageXML.getAttributeValue("heureDebut");
		String hf = plageXML.getAttributeValue("heureFin");

		/*
		 * On extrait les heures de debut et de fin, et on y ajoute la date de
		 * la tournée
		 */
		Calendar heureDebut = Lecteur.extraireHeurePrecise(hd);
		Calendar heureFin = Lecteur.extraireHeurePrecise(hf);
		Horaire horaire = new Horaire(heureDebut, heureFin);
		horaire.completerDate(dateTournee);

		/* On verifie que heureDebut < heureFin */
		if (!heureDebut.before(heureFin))
			throw new SemantiqueException(
					"L'heure de debut doit etre inferieure a l'heure de fin");

		plage = new Plage(new Horaire(heureDebut, heureFin));

		/* Ajout des livraisons de la plage */
		Element groupeLivraisons = plageXML.getChild(N_LIVRAISONS);
		List<Element> livraisons = groupeLivraisons.getChildren(N_LIVRAISON);
		if (livraisons != null) {
			for (Element livraisonXml : livraisons) {
				Livraison livraison = construireLivraison(livraisonXml);

				plage.ajouterLivraison(livraison);
				livraison.setPlageHoraire(plage);
			}
		}

		vueZone.repaint();//TODO : questce que ca fait la ?????

		return plage;
	}

	/**
	 * Construit une livraison a partir d'un element XML represenentant une
	 * livraison
	 * 
	 * @param livraisonXML l'élément XML representant une livraison
	 * @return la livraison créée
	 * @throws IllegalArgumentException si les attributs de
	 *             <code>livraisonXML</code> n'ont pas le format attendu
	 * @throws SemantiqueException l'adresse de la livraison fait référence à 
	 *             un noeud inexistant ou si une livraison ou l'entrepot existe 
	 *             deja à l'adresse de la livraison
	 */
	private Livraison construireLivraison(Element livraisonXML)
			throws IllegalArgumentException, SemantiqueException {
		Livraison livraison = null;
		try {
			int id = Integer.parseInt(livraisonXML.getAttributeValue("id"));
			int adresse = Integer.parseInt(livraisonXML
					.getAttributeValue("adresse"));
			int client = Integer.parseInt(livraisonXML
					.getAttributeValue("client"));

			if (id < 0)
				throw new IllegalArgumentException(
						"L'id doit etre positif ou nul");
			if (adresse < 0)
				throw new IllegalArgumentException(
						"L'adresse doit etre positive ou nulle");
			if (client < 0)
				throw new IllegalArgumentException(
						"Le numero de client doit etre positif ou nul");

			/* Verification de l'existance du noeud d'adresse */
			Noeud noeudAdresse = vueZone.getZone().getNoeudParId(adresse);
			if (noeudAdresse == null) {
				throw new SemantiqueException("La livraison d'id " + id
						+ "fait reference a un noeud inexistant");
			}

			/*
			 * Verification qu'il n'existe pas deja une livraison ou l'entrepot
			 * a cet endroit
			 */
			if (!positionsLivraisons.add(adresse)) {
				throw new SemantiqueException(
						"Impossible d'ajouter la livraison pour le client "
								+ client + " a l'adressse "+ adresse
								+ ". Un point de livraison ou l'entrepot"
								+ " existe deja a ce point.");
			}
			PointDeLivraison ptLivraison = new PointDeLivraison(noeudAdresse);
			livraison = new Livraison(new Destinataire(client), ptLivraison);
			ptLivraison.setLivraison(livraison);

		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(
					"Les attributs d'une livraison doivent etre id(entier),"
							+ "client(entier), adresse(entier)", nfe);
		}
		return livraison;
	}

	/**
	 * Construit un entrepot a partir d'un element XML representant l'entrepot
	 * 
	 * @param entrepotXML la balise XML representant l'entrepot
	 * @return l'entrepot fabrique
	 * @throws SemantiqueException si l'adresse de l'entrepôt fait référence à
	 *             un noeud inexistant
	 * @throws IllegalArgumentException si les attributs de
	 *             <code>entrepotXML</code> n'ont pas le format attendu
	 */
	private Entrepot construireEntrepot(Element entrepotXML)
			throws SemantiqueException, IllegalArgumentException {
		Entrepot entrepot = null;

		try {
			int adresse = Integer.parseInt(entrepotXML
					.getAttributeValue("adresse"));
			if (adresse >= 0) {

				// Si le noeud d'adresse n'existe pasn on s'arrete
				Noeud lieu = vueZone.getZone().getNoeudParId(adresse);
				if (lieu == null)
					throw new SemantiqueException(
							"L'adresse de l'entrepôt ne correspond pas à un "
							+ "noeud existant");

				entrepot = new Entrepot(lieu);
				positionsLivraisons.add(adresse);
				// this.vueZone.getVuesCliquable().add(new
				// VueEntrepot(entrepot));
			} else {
				throw new IllegalArgumentException(
						"L'entrepot doit avoir pour attribut une destination "
						+ ">=0");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(
					"L'entrepot doit avoir pour attribut une destination >=0",
					nfe);
		}
		return entrepot;
	}

}
