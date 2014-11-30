package opti_fret_courly.controleur;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Troncon;
import opti_fret_courly.modele.Zone;
import opti_fret_courly.outil.Lecteur;
import opti_fret_courly.outil.exception.SemantiqueException;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueZone;

import org.jdom2.Element;
import org.jdom2.JDOMException;

/**
 * Cette commande se charge de charger les elements du modele correspondant au
 * plan de la zone.
 * 
 * @author Elody Catinel
 * 
 */
public class CmdChargeZone extends CmdChargement {

	/**
	 * Vue graphique assicociée à la zone
	 */
	private VueZone vueZone;


	/**
	 * Vue donnant le détail d'un élément sélectionné
	 */
	private VueDetailNoeud vueDetailNoeud;
	
	/**
	 * Fichier XML contenant le plan
	 */
	private String fichier;
	/**
	 * Nom de la balise représentant un <code>Noeud</code> dans le fichier
	 */
	private static final String N_NOEUD = "Noeud";
	/**
	 * Nom de la balise représentant un <code>Troncon</code> dans le fichier
	 */
	private static final String N_TRONCON = "TronconSortant";

	/**
	 * Constructeur
	 * 
	 * @param vueZone la vue associée a la zone
	 * @param fichier le fichier contenant le plan
	 */
	public CmdChargeZone(VueZone vueZone, VueDetailNoeud vueDetailNoeud, 
			String fichier) {
		this.fichier = fichier;
		this.vueZone = vueZone;
		this.vueDetailNoeud = vueDetailNoeud;
	}

	/**
	 * Lance la lecture du fichier
	 * @throws SemantiqueException s'il y a une erreur d'incohérence
	 * @throws IllegalArgumentException si vueZone est null
	 * @throws JDOMException s'il y a une erreur de lecture du fichier XML
	 * @throws IOException s'il y a une erreur de lecture du fichier XML
	 */
	public void executer() throws SemantiqueException, IllegalArgumentException,
			JDOMException, IOException {

		if( vueZone == null ){
			throw new IllegalArgumentException("La vue de la zone est nulle");
		}
		
		Element reseau = Lecteur.lireFichierXML(fichier);
		List<Element> noeuds = reseau.getChildren(N_NOEUD);

		/* Liste contenant les noeuds rangés par id */
		Map<Integer, Noeud> listeNoeuds = new HashMap<Integer, Noeud>();
		/* Liste contenant les positions des noeuds déjà insérés */
		Map<Integer, Set<Integer>> listePositionsNoeuds = new HashMap<Integer, 
				Set<Integer>>();
		/* Positions X et Y maximales des noeuds inseres */
		int xMax = 0;
		int yMax = 0;

		/* 1 - On ajoute tous les noeuds */
		for (Element noeudXml : noeuds) {
			Noeud n = construireNoeud(noeudXml);

			/* Si un noeud existe déjà a la même position , erreur */
			if (!ajouterPositionNoeud(n, listePositionsNoeuds))
				throw new SemantiqueException("Un noeud de coordonnees ("
						+ n.getX() + "," + n.getY() + ") existe deja");

			/* Si le noeud de meme id existe deja, erreur */
			if (listeNoeuds.put(n.getId(), n) != null)
				throw new SemantiqueException("Le noeud " + n.getId()
						+ " existe deja");

			xMax = Math.max(xMax, n.getX());
			yMax = Math.max(yMax, n.getY());
		}

		/* 2 - On ajoute les tronçons de chaque noeud */
		for (Element noeudXml : noeuds) {
			int idOrigine = Integer.parseInt(noeudXml.getAttributeValue("id"));

			List<Element> troncons = noeudXml.getChildren(N_TRONCON);
			for (Element tronconXml : troncons) {
				Troncon t = construireTroncon(idOrigine, tronconXml,
						listeNoeuds);

				/* Si le troncon existe déjà, erreur */
				if (!listeNoeuds.get(idOrigine).ajouterTroncon(t))
					throw new SemantiqueException("Le troncon "
							+ t.getOrigine().getId() + " -> "
							+ t.getFin().getId() + " existe deja");
			}
		}

		/* On vérifie qu'il n'existe pas de noeuds non accessibles */
		Noeud noeudInaccessible = verifierNoeudsAccessibles(listeNoeuds);
		if (noeudInaccessible != null)
			throw new SemantiqueException("Le noeud " + noeudInaccessible
					+ " est inaccessible");

		/* 3 - on crée la zone */
		vueDetailNoeud.setVisible(false);
		Zone zone = vueZone.getZone();
		zone.setNoeuds(listeNoeuds);
		zone.setTournee(null);

		vueZone.setHauteur(yMax);
		vueZone.setLargeur(xMax);
		vueZone.initPlan();
		vueZone.repaint();

	}

