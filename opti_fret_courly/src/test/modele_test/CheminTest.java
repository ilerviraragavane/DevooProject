package test.modele_test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import opti_fret_courly.modele.Chemin;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.modele.Troncon;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CheminTest {

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
    public void testAjouterTroncon() {

        //Faut voir avec développeur l'idée. Il faut d'abord fixer les points de départ et arrivée ou pas?
        Chemin c1 = new Chemin();
        assertEquals(c1.getTroncons().size(), 0);

        Noeud n1 = new Noeud(1, 1, 1);
        Noeud n2 = new Noeud(2, 2, 2);
        Noeud n3 = new Noeud(3, 3, 3);
        Noeud n4 = new Noeud(4, 4, 4);
        Noeud n5 = new Noeud(5, 5, 5);
        Noeud n6 = new Noeud(6, 6, 6);

        Troncon t12 = new Troncon("rue12", 1.0, 1.0, n1, n2);
        Troncon t23 = new Troncon("rue23", 1.0, 1.0, n2, n3);
        Troncon t34 = new Troncon("rue34", 1.0, 1.0, n3, n4);
        Troncon t56 = new Troncon("rue56", 1.0, 1.0, n5, n6);

        PointDeLivraison pointDDepart = mock(PointDeLivraison.class);
        when(pointDDepart.getLieu()).thenReturn(n1);
        PointDeLivraison pointDArrivee = mock(PointDeLivraison.class);
        when(pointDArrivee.getLieu()).thenReturn(n4);
        c1.setPointDep(pointDDepart);
        c1.setPointArr(pointDArrivee);

        assertTrue(c1.ajouterTroncon(t12));
        assertTrue(!c1.ajouterTroncon(null));
        assertTrue(c1.ajouterTroncon(t23));
        assertTrue(c1.ajouterTroncon(t56));
        assertTrue(c1.ajouterTroncon(t34));

        List<Troncon> ts = c1.getTroncons();
        assertEquals(ts.size(), 4);
        assertEquals(ts.get(0), t12);
        assertEquals(ts.get(1), t23);
        assertEquals(ts.get(2), t56);
        assertEquals(ts.get(3), t34);

    }

    @Test
    public void equalsTest() {

        Noeud n1 = new Noeud(1, 1, 1);
        Noeud n2 = new Noeud(2, 2, 2);
        Troncon t12 = new Troncon("rue12", 0.500000, 627.500000, n1, n2);

        Chemin c1 = new Chemin();
        Chemin c2 = new Chemin();
        Chemin c3 = new Chemin();
        c1.ajouterTroncon(t12);
        c3.ajouterTroncon(t12);

        assertTrue(c1.equals(c1));
        assertTrue(!c1.equals(c2));
        assertTrue(!c1.equals(null));
        assertTrue(c1.equals(c3));
    }

}
