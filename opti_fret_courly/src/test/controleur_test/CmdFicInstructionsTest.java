package test.controleur_test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import opti_fret_courly.controleur.CmdFicInstructions;
import opti_fret_courly.modele.Chemin;
import opti_fret_courly.modele.Destinataire;
import opti_fret_courly.modele.Entrepot;
import opti_fret_courly.modele.Horaire;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.modele.Tournee;
import opti_fret_courly.modele.Troncon;
import opti_fret_courly.modele.Zone;
import opti_fret_courly.vue.VueDetailsItineraire;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

public class CmdFicInstructionsTest {

	private static Noeud n1 = new Noeud(1, 1, 1);
	private static Noeud n2 = new Noeud(2, 2, 2);
	private static Noeud n3 = new Noeud(3, 3, 3);
	private static Noeud n4 = new Noeud(4, 4, 4);
	private static Noeud n5 = new Noeud(5, 5, 5);

	private static Troncon t12 = new Troncon("run12", 1.0, 1.0, n1, n2);
	private static Troncon t23 = new Troncon("run23", 1.0, 1.0, n2, n3);
	private static Troncon t34 = new Troncon("run34", 1.0, 1.0, n3, n4);
	private static Troncon t45 = new Troncon("run45", 1.0, 1.0, n4, n5);
	private static Troncon t51 = new Troncon("run61", 1.0, 1.0, n5, n1);

	private static Destinataire d2 = new Destinataire(2);
	private static PointDeLivraison pdl2 = new PointDeLivraison(n2);
	private static Livraison l2 = new Livraison(d2, pdl2);

	private static Destinataire d4 = new Destinataire(4);
	private static PointDeLivraison pdl4 = new PointDeLivraison(n4);
	private static Livraison l4 = new Livraison(d4, pdl4);

	private static Destinataire d5 = new Destinataire(5);
	private static PointDeLivraison pdl5 = new PointDeLivraison(n5);
	private static Livraison l5 = new Livraison(d5, pdl5);

	private static Entrepot e = new Entrepot(n1);

	private static Calendar heureDepEntrepot = new GregorianCalendar(2013, 10,
			18, 8, 0);
	private static Calendar heureArrEntrepot = new GregorianCalendar(2013, 10,
			18, 10, 30);
	private static Calendar heureArr2 = new GregorianCalendar(2013, 10, 18, 8,
			30);
	private static Calendar heureDep2 = new GregorianCalendar(2013, 10, 18, 8,
			40);
	private static Calendar heureArr4 = new GregorianCalendar(2013, 10, 18, 9,
			30);
	private static Calendar heureDep4 = new GregorianCalendar(2013, 10, 18, 9,
			40);
	private static Calendar heureArr5 = new GregorianCalendar(2013, 10, 18, 10,
			0);
	private static Calendar heureDep5 = new GregorianCalendar(2013, 10, 18, 10,
			10);
	private static Horaire hdp2 = new Horaire(heureArr2, heureDep2);
	private static Horaire hdp4 = new Horaire(heureArr4, heureDep4);
	private static Horaire hdp5 = new Horaire(heureArr5, heureDep5);

	private static Chemin ch1 = new Chemin();
	private static Chemin ch2 = new Chemin();
	private static Chemin ch3 = new Chemin();
	private static Chemin ch4 = new Chemin();

	private static Calendar debut1 = new GregorianCalendar(2013, 10, 18, 8, 0);
	private static Calendar fin1 = new GregorianCalendar(2013, 10, 18, 9, 0);
	private static Horaire h1 = new Horaire(debut1, fin1);
	private static Plage p1 = new Plage(h1);

	private static Calendar debut2 = new GregorianCalendar(2013, 10, 18, 9, 0);
	private static Calendar fin2 = new GregorianCalendar(2013, 10, 18, 11, 0);
	private static Horaire h2 = new Horaire(debut2, fin2);
	private static Plage p2 = new Plage(h2);

	private static Zone zone = new Zone();
	private static List<Plage> plagesHoraires = new ArrayList<Plage>();
	private static List<Chemin> chemins = new ArrayList<Chemin>();
	
	@Mock
	private static Tournee tournee = mock(Tournee.class);
	
	private static VueDetailsItineraire vueDetailsItineraire = new VueDetailsItineraire();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Horaires de passage aux livraisons
		l2.setHeuresPassage(hdp2);
		l4.setHeuresPassage(hdp4);
		l5.setHeuresPassage(hdp5);

		// Livraisons
		pdl2.setLivraison(l2);
		pdl4.setLivraison(l4);
		pdl5.setLivraison(l5);

		// Chemins
		ch1.ajouterTroncon(t12);
		ch1.setPointDep(e);
		ch1.setPointArr(pdl2);

