package test.modele_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;

import java.util.Calendar;

import opti_fret_courly.modele.Horaire;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Plage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlageTest {

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
    public void testSupprimerLivraison1() {

        Calendar debut = Calendar.getInstance();
        debut.set(2000, 7, 31, 10, 0, 0); //2000-8-31
        Calendar fin = Calendar.getInstance();
        fin.set(2000, 7, 31, 11, 0, 0);
        Horaire h1 = new Horaire(debut, fin);
        Plage p1 = new Plage(h1);

        Livraison l1 = mock(Livraison.class);
        Livraison l2 = mock(Livraison.class);

        assertTrue(p1.ajouterLivraison(l1));
        assertTrue(p1.ajouterLivraison(l2));

        assertTrue(p1.getLivraisons().size() == 2);
        assertEquals(p1.getLivraisons().get(0), l1);
        assertEquals(p1.getLivraisons().get(1), l2);

        assertTrue(p1.supprimerLivraison(l1));

        assertTrue(p1.getLivraisons().size() == 1);
        assertEquals(p1.getLivraisons().get(0), l2);


    }

    @Test
    public void testSupprimerLivraison2() {

        //Supprimer une livraison qui n'est pas dans la plage.
        Calendar debut = Calendar.getInstance();
        debut.set(2000, 7, 31, 10, 0, 0); //2000-8-31
        Calendar fin = Calendar.getInstance();
        fin.set(2000, 7, 31, 11, 0, 0);
        Horaire h1 = new Horaire(debut, fin);
        Plage p1 = new Plage(h1);

        Livraison l1 = mock(Livraison.class);
        Livraison l2 = mock(Livraison.class);

        assertTrue(p1.ajouterLivraison(l1));

        assertTrue(p1.getLivraisons().size() == 1);
        assertEquals(p1.getLivraisons().get(0), l1);

        assertTrue(!p1.supprimerLivraison(l2));
    }

    @Test
    public void testSupprimerLivraison3() {

        //Supprimer null
        Calendar debut = Calendar.getInstance();
        debut.set(2000, 7, 31, 10, 0, 0); //2000-8-31
        Calendar fin = Calendar.getInstance();
        fin.set(2000, 7, 31, 11, 0, 0);
        Horaire h1 = new Horaire(debut, fin);
        Plage p1 = new Plage(h1);

        Livraison l1 = mock(Livraison.class);

        assertTrue(p1.ajouterLivraison(l1));

        assertTrue(p1.getLivraisons().size() == 1);
        assertEquals(p1.getLivraisons().get(0), l1);

        assertTrue(!p1.supprimerLivraison(null));
    }

    @Test
    public void testEntrecoise() {

        Calendar debut1 = Calendar.getInstance();
        debut1.set(2000, 7, 31, 10, 0, 0); //2000-8-31
        Calendar fin1 = Calendar.getInstance();
        fin1.set(2000, 7, 31, 11, 0, 0);
        Horaire h1 = new Horaire(debut1, fin1);
        Plage p1 = new Plage(h1);

        Calendar debut2 = Calendar.getInstance();
        debut2.set(2000, 7, 31, 11, 0, 0);
        Calendar fin2 = Calendar.getInstance();
        fin2.set(2000, 7, 31, 12, 0, 0);
        Horaire h2 = new Horaire(debut2, fin2);
        Plage p2 = new Plage(h2);

        Calendar debut3 = Calendar.getInstance();
        debut3.set(2000, 7, 31, 7, 0, 0);
        Calendar fin3 = Calendar.getInstance();
        fin3.set(2000, 7, 31, 10, 0, 0);
        Horaire h3 = new Horaire(debut3, fin3);
        Plage p3 = new Plage(h3);

        Calendar debut4 = Calendar.getInstance();
        debut4.set(2000, 7, 31, 7, 0, 0);
        Calendar fin4 = Calendar.getInstance();
        fin4.set(2000, 7, 31, 10, 20, 0);
        Horaire h4 = new Horaire(debut4, fin4);
        Plage p4 = new Plage(h4);

        Calendar debut5 = Calendar.getInstance();
        debut5.set(2000, 7, 31, 10, 10, 0);
        Calendar fin5 = Calendar.getInstance();
        fin5.set(2000, 7, 31, 11, 10, 0);
        Horaire h5 = new Horaire(debut5, fin5);
        Plage p5 = new Plage(h5);

        Calendar debut6 = Calendar.getInstance();
        debut6.set(2000, 7, 31, 10, 0, 0);
        Calendar fin6 = Calendar.getInstance();
        fin6.set(2000, 7, 31, 11, 0, 0);
        Horaire h6 = new Horaire(debut6, fin6);
        Plage p6 = new Plage(h6);

        assertTrue(!p1.entrecroise(p2));
        assertTrue(!p1.entrecroise(p3));
        assertTrue(p1.entrecroise(p4));
        assertTrue(p1.entrecroise(p5));
        assertTrue(p1.entrecroise(p6));
    }

    @Test
    public void testAjouterLivraison1() {

        Calendar debut = Calendar.getInstance();
        debut.set(2000, 7, 31, 10, 0, 0); //2000-8-31
        Calendar fin = Calendar.getInstance();
        fin.set(2000, 7, 31, 11, 0, 0);
        Horaire h1 = new Horaire(debut, fin);
        Plage p1 = new Plage(h1);

        assertTrue(p1.estVide());
        assertEquals(p1.getLivraisons().size(), 0);

        Livraison l1 = mock(Livraison.class);
        assertTrue(p1.ajouterLivraison(l1));
        assertEquals(p1.getLivraisons().size(), 1);
        assertEquals(p1.getLivraisons().get(0), l1);

        Livraison l2 = mock(Livraison.class);
        assertTrue(p1.ajouterLivraison(l2));
        assertEquals(p1.getLivraisons().size(), 2);
        assertEquals(p1.getLivraisons().get(0), l1);
        assertEquals(p1.getLivraisons().get(1), l2);
    }

    @Test
    public void testAjouterLivraison2() {

        //Ajouter null
        Calendar debut = Calendar.getInstance();
        debut.set(2000, 7, 31, 10, 0, 0); //2000-8-31
        Calendar fin = Calendar.getInstance();
        fin.set(2000, 7, 31, 11, 0, 0);
        Horaire h1 = new Horaire(debut, fin);
        Plage p1 = new Plage(h1);

        assertTrue(!p1.ajouterLivraison(null));
    }

    @Test
    public void testEstVide() {

        Calendar debut = Calendar.getInstance();
        debut.set(2000, 7, 31, 10, 0, 0); //2000-8-31
        Calendar fin = Calendar.getInstance();
        fin.set(2000, 7, 31, 11, 0, 0);
        Horaire h1 = new Horaire(debut, fin);
        Plage p1 = new Plage(h1);

        assertTrue(p1.estVide());

        Livraison l = mock(Livraison.class);

        p1.ajouterLivraison(l);

        assertTrue(!p1.estVide());
    }

    @Test
    public void testCompareTo() {

        Calendar debut1 = Calendar.getInstance();
        debut1.set(2000, 7, 31, 10, 0, 0); //2000-8-31
        Calendar fin1 = Calendar.getInstance();
        fin1.set(2000, 7, 31, 11, 0, 0);
        Horaire h1 = new Horaire(debut1, fin1);
        Plage p1 = new Plage(h1);

        Calendar debut2 = Calendar.getInstance();
        debut2.set(2000, 7, 31, 11, 0, 0);
        Calendar fin2 = Calendar.getInstance();
        fin2.set(2000, 7, 31, 12, 0, 0);
        Horaire h2 = new Horaire(debut2, fin2);
        Plage p2 = new Plage(h2);

        Calendar debut3 = Calendar.getInstance();
        debut3.set(2000, 7, 31, 7, 0, 0);
        Calendar fin3 = Calendar.getInstance();
        fin3.set(2000, 7, 31, 10, 0, 0);
        Horaire h3 = new Horaire(debut3, fin3);
        Plage p3 = new Plage(h3);

        Calendar debut4 = Calendar.getInstance();
        debut4.set(2000, 7, 31, 7, 0, 0);
        Calendar fin4 = Calendar.getInstance();
        fin4.set(2000, 7, 31, 10, 20, 0);
        Horaire h4 = new Horaire(debut4, fin4);
        Plage p4 = new Plage(h4);

        Calendar debut5 = Calendar.getInstance();
        debut5.set(2000, 7, 31, 10, 10, 0);
        Calendar fin5 = Calendar.getInstance();
        fin5.set(2000, 7, 31, 11, 10, 0);
        Horaire h5 = new Horaire(debut5, fin5);
        Plage p5 = new Plage(h5);

        Calendar debut6 = Calendar.getInstance();
        debut6.set(2000, 7, 31, 10, 0, 0);
        Calendar fin6 = Calendar.getInstance();
        fin6.set(2000, 7, 31, 11, 0, 0);
        Horaire h6 = new Horaire(debut6, fin6);
        Plage p6 = new Plage(h6);

        Calendar debut7 = Calendar.getInstance();
        debut7.set(2000, 7, 31, 5, 0, 0);
        Calendar fin7 = Calendar.getInstance();
        fin7.set(2000, 7, 31, 6, 0, 0);
        Horaire h7 = new Horaire(debut7, fin7);
        Plage p7 = new Plage(h7);

        Calendar debut8 = Calendar.getInstance();
        debut8.set(2000, 7, 31, 11, 10, 0);
        Calendar fin8 = Calendar.getInstance();
        fin8.set(2000, 7, 31, 11, 30, 0);
        Horaire h8 = new Horaire(debut8, fin8);
        Plage p8 = new Plage(h8);

        assertTrue(p1.compareTo(p2) == -1);
        assertTrue(p1.compareTo(p3) == 1);
        assertTrue(p1.compareTo(p4) == 0);
        assertTrue(p1.compareTo(p5) == 0);
        assertTrue(p1.compareTo(p6) == 0);
        assertTrue(p1.compareTo(p7) == 1);
        assertTrue(p1.compareTo(p8) == -1);

    }

}
