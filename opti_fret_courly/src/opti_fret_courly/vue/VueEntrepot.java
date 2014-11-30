package opti_fret_courly.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import opti_fret_courly.modele.Entrepot;

/**
 * Cette classe est la vue permettant de représenter 
 * graphiquement un modèle Entrepot dans l'application.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public class VueEntrepot extends VueEltCliquable {
	/**
	 * Définit le modèle <code>Entrepot</code> associé à la vue 
	 * qui sera modélisé graphiquement dans la <code>zone</code>.
	 */
    private Entrepot entrepotCourant;

    /**
	 * Constructeur de la classe <code>VueEntrepot</code>
	 * @param entrepotCourant Un entrepot qui sera associé à la vue.
	 */
    public VueEntrepot(Entrepot entrepotCourant) {
        this.entrepotCourant = entrepotCourant;
        this.couleur = new Color(128, 0, 128);
    }

    /**
	 * Setter permettant d'assigner un entrepot donnée à la vue courante
	 * @param entrepotCourant Un entrepot qui sera associé à la vue.
	 */
    public void setEntrepotCourant(Entrepot entrepotCourant) {
        this.entrepotCourant = entrepotCourant;
    }

    /**
	 * Méthode estClique de <code>VueEntrepot</code> permettant de savoir 
	 * si un entrepot associé à une vue à été cliqué ou non.
	 * @param point Un point contenant les coordonnée du clic.
	 * @return true si l'entrepot a été cliqué; false sinon.
	 */
    public boolean estClique(Point point) {
    	Point pointEntrepot = new Point(entrepotCourant.getLieu().getX(), 
    									entrepotCourant.getLieu().getY());
    	
    	/*
    	 * On regarde si la distance entre le point cliqué 
    	 * et les coordonnées de l'entrepot est dans la 
    	 * limite de la précision donné
    	 * */
    	if (pointEntrepot.distance(point) < precision) {
    		this.estSelectionne = true;
    	} else {
    		this.estSelectionne = false;
    	}
    	return this.estSelectionne;
    }

    /**
	 * Méthode dessine de <code>VueEntrepot</code> permettant de 
	 * dessiner graphiquement un entrepot sur la vue associé à la zone.
	 * @param g Un graphics dans lequel sera dessiné l'entrepot.
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
         * On fixe une nouvelle taille pour le cercle superieur
         * */
        int tailleCercleSup = this.taille+4;
        g2.setPaint(this.couleur);

        g2.fillOval(
        		(int)((this.entrepotCourant.getLieu().getX() + 
        				espacementBord + 1) / echelleX) - tailleCercleSup / 2, 
        		(int)((this.entrepotCourant.getLieu().getY() + 
        				espacementBord + 1) / echelleY) - tailleCercleSup / 2, 
        				tailleCercleSup, 
        				tailleCercleSup);
        g2.drawOval(
        		(int)((this.entrepotCourant.getLieu().getX() + 
        				espacementBord + 1) / echelleX) - tailleCercleSup / 2, 
        		(int)((this.entrepotCourant.getLieu().getY() + 
        				espacementBord + 1) / echelleY) - tailleCercleSup / 2, 
        				tailleCercleSup, 
        				tailleCercleSup);

        /*
         * On dessine l'entrepot suivant son état de 
         * selection pour la couleur
         * */
        if (estSelectionne) {
        	g2.setPaint(Color.CYAN);
        } else {
        	g2.setPaint(Color.WHITE);
        }
        
        g2.fillOval(
        		(int)((this.entrepotCourant.getLieu().getX() + 
        				espacementBord + 1) / echelleX) - this.taille / 2, 
        		(int)((this.entrepotCourant.getLieu().getY() + 
        				espacementBord + 1) / echelleY) - this.taille / 2, 
        				this.taille, 
        				this.taille);
        g2.drawOval(
        		(int)((this.entrepotCourant.getLieu().getX() + 
        				espacementBord + 1) / echelleX) - this.taille / 2, 
        		(int)((this.entrepotCourant.getLieu().getY() + 
        				espacementBord + 1) / echelleY) - this.taille / 2, 
        				this.taille, 
        				this.taille);

    }

    /**
	 * Getter permettant de recuperer le entrepot associé à la vue
	 * @return entrepotCourant le entrepot associé à la vue.
	 */
    @Override
    public Entrepot getElt() {
        return this.entrepotCourant;
    }

}
