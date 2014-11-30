package test.vue_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;

import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.vue.VuePointLivraison;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class VuePointLivraisonTest {

    @Mock
    private static Noeud lieu = mock(Noeud.class);

    @Mock
    private static PointDeLivraison ptLiv = mock(PointDeLivraison.class);

    @InjectMocks
    private static VuePointLivraison vue = new VuePointLivraison(ptLiv);

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
    public void testEstClique() {
        when(ptLiv.getLieu()).thenReturn(lieu);
        when(lieu.getX()).thenReturn(1);
        when(lieu.getY()).thenReturn(1);

        Point p = new Point(lieu.getX(), lieu.getY());
        System.out.println(" x=" + p.getX() + " ; y=" + p.getY());
        assertTrue(vue.estClique(p));

        p = new Point(lieu.getX() + 2, lieu.getY() + 2);
        System.out.println(" x=" + p.getX() + " ; y=" + p.getY());
        assertTrue(vue.estClique(p));

        p = new Point(lieu.getX() + 10, lieu.getY() + 15);
        System.out.println(" x=" + p.getX() + " ; y=" + p.getY());
        assertFalse(vue.estClique(p));
    }

}
