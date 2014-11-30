package test.modele_test;

import static org.junit.Assert.assertTrue;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Troncon;
import opti_fret_courly.outil.exception.CommandeException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TronconTest {

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
    public void testCalculerDuree1() throws CommandeException {

        Noeud n1 = new Noeud(1, 1, 1);
        Noeud n2 = new Noeud(2, 2, 2);
        Troncon t1 = new Troncon("nomRue1", 0.500000, 627.500000, n1, n2);
        assertTrue(t1.calculerDuree() == 1255);

        Noeud n3 = new Noeud(3, 1, 1);
        Noeud n4 = new Noeud(4, 2, 2);
        Troncon t2 = new Troncon("nomRue1", 0.500000, 0.000000, n3, n4);
        assertTrue(t2.calculerDuree() == 0);
    }

    @Test(expected = CommandeException.class)
    public void testCalculerDuree2() throws CommandeException {

        Noeud n1 = new Noeud(1, 1, 1);
        Noeud n2 = new Noeud(2, 2, 2);
        Troncon t1 = new Troncon("nomRue1", 0.000000, 627.500000, n1, n2);
        t1.calculerDuree();
    }

}
