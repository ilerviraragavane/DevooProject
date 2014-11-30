package opti_fret_courly.outil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderSAX2Factory;

/**
 * Cette classe contient des fonctions outil permettant de lire des fichiers XML et des chaînes formatées
 * @author Elody Catinel
 *
 */
public class Lecteur {


    /**
     * Extrait la racine d'un fichier XML
     * @param fichier le fichier XML a lire
     * @return la racine du fichier
     * @throws IllegalArgumentException
     * @throws JDOMException
     * @throws IOException 
     */
    public static Element lireFichierXML(String fichier) throws JDOMException, IOException,IllegalArgumentException {
        Element racine = null;
        if (fichier != null) {
            XMLReaderSAX2Factory readerFactory = new XMLReaderSAX2Factory(true);
            SAXBuilder builder = new SAXBuilder(readerFactory);
            org.jdom2.Document document = builder.build(fichier);
            racine = document.getRootElement();
        }
        else{
        	throw new IllegalArgumentException("Le fichier est null");
        }
        return racine;
    }

    /**
     * Cree un Objet Calendar a partir d'une chaine contenant une heure (hh:mm)
     * @param heure la chaine contenant l'heure
     * @return l'objet Calendar cree
     * @throws IllegalArgumentException
     */
    public static Calendar extraireHeureArrondie(String heure) throws IllegalArgumentException {
    	final String format = "HH:mm";
    	if( !heure.replaceAll(" ", "").matches("^[0-9]{1,2}:[0-9]{1,2}$")){ /* on ne tolere que les espaces */
            throw new IllegalArgumentException("L'heure  n'a pas pu etre parsee. Elle doit etre de la forme "+format);
    	}
    	return extraireHeure(heure,format);
        
    }
    
    public static Calendar extraireHeurePrecise(String heure) throws IllegalArgumentException {
    	final String format = "HH:mm:0";
    	if( !heure.matches("^[0-9]{1,2}:[0-9]{1,2}:0{1,2}$")){  /* on ne tolere rien */
            throw new IllegalArgumentException("L'heure de debut de la plage n'a pas pu etre parsee. Elle doit etre EXACTEMENT de la forme "+format);
    	}
        return extraireHeure(heure,format); 
     }
    
    /**
     * Cree un Objet Calendar a partir d'une chaine contenant une heure 
     * @param heure la chaine contenant l'heure
     * @param format le format de l'herure
     * @return l'objet Calendar cree
     * @throws IllegalArgumentException
     */
    public static Calendar extraireHeure(String heure,String format) throws IllegalArgumentException {
        Calendar heureCal = new GregorianCalendar();
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            //On essaye de recuperer l'heure pour verifier que c'est bien une heure standard
            heureCal.setTime(formater.parse(heure));

            //Si c'est l'apres-midi am_pm a pm (car Calendar gere les heures entre 0 et 11)
            String[] parties = heure.split(":");
         
            int heuresInt = Integer.parseInt(parties[0]);
            int minutesInt = Integer.parseInt(parties[1]);
            if( heuresInt>23 || minutesInt >59 ){
            	throw new IllegalArgumentException("Les heures doivent etre inferieures a 23.\nLes minutes doivent etre inferieures a 59.");
            }
            /*if (heuresInt >= 12) {
                heureCal.set(Calendar.AM_PM, Calendar.PM);
            }*/
        } catch (ParseException pe) {
            throw new IllegalArgumentException("L'heure n'a pas pu etre parsee. Elle doit etre de la forme "+format,
                                      pe);
        }catch (NumberFormatException nfe){
            throw new NumberFormatException("Les heures ,minutes et secondes doivent etre des nombres sous la forme" +format);
        }
        return heureCal;
    }
    
  
}
