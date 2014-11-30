package opti_fret_courly.modele;

/**
 * La lcase Livraison représente une livraison que le livreur a à effectuer.
 * 
 * @author florentboisselier
 *
 */
public class Livraison {
    /**
     * Permet de définir l'état de la livraison
     */
    private EtatLivraison etat;
    
    
    /**
     * définit le destinataire du colis
     */
    private Destinataire destinataire;
    
    
    /**
     * définit les heure à laquelle le livreur doit effectuer la livraison 
     */
    private Horaire heuresPassage;
    
    
    /**
     * Définit le lieu où le livreur doit effectuer la livraison.
     */
    private PointDeLivraison adresse;
    
    
    /**
     * Définit la plage dans laquelle se trouve la livraison.
     */
    private Plage plageHoraire;


    /**
     * Constructeur de la classe Livraison
     * @param destinataire Définit le destinataire du colis
     * @param adresse Définit l'adresse de livraison
     */
    public Livraison(Destinataire destinataire, PointDeLivraison adresse) {
        this.destinataire = destinataire;
        this.adresse = adresse;
        this.etat = EtatLivraison.NOUVELLE;
        this.heuresPassage = new Horaire();
    }
    

    /**
     * Getter
     * @return 	les horaires de passages
     */
    public Horaire getHeuresPassage(){
    	return heuresPassage;
    }
    
    
    /**
     * Getter
     * @return le destinataire du colis
     */
    public Destinataire getDestinataire() {
        return destinataire;
    }

    
    /**
     * Getter
     * @return le point de livraison
     */
    public PointDeLivraison getAdresse() {
        return adresse;
    }

    
    /**
     * Getter
     * @return la plage horaire dans laquelle se situe la livraison
     */
    public Plage getPlageHoraire() {
        return plageHoraire;
    }
    
    
    /**
	 * Getter
	 * @return retourne l'état de la livraison
	 */
	public EtatLivraison getEtat() {
		return this.etat;
	}

    
    /**
     * Setter
     * @param plageHoraire Définit la plage horaire dans laquelle se situe la 
     * livraison
     */
    public void setPlageHoraire(Plage plageHoraire) {
        this.plageHoraire = plageHoraire;
    }
    
    
    /**
     * Setter
     * @param heuresPassage Redéfinit les horaires de passages du livreur
     */
    public void setHeuresPassage(Horaire heuresPassage){
    	this.heuresPassage = heuresPassage;
    }
	
	
	/**
	 * Permet de mettre à jour l'état de la livraison en fonction de sa plage
	 * et de ses horaires de passage.
	 */
	public void mettreAJourEtat() {
		if( this.heuresPassage.getFin().compareTo(
    			this.plageHoraire.getCreneau().getFin()) <= 0 ){
			this.etat = EtatLivraison.A_L_HEURE;		
		} else {
			this.etat = EtatLivraison.EN_RETARD;
		}
	}
    
	
    /**
     * @return une description textuelle d'une livraison
     */
	public String toString() {
        return "Livraison " + "pour le destinataire " + destinataire.getId() + 
        		" a l'adresse " + adresse;
    }
    
}
