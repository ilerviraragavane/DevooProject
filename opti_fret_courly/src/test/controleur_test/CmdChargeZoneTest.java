package test.controleur_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;

import opti_fret_courly.controleur.CmdChargeZone;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Zone;
import opti_fret_courly.outil.exception.SemantiqueException;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueZone;

import org.jdom2.JDOMException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CmdChargeZoneTest {

	static Zone zone;
	static VueDetailNoeud vueDetailNoeud;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {

        zone = new Zone();
        zone.getNoeuds().clear();
        assertTrue(zone.getNoeuds().isEmpty());

        vueDetailNoeud = mock(VueDetailNoeud.class);
        doNothing().when(vueDetailNoeud).setVisible(any(Boolean.class));
    }

    @After
    public void tearDown() throws Exception {

        zone.getNoeuds().clear();
        assertTrue(zone.getNoeuds().isEmpty());
    }

    @Test
    public void testExecuter1() throws Exception {

        //Cas simple avec un plan de deux noeuds et deux troncon
        String plan1 = "src/test/plans/plan1.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan1);

        cmd.executer();

        assertTrue(zone.getNoeuds().size() == 2);

        Noeud n0 = zone.getNoeudParId(0);
        Noeud n1 = zone.getNoeudParId(1);
        assertTrue(n0.getTronconsSortants().size() == 1);
        assertTrue(n1.getTronconsSortants().size() == 1);
        assertEquals(n0.getTronconsSortants().get(0).getFin(), n1);
        assertEquals(n1.getTronconsSortants().get(0).getFin(), n0);

    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter2() throws Exception {

        //Creer un troncon avec un noeud qui n'existe pas.
        String plan = "src/test/plans/plan2.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);

        try {
            cmd.executer();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }

    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter3() throws Exception {

        //Créer deux tronçons avec le même point de départ et le même point 
    	//	d'arrivée.
        String plan = "src/test/plans/plan3.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }

    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter4() throws Exception {

        //Créer deux noeuds avec le même id.
        String plan = "src/test/plans/plan4.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }

    }

    @Test(expected = JDOMException.class)
    public void testExecuter5() throws Exception {

        //Erreur syntaxique. Sans balise "Reseau".
        String plan = "src/test/plans/plan5.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }

    }

    /*	@Test(expected = ExceptionXML.class)
	public void testExecuter6() throws ExceptionXML {
		
		//Erreur syntaxique. Sans tête du fichier xml.
		String plan = "src/test/plans/plan6.xml";
		VueZone vueZone = mock(VueZone.class);
		CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
		
		cmd.executer();
		
	}*/

    @Test(expected = JDOMException.class)
    public void testExecuter7() throws Exception {

        //Erreur syntaxique. Perdre quelques charactères.
        String plan = "src/test/plans/plan7.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);

        try {
            cmd.executer();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }

    }

    @Test(expected = JDOMException.class)
    public void testExecuter8() throws Exception {

        //Erreur syntaxique. Charactères bizarres dans le fichier.
        String plan = "src/test/plans/plan8.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);

        try {
            cmd.executer();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuter9() throws Exception {

        //Erreur syntaxique. attributs vides.
        String plan = "src/test/plans/plan9.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);

        try {
            cmd.executer();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }

    }

    @Test(expected = JDOMException.class)
    public void testExecuter10() throws Exception {

        //Erreur syntaxique. manque des attributs.
        String plan = "src/test/plans/plan10.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }

    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter11() throws Exception {

        //Troncon circulaire
        String plan = "src/test/plans/plan11.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (SemantiqueException se) {
            System.err.println(se);
            throw se;
        }
    }

    //identique au n°3 !
    @Ignore
    @Test(expected = SemantiqueException.class)
    public void testExecuter12() throws Exception {

        //2 Troncons ayant meme noeud de depart et meme noeud d'arrivee
        String plan = "src/test/plans/plan12.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (SemantiqueException se) {
            System.err.println(se);
            throw se;
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void testExecuter13() throws Exception {

        //Fichier introuvable
        String plan = "src/test/plans/plafdffn12.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (IOException ioe) {
            System.err.println(ioe);
            throw ioe;
        }
    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter14() throws Exception {

        //Noeuds qui ont la meme position x,y
        String plan = "src/test/plans/plan14.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (SemantiqueException se) {
            System.err.println(se);
            throw se;
        }
    }
    
    @Test(expected = JDOMException.class)
    public void testExecuter18() throws Exception {

        //Noeud qui n'a pas de troncon sortant
        String plan = "src/test/plans/plan18.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (SemantiqueException se) {
            System.err.println(se);
            throw se;
        }
    }
    
    @Test(expected = SemantiqueException.class)
    public void testExecuter17() throws Exception {

        //Noeud qui n'est accessible depuis aucun troncon
        String plan = "src/test/plans/plan17.xml";
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        CmdChargeZone cmd = new CmdChargeZone(vueZone, vueDetailNoeud, plan);
        try {
            cmd.executer();
        } catch (SemantiqueException se) {
            System.err.println(se);
            throw se;
        }
    }
    
  
}
