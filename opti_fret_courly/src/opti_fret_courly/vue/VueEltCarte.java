package opti_fret_courly.vue;

import java.awt.Color;
import java.awt.Graphics;

import opti_fret_courly.modele.EltCarte;

/**
 * Cette classe abstraite est la vue permettant de représenter graphiquement un 
 * modèle EltCarte dans l'application.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public abstract class VueEltCarte {
	/**
	 * Définit la couleur de l'<code>EltCarte</code> associé à la vue 
	 * qui sera modélisé graphiquement dans la <code>zone</code>.
	 */
    protected Color couleur;

    /**
	 * Définit la taille de l'<code>EltCarte</code> associé à la vue 
	 * qui sera modélisé graphiquement dans la <code>zone</code>.
	 */
    protected int taille = 8;
    
    /**
	 * Définit la précision du clic de l'<code>EltCarte</code> 
	 * associé à la vue qui sera modélisé graphiquement 
	 * dans la <code>zone</code>.
	 */
    final protected int precision = 5;

    /**
	 * Constructeur par defaut de la classe <code>VueEltCarte</code>
	 */
    public VueEltCarte() {
    	
    }

    /**
	 * Setter permettant d'assigner une couleur donnée à la vue courante
	 * @param couleur Une couleur qui sera associé à la vue.
	 */
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    /**
	 * Setter permettant de recupérer l'élement associé à la vue courante
	 */
    public abstract EltCarte getElt();

    /**
	 * Méthode dessine de <code>VueEltCarte</code> permettant de 
	 * dessiner graphiquement un EltCarte sur la vue associé à la zone.
	 * @param g Un graphics dans lequel sera dessiné le élement.
	 * @param echelleX Un facteur d'echelle suivant la coordonnée X.
	 * @param echelleY Un facteur d'echelle suivant la coordonnée Y.
	 * @param espacementBord Une valeur associé à l'espacement avec les bords.
	 */
    public void dessine(Graphics g, double echelleX, double echelleY, 
    													int espacementBord) {
    	
    }
}
