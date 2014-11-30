package opti_fret_courly.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Noeud représente un intersection de au moins deux troncons.
 * 
 * @author florentboisselier
 *
 */
public class Noeud extends EltCarte {
	
    /**
     * abscisse du noeud
     */
    private int x;

    /**
     * ordonnée du noeud
     */
    private int y;

    /**
     * id du noeud
     */
    private int id;

    /**
     * Liste des troncons dont ce noeud est leur origine
     */
    private List<Troncon> tronconsSortants;


    /**
     * Constructeur par défaut
     * @param id définit l'id du noeud
     * @param x définit l'abscisse du noeud
     * @param y définit l'ordonnée du noeud
     */
    public Noeud(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
        tronconsSortants = new ArrayList<Troncon>();
    }


    /**
     * Getter
     * @return l'id du noeud
     */
    public int getId() {
        return this.id;
    }

    
    /**
     * Getter
     * @return l'abscisse du noeud
     */
    public int getX() {
        return this.x;
    }


    /**
     * Getter
     * @return l'ordonnée du noeud
     */
    public int getY() {
        return this.y;
    }

    
    /**
     * Getter
     * @return retourne la liste des troncons sortants
     */
    public List<Troncon> getTronconsSortants() {
        return tronconsSortants;
    }

    
    /**
     * Getter
     * @param destination noeud vers lequels on veut aller
     * @return le troncon dont le paramètre destinataire est la fin du troncon
     */
    public Troncon getTronconVers(Noeud destination) {
        for (Troncon t : this.getTronconsSortants()) {
            if (t.getFin().equals(destination)) {
                return t;
            }
        }
        return null;
    }

    
    /**
     * Permet d'ajouter un troncon
     * @param troncon troncon à ajouter
     * @return <b>true</b> si le troncon a été ajouté
     * <b>false</b> si le troncon est null, ou que la liste contient déjà ce 
     * troncon, ou que le troncon boucle sur un noeud, ou que le troncon n'a 
     * pas comme origine le bon noeud
     */
    public Boolean ajouterTroncon(Troncon troncon) {
        if (troncon!=null && troncon.getOrigine().equals(this) 
        		&& !tronconsSortants.contains(troncon)
        		&& !troncon.getFin().equals(this)) {
            tronconsSortants.add(troncon);
            return true;
        }
        return false;
    }
    
    
    /**
     * <b>Méthode equals de la classe Noeud permettant de savoir si deux 
     * Noeud sont egaux ou non.</b>
     *
     * @return <b>true</b> si l'objet à comparer est une instance de Noeud et 
     * possédant les mêmes valeurs d'attribut (id, x, y);</br> 
     *  <b>false</b> sinon.
     *
     */
    public boolean equals(Object n) {
        if (n instanceof Noeud)
            return this.id == ((Noeud) n).getId() 
	            && this.x == ((Noeud) n).getX() 
	            && this.y == ((Noeud) n).getY();
        else
            return false;
    }
    

    /**
     * @return une représentation textuelle d'un noeud.
     */
    public String toString() {
        return id + "(" + x + " ," + y + ")";
    }

}
