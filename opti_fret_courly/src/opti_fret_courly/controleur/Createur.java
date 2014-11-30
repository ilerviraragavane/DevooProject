package opti_fret_courly.controleur;

import opti_fret_courly.modele.EltCarte;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.vue.Fenetre;
import opti_fret_courly.vue.VueDetailsItineraire;

/**
 * Cette classe est un singleton qui se charge d'interpréter les demandes
 * d'utilisateur et de créer les commandes correspondantes.
 * 
 * @author LIU Chongguang
 */
public class Createur {

	/**
	 * La fenêtre.
	 */
	private Fenetre fenetre;

	/**
	 * L'objet qui se charge d'exécuter et annuler les commandes.
	 */
	private Invocateur invocateur;

	/**
	 * Constructeur de la classe.
	 * 
	 * @param fenetre La fenêtre.
	 * 
	 */
	public Createur(Fenetre fenetre) {
		this.fenetre = fenetre;
		this.invocateur = new Invocateur(fenetre);
	}


	

	/**
	 * Le chargement des demandes de livraison depuis un fichier XML.
	 * 
	 * @param fichier Le nom de fichier à charger.
	 * @return <b>true</b> s'il n'y a pas eu de problème lors de l'exécution de
	 *         la commande </br> <b>false</b> s'il y a eu un problème lors de
	 *         l'exécution de la commande.
	 */
	public boolean dmdChargementDemandes(String fichier) {
		boolean succes = false;
		try {
			CmdChargeDmd cmd = new CmdChargeDmd(fenetre.getVueZone(),
					fenetre.getVueDetailNoeud(), fichier);
			invocateur.ajouterCmd(cmd);
			afficherSucces("La demande de livraisons du fichier " + fichier
					+ " a bien été chargée.");
			succes = true;
		} catch (Exception e) {
			afficherEchec(e.getMessage());
			e.printStackTrace();
		}
		return succes;
	}

	/**
	 * L'ajout d'une livraiosn à une plage.
	 * 
	 * @param livraison La livraison à ajouter.
	 * @param plage La plage à laquelle la livraison est ajoutée.
	 * @param nouvellePlage True si cette plage est une nouvelle plage, sinon
	 *            false.
	 * @return <b>true</b> s'il n'y a pas eu de problème lors de l'exécution de
	 *         la commande </br> <b>false</b> s'il y a eu un problème lors de
	 *         l'exécution de la commande.
	 */
	public boolean dmdAjout(Livraison livraison, Plage plage,
			Boolean nouvellePlage) {
		boolean succes = false;
		try {
			CmdAjout cmd = new CmdAjout(fenetre.getVueZone(),
					fenetre.getVueDetailNoeud(), livraison, plage,
					nouvellePlage);
			invocateur.ajouterCmd(cmd);
			afficherSucces("L'ajout de la livraison " + livraison
					+ " a bien été effectuée");

			succes = true;
			fenetre.activerItineraire(false);
		} catch (Exception e) {
			afficherEchec(e.getMessage());
			e.printStackTrace();
		}
		return succes;

	}

	/**
	 * Le chargement de la carte d'une zone depuis un fichier XML.
	 * 
	 * @param fichier Le nom du fichier à charger.
	 * @return <b>true</b> s'il n'y a pas eu de problème lors de l'exécution de
	 *         la commande </br> <b>false</b> s'il y a eu un problème lors de
	 *         l'exécution de la commande.
	 */
	public boolean dmdChargementZone(String fichier) {
		boolean succes = false;
		try {
			CmdChargeZone cmd = new CmdChargeZone(fenetre.getVueZone(),
					fenetre.getVueDetailNoeud(), fichier);
			invocateur.ajouterCmd(cmd);
			afficherSucces("Le chargement de la zone du fichier " + fichier
					+ " a bien été effectué.");
			succes = true;
		} catch (Exception e) {
			afficherEchec(e.getMessage());
			e.printStackTrace();
		}
		return succes;

	}

	/**
	 * Création d'un fichier de détail de la tournée.
	 * 
	 * @param vueDetailsItineraire la vue représentant le détail de la tournée
	 * @param fichier Le nom du fichier à créer.
	 * @return <b>true</b> s'il n'y a pas eu de problème lors de l'exécution de
	 *         la commande </br> <b>false</b> s'il y a eu un problème lors de
	 *         l'exécution de la commande.
	 */
	public boolean dmdFicInstructions(VueDetailsItineraire vueDetailsItineraire,String fichier) {
		boolean succes = false;
		try {
			CmdFicInstructions cmd = new CmdFicInstructions(vueDetailsItineraire, fichier);
			invocateur.ajouterCmd(cmd);
			afficherSucces("Le fichier " + fichier + " a bien été édité.");

			succes = true;
		} catch (Exception e) {
			afficherEchec(e.getMessage());
			e.printStackTrace();
		}
		return succes;

	}

