package test.controleur_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import opti_fret_courly.controleur.CmdChargeDmd;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Tournee;
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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CmdChargeDmdTest {

    private static Noeud n1 = new Noeud(1, 1, 1);
    private static Noeud n2 = new Noeud(2, 2, 2);
    private static Noeud n3 = new Noeud(3, 3, 3);
    private static Noeud n4 = new Noeud(4, 4, 4);
    private static Noeud n5 = new Noeud(5, 5, 5);
    private static Noeud n6 = new Noeud(6, 6, 6);
    
    private static Zone zone;
    @Mock
    VueDetailNoeud vueDetailNoeud = mock(VueDetailNoeud.class);


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    	zone = new Zone();
        zone.getNoeuds().clear();
        assertTrue(zone.getNoeuds().isEmpty());
        Map<Integer, Noeud> noeuds = new HashMap<Integer, Noeud>();
        noeuds.put(new Integer(n1.getId()), n1);
        noeuds.put(new Integer(n2.getId()), n2);
        noeuds.put(new Integer(n3.getId()), n3);
        noeuds.put(new Integer(n4.getId()), n4);
        noeuds.put(new Integer(n5.getId()), n5);
        noeuds.put(new Integer(n6.getId()), n6);
        zone.setNoeuds(noeuds);

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

        zone.getNoeuds().clear();
        assertTrue(zone.getNoeuds().isEmpty());
    }

    @Before
    public void setUp() throws Exception {

        zone.setTournee(null);
        assertNull(zone.getTournee());
    }

    @After
    public void tearDown() throws Exception {

        zone.setTournee(null);
        assertNull(zone.getTournee());
    }

    @Test
    public void testExecuter1() throws Exception {

        //Chargement des demandes de livraisons avec 6 noeuds créées.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes1.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        try {
            cmd.executer();
        } catch (Exception e) {
            System.err.println(e);
            throw (e);
        }
        Tournee t = zone.getTournee();
        assertTrue(t.getPlagesHoraires().size() == 3);

        /*On verifie que les livraisons on ete associees aux bons noeuds
         * Et que les plages contienent les bonnes livraisons
         */
        assertEquals(t.getPlagesHoraires().get(0).getLivraisons().get(0).getAdresse().getLieu(), n2);
        assertEquals(t.getPlagesHoraires().get(0).getLivraisons().get(1).getAdresse().getLieu(), n3);
        assertEquals(t.getPlagesHoraires().get(1).getLivraisons().get(0).getAdresse().getLieu(), n5);
        assertEquals(t.getPlagesHoraires().get(2).getLivraisons().get(0).getAdresse().getLieu(), n4);
        assertEquals(t.getPlagesHoraires().get(2).getLivraisons().get(1).getAdresse().getLieu(), n6);

        /* On verifie que les livraisons contiennent leur plage*/
        assertEquals(t.getPlagesHoraires().get(0).getLivraisons().get(0).getPlageHoraire(),t.getPlagesHoraires().get(0));
        assertEquals(t.getPlagesHoraires().get(0).getLivraisons().get(1).getPlageHoraire(),t.getPlagesHoraires().get(0));
        assertEquals(t.getPlagesHoraires().get(1).getLivraisons().get(0).getPlageHoraire(),t.getPlagesHoraires().get(1));
        assertEquals(t.getPlagesHoraires().get(2).getLivraisons().get(0).getPlageHoraire(),t.getPlagesHoraires().get(2));
        assertEquals(t.getPlagesHoraires().get(2).getLivraisons().get(1).getPlageHoraire(),t.getPlagesHoraires().get(2));

        //fail("faut voir avec d��veloppeur comment v��rifier les plages");
    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter2() throws Exception {

        //Noeud d'entrepot n'existe pas.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes2.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud,fichier);
        cmd.executer();
    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter3() throws Exception {

        //Noeud d'une livraison n'existe pas.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes3.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud,fichier);
        cmd.executer();
    }

    @Ignore
    @Test(expected = SemantiqueException.class)
    public void testExecuter4() throws Exception {
        //Même id de livraison dans une plage.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes4.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter5() throws Exception {

        //Plages s'entrecroisent.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes5.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud,fichier);
        cmd.executer();
    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter6() throws Exception {

        //Plages s'entrecroisent.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes6.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter7() throws Exception {

        //Plages s'entrecroisent.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes7.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter8() throws Exception {

        //Plages s'entrecroisent.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes8.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = JDOMException.class)
    public void testExecuter9() throws Exception {

        //Manque l'entrepot.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes9.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter10() throws Exception {

        //Noeud de l'entrepot n'existe pas.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes10.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = SemantiqueException.class)
    public void testExecuter11() throws Exception {

        //Noeud du point de livraison n'existe pas.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes11.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = JDOMException.class)
    public void testExecuter12() throws Exception {

        //balise de livraison dehors balise de plage.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes12.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuter13() throws Exception {

        //Heure mal saisie (il manque ':')
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes13.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuter14() throws Exception {

        //manque des attributs.
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes14.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }
    
    @Test
    public void testExecuter15() throws Exception {

        //La tournee du jour est vide
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes15.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
        assertTrue(zone.getTournee().getPlagesHoraires().isEmpty());
    }
    
    @Test
    public void testExecuter16() throws Exception {

        //Un groupe de livraisons ne contient aucune livraison
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes16.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
        
        assertTrue(zone.getTournee().getPlagesHoraires().size()==1);
    }
    
    @Test
    public void testExecuter17() throws Exception {

        //Une plage est vide
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes17.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuter18() throws Exception {

        //Une heure est mal saisie (heures>23 ou minutes>59 ou secondes>59)
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes18.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
    }
    
    @Test
    public void testExecuter19() throws Exception {

        //Il n'y a pas de livraisons a effecuer pour la journee
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes19.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();
        assertTrue(zone.getTournee().getEntrepot().getLieu().equals(zone.getNoeudParId(1)));
        assertTrue(!zone.getTournee().aDesPlages());
    }
    
    @Test(expected = SemantiqueException.class)
    public void testExecuter20() throws Exception {

        //Deux livraisons au meme endroit
        VueZone vueZone = mock(VueZone.class);
        when(vueZone.getZone()).thenReturn(zone);
        String fichier = "src/test/demandes/demandes20.xml";

        CmdChargeDmd cmd = new CmdChargeDmd(vueZone,vueDetailNoeud, fichier);
        cmd.executer();

    }


}
