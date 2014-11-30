package opti_fret_courly.outil.calcul;

import java.util.ArrayList;
import java.util.List;

/**
 *  <em>CalculateurChemin</em> permet de calculer le plus court chemin entre 2 points
 *  connaissant la matrice de couts.<br/>
 *  L'algorithme utilisé est celui de Dijkstra.
 *  
 * @author Yannick Faula
 *
 */


public class CalculateurChemin {
    /**
     * <em>cout</em> est le cout en secondes du plus court chemin trouvé
     */
    private int cout;

    /**
     * Permet d'avoir la liste des sommets qui constitue le chemin
     */
    private List<Integer> suivants;

    /**
     * Convention :
     * 		s'il n'y a pas d'arc alors la valeur dans la matrice est <= 0
     * 		Premier chiffre : ligne  /  Deuxième : colonne
     */
    private int[][] matrice;

    public CalculateurChemin(int[][] matrice) {
        this.matrice = matrice;
        this.cout = 0;
        this.suivants = new ArrayList<Integer>();
    }

    /**
     * Méthode principale appelée pour trouver le plus court chemin entre i et j.
     * @param i le noeud de départ
     * @param j le noeud d'arrivée
     */
    public void calculerChemin(int i, int j) {
        
    	//Déclaration
    	int taille = matrice.length;
    	int[] d = new int [taille];		//d est le tableau des distances
    	int[] Pi = new int [taille];	//Pi est le tableau des "predecesseurs"
    	int noeud_courant;
    	int dist_noeud_courant;
    	List<Integer> noeuds_suivants = new ArrayList<Integer>();
    	
    	//Initialisation
    	//Ici -1 réprésente l'infini
    	for(int n =0;n<taille;d[n++]=-1);
    	//Ici -1 répresente le successeur Nil d'un noeud
    	for(int n =0;n<taille;Pi[n++]=-1);
    	
    	d[i] = 0;
    	while(!verifierNTraite(d) && trouverMinDist(d) < taille) {
    		noeud_courant = trouverMinDist(d);
    		//on recupere la distance pour le calcul du cout final
    		dist_noeud_courant = d[noeud_courant];
    		d[noeud_courant] = -2;		//On le marque comme déja vu
    		
    		successeurs(noeud_courant, noeuds_suivants, d);
    		for(Integer n: noeuds_suivants) {
    			relacher(d, Pi, noeud_courant, n, dist_noeud_courant);
    		}
    	}
    	
    	remplirSuivants(i, j, Pi);
    	
    }
    
    /**
     * Permet de mettre à jour le tableau des distances et des prédecesseurs 
     * 	dans l'algorithme de Dijkstra
     * @param d 
     * @param Pi le tableau des prédecesseurs
     * @param u le sommet de départ
     * @param v le sommet d'arrivé
     * @param dist_noeud_courant la distance du sommet i vers v passant par u
     */
    private void relacher(int[] d, int[] Pi, int u,int v, int dist_noeud_courant) {
    	int temp = dist_noeud_courant + matrice[u][v];
    	
    	if(d[v] == -1 ) {
    		d[v] = temp;
    		Pi[v] = u;
    	}
    	else if(d[v] > -1){
    		if(temp < d[v]) {
    			d[v] = temp;
    			Pi[v] = u;
    		}
    	}
    }
    
    /**
     * Vérifie si tous les noeuds ont été traités
     * Dans le tablau ils sont égales à -2
     * @param d tableau des distances
     * @return true si les noeuds ont été traités; false sinon.
     */
    private boolean verifierNTraite(int[] d) {
    	int i = 0;
    	int taille = d.length;
    	
    	while(i < taille && d[i] < -1) i++;
    	
    	return (i==taille);
    }
    
    /**
     * Permet de trouver le noeud non traité ayant la plus courte distance
     * calculée à l'instant t
     * @param d tableau des distances
     * @return l'indice du noeud non traité ayant la plus courte distance
     */
    private int trouverMinDist(int [] d) {
    	int n = 0;
    	
    	//On recherche un noeud qui doit etre traité
    	while(n < d.length && d[n] < 0) n++;
    	
    	//à partir de ce noeud on recherche celui qui a la plus courte distance
    	for(int i=n;i<d.length;i++) {
    		if(d[i] > 0) {
    			if(d[i] < d[n])
    				n = i;
    		}
    	}
    	
    	return n;
    }
    
    /**
     * Méthode permettant de récupérer les indices des noeuds successeurs du 
     * noeud donné en paramètre
     * @param n le noeud de départ
     * @param noeuds_suivants liste des noeuds successeurs de n
     * @param d tableau des distances
     */
    private void successeurs(int n,List<Integer> noeuds_suivants, int[] d) {
    	int taille = matrice.length;
    	noeuds_suivants.clear();	//On efface la liste avant de trouver les succ d'un noeud
    	
    	for(int i=0;i < taille; i++) {
    		if(matrice[n][i] >= 0 && d[i] > -2) {
    			noeuds_suivants.add(i);
    		}
    	}
    }
    
    /**
     * Permet de connaitre le chemin complet le plus court entre u et v
     * Cette méthode met également à jour le cout du chemin.
     * @param u noeud de départ
     * @param v noeud d'arrivée
     * @param Pi tableau des prédecesseurs calculé par Dijkstra
     */
    private void remplirSuivants(int u, int v, int[] Pi) {
    	// réinitialisation de "suivants" et "cout"
    	suivants.clear();
    	cout = 0;
    	
    	int i=v;
    	
    	while(i != u && Pi[i] != -1) {
	    		suivants.add(0,i);
	    		cout += matrice[Pi[i]][i];	//calcul du cout du chemin trouvé
	    		i = Pi[i];					//on "remonte" les predecesseurs trouves par Dijkstra
    	}
    	if(i == u)
    		suivants.add(0,u);
    }

    public int getCout() {
        return this.cout;
    }

    public List<Integer> getSuivants() {
    	if(suivants.isEmpty())
    		return null;
    	else
    		return this.suivants;
    }

    public int[][] getMatrice() {
        return this.matrice;
    }
}
