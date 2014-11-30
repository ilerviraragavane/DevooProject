package opti_fret_courly.outil.calcul;

import java.util.ArrayList;

import opti_fret_courly.outil.tsp.Graph;

/**
 * Implémente l'interface Graph nécessaire à TSP pour trouver la tournée 
 * optimale.
 * @author florentboisselier
 *
 */
public class Graphe implements Graph {
	
	/**
	 * Cout de l'arc dont le cout est maximal.
	 */
    private int maxCoutArc;
    
    /**
     * Cout de l'arc dont le cout est minimal.
     */
    private int minCoutArc;
    
    /**
     * Nombre de sommets du graphe.
     */
    private int nbSommets;
    
    /**
     * Matrice des couts du graphe.
     * <code>cout[i][j]</code> représente le poids du chemin pour aller de i à 
     * j.
     */
    private int[][] cout;
    
    /**
     * Liste les noeuds successeurs à un noeud.
     * si <code>j</code> appartient à la liste <code>successeurs.get(i)</code> 
     * alors il existe un chemin pour aller de <code>i</code> à <code>j</code> 
     * dans le graphe.
     */
    private ArrayList<ArrayList<Integer>> successeurs;

    /**
     * Constructeur de la classe <code>Graphe</code>. 
     * @param cout : matrice des couts du graphe.
     * @param nbSommets : nombre de sommets dans le graphe.
     * @param min : poids minimal de l'ensemble des arcs du graphe.
     * @param max : poids maximal de l'ensemble des arcs du graphe.
     * @param succ : liste l'ensemble des succeseurs pour un noeud donné.
     */
    public Graphe(int[][] cout, int nbSommets, int min, int max, ArrayList<ArrayList<Integer>> succ) {
        this.cout = cout;
        this.nbSommets = nbSommets;
        this.minCoutArc = min;
        this.maxCoutArc = max;
        this.successeurs = succ;
    }


    public int getMaxArcCost() {
        return this.maxCoutArc;
    }

    
    public int getMinArcCost() {
        return this.minCoutArc;
    }


    public int getNbVertices() {
        return this.nbSommets;
    }


    public int[][] getCost() {
        return this.cout;
    }


    public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
        if ((i < 0) || (i >= nbSommets))
            throw new ArrayIndexOutOfBoundsException();
        int[] tab = new int[successeurs.get(i).size()];
        for (int j = 0; j < tab.length; j++) {
            tab[j] = successeurs.get(i).get(j);
        }
        return tab;
    }

    
    public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
        if ((i < 0) || (i >= nbSommets))
            throw new ArrayIndexOutOfBoundsException();
        return successeurs.get(i).size();
    }

    /**
     * L'égalité est basée sur l'égalité de tous les attributs.
     * @param graphe Graphe avec lequel tester l'égalité
     * @return vrai si les deux graphes sont égaux, faux sinon.
     */
    public boolean equals(Graphe graphe) {
        if (graphe.getMaxArcCost() != this.getMaxArcCost() || graphe.getMinArcCost() != this.getMinArcCost() ||
            graphe.getNbVertices() != this.getNbVertices()
            || graphe.getCost().length != this.getCost().length) {
            return false;
        }
        for(int i=0 ; i<graphe.getCost().length ; i++) {
			if(graphe.getCost()[i].length != this.getCost().length) {
				return false;
			}
			for(int j=0 ; j<graphe.getCost()[i].length ; j++) {
				int a = graphe.getCost()[i][j];
				int b = this.getCost()[i][j];
				if(a != b) {
					return false;
				}
			}
		}
        return true;
    }
}
