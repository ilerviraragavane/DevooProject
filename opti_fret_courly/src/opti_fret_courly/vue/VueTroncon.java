package opti_fret_courly.vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import opti_fret_courly.modele.Troncon;

/**
 * Cette classe est la vue permettant de représenter 
 * graphiquement un modèle Troncon dans l'application.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public class VueTroncon extends VueEltNonCliquable {
	/**
	 * Définit le modèle <code>Troncon</code> associé à la vue 
	 * qui sera modélisé graphiquement dans la <code>zone</code>.
	 */
    private Troncon tronconCourant;

    /**
	 * Constructeur de la classe <code>VueTroncon</code>
	 * @param tronconCourant Un troncon qui sera associé à la vue.
	 */
    public VueTroncon(Troncon tronconCourant) {
        this.tronconCourant = tronconCourant;
        this.couleur = Color.BLACK;
    }

    /**
  	 * Méthode dessine de <code>VueTroncon</code> permettant de 
  	 * dessiner graphiquement un troncon sur la vue associé à la zone.
  	 * @param g Un graphics dans lequel sera dessiné le troncon.
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

        g2.setStroke(new BasicStroke(1));
        g2.setPaint(this.couleur);
        g2.drawLine(
        		(int)((this.getElt().getOrigine().getX() + espacementBord + 1) 
        														/ echelleX), 
        		(int)((this.getElt().getOrigine().getY() + espacementBord + 1) 
        														/ echelleY), 
        		(int)((this.getElt().getFin().getX() + espacementBord + 1) 
        														/ echelleX), 
       			(int)((this.getElt().getFin().getY() + espacementBord + 1) 
       															/ echelleY));
 
        /*
         * On cherche le milieu afin de placer le poids du 
         * troncon sous forme textuelle
         * */
        /*
        double xMilieu = (double)(this.getElt().getOrigine().getX() + 
        									this.getElt().getFin().getX()) / 2;
        double yMilieu = (double)(this.getElt().getOrigine().getY() + 
        									this.getElt().getFin().getY()) / 2;
        try {
        	g2.setFont(g2.getFont().deriveFont(9.0f));
        	Troncon t = this.tronconCourant;
			g2.drawString(""+t.calculerDuree(), 
							(int)((xMilieu + espacementBord + 1) / echelleX), 
							(int)((yMilieu + espacementBord + 1) / echelleY));
		} catch (CommandeException e) {
			e.printStackTrace();
		}
		//*/
    }

    /**
	 * Setter permettant d'assigner un troncon donnée à la vue courante
	 * @param tronconCourant Un troncon qui sera associé à la vue.
	 */
    public void setTronconCourant(Troncon tronconCourant) {
        this.tronconCourant = tronconCourant;
    }

    /**
	 * Getter permettant de recuperer l'element associé à la vue
	 * @return l'element associé à la vue.
	 */
    public Troncon getElt() {
        return this.tronconCourant;
    }

    /**
  	 * Méthode equals de <code>VueTroncon</code> permettant de 
  	 * verifier si deux vues ont le même troncon associé
  	 * @param vt Un VueTroncon avec qui cette vue sera comparé
  	 * @return true si les deux vues ont le même troncon; false sinon.
  	 */
    public boolean equals(VueTroncon vt) {
        return vt.getElt().equals(this.tronconCourant);
    }
}
