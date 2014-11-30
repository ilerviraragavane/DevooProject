package opti_fret_courly.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * La lcasse Chemin représente un chemin permettant au livreur d'aller d'un 
 * point A à un point B de manière optimale.
 * 
 * @author florentboisselier
 *
 */
public class Chemin extends EltCarte {

    /**
     * Contient la liste des troncon que le chemin emprunte.
     */
    private List<Troncon> troncons;
    
    
    /**
     * Point de départ du chemin
     */
    private PointDArret pointDep;
    
    
    /**
     * Point d'arrivée du chemin
     */
    private PointDArret pointArr;

    
    /**
     * Constructeur par défaut de la classe Chemin.
     * Initialise tous les attributs : </br> 
     * 	- troncons : liste vide</br>
     * 	- pointDep : null</br>
     * 	- PointArr : null
     */
    public Chemin() {
        troncons = new ArrayList<Troncon>();
        pointArr = null;
        pointDep = null;
    }

    
    /**
     * Permet d'ajouter un tronçon à la liste des tronçons
     * @param troncon tronçon à ajouter
     * @return <b>true</b> si le tronçon a bien été ajouté,</br>
     * <b>false</b> si le tronçon est null ou qu'il est déjà dans la liste des 
     * tronçons 
     */
    public Boolean ajouterTroncon(Troncon troncon) {
        Boolean resultat = false;
        if (troncon != null && !this.troncons.contains(troncon)) {
            this.troncons.add(troncon);
            resultat = true;
        }
        return resultat;
    }


    /**
     * Getter
     * @return la liste des tronçons.
     */
    public List<Troncon> getTroncons() {
        return troncons;
    }

    
    /**
     * Getter
     * @return le point de départ du chemin
     */
    public PointDArret getPointDep() {
        return pointDep;
    }

    
    /**
     * Getter
     * @return le point d'arrivée du chemin
     */
    public PointDArret getPointArr() {
        return pointArr;
    }

    
    /**
     * Setter
     * @param pointDep redéfinit le point de départ du chemin
     */
    public void setPointDep(PointDArret pointDep) {
        this.pointDep = pointDep;
    }

    
    /**
     * Setter
     * @param pointArr redéfinit le point d'arrivée du chemin
     */
    public void setPointArr(PointDArret pointArr) {
        this.pointArr = pointArr;
    }

    
    /**
     * @return une représentation textuelle du chemin
     */
    public String toString(){
    	String description = "Chemin de "+pointDep+ " a "+pointArr + " :\n";
    	for(Troncon troncon:troncons){
    		description += troncon.toString();
    	}
    	return description;
    }
    
   
    /**
     * <b>Méthode equals de la classe Chemin permettant de savoir si deux 
     * chemins sont egaux ou non.</b>
     *
     * @return <b>true</b> si l'objet à comparer est une instance de Chemin
     *  et possédant les mêmes valeurs;</br> 
     *  <b>false</b> sinon.
     */
	@Override
	public boolean equals(Object obj) {
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if(!(obj instanceof Chemin) )
			return false;
		Chemin other = (Chemin) obj;
		return other.getTroncons().equals(this.getTroncons());
	}
}
