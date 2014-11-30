package opti_fret_courly.controleur;

import java.util.LinkedList;
import java.util.ListIterator;

import opti_fret_courly.vue.Fenetre;

/**
 * Cette classe se charge de créer et exécuter les commandes. 
 * Elle garde les 20 dernières commandes afin de pouvoir les annuler et 
 * réexécuter.
 * @author LIU Chongguang
 */
public class Invocateur {

    /**
     * Une constante qui définie la taille de la liste pour stocker les 
     * commandes.
     */
    private static final int TAILLE_HISTORIQUE = 20;

    /**
     * Liste pour stocker les commandes annulables.
     */
    private ListIterator<CmdAnnulable> itPosCourante;

    /**
     * Itérateur de la liste de commandes.
     */
    private LinkedList<CmdAnnulable> commandes;
    
    /**
     * Fenetre de l'application
     */
    private Fenetre fenetre;
    
    
    /**
     * Constructeur de la classe.
     * @param fenetre : fenetre de l'application
     */
    public Invocateur(Fenetre fenetre) {
        this.commandes = new LinkedList<CmdAnnulable>();
        this.itPosCourante = commandes.listIterator(0);
        this.fenetre = fenetre;
    }
    
    
    /**
     * Annulation de la dernière commande exécutée.
     * @throws Exception quand il n'y a plus de commandes à annuler
     */
    public void annuler() throws Exception {
    	if( itPosCourante.hasPrevious() ){
    		CmdAnnulable cmd = itPosCourante.previous();
    		cmd.annuler();
    		actualiserEtatBoutons();
    	} else {
    		throw new IllegalStateException("Erreur: Il ne reste plus de "
    				+ "commande à annuler!");
    	}
    }

    
    /**
     * Réexécution de la dernière commande annulée.
     * @throws Exception quand il n'y a plus de commandes à réexécuter.
     */
    public void reexecuter() throws Exception {
    	
    	if( itPosCourante.hasNext() ){
    		CmdAnnulable cmd = itPosCourante.next();
    		cmd.executer();
    		actualiserEtatBoutons();
    	} else {
    		throw new IllegalStateException("Erreur: Il ne reste plus de "
    				+ "commande a reexecuter!");
    	}
    }

    
    /**
     * Ajout et exécution d'une commande non-annulable.
     * @param commande La commande à rajouter et à exéctuer.
     * @throws Exception quand la commande annulable est nulle,
     * ou quand l'exécution de la commande lance des exceptions.
     */
    public void ajouterCmd(CmdNonAnnulable commande) throws Exception {
    	
    	if( commande == null ){
    		throw new IllegalArgumentException("Erreur : CmdNonAnnulable est null.");
    	} else {
	    	commande.executer();
    	}
    }

    /**
     * Ajout et exécution d'une commande de chargement.
     * @param commande La commande à rajouter et à exéctuer. 
     * @throws Exception quand la commande est nulle,
     * ou quand l'exécution de la commande lance des exceptions.
     */
    public void ajouterCmd(CmdChargement commande) throws Exception {
    	
    	if( commande == null ){
    		throw new IllegalArgumentException("Erreur : CmdChargement est null.");
    	} else {
    	    commandes.clear();
	    	commande.executer();
	    	itPosCourante = commandes.listIterator(0);
	    	actualiserEtatBoutons();
    	}
    }
    
    
    /**
     * Ajout et exécution d'une commande annulable.
     * @param commande La commande à rajouter et à exéctuer.
     * @throws Exception quand la commande annulable est nulle,
     * ou quand l'exécution de la commande lance des exceptions.
     */
    public void ajouterCmd(CmdAnnulable commande) throws Exception {
    	if( commande == null ){
    		throw new IllegalArgumentException("Erreur: CmdAnnulable null.");
    	} else {
    		commande.executer();
	    	while( itPosCourante.hasNext() ){
	        	itPosCourante.next();
	        	itPosCourante.remove();
	    	}
	    	if(commandes.size()>=TAILLE_HISTORIQUE){
	    		commandes.pop();
	    	}
	    	commandes.add(commande);
	    	itPosCourante = commandes.listIterator(commandes.size());
	    	actualiserEtatBoutons();
    	}
    }


    /**
     * Active, ou désactive les bouton "annuler" "rétablir" de la fenetre 
     * suivant l'histoire que des commandes exécutées / annulées
     */
    private void actualiserEtatBoutons(){
    	if( fenetre !=null){
    		fenetre.activerAnnuler(itPosCourante.hasPrevious());
        	fenetre.activerRetablir(itPosCourante.hasNext());
    	}
    
    }
    
    
    /*
    //Public pour les tests
	public ListIterator<CmdAnnulable> getItPosCourante() {
		return itPosCourante;
	}

	public LinkedList<CmdAnnulable> getCommandes() {
		return commandes;
	}*/
	
	
}
