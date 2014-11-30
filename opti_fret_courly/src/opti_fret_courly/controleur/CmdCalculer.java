package opti_fret_courly.controleur;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import opti_fret_courly.modele.Chemin;
import opti_fret_courly.modele.Entrepot;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.modele.PointDArret;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.modele.Tournee;
import opti_fret_courly.modele.Troncon;
import opti_fret_courly.modele.Zone;
import opti_fret_courly.outil.calcul.CalculateurChemin;
import opti_fret_courly.outil.calcul.Graphe;
import opti_fret_courly.outil.exception.CommandeException;
import opti_fret_courly.outil.tsp.SolutionState;
import opti_fret_courly.outil.tsp.TSP;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueZone;

/**
 * Cette classe représente une commande permettant de calculer une tournée 
 * optimale passant permettant de déservir toutes les livraisons de la zone.
 * 
 * @author Florent Boisselier
 *
 */
public class CmdCalculer extends CmdNonAnnulable {

    /**
     * Définit le numéro par défaut qui est attribué à l'
     * <code>entrepot</code> dans le <code>graphe</code>.
     */
	private static int NO_ENTREPOT = 0;

	/**
	 * Module permettant de calculer des itinéraires optimaux entre 
	 * deux noeuds de la <code>zone</code>.
	 */
    private CalculateurChemin calculateurChemin;
    
    /**
     * Module permettant de calculer une tournée optimale à partir 
     * d'un <code>graphe</code>.
     */
    private TSP tsp;

    /**
     * Définit la limite de temps accordée au module 
     * <code>TSP</code> afin de trouver la tournée optimale.
     */
    private int limiteDeTemps;
    
    /**
     * Définit la borne maximal
     */
    private int borne;
    
    /**
     * Définit la <code>zone</code> sur laquelle calculer la 
     * <code>tournée</code>.
     */
    private Zone zone;

    /**
     * Définit la <code>vueZone</code> sur laquelle la 
     * <code>tournée</code> est affichée.
     */
    private VueZone vueZone;
    
    /**
     * Définit la <code>vueDetailNoeud</code> sur laquelle le
     * détail d'un point de livraison ou d'un noeud est affiché.
     */
    private VueDetailNoeud vueDetailNoeud;
    
    /**
     * Table permettant de définir une correspondance entre un 
     * numéro de ligne / colonne et un <code>noeud</code> dans la matrice de 
     * <code>CalculateurChemin</code>.
     */
    private Map<Integer, Noeud> mapRangVersNoeud;
    
    /**
     * Table permettant de définir une correspondance entre une
     * <code>id</code> de <code>noeud</code> et le numéro de ligne / colonne
     * par lequel il est représenté dans la matrice de 
     * <code>CalculateurChemin</code>.
     */
    private Map<Integer, Integer> mapIdNoeudVersRang;

    /**
     * Table permettant de définir une correspondance entre un 
     * numéro de ligne / colonne et un <code>PointDeLivraison</code> dans le 
     * graphe de <code>TSP</code>.
     */
    private Map<Integer, PointDArret> mapRangVersPointDArret;
    
    /**
     * Table permettant de définir une correspondance entre une
     * <code>id</code> de <code>PointDArret</code> (<code>id</code> du 
     * <code>noeud</code> associé) et le numéro de ligne / colonne
     * par lequel il est représenté dans le graphe de <code>TSP</code>.
     */
    private Map<Integer, Integer> mapIdPointDArretVersRang;
    
    /**
     * Matrice des couts pour aller d'une livraison à une autre.
     * Cout pour aller de la livraison i à j = cout[i][j] 
     */
    private int cout[][];

