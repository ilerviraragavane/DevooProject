package test.modele_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import opti_fret_courly.modele.Chemin;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.modele.Tournee;
import opti_fret_courly.modele.Troncon;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TourneeTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAjouterPlage1() {

        Tournee t1 = new Tournee();
        assertTrue(t1.getPlagesHoraires().size() == 0);

        Plage p1 = mock(Plage.class);
        Plage p2 = mock(Plage.class);
        t1.ajouterPlage(p2);
        t1.ajouterPlage(p1);

        assertTrue(t1.getPlagesHoraires().size() == 2);
        assertEquals(t1.getPlagesHoraires().get(0), p2);
        assertEquals(t1.getPlagesHoraires().get(1), p1);

    }

    @Test
    public void testAjouterPlage2() {

        //ajouter null		
        Tournee t1 = new Tournee();
        assertTrue(t1.getPlagesHoraires().size() == 0);
        assertTrue(!t1.ajouterPlage(null));
    }

    @Test
    public void testSupprimerPlage1() {

        Tournee t1 = new Tournee();
        assertTrue(t1.getPlagesHoraires().size() == 0);

        Plage p1 = mock(Plage.class);
        Plage p2 = mock(Plage.class);
        Plage p3 = mock(Plage.class);

        t1.ajouterPlage(p1);
        t1.ajouterPlage(p2);
        t1.ajouterPlage(p3);

        assertTrue(t1.getPlagesHoraires().size() == 3);
        assertEquals(t1.getPlagesHoraires().get(0), p1);
        assertEquals(t1.getPlagesHoraires().get(1), p2);
        assertEquals(t1.getPlagesHoraires().get(2), p3);

        t1.supprimerPlage(p2);
        assertTrue(t1.getPlagesHoraires().size() == 2);
        assertEquals(t1.getPlagesHoraires().get(0), p1);
        assertEquals(t1.getPlagesHoraires().get(1), p3);

        t1.supprimerPlage(p3);
        assertTrue(t1.getPlagesHoraires().size() == 1);
        assertEquals(t1.getPlagesHoraires().get(0), p1);
    }

    @Test
    public void testSupprimerPlage2() {

        //Supprimer une plage qui n'est pas dans la tourn��e.		
        Tournee t1 = new Tournee();
        assertTrue(t1.getPlagesHoraires().size() == 0);

        Plage p1 = mock(Plage.class);
        Plage p2 = mock(Plage.class);
        Plage p3 = mock(Plage.class);

        t1.ajouterPlage(p1);
        t1.ajouterPlage(p2);

        assertTrue(!t1.supprimerPlage(p3));
        assertTrue(!t1.supprimerPlage(null));
    }

    @Test
    public void testSupprimerPlage3() {

        //Supprimer null		
        Tournee t1 = new Tournee();
        assertTrue(t1.getPlagesHoraires().size() == 0);

        Plage p1 = mock(Plage.class);
        Plage p2 = mock(Plage.class);

        t1.ajouterPlage(p1);
        t1.ajouterPlage(p2);

        assertTrue(!t1.supprimerPlage(null));
    }

    @Test
    public void testAjouterChemin() {

        Tournee t1 = new Tournee();
        Chemin c1 = new Chemin();
        Chemin c2 = new Chemin();
        Troncon troncon1 = new Troncon("rue1",3.3,2.2,null,null);
        c2.ajouterTroncon(troncon1);

        assertTrue(t1.ajouterChemin(c1));
        assertTrue(t1.ajouterChemin(c2));
        assertTrue(!t1.ajouterChemin(c1));
        assertTrue(!t1.ajouterChemin(c2));
        assertTrue(!t1.ajouterChemin(null));
    }

    @Test
    public void testSupprimerChemin() {

        Tournee t1 = new Tournee();
        Chemin c1 = new Chemin();
        Chemin c2 = new Chemin();
        Troncon troncon1 = new Troncon("rue1",3.3,2.2,null,null);
        c2.ajouterTroncon(troncon1);
        Chemin c3 = new Chemin();
        assertTrue(t1.ajouterChemin(c1));
        assertTrue(t1.ajouterChemin(c2));

        assertTrue(t1.supprimerChemin(c1));
        assertTrue(!t1.supprimerChemin(c3));
        assertTrue(!t1.supprimerChemin(null));
        assertTrue(t1.supprimerChemin(c2));

    }

}
