package opti_fret_courly.modele;

/**
 * La classe Entrepot représente l'entrepot d'où débute et se termine la 
 * tournée.
 * 
 * @author florentboisselier
 *
 */
public class Entrepot extends PointDArret {

	/**
	 * définit le créneaux où le livrueur est parti de 
	 * l'entrepot</br>
	 * <u>heure de départ :</u> heure à laquelle il commence ça tournée,</br>
	 * <u>heure d'arrivée :</u> Heure à laquelle il termine sa tournée
	 */
	private Horaire heuresPassage;
	
	/**
	 * Permet de savoir si l'entrepot à été planifié
	 */
	private boolean estPlanifiee;
	
	
	/**
	 * Constructeur de la classe Entrepot
	 * @param lieu Noeud définissant l'emplassement de l'entrepot.
	 */
    public Entrepot(Noeud lieu) {
        super(lieu);
        heuresPassage = new Horaire();
        this.estPlanifiee = false;
    }

    
    /**
     * @return une représentation textuelle d'un entrepot.
     */
    public String toString() {
        return "Entrepot a l'adresse " + lieu.toString();
    }

    
    /**
     * Getter
     * @return le créneau où le livreur est parti de l'entrepot.
     */
	public Horaire getHeuresPassage() {
		return heuresPassage;
	}

	
	/**
	 * Setter
	 * @param heuresPassage redéfinit le créneau où le livreur est parti de
	 * l'entrepot.
	 */
	public void setHeuresPassage(Horaire heuresPassage) {
		this.heuresPassage = heuresPassage;
	}


	public boolean estPlanifiee() {
		return estPlanifiee;
	}


	public void setEstPlanifiee(boolean estPlanifiee) {
		this.estPlanifiee = estPlanifiee;
	}
    
    

}
