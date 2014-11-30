package opti_fret_courly.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import opti_fret_courly.modele.EtatLivraison;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.PointDeLivraison;

/**
 * Cette classe est la vue permettant de représenter 
 * graphiquement un modèle PointDeLivraison dans l'application.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public class VuePointLivraison extends VueEltCliquable {
	/**
	 * Définit le modèle <code>PointDeLivraison</code> 
	 * associé à la vue qui sera modélisé graphiquement dans 
	 * la <code>zone</code>.
	 */
    private PointDeLivraison pointLivraisonCourant;

    /**
	 * Constructeur de la classe <code>VuePointLivraison</code>
	 * @param pointLivraisonCourant Un point de livraison qui 
	 * sera associé à la vue.
	 */
    public VuePointLivraison(PointDeLivraison pointLivraisonCourant) {
    	this.taille=12;
        this.pointLivraisonCourant = pointLivraisonCourant;
        this.couleur = new Color(50, 205, 50);
    }

    /**
  	 * Méthode dessine de <code>VuePointLivraison</code> 
  	 * permettant de dessiner graphiquement un point de 
  	 * livraison sur la vue associé à la zone.
  	 * @param g Un graphics dans lequel sera dessiné le point de livraison.
  	 * @param echelleX Un facteur d'echelle suivant la coordonnée X.
  	 * @param echelleY Un facteur d'echelle suivant la coordonnée Y.
  	 * @param espacementBord Une valeur associé à l'espacement avec les bords.
  	 */
    public void dessine(Graphics g,  double echelleX, double echelleY, 
    													 int espacementBord) {
    	/*
		 * On transforme le Graphics initial en Graphics2D afin des 
		 * fonctionnalité tel que l'epaisseur
		 * */
    	Graphics2D g2 = (Graphics2D) g;
    	
    	/*
         * On dessine le point de livraison suivant son état de 
         * selection pour la couleur
         * */
        if (estSelectionne()) {
        	g2.setPaint(Color.CYAN);
        } else {
        	/*
             * On dessine le point de livraison suivant son état 
             * de livraison pour la couleur
             * */
        	Livraison livraison = this.pointLivraisonCourant.getLivraison();
        	if (livraison.getEtat() == EtatLivraison.NOUVELLE) {
        		g2.setPaint(new Color(255,165,0));
        	} else if (livraison.getEtat() == EtatLivraison.EN_RETARD ) {
        		g2.setPaint(Color.RED);
        	} else if (livraison.getEtat() == EtatLivraison.A_L_HEURE ){
        		g2.setPaint(this.couleur);
        	}
        }

        g2.fillOval(
        		(int)((this.pointLivraisonCourant.getLieu().getX() + 
        					espacementBord + 1) / echelleX) - this.taille / 2, 
        		(int)((this.pointLivraisonCourant.getLieu().getY() + 
        					espacementBord + 1) / echelleY) - this.taille / 2, 
        					this.taille, 
        					this.taille);
        g2.drawOval(
        		(int)((this.pointLivraisonCourant.getLieu().getX() + 
        					espacementBord + 1) / echelleX) - this.taille / 2, 
        		(int)((this.pointLivraisonCourant.getLieu().getY() + 
        					espacementBord + 1) / echelleY) - this.taille / 2, 
        					this.taille, 
        					this.taille);
    }

    /**
	 * Méthode estClique de <code>VuePointLivraison</code> 
	 * permettant de savoir si un point de livraison associé 
	 * à une vue à été cliqué ou non.
	 * @param point Un point contenant les coordonnée du clic.
	 * @return true si le point de livraison a été cliqué; false sinon.
	 */
    public boolean estClique(Point point) {
    	Point pointPointLivraison = 
    			new Point(pointLivraisonCourant.getLieu().getX(), 
    					  pointLivraisonCourant.getLieu().getY());
    	if (pointPointLivraison.distance(point) < precision) {
    		this.estSelectionne = true;
    	} else {
    		this.estSelectionne = false;
    	}
        return estSelectionne;
    }

    /**
	 * Setter permettant d'assigner un point de livraison 
	 * donnée à la vue courante
	 * @param pointLivraisonCourant le nouveau point de livraison.
	 */
    public void setPointLivraisonCourant(
    								PointDeLivraison pointLivraisonCourant) {
        this.pointLivraisonCourant = pointLivraisonCourant;
    }

    /**
	 * Getter permettant de recuperer le point de 
	 * livraison associé à la vue
	 * @return pointLivraisonCourant le point de 
	 * livraison associé à la vue.
	 */
    @Override
    public PointDeLivraison getElt() {
        return this.pointLivraisonCourant;
    }

}
