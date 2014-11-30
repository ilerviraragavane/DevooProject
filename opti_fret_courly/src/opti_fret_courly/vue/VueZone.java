package opti_fret_courly.vue;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import opti_fret_courly.controleur.Createur;
import opti_fret_courly.modele.Chemin;
import opti_fret_courly.modele.EltCarte;
import opti_fret_courly.modele.Entrepot;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.modele.Troncon;
import opti_fret_courly.modele.Zone;

/**
 * Cette classe est la vue principale permettant de représenter 
 * graphiquement un modèle Zone dans l'application.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public class VueZone extends JPanel  {

	/**
	 * La hauteur réel de la vue.
	 */
    private int hauteur;

    /**
	 * La largeur réel de la vue.
	 */
    private int largeur;

    /**
	 * Le facteur d'échelle suivant 
	 * la coordonnée X
	 */
    private double echelleX;
    
    /**
	 * Le facteur d'échelle suivant 
	 * la coordonnée Y
	 */
    private double echelleY;
    
    /**
	 * Une valeur donnant un espace 
	 * avec les bords de la vue
	 */
    final private int espacementBord=0;
    
    /**
	 * Une valeur donnant un espace 
	 * avec coordonnées les plus extrèmes
	 */
    final private int espace=20;
    
    /**
	 * Label indiquant les coordonnées 
	 * de la souris
	 */
    private JLabel labelSouris = new JLabel("Souris (0,0)");

    /**
     * Liste des vues non cliquables
     */
    private List<VueEltNonCliquable> vuesNonCliquable;

    /**
     * Liste des vues cliquables
     */
    private List<VueEltCliquable> vuesCliquable;

    /**
	 * L'élement de la carte qui est selectionnée
	 */
    private EltCarte eltSelectionne;
    
    /**
	 * La zone de la carte qui est associé à la vue
	 */
    private Zone zone;

    /**
	 * Le créateur
	 */
    private Createur createur;

    /**
	 * Constructeur de la classe <code>VueZone</code> 
	 * dans lequelle on va initialiser les paramètres de la vue.
	 * @param createur Le créateur.
	 */
    public VueZone(Createur createur) {
    	this.createur=createur;
        this.hauteur = 500;
        this.largeur = 500;
        this.zone = new Zone();
        vuesNonCliquable = new ArrayList<VueEltNonCliquable>();
        vuesCliquable = new ArrayList<VueEltCliquable>();
        eltSelectionne = null;
        /*this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
            	labelSouris.setText("Souris ( " + convertCoordX(e.getX()) 
            									+ " ; " 
            									+ convertCoordY(e.getY()) 
            									+ " )");
            }
        });*/
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                getEltClique(new Point(convertCoordX(e.getX()), 
                					   convertCoordY(e.getY())));
            }
        });
    }

    /**
	 * Getter permettant de recuperer la hauteur réel de la vue
	 * @return la hauteur réel de la vue.
	 */
    public int getHauteur() {
        return this.hauteur;
    }

    /**
	 * Getter permettant de recuperer la largeur réel de la vue
	 * @return la largeur réel de la vue.
	 */
    public int getLargeur() {
        return this.largeur;
    }

    /**
	 * Getter permettant de recuperer le facteur d'échelle suivant X  de la vue
	 * @return le facteur d'echelle X.
	 */
    public double getEchelleX() {
        return this.echelleX;
    }

    /**
	 * Getter permettant de recuperer le facteur d'échelle suivant Y  de la vue
	 * @return le facteur d'echelle Y.
	 */
    public double getEchelleY() {
        return this.echelleY;
    }

    /**
  	 * Méthode de conversion de la vue afin de transformer les 
  	 * coordonnées pixel X en coordonnée réel X.
  	 * @param x la coordonnée en pixel.
  	 */
    public int convertCoordX(int x) {
    	/*
    	 * On utilise un produit en croix pour trouver la relation
    	 * */
    	return (int)((largeur + 1 + espacementBord) * (x - espacementBord) 
    													/ (this.getWidth()));
    }

    /**
  	 * Méthode de conversion de la vue afin de transformer les 
  	 * coordonnées pixel Y en coordonnée réel Y.
  	 * @param y la coordonnée en pixel.
  	 */
    public int convertCoordY(int y) {
    	/*
    	 * On utilise un produit en croix pour trouver la relation
    	 * */
        return (int)((hauteur + 1 + espacementBord) * (y - espacementBord) 
        												/ (this.getHeight()));
    }

    /**
  	 * Méthode de la vue qui recherche l'élement cliqué et qui actualise 
  	 * son état
  	 * @param point Elle correspond au coordonnnées du clic de la souris.
  	 */
    public void getEltClique(Point point) {
    	/*
    	 * On part d'une vue null
    	 * */
    	VueEltCarte vueCliquee = null;
    	
    	/*
    	 * On vérifie que la tournée n'est pas null afin de bloqué le clic
    	 * */
    	if(this.zone.getTournee()!=null){
	    	boolean estClique = false;
	    	
	    	/*
	    	 * On recherche l'élement cliqué dans la liste des élements 
	    	 * cliquables
	    	 * */
	    	for (int i = 0; i < this.vuesCliquable.size(); i++) {
	    		estClique = this.vuesCliquable.get(i).estClique(point);
	    		if (estClique) {
	    			if (vueCliquee == null) {  
	    				vueCliquee = this.vuesCliquable.get(i);
	    			} else if (this.vuesCliquable.get(i) instanceof 
	    													VuePointLivraison) {
	    				vueCliquee = this.vuesCliquable.get(i);
	    			} else if (this.vuesCliquable.get(i) instanceof 
	    													VueEntrepot) {
	    				vueCliquee = this.vuesCliquable.get(i);
	    			}
	    		}
			}
	    	
	    	/*
	    	 * On vérifie l'etat de la vue et on affiche selon le cas de figure
	    	 * */
	    	if(vueCliquee!=null){
				eltSelectionne = vueCliquee.getElt();
	    		createur.dmdSelection(vueCliquee.getElt());
				
			}else{
				eltSelectionne =null;
				createur.dmdSelection(null);
			}
	    	this.repaint();
    	}
    }

    /**
  	 * Méthode paintComponent de la vue chargé de peindre et repeindre la 
  	 * vue à chaque modifications de la zone.
  	 * @param g L'objet Graphics sur laquelle sera dessiné les éléments.
  	 */
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	dessine(g);
    }

    /**
  	 * Méthode dessine de <code>VueZone</code> permettant de 
  	 * dessiner graphiquement les différents élements 
  	 * de la vue associé à la zone.
  	 * @param g Un graphics dans lequel sera dessiné la zone et ses élements.
  	 */
    public void dessine(Graphics g) {
    	
    	/*
    	 * On actualise l'echelle avec les paramètres courant
    	 * */
    	calculEchelle();
    	
    	/*
    	 * On cherche d'abord le nombre de chemin afin de 
    	 * calculer l'intensité de la couleur pour le tracé
    	 * */
    	int taille = 0;
    	for (VueEltNonCliquable vue : this.vuesNonCliquable) {
    		if (vue instanceof VueChemin) {
    			taille++;
    		}
    	}
    	
    	/*
    	 * On commance par dessiner les vues non cliquables
    	 * */
    	int j=0;
    	for (VueEltNonCliquable vue : this.vuesNonCliquable) {
    		
    		/*
        	 * On dessine les vues de chemins avec une instensité afin 
        	 * d'avoir une meilleur suivi du parcours
        	 * */
    		if(vue instanceof VueChemin) {
    			( (VueChemin) vue ).dessine(g, 
    					echelleX, echelleY, espacementBord, 
    					255 - ( j * 200 ) / ( taille - 1 ) );
    			j++;
    		} else {
    			vue.dessine(g, echelleX, echelleY, espacementBord);
    		}
		}
    	
    	/*
    	 * On dessine les vues cliquables
    	 * */
    	for (VueEltCliquable vue : this.vuesCliquable) {
    		vue.dessine(g, echelleX, echelleY, espacementBord);
		}	
    }
    
    /**
  	 * Méthode d'initialisation qui permet de créer 
  	 * les vues à partir de la zone associé à la vue
  	 */
    public void initPlan() {
    	Map<Integer,Noeud> listNoeuds = this.zone.getNoeuds();
    	this.vuesCliquable.clear();
    	this.vuesNonCliquable.clear();
    	for(int i = 0; i < listNoeuds.size(); i++){
    		
    		//ajout de la vue du noeud courant
    		Noeud noeud = listNoeuds.get(i);
    		VueNoeud vueNoeud = new VueNoeud(noeud);
    		this.vuesCliquable.add(vueNoeud);
    		if( noeud.equals(eltSelectionne)){
    			vueNoeud.setSelectionne(true);
    		}
    		
    		List<Troncon> listeTronconsSortant = 
    									listNoeuds.get(i).getTronconsSortants();
    		for(int j=0; j < listeTronconsSortant.size(); j++){
    			int idSortie = listeTronconsSortant.get(j).getFin().getId();
    			/*	si idA est inferieur a idB on trace le troncon 
    			 * (pour pas avoir de doublon)
    			 * */
    			if (i < idSortie){
    				this.vuesNonCliquable.add(
    							new VueTroncon(listeTronconsSortant.get(j)));
    			}
    		}
    	}
    }
   
    /**
	 * Getter permettant de recuperer le label des coordonnées de la souris
	 * @return labelSouris Le label des coordonnées de la souris.
	 */
    public JLabel getLabelSouris(){
    	return labelSouris;
    }

    /**
	 * Setter permettant de fixer l'hauteur de la vue
	 * @param hauteur La hauteur qui sera associé à la vue
	 */
    public void setHauteur(int hauteur) {
        this.hauteur = hauteur + espace;
    }
    
    /**
	 * Setter permettant de fixer la largeur de la vue
	 * @param largeur La largeur qui sera associé à la vue
	 */
    public void setLargeur(int largeur) {
        this.largeur = largeur + espace;
    }
    
    /**
	 * Méthode permettant de calculer la nouvelle 
	 * echelle suivant X et Y afin de convertir les mesures 
	 * pixels en mesures réels.
	 */
    public void calculEchelle(){
        this.echelleX = (((double)this.largeur + 1 + espacementBord) 
        										/ ((double)this.getWidth()));
        this.echelleY = (((double)this.hauteur + 1 + espacementBord) 
        										/ ((double)this.getHeight()));
    }

    /**
	 * Setter permettant d'assigner un element selectionné à la vue
	 * @param eltSelectionne L'element selectionné qui sera assigné à la zone
	 */
    public void setEltSelectionne(EltCarte eltSelectionne) {
        this.eltSelectionne = eltSelectionne;
    }

    /**
	 * Méthode permettant de d'actualiser les vues courantes de la 
	 * vue zone afin d'appliquer les modifications.
	 */
	public void actualiserVue() {
		this.vuesCliquable.clear();
		this.vuesNonCliquable.clear();
		
		Map<Integer,Noeud> listNoeuds = this.zone.getNoeuds();
		Map<Integer,PointDeLivraison> listLivraison = 
										new HashMap<Integer,PointDeLivraison>();
		Map<Integer,Chemin> listChemin = new HashMap<Integer,Chemin>();
		
		initPlan();
		
		List<Chemin> listeChemins = this.zone.getTournee().getChemins();
		for(Chemin chemin : listeChemins){
			VueChemin vue = new VueChemin(chemin);
			this.vuesNonCliquable.add(vue);
		}
		
		int k=0;
		List<Plage> lp = this.zone.getTournee().getPlagesHoraires();
		
		for(int i=0; i < lp.size(); i++){
			for(int j=0; j < lp.get(i).getLivraisons().size(); j++){
				listLivraison.put(k, 
								lp.get(i).getLivraisons().get(j).getAdresse());
				k++;
			}
		}
		/* Vue entrepot */
		Entrepot entrepot = this.zone.getTournee().getEntrepot();
		VueEntrepot vueEntrepot = new VueEntrepot(entrepot);
    	this.vuesCliquable.add(vueEntrepot);
    	if(entrepot.equals(eltSelectionne)){
    		vueEntrepot.setSelectionne(true);
		}
    	
    	
    	for(int i =0; i<listLivraison.size(); i++){
    		PointDeLivraison pt = listLivraison.get(i);
    		VuePointLivraison vuePoint = new VuePointLivraison(pt);
    		//ajout de la vue du point de livraison courant
    		this.vuesCliquable.add(vuePoint);
    		if(pt.equals(eltSelectionne)){
    			vuePoint.setSelectionne(true);
    		}
    	}
	}
	
	/**
	 * Getter permettant de recuperer la zone associé à la vue
	 * @return la zone associé à la vue.
	 */
	public Zone getZone(){
		return this.zone;
	}
}
