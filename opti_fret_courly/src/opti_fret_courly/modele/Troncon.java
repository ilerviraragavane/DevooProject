package opti_fret_courly.modele;

import java.util.ArrayList;
import java.util.List;

import opti_fret_courly.outil.exception.CommandeException;

/**
 * La classe Troncon représente un tronçon (entre deux intersections) d'une 
 * route.
 * 
 * @author florentboisselier
 *
 */
public class Troncon extends EltCarte {
	
	/**
	 * Décrit la vitesse à laquelle le livreur peut rouler sur le tronçon
	 * en m/s
	 */
	private double vitesse;
	

	/**
	 * Décrit la distance du tronçon en m
	 */
	private double distance;
	
	
	/**
	 * Définit le noeud où le tronçon permet d'aller
	 */
	private Noeud fin;
	
	
	/**
	 * Définti le noeud d'où le tronçon part
	 */
	private Noeud origine;
	
	
	/**
	 * Définit le nom de la rue représentée par le troncon.
	 */
	private String nomRue;
	
	
	/**
	 * Liste contenant tous les chemins auquel appartient le troncon.
	 */
	private List<Chemin> chemins;

	
	/**
	 * Constructeur de la classe Troncon
	 * @param nomRue définit le nom du tronçon
	 * @param vitesse Définit la vitesse du livreur sur le tronçon (m/s)
	 * @param longueur Définit la longueur du tronçon (m)
	 * @param origine Définit le noeud de départ du tronçon
	 * @param fin Définit le noeud où se termine le tronçon
	 */
	public Troncon(String nomRue, double vitesse, double longueur,
			Noeud origine, Noeud fin) {
		this.nomRue = nomRue;
		this.vitesse = vitesse;
		this.distance = longueur;
		this.origine = origine;
		this.fin = fin;
		this.chemins = new ArrayList<Chemin>();
	}

	
	/**
	 * Permet de calculer le temps que met le livreur pour parcourir le tronçon;
	 * @return la durée de parcours
	 * @throws CommandeException si la vitesse est négative ou nulle ou si
	 * la distance est strictement négative.
	 */
	public int calculerDuree() throws CommandeException {
		if( vitesse <= 0 ){
			throw new CommandeException("La vitesse du troncon est négative ou "
					+ "nulle.");
		}
		if( distance < 0 ){
			throw new CommandeException("La longueur du troncon est négative.");
		}
		return (int) ( distance / vitesse );
	}
    
    
    /**
     * <b>Méthode equals de la classe Troncon permettant de savoir si deux 
     * troncons sont egaux ou non.</b>
     *
     * @return <b>true</b> si l'objet à comparer est une instance de Troncon et
     *  possèdant les mêmes valeurs d'attribut (origine et fin);</br> 
     *  <b>false</b> sinon.
     *
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Troncon autre = (Troncon) obj;
		return this.origine.equals(autre.origine)
				&& this.fin.equals(autre.fin);
	}
	
	
	/**
	 * Getter
	 * @return la liste des chemins auquel appartient le troncon
	 */
	public List<Chemin> getChemins(){
		return chemins;
	}

	/**
	 * Getter
	 * @return le noeud où se termine le troncon
	 */
	public Noeud getFin() {
		return fin;
	}

	
	/**
	 * Getter
	 * @return le noeud d'où part le troncon
	 */
	public Noeud getOrigine() {
		return origine;
	}

	/**
	 * Getter
	 * @return le nom du troncon
	 */
	public String getNomRue() {
		return nomRue;
	}

	
	/**
	 * @return une représentation textuelle d'un troncon
	 */
	public String toString() {
		return origine.getId() + "->" + fin.getId() + "(" + nomRue + ")";
	}
	
	
	/**
	 * Permet d'ajouter un chemin à la liste des chemins auquel le trocon 
	 * appartient
	 * @param chemin chemin à ajouter
	 * @return <b>true</b> si le chemin est ajouté à la liste</br>
	 * <b>false</b> si le chemin est null ou que la liste contient déjà le 
	 * chemin.
	 */
	public Boolean ajouterChemin(Chemin chemin){
		if(chemin != null && !this.chemins.contains(chemin) ){
			this.chemins.add(chemin);
			return true;
		}
		return false;
	}

}
