package test.controleur_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.List;

import opti_fret_courly.controleur.CmdAjout;
import opti_fret_courly.controleur.Createur;
import opti_fret_courly.modele.Destinataire;
import opti_fret_courly.modele.EltCarte;
import opti_fret_courly.modele.Entrepot;
import opti_fret_courly.modele.Horaire;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.modele.Tournee;
import opti_fret_courly.modele.Troncon;
import opti_fret_courly.modele.Zone;
import opti_fret_courly.outil.exception.CommandeException;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CmdAjoutTest {

    static Noeud n1 = new Noeud(1, 1, 1);
    static Noeud n2 = new Noeud(2, 2, 2);
    static Noeud n3 = new Noeud(3, 3, 3);
    static Noeud n4 = new Noeud(4, 4, 4);
    static Noeud n5 = new Noeud(5, 5, 5);
    static Noeud n6 = new Noeud(6, 6, 6);

    static Troncon t12 = new Troncon("run12", 1.0, 1.0, n1, n2);
    static Troncon t21 = new Troncon("run12", 1.0, 1.0, n2, n1);
    static Troncon t23 = new Troncon("run23", 1.0, 1.0, n2, n3);
    static Troncon t34 = new Troncon("run34", 1.0, 1.0, n3, n4);
    static Troncon t45 = new Troncon("run45", 1.0, 1.0, n4, n5);
    static Troncon t56 = new Troncon("run56", 1.0, 1.0, n5, n6);
    static Troncon t61 = new Troncon("run61", 1.0, 1.0, n6, n1);

    static Destinataire d2 = new Destinataire((int) 2);
    static PointDeLivraison pdl2 = new PointDeLivraison(n2);
    static Livraison l2 = new Livraison(d2, pdl2);

    static Destinataire d3 = new Destinataire((int) 3);
    static PointDeLivraison pdl3 = new PointDeLivraison(n3);
    static Livraison l3 = new Livraison(d3, pdl3);

    static Destinataire d4 = new Destinataire((int) 4);
    static PointDeLivraison pdl4 = new PointDeLivraison(n4);
    static Livraison l4 = new Livraison(d4, pdl4);

    static Calendar debut1 = Calendar.getInstance();
    static Calendar fin1 = Calendar.getInstance();
    static Horaire h1 = new Horaire(debut1, fin1);
    static Plage p1 = new Plage(h1);

    static Calendar debut2 = Calendar.getInstance();
    static Calendar fin2 = Calendar.getInstance();
    static Horaire h2 = new Horaire(debut2, fin2);
    static Plage p2 = new Plage(h2);

    static Entrepot e = new Entrepot(n1);

    @Mock
    static Createur createur = mock(Createur.class);
    
    static VueZone vueZone = new VueZone(createur);
    
    static VueDetailNoeud vueDetailNoeud = new VueDetailNoeud(createur);



    @Before
    public void setUp() throws Exception {

        Zone z = vueZone.getZone();
        z.setTournee(new Tournee());
        z.getTournee().getPlagesHoraires().clear();
        assertTrue(z.getTournee().getPlagesHoraires().isEmpty());
        z.getNoeuds().clear();
        assertTrue(z.getNoeuds().isEmpty());

        n1.ajouterTroncon(t12);
        n2.ajouterTroncon(t21);
        n2.ajouterTroncon(t23);
        n3.ajouterTroncon(t34);
        n4.ajouterTroncon(t45);
        n5.ajouterTroncon(t56);
        n6.ajouterTroncon(t61);

        pdl2.setLivraison(l2);
        pdl3.setLivraison(l3);
        pdl4.setLivraison(l4);

        debut1.set(2000, 7, 31, 8, 0, 0);
        fin1.set(2000, 7, 31, 9, 0, 0);
        debut2.set(2000, 7, 31, 10, 0, 0);
        fin2.set(2000, 7, 31, 11, 0, 0);
        
        l2.setPlageHoraire(p1);
        l3.setPlageHoraire(p2);
        l4.setPlageHoraire(p2);

        p1.ajouterLivraison(l2);
        p2.ajouterLivraison(l3);
        p2.ajouterLivraison(l4);

        z.getTournee().ajouterPlage(p1);
        z.getTournee().ajouterPlage(p2);
        z.getTournee().setEntrepot(e);
        
        /*On efface les millisecondes*/
        fin2.clear(Calendar.MILLISECOND);
        fin1.clear(Calendar.MILLISECOND);
        debut2.clear(Calendar.MILLISECOND);
        debut1.clear(Calendar.MILLISECOND);
        
        when(createur.dmdSelection(any(EltCarte.class))).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {

        Zone z = vueZone.getZone();
        
        List<Plage> ph = z.getTournee().getPlagesHoraires();
        for(Plage p : ph){
        	p.getLivraisons().clear();
        	assertTrue(p.getLivraisons().isEmpty());
        }        
        z.getTournee().getPlagesHoraires().clear();
        assertTrue(z.getTournee().getPlagesHoraires().isEmpty());
        
        n1.getTronconsSortants().clear();
        n2.getTronconsSortants().clear();
        n3.getTronconsSortants().clear();
        n4.getTronconsSortants().clear();
        n5.getTronconsSortants().clear();
        n6.getTronconsSortants().clear();
        z.getNoeuds().clear();
        assertTrue(z.getNoeuds().isEmpty());
    }

    @Test
    public void testExecuter1() throws CommandeException {

        /*Ajout d'une livraison dans une plage qui existe déjà.*/
        Zone z = vueZone.getZone();

        Destinataire d5 = new Destinataire((int) 5);
        PointDeLivraison pdl5 = new PointDeLivraison(n5);
        Livraison l5 = new Livraison(d5, pdl5);
        pdl5.setLivraison(l5);


        CmdAjout cmd = new CmdAjout(vueZone, vueDetailNoeud, l5, p2, false);
        cmd.executer();

        Plage p = z.getTournee().getPlagesHoraires().get(1);
        assertEquals(p, p2);
        assertTrue(p.getLivraisons().size() == 3);
        assertEquals(p.getLivraisons().get(0), l3);
        assertEquals(p.getLivraisons().get(1), l4);
        assertEquals(p.getLivraisons().get(2), l5);
    }

    @Test
    public void testExecuter2() throws CommandeException {

        /*Ajout d'une livraison dans une nouvelle plage.*/
        Destinataire d5 = new Destinataire((int) 5);
        PointDeLivraison pdl5 = new PointDeLivraison(n5);
        Livraison l5 = new Livraison(d5, pdl5);
        pdl5.setLivraison(l5);

        Calendar debut3 = Calendar.getInstance();
        debut3.set(2000, 7, 31, 11, 0, 0);
        Calendar fin3 = Calendar.getInstance();
        fin3.set(2000, 7, 31, 12, 0, 0);
        fin3.clear(Calendar.MILLISECOND);
        debut3.clear(Calendar.MILLISECOND);
        
        Horaire h3 = new Horaire(debut3, fin3);
        Plage p3 = new Plage(h3);

        CmdAjout cmd = new CmdAjout(vueZone, vueDetailNoeud, l5, p3, true);
        cmd.executer();

        Zone z = vueZone.getZone();
        assertTrue(z.getTournee().getPlagesHoraires().size() == 3);
        Plage p = z.getTournee().getPlagesHoraires().get(2);
        assertEquals(p, p3);
        assertTrue(p.getLivraisons().size() == 1);
        assertEquals(p.getLivraisons().get(0), l5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuter3() throws Exception {

        /*Ajout d'une livraison null.
        A créer une exception pour ce cas.*/        
    	CmdAjout cmd = new CmdAjout(vueZone, vueDetailNoeud, null, p2, true);
        cmd.executer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuter4() throws Exception {

        /*Ajout d'une livraison dans une plage null.
        A créer une exception pour ce cas.*/        
    	Destinataire d5 = new Destinataire((int) 5);
        PointDeLivraison pdl5 = new PointDeLivraison(n5);
        Livraison l5 = new Livraison(d5, pdl5);

        CmdAjout cmd = new CmdAjout(vueZone, vueDetailNoeud, l5, null, true);
        cmd.executer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuter5() throws Exception {

        /*Ajout d'une livraison avec vueZone null.
        A créer une exception pour ce cas.*/        
    	Destinataire d5 = new Destinataire((int) 5);
        PointDeLivraison pdl5 = new PointDeLivraison(n5);
        Livraison l5 = new Livraison(d5, pdl5);

        CmdAjout cmd = new CmdAjout(null, vueDetailNoeud, l5, p2, true);
        cmd.executer();
    }

    @Test
    public void testAnnuler1() throws Exception {

        /*Suppresion d'une livraison d'une plage qui existe déjà.*/
        CmdAjout cmd = new CmdAjout(vueZone, vueDetailNoeud, l3, p2, false);
        cmd.annuler();

        Zone z = vueZone.getZone();
        assertTrue(z.getTournee().getPlagesHoraires().size() == 2);
        Plage p = z.getTournee().getPlagesHoraires().get(1);
        assertEquals(p, p2);
        System.out.print(p.getLivraisons().size());
        assertTrue(p.getLivraisons().size() == 1);
        assertEquals(p.getLivraisons().get(0), l4);

    }

    @Test
    public void testAnnuler2() throws Exception {

        /*Suppresion d'une livraison d'une nouvelle plage.*/
        CmdAjout cmd = new CmdAjout(vueZone, vueDetailNoeud, l2, p1, true);
        cmd.annuler();

        Zone z = vueZone.getZone();
        assertTrue(z.getTournee().getPlagesHoraires().size() == 1);
        Plage p = z.getTournee().getPlagesHoraires().get(0);
        assertEquals(p, p2);
        assertTrue(p.getLivraisons().size() == 2);
        assertEquals(p.getLivraisons().get(0), l3);
        assertEquals(p.getLivraisons().get(1), l4);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnnuler3() throws Exception {

        /*Suppresion d'une livraison null.*/
        CmdAjout cmd = new CmdAjout(vueZone, vueDetailNoeud, null, p2, true);
        cmd.annuler();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnnuler4() throws Exception {

        /*Suppresion d'une livraison d'une plage null.*/
        CmdAjout cmd = new CmdAjout(vueZone, vueDetailNoeud, l3, null, true);
        cmd.annuler();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnnuler5() throws CommandeException {

        /*Suppresion d'une livraison avec vueZone null.*/
        CmdAjout cmd = new CmdAjout(null,  vueDetailNoeud,l3, p1, true);
        cmd.annuler();

    }

}
