package opti_fret_courly.controleur;

import opti_fret_courly.outil.exception.CommandeException;

/**
 * Classe abstraite représentant une commande qui peut être annulée.
 * @author LIU Chongguang
 */
public abstract class CmdAnnulable extends Commande {

	/**
	 * Annulation de cette commande.
	 * @throws CommandeException lorsqu'il y a une erreur lors de l'annulation
	 *  d'une commande
	 */
    public abstract void annuler() throws CommandeException;

}