	/**
	 * Annulation de la dernière demande exécutée.
	 * 
	 * @return <b>true</b> s'il n'y a pas eu de problème lors de l'exécution de
	 *         la commande </br> <b>false</b> s'il y a eu un problème lors de
	 *         l'exécution de la commande.
	 */
	public boolean dmdAnnulation() {
		boolean succes = false;
		try {
			invocateur.annuler();
			succes = true;
			fenetre.activerItineraire(false);
			afficherSucces("L'annulation a bien été effectuée");

		} catch (Exception e) {
			afficherEchec(e.getMessage());
			e.printStackTrace();
		}
		return succes;

	}

	/**
	 * sélection d'un élément de l'interface utilisateur.
	 * 
	 * @param eltClique L'élément de l'interface utilisateur qui est cliqué.
	 * 
	 * @return <b>true</b> s'il n'y a pas eu de problème lors de l'exécution de
	 *         la commande </br> <b>false</b> s'il y a eu un problème lors de
	 *         l'exécution de la commande.
	 */
	public boolean dmdSelection(EltCarte eltClique) {
		boolean succes = false;
		try {
			CmdSelection cmd = new CmdSelection(fenetre.getVueDetailNoeud(),
					fenetre.getVueZone(), eltClique);
			invocateur.ajouterCmd(cmd);
			if (eltClique != null)
				afficherSucces("L'élément " + eltClique + " a été sélectionné");

			succes = true;
		} catch (Exception e) {
			afficherEchec(e.getMessage());
			e.printStackTrace();
		}
		return succes;

	}

	/**
	 * Suppression d'une livraiosn.
	 * 
	 * @param livraison La livraison à supprimer.
	 * 
	 * @return <b>true</b> s'il n'y a pas eu de problème lors de l'exécution de
	 *         la commande </br> <b>false</b> s'il y a eu un problème lors de
	 *         l'exécution de la commande.
	 */
	public boolean dmdSuppression(Livraison livraison) {
		boolean succes = false;
		try {
			CmdSuppression cmd = new CmdSuppression(fenetre.getVueZone(),
					fenetre.getVueDetailNoeud(), livraison);
			invocateur.ajouterCmd(cmd);
			succes = true;
			fenetre.activerItineraire(false);
			afficherSucces("La livraison " + livraison + " a été supprimée");
		} catch (Exception e) {
			afficherEchec(e.getMessage());
			e.printStackTrace();
		}
		return succes;

	}

	/**
	 * Calculer la tournée optimale.
	 * 
	 * @return <b>true</b> s'il n'y a pas eu de problème lors de l'exécution de
	 *         la commande </br> <b>false</b> s'il y a eu un problème lors de
	 *         l'exécution de la commande.
	 */
	public boolean dmdCalculer() {
		boolean succes = false;
		try {
			CmdCalculer cmd = new CmdCalculer(fenetre.getVueZone(),
					fenetre.getVueDetailNoeud(),
					fenetre.getVueZone().getZone(), 100);
			invocateur.ajouterCmd(cmd);
			succes = true;
			fenetre.activerItineraire(true);
			afficherSucces("La tournée a été recalculée");

		} catch (Exception e) {
			afficherEchec(e.getMessage());
			e.printStackTrace();
		}
		return succes;

	}

	/**
	 * Réexécution de la dernière demande annulée.
	 * 
	 * @return <b>true</b> s'il n'y a pas eu de problème lors de l'exécution de
	 *         la commande </br> <b>false</b> s'il y a eu un problème lors de
	 *         l'exécution de la commande.
	 */
	public boolean dmdRefaire() {
		boolean succes = false;
		try {
			invocateur.reexecuter();
			succes = true;
			fenetre.activerItineraire(false);
			afficherSucces("La réexecution a bien été effectuée");

		} catch (Exception e) {
			afficherEchec(e.getMessage());
			e.printStackTrace();
		}
		return succes;
	}
	
	/**
	 * Permet d'afficher un message indiquant le mauvais déroulement d'une commande
	 * @param msg le message à afficher
	 */
	public void afficherEchec(String msg) {
		fenetre.getVueZoneDeDialogue().afficherMessage(msg, true);

	}

	/**
	 * Permet d'afficher un message indiquant le bon déroulement d'une commande
	 * @param msg le message à afficher
	 */
	public void afficherSucces(String msg)  {
		fenetre.getVueZoneDeDialogue().afficherMessage(msg, false);
	}
}
