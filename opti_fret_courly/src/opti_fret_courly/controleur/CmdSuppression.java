package opti_fret_courly.controleur;

import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.outil.exception.CommandeException;
import opti_fret_courly.outil.exception.SemantiqueException;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueZone;

/**
 * Cette commande se charge de supprimer une livraison.
 * @author LIU Chongguang
 */
public class CmdSuppression extends CmdAnnulable {
    /**
     * True si la plage correspondante est supprimée, sinon false.
     */
    private Boolean plageSupprimee;

    /**
     * La vue de la zone.
     */
    private VueZone vueZone;
    
    /**
     * La vue de affichant les détails sur un noeud ou une livraison
     */
    private VueDetailNoeud vueDetailNoeud;

    /**
     * La livraison à supprimer
     */
    private Livraison livraison;

    /**
     * Constructeur de la classe.
     * @param vueZone 
     * @param livraison    
     *                
     */
    public CmdSuppression(VueZone vueZone, VueDetailNoeud vueDetailNoeud, 
    		Livraison livraison) {
        this.vueZone = vueZone;
        this.vueDetailNoeud = vueDetailNoeud;
        this.livraison = livraison;
        this.plageSupprimee = false;
    }

    /**
     * Execution de la suppression d'une livraiosn.
     * @throws IllegalArgumentException si la livraison est null, 
     * la vueDetailNoeud est null ou la vueZone est null
     * @throws CommandeException si la livraison n'est pas dans la plage horaire
     */
    public void executer() throws CommandeException, IllegalArgumentException {

    		if( livraison == null ){
    			throw new IllegalArgumentException("Impossible de supprimer "
    					+ "la livraison : La livraison a supprimer est nulle");
    		}
    		if( vueDetailNoeud == null || vueZone== null ){
    			throw new IllegalArgumentException("Impossible de supprimer "
    					+ "la livraison : L'une des vues est nulle");

    		}
    		
            Plage p = livraison.getPlageHoraire();
            if( p.supprimerLivraison(livraison) == true ){
                if( p.estVide() ){
                	vueZone.getZone().getTournee().supprimerPlage(p);
                    plageSupprimee = true;
                }
            } else {
            	 throw new CommandeException("Impossible de supprimer la "
            	 		+ "livraison n'est pas dans la plage horaire");
            }
     
        	vueDetailNoeud.setEltCourant(null);
        	vueDetailNoeud.setVisible(false);
        	
        	vueZone.setEltSelectionne(null);
        	vueZone.actualiserVue();
        	vueZone.repaint();
       
        
    }

    /**
     * Annulation de la suppression d'une livraison.
     * @throws IllegalArgumentException si la livraison est null, 
     * la vueDetailNoeud est null ou la vueZone est null
     * @throws SemantiqueException si la plage ajoutée s'entrecroise avec une
     * existante.
     */
    public void annuler() throws IllegalArgumentException,SemantiqueException {

    	if( livraison == null ){
			throw new IllegalArgumentException("Impossible de rétablir la "
					+ "livraison : La livraison a supprimer est nulle");
		}
		if(vueDetailNoeud == null || vueZone == null ){
			throw new IllegalArgumentException("Impossible de rétablir la "
					+ "livraison : L'une des vues est nulle");

		}

        if (plageSupprimee == true) {
        	if(!vueZone.getZone().getTournee().ajouterPlage(
        			livraison.getPlageHoraire())){
        		throw new SemantiqueException("La plage ajoutee s'entrecroise "
        				+ "avec une existante");
        	}
            plageSupprimee = false;
        }

        livraison.getPlageHoraire().ajouterLivraison(livraison);
  
    	vueDetailNoeud.setEltCourant(livraison.getAdresse());
    	vueDetailNoeud.setVisible(true);
    	
    	vueZone.setEltSelectionne(livraison.getAdresse());
    	vueZone.actualiserVue();
    	vueZone.repaint();
       

    }
}
