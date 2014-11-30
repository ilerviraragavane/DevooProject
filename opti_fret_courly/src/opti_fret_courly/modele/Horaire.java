package opti_fret_courly.modele;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * La classe Horaire permet de représenter un espace temprel délimité par 
 * une heure de début et une heure de fin. 
 * 
 * @author florentboisselier
 *
 */
public class Horaire implements Comparable<Horaire> {
	
    /**
     * Définit l'heure du début de l'horaire.
     */
    private Calendar debut;

    
    /**
     * Définit l'heure de fin de l'horaire.
     */
    private Calendar fin;

    
    /**
     * Constructeur par défaut de la classe Horaire.
     * Initialise les attributs <code>debut</code> et <code>fin</code> à 
     * l'heure actuelle
     */
    public Horaire() {
        this.debut = new GregorianCalendar();
        this.fin = new GregorianCalendar();
    }
    
    
    /**
     * Constructeur de la classe Horaire
     * @param debut : Définit l'attribut <code>debut</code>, correspondant à 
     * l'heure du début de l'horaire
     * @param fin : Défnit l'attribut <code>fin</code>, correspondant à 
     * l'heure de fin de l'horaire
     */
    public Horaire(Calendar debut, Calendar fin) {
        this.debut = debut;
        this.fin = fin;
    }

    
    /**
     * Getter
     * @return L'heure de début de l'horaire
     */
    public Calendar getDebut() {
        return debut;
    }

    
    /**
     * Getter
     * @return L'heure de fin de l'horaire
     */
    public Calendar getFin() {
        return fin;
    }

    
    /**
     * Formate un calendrier avec son heure uniquement, en tenant compte des 
     * secondes
     * @param heure l'heure a formater
     * @return la chaine contenant l'heure
     */
    public static String formaterHeurePrecise(Calendar heure) {
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
        return formater.format(heure.getTime());
    }
    
    
    /**
     * Formate un calendrier avec son heure uniquement, sans tenir compte des 
     * secondes
     * @param heure l'heure a formater
     * @return la chaine contenant l'heure
     */
    public static String formaterHeureArrondie(Calendar heure) {
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        return formater.format(heure.getTime());
    }
    
    
    /**
     * Permet de définir la date (jour, mois, année) de l'horaire afin que 
     * les comparaisons entre horaires soient cohérentes.
     * @param dateOriginale
     */
    public void completerDate(Calendar dateOriginale){
    	completerDate(dateOriginale,debut);
    	completerDate(dateOriginale,fin);
    }
    
    
    /**
     * Recopie la date d'un calendrier dans un autre
     * @param dateOriginale le calendrier qui contient a date a recopier
     * @param dateIncomplete le calendrier qui contient la date a completer
     */
    private static void completerDate(Calendar dateOriginale, 
    		Calendar dateIncomplete){
    	
    	dateIncomplete.set(Calendar.DAY_OF_MONTH, 
    			dateOriginale.get(Calendar.DAY_OF_MONTH));
    	
    	dateIncomplete.set(Calendar.MONTH, 
    			dateOriginale.get(Calendar.MONTH));
    	
    	dateIncomplete.set(Calendar.YEAR, 
    			dateOriginale.get(Calendar.YEAR));
    }
    
    
    /**
     * Permet de donner une représentation textuelle d'un horaire.
     */
    public String toString() {
        return formaterHeurePrecise(debut) + " - " + formaterHeurePrecise(fin);
    }
    
    
    /**
     * <b>Méthode equals de la classe Horaire permettant de savoir si deux 
     * horaire sont egaux ou non.</b>
     *
     * @return <b>true</b> si l'objet à comparer est une instance de Horaire et 
     * possède le même début et la même fin</br> 
     * <b>false</b> sinon.
     *
     */
    public boolean equals(Object obj){
	    if( obj==null ) 
	    	return false;
	    if(!(obj instanceof Horaire) ){
	    	/* Verfication si obj est bien un horaire */
	    	return false;
	    }
	    Horaire autre = (Horaire) obj;
	    return debut.equals(autre.debut) && fin.equals(autre.fin);
    }

    /**
     * Permet de comparer deux Horaires
     * @param h Horaire avec lequel effectuer la comparaison
     * @return <b>-1</b> si cet horaire est avant l'horaire <code>h</code></br>
     * <b>1</b> si cet horaire est après l'horaire <code>h</code></br>
     * <b>0</b> si cet horaire et l'horaire <code>h</code> s'entrecroisent.
     */
	@Override
	public int compareTo(Horaire h) {
        if (fin.before(h.debut) || fin.equals(h.debut))
            return -1;
        else if (debut.after(h.fin) || debut.equals(h.fin))
            return 1;
        else
            return 0;
	}

}
