package opti_fret_courly.vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import opti_fret_courly.modele.Chemin;
import opti_fret_courly.modele.Troncon;

/**
 * Cette classe est la vue permettant de représenter graphiquement un 
 * modèle Chemin dans l'application.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public class VueChemin extends VueEltNonCliquable {
	/**
	 * Définit le modèle <code>Chemin</code> associé à la vue 
	 * qui sera modélisé graphiquement dans la <code>zone</code>.
	 */
	private Chemin cheminCourant;
	
	/**
	 * Nombre de pixel servant au decale d'un chemin qui passe 
	 * plus d'une fois par le même troncon
	 */
	private int nb;

	/**
	 * Constructeur de la classe <code>VueChemin</code>
	 * @param cheminCourant Un chemin qui sera associé à la vue.
	 */
	public VueChemin(Chemin cheminCourant) {
		this.cheminCourant = cheminCourant;
		this.couleur = new Color(30,144,255);
		nb = 2;
	}

	/**
	 * Méthode dessine de <code>VueChemin</code> permettant de 
	 * dessiner graphiquement un chemin sur la vue associé à la zone.
	 * @param g Un graphics dans lequel sera dessiné le chemin.
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
		for (int i = 0; i < this.cheminCourant.getTroncons().size(); i++) {
			Troncon t = this.cheminCourant.getTroncons().get(i);
			/*
			 * On modifie l'epaisseur des traits qui sera dessiné
			 * */
			g2.setStroke(new BasicStroke(3));
			g2.setPaint(this.couleur);
			g2.drawLine(
				(int)((t.getOrigine().getX() + espacementBord + 1) / echelleX), 
				(int)((t.getOrigine().getY() + espacementBord + 1) / echelleY),
				(int)((t.getFin().getX() + espacementBord + 1) / echelleX), 
				(int)((t.getFin().getY() + espacementBord + 1) / echelleY));
		}
	}
    
    /**
	 * Méthode dessine de <code>VueChemin</code> permettant de 
	 * dessiner graphiquement un chemin sur la vue associé à la zone.
	 * @param g Un graphics dans lequel sera dessiné le chemin.
	 * @param echelleX Un facteur d'echelle suivant la coordonnée X.
	 * @param echelleY Un facteur d'echelle suivant la coordonnée Y.
	 * @param espacementBord Une valeur associé à l'espacement avec les bords.
	 * @param intensite Une valeur associé à l'intensité de la couleur.
	 */
	public void dessine(Graphics g, double echelleX, double echelleY, 
										int espacementBord, int intensite) {
		/*
		 * On transforme le Graphics initial en Graphics2D afin des 
		 * fonctionnalité tel que l'epaisseur
		 * */
		Graphics2D g2 = (Graphics2D) g;
		for (Troncon t : this.cheminCourant.getTroncons() ){
			/*
			 * On modifie le décallage des traits qui seront dessinés
			 * */
			
			
			// On ajoute un décalage de 2 sur tous les chemins
			int decalageX = 0;
            int decalageY = 0;
            int decalageCouleur = 0;
            int decalage = nb * ( 1 + 2 * 
            		t.getChemins().indexOf(this.cheminCourant));
            if (decalage != nb) {
            	decalageCouleur = 150;
            }
            if ((t.getOrigine().getY() - t.getFin().getY()) <= 0) {
            	decalage =- decalage;
            }
                        
            if (((t.getOrigine().getX() - t.getFin().getX()) == 0) 
            		|| (Math.abs((t.getOrigine().getY() - t.getFin().getY()) /
            		   (t.getOrigine().getX() - t.getFin().getX())) >= 0.4)) {
            	decalageX = decalage;
            	decalageY = 0;
            } else {
            	decalageX = 0;
            	decalageY = decalage;
            }
            
            //On dessine notre line
            g2.setStroke(new BasicStroke(3));
			g2.setPaint(new Color(30 + decalageCouleur, 144, intensite));
            
			g2.drawLine(
				(int)((t.getOrigine().getX() + espacementBord + 1 + decalageX) 
																/ echelleX), 
				(int)((t.getOrigine().getY() + espacementBord + 1 + decalageY) 
																/ echelleY),
				(int)((t.getFin().getX() + espacementBord + 1 + decalageX) 
																/ echelleX), 
				(int)((t.getFin().getY() + espacementBord + 1 + decalageY) 
																/ echelleY));
			/*
			 * On fait appel à la méthode permettant de dessiner les flèches
			 * */
			dessineFleche(g, echelleX, echelleY, espacementBord, decalageX, 
																decalageY, t);
			//dessineFleche(g, echelleX, echelleY, espacementBord, t);
		}
	}

	/**
	 * Méthode dessine de la <code>VueChemin</code> permettant de 
	 * dessiner graphiquement un chemin sur la vue associé à la zone.
	 * @param g Un graphics dans lequel sera dessiné le chemin.
	 * @param echelleX Un facteur d'echelle suivant la coordonnée X.
	 * @param echelleY Un facteur d'echelle suivant la coordonnée Y.
	 * @param espacementBord Une valeur associé à l'espacement avec les bords.
	 * @param troncon Un troncon associé à la vue courante.
	 * @param decalageX Définit l'écart (abscisse) entre le centre de la droite  
	 * reliant les deux noeuds où le troncon est affiché (en cas de plusieurs 
	 * utilisations d'un même troncon)
	 * @param decalageY Définit l'écart (ordonnée) entre le centre de la droite  
	 * reliant les deux noeuds où le troncon est affiché (en cas de plusieurs 
	 * utilisations d'un même troncon)
	 */
    public void dessineFleche(Graphics g, double echelleX, double echelleY, 
    										int espacementBord, int decalageX , 
    										int decalageY ,Troncon troncon) {
    	/*
		 * On recupere les coordonnées des extremité du troncon.
		 * */
    	int xA = troncon.getOrigine().getX();
    	int yA = troncon.getOrigine().getY();
    	int xB = troncon.getFin().getX();
    	int yB = troncon.getFin().getY();

    	/*
		 * On calcule les coordonnées du 1/4 du troncon soit 
		 * le milieu du milieu.
		 * */
    	double xI = (double)(((double)(xB+xA)/2+xA)/2) + decalageX;
    	double yI = (double)(((double)(yB+yA)/2+yA)/2) + decalageY;

    	/*
		 * On calcul l'angle entre le troncon et l'horizontal
		 * */
    	double alpha = Math.abs(Math.atan((double)(yB-yA)/(double)(xB-xA)));

    	/*
		 * On positionne les différents ecarts pour le 
		 * tracé des flèches
		 * */
    	int ecart = 10;
    	double ecartXPositif = xI+ecart; 
    	double ecartXNegatif = xI-ecart; 
    	double ecartYPositif = yI+ecart; 
    	double ecartYNegatif = yI-ecart; 

    	/*
		 * On transforme le Graphics initial en Graphics2D afin des 
		 * fonctionnalité tel que l'epaisseur
		 * */
    	Graphics2D g2 = (Graphics2D) g;
		
		/*
		 * On modifie l'epaisseur des traits qui sera dessiné
		 * */
		g2.setStroke(new BasicStroke(2));
    	
    	/*
		 * On trace la flèche selon les cas de figures en 
		 * faisant des ajustements
		 * */
        if (alpha>Math.toRadians(70)) {
        	ecartXPositif -= 2; 
        	ecartXNegatif += 2; 
        	ecartYPositif -= 2; 
        	ecartYNegatif += 2; 
        	if (yA < yB) {
        		g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		        			(int)((yI + espacementBord + 1) / echelleY),
		        			(int)((ecartXNegatif + espacementBord + 1) 
		        												/ echelleX), 
		        			(int)((ecartYNegatif + espacementBord + 1) 
		        												/ echelleY));
		        g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		        			(int)((yI + espacementBord + 1) / echelleY),
		    				(int)((ecartXPositif + espacementBord + 1) 
		    													/ echelleX), 
		    				(int)((ecartYNegatif + espacementBord + 1) 
		    													/ echelleY));
        	} else {
		        g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		        			(int)((yI + espacementBord + 1) / echelleY),
	        				(int)((ecartXNegatif + espacementBord + 1) 
	        													/ echelleX), 
	        				(int)((ecartYPositif + espacementBord + 1) 
	        													/ echelleY));
		        g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		        			(int)((yI + espacementBord + 1) / echelleY),
	    					(int)((ecartXPositif + espacementBord + 1) 
	    														/ echelleX), 
	    					(int)((ecartYPositif + espacementBord + 1) 
	    														/ echelleY));
        	}
		} else {
	    	if (xA < xB) {
	    		if (alpha <Math.toRadians(20)) {
	    			ecartXPositif -= 2; 
	            	ecartXNegatif += 2; 
	            	ecartYPositif -= 2; 
	            	ecartYNegatif += 2; 
	    	        g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
	    	        			(int)((yI + espacementBord + 1) / echelleY),
	    	        			(int)((ecartXNegatif + espacementBord + 1) 
	    	        											/ echelleX), 
	    	        			(int)((ecartYNegatif + espacementBord + 1) 
	    	        											/ echelleY));
	    	        g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
	    	        			(int)((yI + espacementBord + 1) / echelleY),
	    	    				(int)((ecartXNegatif + espacementBord + 1) 
	    	    												/ echelleX), 
	    	    				(int)((ecartYPositif + espacementBord + 1) 
	    	    												/ echelleY));
				} else {
		    		if (yA < yB) {
		    	        g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		    	        			(int)((yI + espacementBord + 1) / echelleY),
	    	        				(int)((xI + espacementBord + 1) / echelleX), 
	    	        				(int)((ecartYNegatif + espacementBord + 1) 
	    	        											/ echelleY));
		    	        g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		    	        			(int)((yI + espacementBord + 1) / echelleY),
	    	    					(int)((ecartXNegatif + espacementBord + 1) 
	    	    												/ echelleX), 
	    	    					(int)((yI + espacementBord + 1) 
	    	    												/ echelleY));
	    			} else {
		    			g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		    						(int)((yI + espacementBord + 1) / echelleY),
			        				(int)((xI + espacementBord + 1) / echelleX), 
			        				(int)((ecartYPositif + espacementBord + 1) 
			        											/ echelleY));
		    			g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		    						(int)((yI + espacementBord + 1) / echelleY),
			    					(int)((ecartXNegatif + espacementBord + 1) 
			    												/ echelleX), 
			    					(int)((yI + espacementBord + 1) 
			    												/ echelleY));
		    		 }
				}
	    	} else {
	    		if (alpha < Math.toRadians(20)) {
	    			ecartXPositif -= 2; 
	            	ecartXNegatif += 2; 
	            	ecartYPositif -= 2; 
	            	ecartYNegatif += 2; 
	    	        g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
	    	        			(int)((yI + espacementBord + 1) / echelleY),
	    	        			(int)((ecartXPositif + espacementBord + 1) 
	    	        											/ echelleX), 
	    	        			(int)((ecartYNegatif + espacementBord + 1) 
	    	        											/ echelleY));
	    	        g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
	    	        			(int)((yI + espacementBord + 1) / echelleY),
	    	    				(int)((ecartXPositif + espacementBord + 1) 
	    	    												/ echelleX), 
	    	    				(int)((ecartYPositif + espacementBord + 1) 
	    	    												/ echelleY));
				} else {
		    		if (yA < yB) {
		    			g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		    						(int)((yI + espacementBord + 1) / echelleY),
		    						(int)((xI + espacementBord + 1) / echelleX), 
		    						(int)((ecartYNegatif + espacementBord + 1) 
		    													/ echelleY));
		    			g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		    						(int)((yI + espacementBord + 1) / echelleY),
			    					(int)((ecartXPositif + espacementBord + 1) 
			    												/ echelleX), 
			    					(int)((yI + espacementBord + 1) 
			    												/ echelleY));
		    		} else {
		    			g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		    						(int)((yI + espacementBord + 1) / echelleY),
									(int)((xI + espacementBord + 1) / echelleX), 
									(int)((ecartYPositif + espacementBord + 1) 
																/ echelleY));
		    			g2.drawLine((int)((xI + espacementBord + 1) / echelleX), 
		    						(int)((yI + espacementBord + 1) / echelleY),
		    						(int)((ecartXPositif + espacementBord + 1) 
		    													/ echelleX), 
		    						(int)((yI + espacementBord + 1) 
		    													/ echelleY));
		    		}
				}
	    	}
		}
    }

    /**
	 * Setter permettant d'assigner un chemin donnée à la vue courante
	 * @param cheminCourant Un chemin qui sera associé à la vue.
	 */
    public void setCheminCourant(Chemin cheminCourant) {
    	this.cheminCourant = cheminCourant;
    }

    /**
	 * Getter permettant de recuperer le chemin associé à la vue
	 * @return cheminCourant le chemin associé à la vue.
	 */
    @Override
    public Chemin getElt() {
        return this.cheminCourant;
    }
}
