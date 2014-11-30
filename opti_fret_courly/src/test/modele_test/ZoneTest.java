package test.modele_test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Zone;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ZoneTest {
	private static Zone zone;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	zone = new Zone();
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
    public void testGetNoeudParId1() {

        Noeud n1 = new Noeud(1, 1, 1);
        Noeud n2 = new Noeud(2, 2, 2);
        Noeud n3 = new Noeud(3, 3, 3);

        Zone z1 = zone;
        assertTrue(z1.getNoeuds().size() == 0);

        Map<Integer, Noeud> ns = new HashMap<Integer, Noeud>();
        ns.put(new Integer(1), n1);
        ns.put(new Integer(3), n3);
        ns.put(new Integer(2), n2);

        z1.setNoeuds(ns);

        assertEquals(z1.getNoeudParId(2), n2);
        assertEquals(z1.getNoeudParId(3), n3);
        assertEquals(z1.getNoeudParId(8), null);
    }

}
