package opti_fret_courly.modele;

/**
 * La classe PointDeLivraison représente les endroits où le livreur doit 
 * effectuer ses livraisons.
 * 
 * @author florentboisselier
 *
 */
public class PointDeLivraison extends PointDArret {

	/**
	 * Définit la livraison qui est associée à ce point de livraison
	 */
    private Livraison livraison;
    
    
    /**
     * Constructeur de la classe PointDeLivraison
     * Définit l'attribut livraison à null
     * @param lieu lieu de livraison
     */
    public PointDeLivraison(Noeud lieu) {
        super(lieu);
        this.livraison = null;
    }

    /**
     * Getter
     * @return la livraison associée au point de livraison
     */
    public Livraison getLivraison() {
        return livraison;
    }

    
    /**
     * Setter
     * @param livraison redéfinit la livraison associée au point de livraison
     */
    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    
    /**
     * @return une représentation textuelle d'un PointDeLivraison
     */
    public String toString() {
        return lieu.toString();
    }


}