    /**
     * Constructeur de la classe <code>CmdCalculer</code>
     * @param zone Une zone dans laquelle le calcul de tournée va être effectué.
     * @param limiteDeTemps La limite de temps laissée à <code>TSP</code> 
     * pour trouver la tournée optimale.
     */
    public CmdCalculer(VueZone vueZone,VueDetailNoeud vueDetailNoeud, 
    		Zone zone, int limiteDeTemps) {
        this.calculateurChemin = null;
        this.tsp = null;
        this.vueZone = vueZone;
        this.vueDetailNoeud = vueDetailNoeud;
        this.zone = zone;
        this.limiteDeTemps = limiteDeTemps;
        this.mapRangVersNoeud = new TreeMap<Integer, Noeud>();
        this.mapIdNoeudVersRang = new TreeMap<Integer, Integer>();
        this.mapRangVersPointDArret = new TreeMap<Integer, PointDArret>();
        this.mapIdPointDArretVersRang = new TreeMap<Integer, Integer>();
        this.cout = null;
    }


    /**
     * Construit la table entre les <code>Noeud</code> et les numéros de 
     * colonnes / lignes de la matrice nécessaire à 
     * <code>CalculateurChemin</code>.
     * @throws IllegalArgumentException Si la zone est nulle
     */
    private void construireMappingNoeud() throws CommandeException {
        if( zone == null ){
        	throw new IllegalArgumentException("La zone n'est pas définie.");
        }
        
        this.mapRangVersNoeud.clear();
        this.mapIdNoeudVersRang.clear();
        
        int rang = 0;
        for( Noeud n : zone.getNoeuds().values() ){
            this.mapRangVersNoeud.put(rang, n);
            this.mapIdNoeudVersRang.put(n.getId(), rang++);
        }
    }


