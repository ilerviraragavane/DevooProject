package test.modele_test;


import static org.junit.Assert.*;

import java.util.List;

import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Troncon;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class NoeudTest {

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

        Noeud n1 = new Noeud(1, 1, 1);
        Noeud n2 = new Noeud(2, 2, 2);
        Noeud n3 = new Noeud(3, 3, 3);

        Troncon t1 = new Troncon("nomRue1", 100.0, 234.3, n1, n2);
        Troncon t2 = new Troncon("nomRue1", 200.0, 234.3, n2, n1);

        //L'origine du tronçon t1 est Noeud n1.
        assertTrue(n1.ajouterTroncon(t1));
        assertTrue(!n2.ajouterTroncon(t1));

        //L'origine du tronçon t2 est Noeud n2.
        assertTrue(n2.ajouterTroncon(t2));
        assertTrue(!n2.ajouterTroncon(t1));

        //Noeud n3 n'est l'origine d'aucun tronçon.
        assertTrue(!n3.ajouterTroncon(t1));
        assertTrue(!n3.ajouterTroncon(t2));

        //L'ajout d'un tronçon null renvoit faux.
        assertTrue(!n3.ajouterTroncon(null));
        assertTrue(!n1.ajouterTroncon(null));


        Noeud n4 = new Noeud(4, 4, 4);
        Noeud n5 = new Noeud(5, 5, 5);
        Noeud n6 = new Noeud(6, 6, 6);
        Noeud n7 = new Noeud(7, 7, 7);

        Troncon t45 = new Troncon("nomRue45", 100.0, 234.3, n4, n5);
        Troncon t46 = new Troncon("nomRue46", 200.0, 234.3, n4, n6);
        Troncon t47 = new Troncon("nomRue47", 100.0, 234.3, n4, n7);
        Troncon t56 = new Troncon("nomRue56", 200.0, 234.3, n5, n6);

        //On simule differents cas d'ajout des tronçons.
        assertTrue(n4.ajouterTroncon(t46));
        assertTrue(n4.ajouterTroncon(t47));
        assertTrue(!n4.ajouterTroncon(null));
        assertTrue(!n4.ajouterTroncon(t56));
        assertTrue(n4.ajouterTroncon(t45));

        List<Troncon> ts = n4.getTronconsSortants();
        assertTrue(ts.size() == 3);
        assertEquals(ts.get(0), t46);
        assertEquals(ts.get(1), t47);
        assertEquals(ts.get(2), t45);
    }

    @Test
    public void testEquals() {

        Noeud n1 = new Noeud(1, 1, 1);
        Noeud n2 = new Noeud(2, 2, 2);
        Noeud n3 = new Noeud(1, 3, 3);
        Noeud n4 = new Noeud(4, 1, 1);
        Noeud n5 = new Noeud(1, 1, 1);

        assertTrue(n1.equals(n1));
        assertTrue(n1.equals(n5));
        assertTrue(!n1.equals(n2));
        assertTrue(!n1.equals(n3));
        assertTrue(!n1.equals(n4));
        assertTrue(!n1.equals(null));

    }
    
    @Test
    public void testGetTronconVers(){

        Noeud n1 = new Noeud(1, 1, 1);
        Noeud n2 = new Noeud(2, 2, 2);
        Noeud n3 = new Noeud(3, 3, 3);
        Noeud n4 = new Noeud(4, 4, 4);

        Troncon t12 = new Troncon("nomRue1", 100.0, 234.3, n1, n2);
        Troncon t21 = new Troncon("nomRue1", 200.0, 234.3, n2, n1);
        Troncon t13 = new Troncon("nomRue13", 200.0, 234.3, n1, n3);
        
        n1.ajouterTroncon(t12);
        n1.ajouterTroncon(t13);
        n2.ajouterTroncon(t21);
        
        assertEquals(n1.getTronconVers(n2),t12);
        assertEquals(n1.getTronconVers(n3),t13);
        assertEquals(n1.getTronconVers(n4),null);
        assertEquals(n2.getTronconVers(n1),t21);   	
    	
    }

}
