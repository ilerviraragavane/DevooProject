package opti_fret_courly.modele;

import java.util.HashMap;
import java.util.Map;

/**
 * La classe Zone représente une zone de Lyon arbitrairement découpée, dans 
 * laquelle est effectué une seule tournée par un seul livreur.
 * 
 * @author florentboisselier
 *
 */
public class Zone {
	
	/**
	 * représentant la tournée qui est effectuée dans cette zone de 
	 * Lyon
	 */
    private Tournee tournee;

    
    /**
     * Liste qui contient l'ensemble des noeud de la zone
     */
    private Map<Integer, Noeud> noeuds;


    /**
     * Constructeur par défaut.
     * Il initialise la tournée et l'ensemble des noeuds avec aucun noeud. 
     */
    public Zone() {
        this.noeuds = new HashMap<Integer, Noeud>();
        this.tournee = new Tournee();
    }

    


    
    /**
     * Getter
     * @return retourne la tournée associée à la zone.
     */
    public Tournee getTournee() {
        return this.tournee;
    }

    
    /**
     * Getter
     * @param idNoeud id du noeud à retourner
     * @return le <b>noeud</b> ayant pour id le paramètre idNoeud,</br>
     * <b>null</b> Si aucun noued avec cette id n'est définit, 
     */
    public Noeud getNoeudParId(int idNoeud) {
        return this.noeuds.get(idNoeud);
    }

    
    /**
     * Getter
     * @return l'ensemble des noeuds de la zone.
     */
    public Map<Integer, Noeud> getNoeuds() {
        return this.noeuds;
    }
    

    /**
     * Setter
     * @param noeuds redéfinit l'ensemble des noeuds de la zone.
     */
    public void setNoeuds(Map<Integer, Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    
    /**
     * Setter
     * @param tournee redéfinit la tournée de la zone.
     */
    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
    }
}
