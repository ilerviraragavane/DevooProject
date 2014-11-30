package opti_fret_courly.modele;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * La classe Plage représente une plage horaire dans laquelle doit être 
 * effectué une série de livraisons.
 * 
 * @author florentboisselier
 *
 */
public class Plage implements Comparable<Plage> {

	/**
	 * Définit les bornes de la plage horaire
	 */
    private Horaire creneau;
    
    
    /**
     * Contient la liste des livraisons a effectuer durant cette 
     * plage horaire
     */
    private List<Livraison> livraisons;


    /**
     * Constructeur de la classe Plage
     * @param horaire : Définit l'attribut creneau délimitant de début et la 
     * fin de la plage horaire
     */
    public Plage(Horaire horaire) {
        this.creneau = horaire;
        this.livraisons = new ArrayList<Livraison>();
    }
    

    /**
     * Permet d'ajouter une livraison à la plage
     * @param livraison : Livraison à ajouter
     * @return <b>true</b> si la livriason a été ajoutée à la plage</br>
     * <b>false</b> si la livraison était déjà dans la liste ou qu'elle n'a pas 
     * pue être ajoutée.
     */
    public Boolean ajouterLivraison(Livraison livraison) {

        if (livraison != null) {
            livraisons.add(livraison);
            return true;
        }
        return false;
    }
    

    /**
     * Permet de supprimer une livraison
     * @param livraison : Livraison à supprimer
     * @return <b>true</b> si la livriason a été suppréiée de la plage ; 
     * <b>false</b> si la livraison n'était pas dans la liste ou qu'elle n'a 
     * pas pue être supprimée.
     */
    public Boolean supprimerLivraison(Livraison livraison) {

        Boolean resultat = false;
        if (livraison != null && livraisons.contains(livraison)) {
            resultat = livraisons.remove(livraison);
        }
        return resultat;
    }
    

    /**
     * Permet de tester si deux plages s'entrecroisent.
     * @param autrePlage : Plager avec laquelle faire ce test
     * @return <b>true</b> si les plages s'entrecroisent,</br> 
     * <b>false</b> sinon.
     */
    public Boolean entrecroise(Plage autrePlage) {

        Boolean resultat = true;

        Calendar autreDebut = autrePlage.getCreneau().getDebut();
        Calendar autreFin = autrePlage.getCreneau().getFin();
        Calendar debut = creneau.getDebut();
        Calendar fin = creneau.getFin();

       
        if (autreDebut.after(fin) || autreDebut.equals(fin))
            resultat = false;
        else if (autreFin.before(debut) || autreFin.equals(debut))
            resultat = false;
        else if (debut.after(autreFin) || debut.equals(autreFin))
            resultat = false;
        else if (fin.before(autreDebut) || fin.equals(autreDebut))
            resultat = false;

        return resultat;
    }
    

    /**
     * @return <b>true</b> si la plage ne contient pas de livraison.
     */
    public Boolean estVide() {
        return livraisons.isEmpty();
    }


	/**
	 * @return une représentation textuelle d'une plage
	 */
    public String toString() {
        return "Plage : " + Horaire.formaterHeurePrecise(creneau.getDebut()) + 
        		"- " + 
        		Horaire.formaterHeurePrecise(creneau.getFin()) +
               "\n";
    }

    
    /**
     * Premet d'afficher la plage de manière détaillée.
     * @return une chaine de caractère décrivant la plage
     */
    public String afficherDetailPlage() {
        String description = this.toString();
        for (int i = 0; i < livraisons.size(); i++) {
            description += "\t" + livraisons.get(i) + "\n";
        }
        return description;
    }
    

    /**
     * Getter
     * @return retourne l'attribut <code>livraisons</code> qui contient la 
     * liste des livraisons de la plage.
     */
    public List<Livraison> getLivraisons() {
        return livraisons;
    }

    
    /**
     * Getter
     * @return retourne l'attribut creneau de type Horaire. 
     */
    public Horaire getCreneau() {
        return creneau;
    }
    
    
    /**
     * <b>Méthode equals de la classe Plage permettant de savoir si deux 
     * plage sont egales ou non.</b>
     *
     * @return <b>true</b> si l'objet à comparer est une instance de Plage et 
     * possédant les mêmes valeurs d'horaire;</br> 
     *  <b>false</b> sinon.
     *
     */
    public boolean equals(Object obj){
	    if( obj == null ) 
	    	return false;
	    if(!(obj instanceof Plage) ){ 
	    	/* Verfication si obj est bien un plage */
	    	return false; 
    	}
	    Plage autre = (Plage) obj;
	    return creneau.equals(autre.creneau);
    }

    /**
     * Permet de comparer deux plages.
     * On se base sur la comparaison des créneaux.
     */
	@Override
	public int compareTo(Plage o) {
		return this.creneau.compareTo(o.creneau);
	}

}
