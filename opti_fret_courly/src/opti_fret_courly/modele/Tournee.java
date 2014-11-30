package opti_fret_courly.modele;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * La classe Tournee représente la tournée optimale qui doit être effectuée 
 * par le livreur.
 * 
 * @author florentboisselier
 *
 */
public class Tournee {

	/**
	 * Définit l'entrepot de la tournée, cela correspond au point 
	 * de départ et d'arrivée de celle-ci.
	 */
    private Entrepot entrepot;

    
    /**
     * @atribut Contient l'ensemble des plages horaires dans lesquelles il faut
     * livrer les colis.
     */
    private List<Plage> plagesHoraires;

    
    /**
     * Contient l'ensemble des chemins formant la tournée
     */
    private List<Chemin> chemins;
    
    
    /**
     * Contient la date pour laquelle on planifie la tournée
     */
    private Calendar date;
    

    /**
     * Constructeur de la classe Tournee.
     * Initialise les listes de plageshoraires et Chemins,
     * et calcule la date pour laquelle on planifie la tournée. 
     */
    public Tournee() {
        this.plagesHoraires = new ArrayList<Plage>();
        this.chemins = new ArrayList<Chemin>();
        
        //On initialise la date de la tournee a la date du lendemain de son calcul
        this.date = Calendar.getInstance();
        this.date.add(Calendar.DAY_OF_MONTH, 1);
        if( this.date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ){
        	this.date.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    

    /**
     * Permet d'ajouter un chemin à la liste des chemins
     * @param ch chemin à ajouter
     * @return <b>true</b> si le chemin a été ajouté</br>
     * <b>false</b> si le chemin est null ou est déjà dans la liste
     */
    public Boolean ajouterChemin(Chemin ch) {
        List<Chemin> lc = this.getChemins();
        if (!lc.contains(ch) && ch != null) {
            lc.add(ch);
            return true;
        }
        return false;
    }

    
    /**
     * Permet de supprimer un chemin de la liste des chemins 
     * @param ch chemin à supprimer
     * @return <b>true</b> si le chemin a été supprimé</br>
     * <b>false</b> si le chemin est null ou n'est pas dans la liste
     */
    public Boolean supprimerChemin(Chemin ch) {
        if (this.getChemins().contains(ch) && ch != null) {
        	this.getChemins().remove(ch);
            return true;
        }
        return false;
    }

    /**
     * Permet d'ajouter une plage à la liste des plages horaires
     * @param plageHoraire plage à ajouter
     * @return <b>true</b> si la plage a été ajoutée</br>
     * <b>false</b> si la plage est null ou est déjà dans la liste
     */
    public Boolean ajouterPlage(Plage plageHoraire) {
    	Boolean ajouter = false;
    	if(plageHoraire!=null){
    		ajouter = true;
        	for (int i=0; i<plagesHoraires.size() && ajouter ; i++){
        		if(plagesHoraires.get(i).entrecroise(plageHoraire)){
        			ajouter = false;
        		}
        	}
    	}
    	if(ajouter)
    		plagesHoraires.add(plageHoraire);
    	return ajouter;
    }


    /**
     * Permet de supprimer une plage de la liste des plages horaires
     * @param plageHoraire plage à supprimer
     * @return <b>true si la plage a été supprimée</br>
     * <b>false</b> si la plage est null ou qu'elle n'est pas dans la liste
     */
    public Boolean supprimerPlage(Plage plageHoraire) {
        Boolean resultat = false;
        if (plagesHoraires.contains(plageHoraire) && plageHoraire != null) {
            plagesHoraires.remove(plageHoraire);
            resultat = true;
        }
        return resultat;
    }

    
    /**
     * Setter
     * @param entrepot redéfinit l'entrepot de la tournée.
     */
    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }
    

    /**
     * Getter
     * @return la liste des plages horaires.
     */
    public List<Plage> getPlagesHoraires() {
        return plagesHoraires;
    }

    
    /**
     * Getter
     * @return l'entrepot
     */
    public Entrepot getEntrepot() {
        return this.entrepot;
    }

    
    /**
     * Getter
     * @return la liste des chemins compostant la tournée optimale
     */
	public List<Chemin> getChemins() {
		return chemins;
	}
	
	
	/**
	 * Getter
	 * @return la date pour laquelle la tournée est planifiée
	 */
	public Calendar getDate(){
		return date;
	}
	

    /**
     * @return une description textuelle d'une tournée.
     */
    public String toString() {
        String description = entrepot + "\n";
        for (int i = 0; i < plagesHoraires.size(); i++) {
            description += plagesHoraires.get(i).afficherDetailPlage();
        }
        return description;
    }
    
	
	/**
	 * Permet de savoir si la tournee contient des livraisons a effectuer
	 * @return true si il y des livraisons a effectuer et false sinon
	 */
	public boolean aDesPlages(){
		return !plagesHoraires.isEmpty();
	}
}
