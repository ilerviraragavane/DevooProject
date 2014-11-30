package opti_fret_courly.controleur;

import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.outil.exception.SemantiqueException;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueZone;

/**
 * Cette commande se charge d'ajouter une livraison.
 * @author LIU Chongguang
 */
public class CmdAjout extends CmdAnnulable {
    /**
     * La vue de la zone.
     */
    private VueZone vueZone;
    
    /**
     * Vue décrivant un element sélectionné
     */
    private VueDetailNoeud vueDetailNoeud;

    /**
     * True si cette plage est une nouvelle plage, sinon false.
     */
    private Boolean nouvellePlage;

    /**
     * La plage à laquelle la livraiosn est rajoutée.
     */
    private Plage plage;

    /**
     * La livraiosn à rajoutée.
     */
    private Livraison livraison;
    
    /**
     * Constructeur de la classe.
     * @param vueZone 
     * @param livraison
     * @param plage
     * @param nouvellePlage 
     *                
     */
    public CmdAjout(VueZone vueZone,VueDetailNoeud vueDetailNoeud, 
    		Livraison livraison, Plage plage, Boolean nouvellePlage) {
        this.vueZone = vueZone;
        this.vueDetailNoeud = vueDetailNoeud;
        this.livraison = livraison;
        this.plage = plage;
        this.nouvellePlage = nouvellePlage;
    }

    /**
     * Annulation d'un ajout d'une livraison.
     * @throws IllegalArgumentException si la livraison est null, la plage 
     * 		est null, la vueDetailNoeud est null ou la vueZone est null
     */
    public void annuler() throws IllegalArgumentException {

    	if(livraison==null || plage == null || vueZone == null 
    			|| vueDetailNoeud == null ){
    		throw new IllegalArgumentException("Impossible d'ajouter la "
    				+ "livraison : La plage ou la livraison ou l'une des"
    				+ "vues sont nuls");
    	}
        
    	plage.supprimerLivraison(livraison);
        if (nouvellePlage == true) {
            vueZone.getZone().getTournee().supprimerPlage(plage);
        }
   
    	vueDetailNoeud.setEltCourant(null);
    	vueDetailNoeud.setVisible(false);
    	
    	vueZone.setEltSelectionne(null);
    	vueZone.actualiserVue();
    	vueZone.repaint();

    }

    /**
     * Execution de l'ajout d'une livraiosn.
     * @throws SemantiqueException si la plage ajoutée s'entrecroise avec une
     * 		existante.
     * @throws IllegalArgumentException si la livraison est null, la plage 
     * 		est null, la vueDetailNoeud est null ou la vueZone est null
     */
    public void executer() throws SemantiqueException, 
				IllegalArgumentException {
    	
    	if( livraison == null || plage == null || vueZone == null 
    			|| vueDetailNoeud == null ){
    		throw new IllegalArgumentException("Impossible d'ajouter la "
    				+ "livraison : La plage ou la livraison ou l'une des vues "
    				+ "sont nuls");
    	}
    	
        livraison.setPlageHoraire(plage);
        if( nouvellePlage == true ){
        	plage.ajouterLivraison(livraison);
        	if(!vueZone.getZone().getTournee().ajouterPlage(plage) ){
        		throw new SemantiqueException("La plage ajoutee s'entrecroise "
        				+ "avec une existante");
        	}
        } else {
        	plage.ajouterLivraison(livraison);
        }
      
    	vueDetailNoeud.setEltCourant(livraison.getAdresse());
    	vueDetailNoeud.setVisible(true);
    	vueZone.setEltSelectionne(livraison.getAdresse());
    	vueZone.actualiserVue();
    	vueZone.repaint();
        
        
    }


}
