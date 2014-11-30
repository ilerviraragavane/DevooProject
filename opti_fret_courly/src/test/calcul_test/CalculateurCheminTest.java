package test.calcul_test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import opti_fret_courly.outil.calcul.CalculateurChemin;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculateurCheminTest {

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
	public void testCalculerChemin() {
		// Mise en place du contexte
		int[][] matrice = new int[4][4];
		
		for(int i=0;i<4;i++)
			for(int j=0;j<4;matrice[i][j++]=-1);
		// Poids du trajet de 0 à 1
		matrice[0][1] = 2;
		
		matrice[0][3] = 10;
		matrice[1][2] = 2;
		matrice[1][3] = 6;
		matrice[2][3] = 2;
		
		CalculateurChemin cc = new CalculateurChemin(matrice);
		cc.calculerChemin(0, 3);
		
		List<Integer> solution = new ArrayList<Integer>();
		solution.add(0);
		solution.add(1);
		solution.add(2);
		solution.add(3);
		
		//	Calcul d'un chemin possible
		assertTrue(cc.getCout() == 6);
		
		assertTrue(cc.getSuivants().equals(solution));
		
		// Calcul d'un chemin impossible
		cc.calculerChemin(3, 0);
		
		assertTrue(cc.getCout() == 0);
		
		assertTrue(cc.getSuivants()==null);
	}

}
