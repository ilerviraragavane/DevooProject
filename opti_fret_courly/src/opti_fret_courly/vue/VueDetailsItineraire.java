package opti_fret_courly.vue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import opti_fret_courly.modele.Chemin;
import opti_fret_courly.modele.Entrepot;
import opti_fret_courly.modele.Horaire;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.PointDArret;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.modele.Tournee;
import opti_fret_courly.modele.Troncon;

/**
 * Cette vue représente la desctiption de la tournée
 * @author Elody Catinel
 *
 */
public class VueDetailsItineraire {

	/**
	 * La tournée calculée
	 */
	Tournee tournee;

	/**
	 * Chaine contenant le caractère de retour à la ligne correspondant au 
	 * format de l'OS utilisé
	 */
	private static final String newLine = System.getProperty("line.separator");

	/**
	 * Constructeur
	 */
	public VueDetailsItineraire() {
		this.tournee = null;
	}

	/**
	 * Ecrit la description de l'itinéraire sous forme textuelle
	 * @return le buffer contenant la description
	 * @throws IOException lorsqu'il y a une erreur lors de l'écriture dans 
	 * le buffer
	 */
	public StringBuilder ecrire() throws IOException {
		if (tournee == null) {
			throw new IllegalStateException(
					"La tournee 'est pas calculée : impossible d'ecrire les"
							+ " instructions");
		}
		StringBuilder buff = new StringBuilder();

		/* Date de la tournée */
		ecrireDate(buff, tournee.getDate());

		if (tournee.aDesPlages()) {
			Entrepot entrepot = tournee.getEntrepot();
			List<Chemin> chemins = tournee.getChemins();

			if (chemins == null || chemins.isEmpty()) {
				throw new IllegalStateException(
						"La tournee n'a pas ete calculee : impossible "
								+ "d'ecrire les instructions");
			}

			/* Informations générales sur la tournée */
			int nbChemins = chemins.size();
			buff.append("Nombre de livraisons du jour : " + (nbChemins - 1));
			buff.append(newLine);
			Horaire horairesEntrepot = entrepot.getHeuresPassage();
			buff.append("Heure de départ de l'entrepôt : "
					+ Horaire.formaterHeureArrondie(
												horairesEntrepot.getDebut()));
			buff.append(newLine);
			buff.append("Heure de retour à l'entrepôt : "
					+ Horaire.formaterHeureArrondie(
												horairesEntrepot.getFin()));
			buff.append(newLine);

			/* Liste des livraisons */
			for (int i = 0; i < nbChemins; i++) {
				Chemin chemin = chemins.get(i);
				ecrireCheminement(buff, chemin, i + 1);
			}

		} else {
			buff.append("Il n'y a pas de livraison à effectuer.");
		}
		return buff;

	}

	/**
	 * Ecrit la date de la tournée
	 * 
	 * @param buff le buffer dans lequel il faut ecrire
	 * @param date la date de la tournée
	 * @throws IOException lorsqu'il y a une erreur lors de l'écriture dans 
	 * le buffer
	 */
	private void ecrireDate(StringBuilder buff, Calendar date)
			throws IOException {
		buff.append("Tournee du ");
		DateFormat format = DateFormat.getDateInstance(SimpleDateFormat.LONG);
		buff.append(format.format(date.getTime()));
		buff.append(newLine);
		buff.append(newLine);
	}

	/**
	 * Ecrit les instructions concernant une livraison
	 * 
	 * @param buff le buffer dans lequel il faut écrire
	 * @param chemin le chemin permettant de réaliser une livraison
	 * @param position la position de cette livraison dans la tournée
	 * @throws IOException lorsqu'il y a une erreur lors de l'écriture dans 
	 * le buffer
	 */
	private void ecrireCheminement(StringBuilder buff, Chemin chemin,
			int position) throws IOException {

		PointDArret arrivee = chemin.getPointArr();
		buff.append(newLine);

		if (arrivee instanceof PointDeLivraison) {
			ecrireTitre(buff, "Livraison " + position);
			Livraison livraison = ((PointDeLivraison) arrivee).getLivraison();

			buff.append("Destinataire : " + 
										livraison.getDestinataire().getId());
			buff.append(newLine);
			buff.append("Lieu de livraison : " + arrivee.getLieu().getId());
			buff.append(newLine);
			Calendar debut = 
						   livraison.getPlageHoraire().getCreneau().getDebut();
			Calendar fin = livraison.getPlageHoraire().getCreneau().getFin();
			String creneau = Horaire.formaterHeureArrondie(debut) 
							 + " - " + 
							 Horaire.formaterHeureArrondie(fin); 
			buff.append("Plage horaire : " + creneau);
			buff.append(newLine);

			ecrireItineraire(buff, chemin);

			Horaire heuresPassage = livraison.getHeuresPassage();
			buff.append("Heure d'arrivée prévue : "
					+ Horaire.formaterHeureArrondie(heuresPassage.getDebut()));
			buff.append(newLine);
			buff.append("Heure de départ prévue pour le prochain point : "
					+ Horaire.formaterHeureArrondie(heuresPassage.getFin()));
			buff.append(newLine);

		} else {
			ecrireTitre(buff, "Retour à l'entrepôt");
			ecrireItineraire(buff, chemin);
		}
		buff.append(newLine);
	}

	/**
	 * Ecrit un titre dans le buffer
	 * 
	 * @param buff le buffer dans lequel il faut écrire
	 * @param titre le titre à afficher
	 * @throws IOException lorsqu'il y a une erreur lors de l'écriture dans 
	 * le buffer
	 */
	private void ecrireTitre(StringBuilder buff, String titre)
			throws IOException {
		buff.append("----------- ");
		buff.append(titre);
		buff.append(" -----------");
		buff.append(newLine);

	}

	/**
	 * Ecrit la descrition des tronçons à prendre pour suivre un chemin
	 * 
	 * @param buff le buffer dans lequel il faut ecrire
	 * @param chemin le chemin
	 * @throws IOException lorsqu'il y a une erreur lors de l'écriture dans 
	 * le buffer
	 */
	private void ecrireItineraire(StringBuilder buff, Chemin chemin) 
															throws IOException {

		PointDArret depart = chemin.getPointDep();

		buff.append("Itinéraire : ");
		buff.append(newLine);
		if (depart instanceof PointDeLivraison) {
			buff.append("\t>>> Depuis le lieu " + depart.getLieu().getId());
			buff.append(newLine);
		} else {
			buff.append("\t>>> Depuis l'entrepot ");
			buff.append(newLine);
		}

		List<Troncon> troncons = chemin.getTroncons();
		int nbtroncons = troncons.size();
		String instruction = "";

		for (int i = 0; i < nbtroncons; i++) {
			Troncon troncon = troncons.get(i);
			instruction = "\t> A la prochaine intersection, prendre la rue ";
			if (i == 0) {
				instruction = "\t> Prendre la rue ";
			}else{
				Troncon previous = troncons.get(i - 1);
				if (troncon.getNomRue().equals(previous.getNomRue())) {
					instruction = "\t> A la prochaine intersection, continuer "
																+ "sur la rue ";
				}
			}
			
			buff.append(instruction);
			buff.append(troncon.getNomRue());
			buff.append(newLine);
		}
		buff.append("\t>>> Vous êtes arrivé !");
		buff.append(newLine);
	}


	/**
	 * Setter de la tournée
	 * @param tournee la tournée
	 */
	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
	}
	
	
	
}
