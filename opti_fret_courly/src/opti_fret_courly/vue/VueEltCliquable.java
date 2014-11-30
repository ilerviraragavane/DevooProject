package opti_fret_courly.vue;

import java.awt.Point;

/**
 * Cette classe est la vue permettant de représenter 
 * graphiquement un element cliquable dans l'application.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public abstract class VueEltCliquable extends VueEltCarte {
	/**
	 * Définit l'état de selection d'un element 
	 * cliquable associé à la vue qui sera modélisé 
	 * graphiquement dans la <code>zone</code>.
	 */
	protected boolean estSelectionne;

	/**
	 * Méthode estClique de <code>VueEltClique</code> permettant de savoir 
	 * si un element associé à une vue à été cliqué ou non.
	 * @param point Un point contenant les coordonnée du clic.
	 * @return true si l'élement a été cliqué; false sinon.
	 */
    public abstract boolean estClique(Point point);
    
    /**
	 * Setter permettant d'assigner un état de selection donnée 
	 * à la vue courante
	 * @param estSelectionne Un état de selection qui sera 
	 * associé à la vue.
	 */
    public void setSelectionne(boolean estSelectionne) {
    	this.estSelectionne = estSelectionne;
    }

    /**
	 * Getter permettant de recuperer l'état de selction 
	 * de la vue courante
	 * @return l' etat de selection de la vue
	 */
	public boolean estSelectionne() {
		return estSelectionne;
	}
    
}