		ch2.ajouterTroncon(t23);
		ch2.ajouterTroncon(t34);
		ch2.setPointDep(pdl2);
		ch2.setPointArr(pdl4);

		ch3.ajouterTroncon(t45);
		ch3.setPointDep(pdl4);
		ch3.setPointArr(pdl5);

		ch4.ajouterTroncon(t51);
		ch4.setPointDep(pdl5);
		ch4.setPointArr(e);

		chemins.add(ch1);
		chemins.add(ch2);
		chemins.add(ch3);
		chemins.add(ch4);

		// Entrepot
		e.setHeuresPassage(new Horaire(heureDepEntrepot, heureArrEntrepot));

		// Plages
		fin1.add(Calendar.MINUTE, 10);
		debut2.add(Calendar.MINUTE, 20);
		fin2.add(Calendar.MINUTE, 30);

		p1.ajouterLivraison(l2);
		l2.setPlageHoraire(p1);

		p2.ajouterLivraison(l5);
		p2.ajouterLivraison(l4);
		l5.setPlageHoraire(p2);
		l4.setPlageHoraire(p2);
		plagesHoraires.add(p1);
		plagesHoraires.add(p2);

		zone.setTournee(tournee);
		vueDetailsItineraire.setTournee(tournee);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		File file = new File("instructions.txt");
		file.delete();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecuter1() throws Exception {

		// Fichier de sortie OK
		when(tournee.aDesPlages()).thenReturn(true);
		when(tournee.getDate()).thenReturn(new GregorianCalendar(2013,10,18));
		when(tournee.getEntrepot()).thenReturn(e);
		when(tournee.getPlagesHoraires()).thenReturn(plagesHoraires);
		when(tournee.getChemins()).thenReturn(chemins);

		CmdFicInstructions cmd = new CmdFicInstructions(vueDetailsItineraire, 
				"instructions.txt");
		try {
			cmd.executer();
		} catch (Exception e) {
			System.err.println(e);
			throw (e);
		}

		// Comparaison du fichier a celui qui aurait etre du obtenu
		assertTrue(comparerFichiers("instructions.txt",
				"src/test/instructions/instructions1verif.txt"));
	}

	@Test
	public void testExecuter2() throws Exception {

		// Fichier de sortie OK
		when(tournee.aDesPlages()).thenReturn(false);
		when(tournee.getDate()).thenReturn(new GregorianCalendar(2013,10,18));
		when(tournee.getEntrepot()).thenReturn(e);
		when(tournee.getPlagesHoraires()).thenReturn(new ArrayList<Plage>());
		when(tournee.getChemins()).thenReturn(new ArrayList<Chemin>());
		
		CmdFicInstructions cmd = new CmdFicInstructions(vueDetailsItineraire, "instructions.txt");
		try {
			cmd.executer();
		} catch (Exception e) {
			System.err.println(e);
			throw (e);
		}

		// Comparaison du fichier a celui qui aurait etre du obtenu
		assertTrue(comparerFichiers("instructions.txt",
				"src/test/instructions/instructions2verif.txt"));
	}

	/**
	 * Compare deux fichiers
	 * 
	 * @param fichierCompare
	 *            le fichier compare
	 * @param fichierReference
	 *            le fichier de reference
	 * @return true si les deux fichiers sont égaux, et faux sinon
	 * @throws IOException
	 */
	private boolean comparerFichiers(String fichierCompare,
			String fichierReference) throws IOException {
		BufferedReader buff = new BufferedReader(new FileReader(fichierCompare));
		BufferedReader buffVerif = new BufferedReader(new FileReader(
				fichierReference));
		boolean memeFichier = true;
		try {
			String ligneCourante = buff.readLine();
			String ligneCouranteVerif = buffVerif.readLine();

			int i = 0;
			while (ligneCourante != null && ligneCouranteVerif != null
					&& memeFichier) {
				memeFichier = ligneCourante.equals(ligneCouranteVerif);

				ligneCourante = buff.readLine();
				ligneCouranteVerif = buffVerif.readLine();
				i++;
			}

			if (!memeFichier) {
				System.out
						.println("Difference entre le fichier instructions.txt et le fichier attendu a la ligne "
								+ (i - 1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			buff.close();
			buffVerif.close();
		}

		return memeFichier;
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void testExecuter3() throws Exception {
		// Fichier qui existe deja
		File file = new File("instructions.txt");
		file.createNewFile();

		CmdFicInstructions cmd = new CmdFicInstructions(vueDetailsItineraire, "instructions.txt");
		try {
			cmd.executer();
		} catch (Exception e) {
			System.err.println(e);
			throw (e);
		}

	}

	

}