    /**
     * Construit la table entre les <code>Noeud</code> et les numéros de 
     * colonnes / lignes du graphe nécessaire à <code>TSP</code>.
     * @throws IllegalArgumentException Si un des éléments suivant n'est pas
     *  défini :
     * <code>Zone</code>, <code>Tournee</code>, <code>Entrepot</code>, 
     * <code>Plage</code>
     * @throws CommandeException si deux points d'arret ou la même id
     */
    private void construireMappingPointDArret() throws IllegalArgumentException,
    		CommandeException {
        if( zone == null ){
        	throw new IllegalArgumentException("La zone n'est pas définie.");
        }
        if( zone.getTournee() == null ){
        	throw new IllegalArgumentException("La tournée n'est pas définie.");
        }
        if( zone.getTournee().getEntrepot() == null ){
        	throw new IllegalArgumentException("Il n'existe aucun entrepot pour"
        			+ " la tournée.");
        }
        if( zone.getTournee().getPlagesHoraires() == null ){
        	throw new IllegalArgumentException("Aucune plage horaire n'a été "
        			+ "définie.");
        }
        
        this.mapRangVersPointDArret.clear();
        this.mapIdPointDArretVersRang.clear();
        
        this.mapIdPointDArretVersRang.put(
        		zone.getTournee().getEntrepot().getLieu().getId(), 
        		NO_ENTREPOT);
        this.mapRangVersPointDArret.put(
        		NO_ENTREPOT, 
        		zone.getTournee().getEntrepot());

        int rang = 0;
        for (Plage p : zone.getTournee().getPlagesHoraires()) {
            for (Livraison l : p.getLivraisons()) {
            	if(rang==NO_ENTREPOT) {
            		rang++;
            	}
                this.mapRangVersPointDArret.put(
                		rang, 
                		l.getAdresse());
                this.mapIdPointDArretVersRang.put(
                		l.getAdresse().getLieu().getId(), 
                		rang++);
            }
        }
        if( mapRangVersPointDArret.size() != mapIdPointDArretVersRang.size() ){
        	if( mapRangVersPointDArret.size() < mapIdPointDArretVersRang.size() 
        			){
        		throw new CommandeException("Deux PointDArret ont le même rang."
        				);
        	} else {
        		throw new CommandeException("Deux PointDArret ont la même id.");
        	}
        }
        if( this.mapRangVersPointDArret.size() == 0 ){
        	throw new CommandeException("Aucune livraison.");
        }
    }

 
    /**
     * Permet d'initialiser l'attribut <code>calculateurChemin</code>.
     * @throws CommandeException Si aucune livraison n'est définie, ou s'il
     * 		y a un problème de cohérence du plan.
     * @Note Utilise uniquement les tables <code>mapRangVersNoeud</code> et 
     * <code>mapIdNoeudVersRang</code> ; n'utilise donc pas la <code>zone</code>
     * .
     */
    private void construireCalculateurChemin() throws CommandeException {
        construireMappingNoeud();

        if( this.mapRangVersNoeud.size() == 0 ){
        	throw new CommandeException( "Aucune livraison n'est définie." );
        }
        
        int tailleMatrice = this.mapRangVersNoeud.size();
        
        /* On calcule la matrice du CalculateurChemin */
        int[][] matrice = new int[tailleMatrice][tailleMatrice];
        
        /* On rempli la matrice de -1 */
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                matrice[i][j] = -1;
            }
        }

        Iterator<Entry<Integer, Noeud>> itNoeud = 
        		this.mapRangVersNoeud.entrySet().iterator();

        while( itNoeud.hasNext() ){
            Entry<Integer, Noeud> paire = itNoeud.next();
            
            /* on récupère le numéro de colonne / ligne du noeud */
            int i = paire.getKey().intValue();

            for( Troncon tSortant : paire.getValue().getTronconsSortants() ){
                Noeud nSortant = tSortant.getFin();
                
                /* on récupère le numéro de colonne / ligne du noeud sortant */
                Integer j = this.mapIdNoeudVersRang.get(nSortant.getId());
                
                if( j == null ){
                	throw new CommandeException( "Problème de cohérence du "
                			+ "plan." );
                }
                
                /* On définit le poids du troncon dans la matrice */
                matrice[i][j.intValue()] = tSortant.calculerDuree();
            }
        }

        /* On construit le CalculateurChemin  */
        this.calculateurChemin = new CalculateurChemin(matrice);
    }


    /**
     * Permet d'initialiser un <code>Graphe</code> utilisé par <code>TSP</code>. 
     * @PreCondition Avoir initialisé le <code>CalculateurChemin</code>
     * @return Le <code>Graphe</code>
     * @throws CommandeException s'il y a la précondition qui n'est pas 
     * vérifiée, </br> 
     * que la zone est null, </br> 
     * que la tournee soit null, </br>
     * s'il y a aucunes plages horaires de définies, ou aucunes livraisons
     */
    private Graphe construireGraphe() throws CommandeException {
        if( this.calculateurChemin == null ){
            throw new CommandeException("Le module de calcule de chemin est "
            		+ "non initialisé.");
        }
        if( this.zone == null ){
        	throw new CommandeException("La zone n'est pas définie.");
        }
        if( this.zone.getTournee() == null ){
        	throw new CommandeException("La tournée de la zone n'est pas "
        			+ "définie.");
        }
        
        /* On trie les plages horaires */
        List<Plage> lp = zone.getTournee().getPlagesHoraires();
        if( lp == null ){
        	throw new CommandeException("Il n'y a aucunes plages horaires de "
        			+ "définies.");
        }
        Collections.sort(lp);
        
        /* on établit le mapping entre les pointDeLivraison 
         * et les coordonnées dans la matrice
         */
        construireMappingPointDArret();

        /* On détermine les dimensions de la matrice */
        int nbSommets = this.mapRangVersPointDArret.size();
        if( nbSommets == 0 ){
        	throw new CommandeException("Il y a aucunes livraisons de "
        			+ "définies.");
        }
        
        /* On initialise la matrice des cout*/
        this.cout = new int[nbSommets][nbSommets];
        /* Ainsi que la liste des successeurs à un noeud */
        ArrayList<ArrayList<Integer>> successeurs = 
        		new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < nbSommets; i++) {
            successeurs.add(new ArrayList<Integer>());
        }

        /* On initialise la liste des provenances possibles au noeud de 
         * l'entrepot
         */
        List<Integer> provenancesPossibles = new ArrayList<Integer>();
        provenancesPossibles.add(NO_ENTREPOT);

        for (Plage pl : lp) {

            /* on initialise la liste des prochaines provenances possibles */
            List<Integer> prochainesProvenancesPossibles = 
            		new ArrayList<Integer>();
            
            if( pl.getLivraisons() == null
            		|| pl.getLivraisons().size() == 0 ){
            	throw new CommandeException("Il n'y a pas de livraisons dans "
            			+ "la plage : " + pl.getCreneau().toString());
            }

            for (Livraison liv1 : pl.getLivraisons()) {
                
            	/* On trouve la colonne de la matrice correspondant à cette 
            	 * Livraison
            	 */
                Integer iRangMatrice = this.mapIdNoeudVersRang.get(
                		liv1.getAdresse().getLieu().getId());
                
                /* On trouve la colonne de la matrice du graphe correspondant 
                 * à cette Livraison
                 */
                Integer iRangGraphe = this.mapIdPointDArretVersRang.get(
                		liv1.getAdresse().getLieu().getId());
                
                /* on ajoute cette livraison comme provenance possible pour la 
                 * prochaine itération
                 */
                prochainesProvenancesPossibles.add(iRangGraphe);

                /* On ajoute le chemin de la provenance au point de livraison 
                 * i
                 */
                for (int provenanceRangGraphe : provenancesPossibles) {
                	PointDArret pt = this.mapRangVersPointDArret
                			.get(provenanceRangGraphe);
                    Integer provenanceRangMatrice = this.mapIdNoeudVersRang
                    		.get(pt.getLieu().getId());
                	
                    this.calculateurChemin.calculerChemin(provenanceRangMatrice, 
                    		iRangMatrice.intValue());
                    this.cout[provenanceRangGraphe][iRangGraphe.intValue()] = 
                    		this.calculateurChemin.getCout();
                    
                    /* on établit la liste des successeurs possibles */
                    successeurs.get(provenanceRangGraphe).add(iRangGraphe);
                }


                /* On ajoute le poids de chaque chemin avec les points de 
                 * Livraison d'une même plage horaire
                 */
                for (Livraison liv2 : pl.getLivraisons()) {

                    /* On trouve la colonne de la matrice correspondant à cette 
                     * Livraison
                     */
                    Integer jRangMatrice = this.mapIdNoeudVersRang
                    		.get(liv2.getAdresse().getLieu().getId());
                    
                    /* On trouve la colonne de la matrice du graphe 
                     * correspondant à cette Livraison
                     */
                    Integer jRangGraphe = this.mapIdPointDArretVersRang.get(
                    		liv2.getAdresse().getLieu().getId());
                    
                    /* on ajoute tous les chemins partant de la livraison i 
                     * vers une autre livraison de la même plage
                     */
                    if (!liv1.equals(liv2)) {
                        this.calculateurChemin.calculerChemin(
                        		iRangMatrice, 
                        		jRangMatrice);

                        this.cout[iRangGraphe][jRangGraphe] = 
                        		this.calculateurChemin.getCout();

                        /* on établit la liste des successeurs possible */
                        successeurs.get(iRangGraphe).add(jRangGraphe);
                    }

                } /* fin de for (j) */
            } /* fin de for (i) */

            /* On passe à la plage suivante */
            provenancesPossibles = prochainesProvenancesPossibles;
        } /* fin de for (plage) */

        /* Pour finir on retourne à l'entrepot : */
        for (int iRangGraphe : provenancesPossibles) {
        	PointDArret pt = this.mapRangVersPointDArret.get(iRangGraphe);
        	int iRangMatrice = this.mapIdNoeudVersRang.get(
        			pt.getLieu().getId());
        	
        	PointDArret entrepot = this.mapRangVersPointDArret.get(NO_ENTREPOT);
        	int eRangMatrice = this.mapIdNoeudVersRang.get(
        			entrepot.getLieu().getId());
            this.calculateurChemin.calculerChemin(iRangMatrice, eRangMatrice);
            this.cout[iRangGraphe][NO_ENTREPOT] = 
            		this.calculateurChemin.getCout();

            /* on établit la liste des successeurs possible */
            successeurs.get(iRangGraphe).add(NO_ENTREPOT);
        }


        int minCoutArc = Integer.MAX_VALUE;
        int maxCoutArc = 0;

        /* trouve Max et Min */
        for (int i = 0; i < this.cout.length; i++) {
            for (int j = 0; j < this.cout[i].length; j++) {
                if (this.cout[i][j] != 0) {
                    maxCoutArc = Math.max(maxCoutArc, this.cout[i][j]);
                    minCoutArc = Math.min(minCoutArc, this.cout[i][j]);
                }
            }
        }
        /* Rempli la matrice */
        for (int i = 0; i < this.cout.length; i++) {
            for (int j = 0; j < this.cout[i].length; j++) {
                if (this.cout[i][j] == 0) {
                    this.cout[i][j] = maxCoutArc + 1;
                }
            }
        }

        /* on construit et retourne le graphe */
        return new Graphe(this.cout, nbSommets, minCoutArc, maxCoutArc, 
        		successeurs);
    }

 
    /**
     * Construit le chemin optimal entre deux <code>PointDArret</code>.
     * @param ptDep Point de départ du chemin.
     * @param ptArr Point d'arrivée du chemin.
     * @return <code>null</code> si une des pré-condictions n'est pas vérifiée.
     * Ou le <code>chemin</code> permettant d'aller du point de départ au point 
     * d'arrivée
     * @throws CommandeException  S'il y a la précondition non vérifiée, ou 
     * s'il y a un problème de cohérence entre la carte et la modélisation.
     * @PreCondition Les tables doivent être initialisées, ainsi que le 
     * <code>CalculateurChemin</code>
     */
    private Chemin construireChemin(PointDArret ptDep, PointDArret ptArr) 
    		throws CommandeException {

        if( this.mapIdNoeudVersRang == null 
        		|| this.mapIdPointDArretVersRang == null 
        		|| this.mapRangVersNoeud == null 
        		|| this.mapRangVersPointDArret == null 
        		|| this.calculateurChemin == null
        		|| ptDep == null
        		|| ptArr == null ){
            throw new CommandeException("Les éléments permettant de construire "
            		+ "un chemin ne sont pas tous correctement initialisés.");
        }

        Chemin ch = new Chemin();
        ch.setPointArr(ptArr);
        ch.setPointDep(ptDep);

        /* On trouve quel numéro à le noeud de départ du chemin dans notre 
         * matrice
         */
        int idDep = ptDep.getLieu().getId();
        int idArr = ptArr.getLieu().getId();
        Integer debut = this.mapIdNoeudVersRang.get(idDep);
        Integer arrivee = this.mapIdNoeudVersRang.get(idArr);
        
        /* on demande au calculateurChemin de calculer le chemin pour aller de 
         * notre point de départ jusqu'au prochain point d'arret
         */
        this.calculateurChemin.calculerChemin(debut.intValue(), 
        		arrivee.intValue());

        /* On initialise le point de départ la provenance du noeud sur lequel 
         * on veut aller
         */
        Noeud provenance = ptDep.getLieu();
        for( Integer i : this.calculateurChemin.getSuivants() ){
            /* on passe le premier point */
        	int temp = this.mapIdNoeudVersRang.get(provenance.getId());
            if( i.intValue() != temp ){

                /* On cherche le noeud prochain vers lequel le chemin doit 
                 * passer
                 */
                Noeud prochain = this.mapRangVersNoeud.get(i.intValue());
                if( prochain == null ){
                	throw new CommandeException("Erreur de cohérence entre la "
                			+ "carte et la modélisation.");
                }
                
                /* on trouve le troncon allant du noeud d'où on vient 
                 * (provenance) vers le prochain noeud (prochain)
                 */
                Troncon t = provenance.getTronconVers(prochain);
                if( t == null ){
                	throw new CommandeException("Erreur de cohérence entre la "
                			+ "carte et la modélisation.");
                }
                
                /* on ajoute le troncon au chemin */
                ch.ajouterTroncon(t);

                /* On passe au prochain troncon */
                provenance = prochain;
            }
        } /* fin for i */

        return ch;
    }

    /**
     * Permet d'établir les créneaux horaires de passage du livreur pour 
     * chaque livraisons
     * @throws CommandeException si la tournée ne débute pas par un entrepot
     */
    private void calculerItineraire() throws CommandeException{
    	
    	List<Chemin> chemins = zone.getTournee().getChemins();
    	int temps_parcours = 0;
    	PointDeLivraison depart;
    	PointDeLivraison arrivee;
    	Entrepot entrepot;
    	Plage plage_courante;
    	Calendar horaire;
    	long sec;
    	
    	/* Chemin entre l'entrepot et le 1er noeud */
    	if(chemins.get(0).getPointDep() instanceof Entrepot){
    		entrepot = (Entrepot)chemins.get(0).getPointDep();
    		arrivee = (PointDeLivraison) chemins.get(0).getPointArr();
    		
    		Integer i = this.mapIdPointDArretVersRang.get(
    				entrepot.getLieu().getId());
    		Integer j = this.mapIdPointDArretVersRang.get(
    				arrivee.getLieu().getId());
    		
    		temps_parcours = this.cout[i.intValue()][j.intValue()];
    		
    		plage_courante = arrivee.getLivraison().getPlageHoraire();
    		sec = plage_courante.getCreneau().getDebut().getTimeInMillis();
    		
    		entrepot.getHeuresPassage().getDebut().
    			setTimeInMillis(sec - temps_parcours*1000);
    		
    		horaire = arrivee.getLivraison().getHeuresPassage().getDebut();
    		horaire.setTimeInMillis(sec);
    		horaire = arrivee.getLivraison().getHeuresPassage().getFin();
    		horaire.setTimeInMillis(sec);
    		horaire.add(Calendar.MINUTE, 10);
    		

			/* On met à jour l'état de la livraison (A_L_HEURE ou EN_RETARD)
    		 */
			arrivee.getLivraison().mettreAJourEtat();
    	}
    	else{
    		throw new CommandeException("La tournée ne débute pas par un "
    				+ "entrepot.");
    	}
    	
    	for(Chemin c : chemins){
    		/* On teste si on a 2 points de livraisons
    		 * on ne traite pas le cas des chemins depuis et vers l'entrepot
    		 */
    		if(c.getPointArr() instanceof PointDeLivraison
    				&& c.getPointDep() instanceof PointDeLivraison){
    			depart = (PointDeLivraison)c.getPointDep();
        		arrivee = (PointDeLivraison) c.getPointArr();
        		plage_courante = arrivee.getLivraison().getPlageHoraire();
        		
	    		horaire = depart.getLivraison().getHeuresPassage().getFin();
	    		sec = horaire.getTimeInMillis();
	    		
	    		
	    		horaire = arrivee.getLivraison().getHeuresPassage().getDebut();
	    		horaire.setTimeInMillis(sec);
	    		int idDep = depart.getLieu().getId();
	    		Integer i = this.mapIdPointDArretVersRang.get(idDep);
	    		int idArr = arrivee.getLieu().getId(); 
	    		Integer j = this.mapIdPointDArretVersRang.get(idArr);
	    		
	    		/* On récupere le temps de parcours en SECONDES 
	    		 * puis on l'ajoute
	    		 */
	    		temps_parcours = this.cout[i.intValue()][j.intValue()];
	    		horaire.add(Calendar.SECOND, temps_parcours);
	    		
	    		/* On choisit le max entre le début de la plage horaire et 
	    		 * l'horaire d'arrivée calculé.
    			 */
    			if( plage_courante.getCreneau().getDebut().
    					compareTo(horaire) > 0 ){
    				sec = plage_courante.getCreneau().getDebut().
    						getTimeInMillis();
    	    		horaire.setTimeInMillis(sec);
    			}
	    			
    			/* On prévoit toujours 10 minutes d'arret pour livrer le colis
    			 */
	    		sec = horaire.getTimeInMillis();
    			horaire = arrivee.getLivraison().getHeuresPassage().getFin();
    			horaire.setTimeInMillis(sec);
	    		horaire.add(Calendar.MINUTE, 10);
	    		
    			/* On met à jour l'état de la livraison (A_L_HEURE ou EN_RETARD)
	    		 */
    			arrivee.getLivraison().mettreAJourEtat();
    		}
    		else if(c.getPointArr() instanceof Entrepot &&
    				c.getPointDep() instanceof PointDeLivraison){
    			depart = (PointDeLivraison)c.getPointDep();
    			horaire = depart.getLivraison().getHeuresPassage().getFin();
    			
    			sec = horaire.getTimeInMillis();
    			horaire = entrepot.getHeuresPassage().getFin();
    			horaire.setTimeInMillis(sec);
    					
				Integer i = this.mapIdPointDArretVersRang.get(
	    				depart.getLieu().getId());
	    		Integer j = this.mapIdPointDArretVersRang.get(
	    				entrepot.getLieu().getId());
	    		
	    		/* On récupere le temps de parcours en SECONDES 
	    		 * puis on l'ajoute
	    		 */
	    		temps_parcours = this.cout[i.intValue()][j.intValue()];
	    		horaire.add(Calendar.SECOND, temps_parcours);
    		}
    	}
    	/**
    	 * On signale que l'entrepot est planifié
    	 */
    	entrepot.setEstPlanifiee(true);
    }
    
    
    /**
     * Permet d'exécuter la commande CmdCalculer et donc d'obtenir la 
     * tournée optimale.
     * @throws CommandeException  S'il y a un problème lors de l'exécution
     */
    public void executer() throws CommandeException {
        /* --------------------------------------------------- */
        /* initialisation du contexte */
        construireCalculateurChemin();
        Graphe graphe = construireGraphe();
        this.borne = graphe.getNbVertices() * (graphe.getMaxArcCost()+1);
        this.tsp = new TSP(graphe);

        /* réinitialisation de l'affichage des chemins */
        Tournee t = this.zone.getTournee();  
        if( t == null )
        	throw new CommandeException("Il n'y a pas de tournée définie "
        			+ "pour la zone");
        t.getChemins().clear();
        for( Noeud n : vueZone.getZone().getNoeuds().values() )
        	for( Troncon troncon : n.getTronconsSortants() )
        		troncon.getChemins().clear();
        
        /* --------------------------------------------------- */
        boolean reessayerCalcul = true;
        
        while( reessayerCalcul ){
	        /* calcul de la solution */
	        tsp.solve(this.limiteDeTemps, this.borne);
	        
	        String s = "";
	        switch( tsp.getSolutionState() ){
	        case SOLUTION_FOUND :
	        	s = (String) JOptionPane.showInputDialog(
	                    null,
	                    "Une solution a été trouvée, cependant ce n'est pas "
	                    	+ "l'optimale.\n"
	            		+ "Pour continuer avec cette solution, cliquez sur "
	            			+ "Annuler.\n"
	            		+ "Pour tenter de trouver la solution optimale, entrez "
	            			+ "une durée (en ms) de calcul supérieure :",
	                    "Solution non optimale trouvée.",
	                    JOptionPane.WARNING_MESSAGE,
	                    null,
	                    null,
	                    this.limiteDeTemps);
	        	if( s == null )
	        		reessayerCalcul = false;
	        	break;
	        case NO_SOLUTION_FOUND :
	        	s = (String) JOptionPane.showInputDialog(
	                    null,
	                    "Aucune solution n'a été trouvée.\n"
	            		+ "Pour tenter de trouver une solution, entrez une "
	            			+ "durée (en ms) de calcul supérieure :",
	                    "Pas de solution trouvée.",
	                    JOptionPane.WARNING_MESSAGE,
	                    null,
	                    null,
	                    this.limiteDeTemps);
	        	if( s == null )
	        		throw new CommandeException("Le calcul a été annulé car il "
	        				+ "n'y a pas eu de solution trouvée.");
	        	break;
	        case INCONSISTENT :    	
	        	s = (String) JOptionPane.showInputDialog(
	                    null,
	                    "Aucune solution n'a été trouvée.\n"
	            		+ "Pour tenter de trouver une solution, entrez une "
	            			+ "borne maxiamle supérieure :",
	                    "Pas de solution trouvée.",
	                    JOptionPane.WARNING_MESSAGE,
	                    null,
	                    null,
	                    this.borne);
	        	if( s == null )
	        		throw new CommandeException("Le calcul a été annulé car il "
	        				+ "n'y a pas eu de solution trouvée.");
	        	break;
	        case OPTIMAL_SOLUTION_FOUND :
	        	reessayerCalcul = false;
	        	break;
	        }
	        
	        if( reessayerCalcul ){
	        	try {
	        		if(tsp.getSolutionState() == SolutionState.SOLUTION_FOUND
	        				|| tsp.getSolutionState() == 
	        				SolutionState.NO_SOLUTION_FOUND ){
	        			this.limiteDeTemps = Integer.parseInt(s);
	        		}
	        		if(tsp.getSolutionState() == SolutionState.INCONSISTENT){
	        			this.borne = Integer.parseInt(s);
	        		}
	        	} catch( Exception e ){
	        		JOptionPane.showMessageDialog(null, e.getMessage());
	        		e.printStackTrace();
	        	}
	        }
        }
        /* --------------------------------------------------- */
        /* exploitation du résultat */
        /* on construit les chemin à partir du résultat de TSP */

        /* On définit une liste permettant de stocker temporairement les
         * résultats jusqu'à être sûr qu'il n'y ait aucunes erreurs pour 
         * modifier le modèle
         */
        ArrayList<Chemin> lch = new ArrayList<Chemin>();
        
        /* On définit le départ à l'entrepot */
        int depart = NO_ENTREPOT;
        /* Donc on cherche quel est le prochain point de passage */
        int arrivee = tsp.getNext()[NO_ENTREPOT];

        /* On construit un chemin pour chaques livraison
         * De plus on établi une deuxième condition de sortie pour s'assurer
         * que l'on ne bouclera pas à l'infini
         */
        int nbMaxBoucle = tsp.getNext().length + 1;
        int nbBoucle = 0;
        do {
            PointDArret ptDep = this.mapRangVersPointDArret.get(depart);
            PointDArret ptArr = this.mapRangVersPointDArret.get(arrivee);
            
            Chemin ch = construireChemin(ptDep, ptArr);
            if(ch==null) {
            	throw new CommandeException("Erreur de construction d'un chemin"
            			+ "pour aller de " + ptDep.getLieu().getId() + " vers "
            					+ ptArr.getLieu().getId() + ".");
            }
            lch.add(ch);
            
            /* On passe au suivant */
            depart = arrivee;
            arrivee = tsp.getNext()[depart];
            
            /* On boucle tant que l'on est pas revenu à l'entrepot */
        } while (depart != NO_ENTREPOT && nbBoucle++ < nbMaxBoucle );
        
        if( nbBoucle == nbMaxBoucle )
        	throw new CommandeException("La construction du chemin de la "
        			+ "tournée n'a pas pû aboutir.");
        
        /* Maintenant que tous les points critiques ont été passés, on peut 
         * modifier le modèle.
         */
        for( Chemin ch : lch ){
            t.ajouterChemin(ch);
            for( Troncon troncon : ch.getTroncons() ){
            	troncon.ajouterChemin(ch);
            }
        }
        
        this.calculerItineraire();
        
        // on rafraichi la vue
        if( vueZone != null ){
        	vueDetailNoeud.actualiser();
        	vueZone.actualiserVue();
        	vueZone.repaint();
        } else {
        	throw new CommandeException("Erreur: vueZone null!");
        }
    }

    
    /*
    // pour les tests
    public CalculateurChemin getCalculateurChemin() {
        return calculateurChemin;
    }

    public Map<Integer, Noeud> getMapRangVersNoeud() {
        return this.mapRangVersNoeud;
    }

    public Map<Integer, Integer> getMapIdNoeudVersRang() {
        return this.mapIdNoeudVersRang;
    }

    public Map<Integer, PointDArret> getMapRangVersPointDArret() {
        return this.mapRangVersPointDArret;
    }

    public Map<Integer, Integer> getMapIdPointDArretVersRang() {
        return this.mapIdPointDArretVersRang;
    }

    public void setCalculateurChemin(CalculateurChemin calculateurChemin) {
        this.calculateurChemin = calculateurChemin;
    }*/
}
