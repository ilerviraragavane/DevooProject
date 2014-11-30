package opti_fret_courly.modele;

/**
 * La classe PointDArret représente les endroits où le livreur va s'arreter.
 * 
 * @author florentboisselier
 *
 */
public abstract class PointDArret extends EltCarte {
	
	/**
	 * Définit le lieu où le livrueur s'arrête
	 */
    protected Noeud lieu;

    /**
     * Constructeur de la classe PointDArret
     * @param lieu : définit le lieu d'arret
     */
    public PointDArret(Noeud lieu) {
        this.lieu = lieu;
    }

    
    /**
     * Getter
     * @return Le lieu d'arret du livreur
     */
    public Noeud getLieu() {
        return this.lieu;
    }
}