	/**
	 * Verifie que tous les noeuds sont accessibles depuis un tronçon.
	 * 
	 * @param noeuds la liste des noeuds
	 * @return le 1er noeud inaccessible trouvé, ou null si tous les noeuds sont
	 *         accessibles.
	 */
	private Noeud verifierNoeudsAccessibles(Map<Integer, Noeud> noeuds) {

		for (Noeud noeudArrivee : noeuds.values()) {
			/*Pour tous les noeuds, on cherche s'il existe un troncon qui y va*/
			boolean trouve = false;
			for (Noeud noeudDepart : noeuds.values()) {
				if (noeudDepart.getTronconVers(noeudArrivee) != null) {
					trouve = true;
					break;
				}
			}
			if (!trouve) {
				return noeudArrivee;
			}
		}
		return null;

	}

	/**
	 * Ajoute la position d'un noeud à une liste des positions des noeuds
	 * 
	 * @param noeud le noeud
	 * @param positionsNoeuds la liste des positions des noeuds
	 * @return true si la position a ete ajoutee - ou false sinon (cela signifie
	 *         que la position existait deja)
	 */
	private boolean ajouterPositionNoeud(Noeud noeud,
			Map<Integer, Set<Integer>> positionsNoeuds) {
		boolean ajoute = true;
		int x = noeud.getX();
		int y = noeud.getY();

		if (positionsNoeuds.containsKey(x)) {
			ajoute = positionsNoeuds.get(x).add(y);
		} else {
			Set<Integer> lesY = new HashSet<Integer>();
			lesY.add(y);
			positionsNoeuds.put(x, lesY);
		}
		return ajoute;
	}

	/**
	 * Cree un <code>Troncon<code> a partir d'un élement XML
	 * 
	 * @param idOrigine origine du troncon
	 * @param tronconElt l'element XML correspondant au troncon
	 * @param noeuds la liste des noeuds existants
	 * @return le troncon construit - ou null si une erreur s'est produite
	 * @throws IllegalArgumentException s'il les attributs de 
	 * 			<code>tronconElt</code> ne correspondent pas au format attendu.
	 * @throws SemantiqueException si le tronçon boucle sur lui-même ou si le 
	 * 			tronçon fait référence à un noeud de fin inexistant
	 */
	private Troncon construireTroncon(int idOrigine, Element tronconElt,
			Map<Integer, Noeud> noeuds) throws IllegalArgumentException,
			SemantiqueException {
		Troncon tronconConstruit = null;
		try {

			double vitesse = Double.parseDouble(tronconElt
					.getAttributeValue("vitesse"));
			double longueur = Double.parseDouble(tronconElt
					.getAttributeValue("longueur"));

			int destination = Integer.parseInt(tronconElt
					.getAttributeValue("destination"));
			String nomRue = tronconElt.getAttributeValue("nomRue");

			if (nomRue.isEmpty())
				throw new IllegalArgumentException(
						"Erreur a un troncon d'origine "
								+ idOrigine
								+ " : le nom de la rue doit etre compose d'au "
								+ "moins 1 caractere");

			if (vitesse <= 0)
				throw new IllegalArgumentException("Erreur a un  troncon"
						+ "d'origine " + idOrigine
						+ " : la vitesse doit etre positive ou nulle");

			if (longueur < 0)
				throw new IllegalArgumentException("Erreur a un  troncon "
						+ "d'origine :"
						+ idOrigine + " : longueur doit etre positive");

			/* Verification que le troncon ne boucle pas sur lui meme */
			if (idOrigine == destination)
				throw new SemantiqueException("Le troncon d'origine "
						+ idOrigine + " boucle sur lui-meme");

			/* Verification de l'existence du noeud de destination */
			if (!noeuds.containsKey(destination)) {
				throw new SemantiqueException(
						"Le noeud de destination du troncon d'origine "
								+ idOrigine + " et de rue " + nomRue
								+ " est inconnue.");
			} else {
				tronconConstruit = new Troncon(nomRue, vitesse, longueur,
						noeuds.get(idOrigine), noeuds.get(destination));
			}

		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(
					"Erreur sur un troncon d'origine "
							+ idOrigine
							+ ".La destination doit etre un entier.  La "
							+ "longueur et la vitesse doivent etre des "
							+ "decimaux.",
					nfe);
		}

		return tronconConstruit;
	}

	/**
	 * Construit un noeud du modele a partir d'un element XML
	 * 
	 * @param noeudElt le noeud XML
	 * @return le noeud construit - ou null s'il n'a pas pu etre construit
	 * @throws  IllegalArgumentException si les attributs de 
	 * 				<code>noeudElt</code> n'ont pas le format attendu
	 */
	private Noeud construireNoeud(Element noeudElt)
			throws IllegalArgumentException {
		Noeud noeudConstruit = null;

		try {
			int x = Integer.parseInt(noeudElt.getAttributeValue("x"));
			int y = Integer.parseInt(noeudElt.getAttributeValue("y"));
			int id = Integer.parseInt(noeudElt.getAttributeValue("id"));

			if (x >= 0 && y >= 0 && id >= 0) {
				noeudConstruit = new Noeud(id, x, y);
			} else {
				throw new IllegalArgumentException("Les attributs d'un noeud "
						+ "d'origine "
						+ id
						+ " sont invalides :x,yet id doivent etre positifs");
			}

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Les attributs d'un noeud sont invalides : x,y,id doivent "
					+ "etre des entiers",
					e);
		}

		return noeudConstruit;
	}
}
