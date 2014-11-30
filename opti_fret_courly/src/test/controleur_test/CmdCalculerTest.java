package test.controleur_test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import opti_fret_courly.controleur.CmdCalculer;
import opti_fret_courly.modele.Chemin;
import opti_fret_courly.modele.Entrepot;
import opti_fret_courly.modele.Horaire;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.modele.Tournee;
import opti_fret_courly.modele.Troncon;
import opti_fret_courly.modele.Zone;
import opti_fret_courly.outil.calcul.CalculateurChemin;
import opti_fret_courly.outil.exception.CommandeException;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueZone;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CmdCalculerTest {

    @Mock
    private static Noeud n1 = mock(Noeud.class);

    @Mock
    private static Noeud n2 = mock(Noeud.class);

    @Mock
    private static Entrepot e = mock(Entrepot.class);

    @Mock
    private static Troncon t1 = mock(Troncon.class);

    @Mock
    private static Troncon t2 = mock(Troncon.class);

    @Mock
    private static Zone zone = mock(Zone.class);

    @Mock
    private static VueZone vueZone = mock(VueZone.class);
    @Mock
    private static VueDetailNoeud vueDetailNoeud = mock(VueDetailNoeud.class);

    @Mock
    private static CalculateurChemin cc = mock(CalculateurChemin.class);

    @Mock
    private static Tournee tournee;
    
    @Mock
    private static List<Chemin> listChemins;

    @Mock
    private static Plage plagehoraire = mock(Plage.class);
    
    @Mock
    private static Horaire creneau = mock(Horaire.class);
    
    private static Calendar calDebut = new GregorianCalendar();
    private static Calendar calFin = new GregorianCalendar();
    

    @Mock
    private static Horaire creneauEntrepot = mock(Horaire.class);
    
    private static Calendar calDebutEntrepot = new GregorianCalendar();
    private static Calendar calFinEntrepot = new GregorianCalendar();

    @Mock
    private static Horaire creneauLiv = mock(Horaire.class);
    
    private static Calendar calDebutLiv = new GregorianCalendar();
    private static Calendar calFinLiv = new GregorianCalendar();

    @Mock
    private static Livraison livraison = mock(Livraison.class);

    @Mock
    private static PointDeLivraison ptLivraison = mock(PointDeLivraison.class);

    @InjectMocks
    private static CmdCalculer c;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        c = new CmdCalculer(vueZone,vueDetailNoeud, zone, 3000);
        listChemins = new ArrayList<Chemin>();
        tournee = mock(Tournee.class);
        
        // définition des éléments
       
        // vueZone
        when(vueZone.getZone()).thenReturn(zone);
        
        // zone
        Map<Integer, Noeud> map = new HashMap<Integer, Noeud>();
        map.put(1, n1);
        map.put(2, n2);
        when(zone.getNoeuds()).thenReturn(map);
        when(zone.getNoeudParId(1)).thenReturn(n1);
        when(zone.getNoeudParId(2)).thenReturn(n2);
        when(zone.getTournee()).thenReturn(tournee);

        // n1
        List<Troncon> lt1 = new ArrayList<Troncon>();
        lt1.add(t1);
        when(n1.getTronconsSortants()).thenReturn(lt1);
        when(n1.getId()).thenReturn(1);
        when(n1.getTronconVers(n2)).thenReturn(t1);

        // n2
        List<Troncon> lt2 = new ArrayList<Troncon>();
        lt2.add(t2);
        when(n2.getTronconsSortants()).thenReturn(lt2);
        when(n2.getId()).thenReturn(2);
        when(n2.getTronconVers(n1)).thenReturn(t2);

        // t1
        when(t1.calculerDuree()).thenReturn(1);
        when(t1.getFin()).thenReturn(n2);
        when(t1.getOrigine()).thenReturn(n1);
        List<Chemin> lch1 = new ArrayList<Chemin>();
        when(t1.getChemins()).thenReturn(lch1);

        // t2
        when(t2.calculerDuree()).thenReturn(1);
        when(t2.getFin()).thenReturn(n1);
        when(t2.getOrigine()).thenReturn(n2);
        List<Chemin> lch2 = new ArrayList<Chemin>();
        when(t2.getChemins()).thenReturn(lch2);

        // e
        when(e.getLieu()).thenReturn(n1);
        when(e.getHeuresPassage()).thenReturn(creneauEntrepot);

        // tournee
        when(tournee.getEntrepot()).thenReturn(e);
        List<Plage> lp = new ArrayList<Plage>();
        lp.add(plagehoraire);
        when(tournee.getPlagesHoraires()).thenReturn(lp);
        when(tournee.getChemins()).thenReturn(listChemins);
        when(tournee.ajouterChemin(any(Chemin.class))).thenCallRealMethod();

        // plage horaire
        List<Livraison> ll = new ArrayList<Livraison>();
        ll.add(livraison);
        when(plagehoraire.getLivraisons()).thenReturn(ll);
        when(plagehoraire.getCreneau()).thenReturn(creneau);

        // creneau
        when(creneau.getDebut()).thenReturn(calDebut);
        when(creneau.getFin()).thenReturn(calFin);

        // creneauLiv
        when(creneauLiv.getDebut()).thenReturn(calDebutLiv);
        when(creneauLiv.getFin()).thenReturn(calFinLiv);        
        
        // creneauEntrepot
        when(creneauEntrepot.getDebut()).thenReturn(calDebutEntrepot);
        when(creneauEntrepot.getFin()).thenReturn(calFinEntrepot);

        // livraison
        when(livraison.getAdresse()).thenReturn(ptLivraison);
        when(livraison.getPlageHoraire()).thenReturn(plagehoraire);
        when(livraison.getHeuresPassage()).thenReturn(creneauLiv);

        // pt de livraison
        when(ptLivraison.getLieu()).thenReturn(n2);
        when(ptLivraison.getLivraison()).thenReturn(livraison);
        
        //vue detail
        doNothing().when(vueDetailNoeud).actualiser();

    }

    @After
    public void tearDown() throws Exception {
    }
    
    
    /*Décommenter tous les tests ci-dessous quand les méthodes 
    getCalculateurChemin(), getMapRangVersNoeud(), getMapIdNoeudVersRang(),
    getMapRangVersPointDArret(), getMapIdPointDArretVersRang() 
    et setCalculateurChemin() de la classe CmdCalculer sont décommentées*/
    
    /*
    @Test
    public void testConstruireMappingNoeud() throws CommandeException {

        Map<Integer, Noeud> m = new HashMap<Integer, Noeud>();
        m.put(0, n1);
        m.put(1, n2);
        Map<Integer, Integer> m2 = new HashMap<Integer, Integer>();
        m2.put(1, 0);
        m2.put(2, 1);

        c.construireMappingNoeud();
        assertTrue(c.getMapRangVersNoeud().equals(m));
        assertTrue(c.getMapIdNoeudVersRang().equals(m2));
    }
    //*/
    /*	
    @Test
    public void testConstruireMappingPointDArret() throws CommandeException {

        Map<Integer, PointDArret> m = new HashMap<Integer, PointDArret>();
        m.put(0, e);
        m.put(1, ptLivraison);
        Map<Integer, Integer> m2 = new HashMap<Integer, Integer>();
        m2.put(1, 0);
        m2.put(2, 1);

        c.construireMappingPointDArret();
        assertTrue(c.getMapRangVersPointDArret().equals(m));
        assertTrue(c.getMapIdPointDArretVersRang().equals(m2));
    }
    //*/
    /*	
    @Test
    public void testConstruireCalculateurChemin() throws CommandeException {

        c.construireCalculateurChemin();

        int[][] m = c.getCalculateurChemin().getMatrice();
        assertTrue(m[0][0] <= -1 
        	&& m[0][1] == 1
        	&& m[1][0] <= 1 
        	&& m[1][1] <= -1);
    }
    //*/
    /*
    @Test
    public void testConstruireChemin() throws CommandeException {

        // ----------------
        // Test 1

        //définition de calculateurChemin
        List<Integer> suivants = new ArrayList<Integer>();
        suivants.add(0);
        suivants.add(1);
        when(cc.getSuivants()).thenReturn(suivants);
        c.setCalculateurChemin(cc);

        Chemin chemin = mock(Chemin.class);
        List<Troncon> lt = new ArrayList<Troncon>();
        lt.add(t1);

        when(chemin.getTroncons()).thenReturn(lt);
        when(chemin.getPointDep()).thenReturn(e);
        when(chemin.getPointArr()).thenReturn(ptLivraison);

        c.construireMappingNoeud();
        c.construireMappingPointDArret();

        Chemin ch = c.construireChemin(e, ptLivraison);
        assertTrue(ch.equals(chemin));

        // --------------
        // Test 2

        // modification du résultat mocké du calculateur
        suivants.clear();
        suivants.add(1);
        suivants.add(0);

        lt.clear();
        lt.add(t2);

        when(chemin.getTroncons()).thenReturn(lt);
        when(chemin.getPointDep()).thenReturn(ptLivraison);
        when(chemin.getPointArr()).thenReturn(e);

        ch = c.construireChemin(ptLivraison, e);
        assertTrue(ch.equals(chemin));
        
         
    }
    //*/

    @Test
    public void testExecuter1() throws CommandeException {
    	c.executer();
    	
    	Chemin ch1 = mock(Chemin.class);
    	when(ch1.getPointDep()).thenReturn(e);
    	when(ch1.getPointArr()).thenReturn(ptLivraison);
    	List<Troncon> lt1 = new ArrayList<Troncon>();
    	lt1.add(t1);
    	when(ch1.getTroncons()).thenReturn(lt1);

    	Chemin ch2 = mock(Chemin.class);
    	when(ch2.getPointDep()).thenReturn(ptLivraison);
    	when(ch2.getPointArr()).thenReturn(e);
    	List<Troncon> lt2 = new ArrayList<Troncon>();
    	lt2.add(t2);
    	when(ch2.getTroncons()).thenReturn(lt2);
    	
    	assertTrue(listChemins.get(0).equals(ch1));
    	assertTrue(listChemins.get(1).equals(ch2));
    }

    @Test
    public void testExecuter2() throws CommandeException {
    	c.executer();
    	
    	Chemin ch1 = mock(Chemin.class);
    	when(ch1.getPointDep()).thenReturn(e);
    	when(ch1.getPointArr()).thenReturn(ptLivraison);
    	List<Troncon> lt1 = new ArrayList<Troncon>();
    	lt1.add(t1);
    	when(ch1.getTroncons()).thenReturn(lt1);

    	Chemin ch2 = mock(Chemin.class);
    	when(ch2.getPointDep()).thenReturn(ptLivraison);
    	when(ch2.getPointArr()).thenReturn(e);
    	List<Troncon> lt2 = new ArrayList<Troncon>();
    	lt2.add(t2);
    	when(ch2.getTroncons()).thenReturn(lt2);
    	
    	assertTrue(listChemins.get(0).equals(ch1));
    	assertTrue(listChemins.get(1).equals(ch2));
    }
    
    /*
    @Test
    public void testConstruireGraphe() throws CommandeException {
        Graphe graphe = mock(Graphe.class);
        int[][] matrice = new int[2][2];
        matrice[0][0] = 2;
        matrice[0][1] = 1;
        matrice[1][0] = 1;
        matrice[1][1] = 2;
        when(graphe.getCost()).thenReturn(matrice);
        int[] res0 = new int[1];
        res0[0] = 1;
        when(graphe.getSucc(0)).thenReturn(res0);
        int[] res1 = new int[1];
        res1[0] = 0;
        when(graphe.getSucc(1)).thenReturn(res1);
        when(graphe.getMaxArcCost()).thenReturn(1);
        when(graphe.getMinArcCost()).thenReturn(1);
        when(graphe.getNbVertices()).thenReturn(2);

        c.construireCalculateurChemin();

        Graphe g = c.construireGraphe();
        assertTrue(g.equals(graphe));
    }
    //*/
}
