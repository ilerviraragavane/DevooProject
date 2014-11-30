package opti_fret_courly.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import opti_fret_courly.modele.Noeud;

/**
 * Cette classe est la vue permettant de représenter 
 * graphiquement un modèle Noeud dans l'application.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public class VueNoeud extends VueEltCliquable {
	/**
	 * Définit le modèle <code>Noeud</code> associé à la vue 
	 * qui sera modélisé graphiquement dans la <code>zone</code>.
	 */
    private Noeud noeudCourant;

    /**
	 * Constructeur de la classe <code>VueNoeud</code>
	 * @param noeudCourant Un noeud qui sera associé à la vue.
	 */
    public VueNoeud(Noeud noeudCourant) {
        this.noeudCourant = noeudCourant;
        this.couleur = Color.BLACK;
    }

    /**
	 * Méthode estClique de <code>VueNoeud</code> permettant de savoir 
	 * si un noeud associé à une vue à été cliqué ou non.
	 * @param point Un point contenant les coordonnée du clic.
	 * @return true si le noeud a été cliqué; false sinon.
	 */
    public boolean estClique(Point point) {
    	Point pointNoeud = new Point(noeudCourant.getX(),noeudCourant.getY());
    	
    	/*
    	 * On regarde si la distance entre le point cliqué 
    	 * et les coordonnées du noeud est dans la 
    	 * limite de la précision donné
    	 * */
    	if (pointNoeud.distance(point) < precision) {
    		this.estSelectionne = true;
    	} else {
    		this.estSelectionne = false;
    	}
    	return this.estSelectionne;
    }

    /**
	 * Setter permettant d'assigner un noeud donnée à la vue courante
	 * @param noeudCourant Un noeud qui sera associé à la vue.
	 */
    public void setNoeudCourant(Noeud noeudCourant) {
        this.noeudCourant = noeudCourant;
    }

    /**
  	 * Méthode dessine de <code>VueNoeud</code> permettant de 
  	 * dessiner graphiquement un noeud sur la vue associé à la zone.
  	 * @param g Un graphics dans lequel sera dessiné le noeud.
  	 * @param echelleX Un facteur d'echelle suivant la coordonnée X.
  	 * @param echelleY Un facteur d'echelle suivant la coordonnée Y.
  	 * @param espacementBord Une valeur associé à l'espacement avec les bords.
  	 */
    public void dessine(Graphics g, double echelleX, double echelleY, 
    													int espacementBord) {
    	/*
		 * On transforme le Graphics initial en Graphics2D afin des 
		 * fonctionnalité tel que l'epaisseur
		 * */
        Graphics2D g2 = (Graphics2D) g;
        
        /*
         * On dessine le noeud suivant son état de 
         * selection pour la couleur
         * */
        if(estSelectionne){
        	g2.setPaint(Color.CYAN);
        } else {
        	g2.setPaint(this.couleur);
        }

        g2.fillOval(
        		(int)((this.noeudCourant.getX() + espacementBord + 1) / 
        											echelleX) - this.taille / 2, 
        		(int)((this.noeudCourant.getY() + espacementBord + 1) / 
        											echelleY) - this.taille / 2, 
        											this.taille, 
        											this.taille);
        g2.drawOval(
        		(int)((this.noeudCourant.getX() + espacementBord + 1) / 
        											echelleX) - this.taille / 2, 
        		(int)((this.noeudCourant.getY() + espacementBord + 1) / 
        											echelleY) - this.taille / 2, 
        											this.taille, 
        											this.taille);
    }
    
    /**
	 * Getter permettant de recuperer l'element  noeud associé à la vue
	 * @return l'element noeud associé à la vue.
	 */
    @Override
    public Noeud getElt() {
        return this.noeudCourant;
    }

    /**
  	 * Méthode equals de <code>VueNoeud</code> permettant de 
  	 * verifier si deux vues ont le même noeud associé
  	 * @param vn Un VueNoeud avec qui cette vue sera comparé
  	 * @return true si les deux vues ont le même noeud; false sinon.
  	 */
    public boolean equals(VueNoeud vn) {
        return vn.getElt().equals(this.noeudCourant);
    }
}
