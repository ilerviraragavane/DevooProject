package test.controleur_test;

import static org.mockito.Mockito.mock;
import opti_fret_courly.vue.Fenetre;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InvocateurTest {

	private static Fenetre fenetre= mock(Fenetre.class);
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
    public void test1(){
    	
    }
    
    /*Décommenter tous les tests ci-dessous quand les méthodes 
      getItPosCourante() et getCommandes() de la classe Invocateur 
      sont décommentées*/

    /*
    @Test
    public void testAjouterCmdCmdNonAnnulable() throws Exception {

    	Invocateur inv = new Invocateur(fenetre);
    	LinkedList<CmdAnnulable> commandes = inv.getCommandes();
    	assertTrue(commandes.isEmpty());
    	ListIterator<CmdAnnulable> itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasNext());
    	assertTrue(!itPosCourante.hasPrevious());
    	
    	CmdNonAnnulable commande1 = mock(CmdNonAnnulable.class);
    	doNothing().when(commande1).executer();
    	
    	inv.ajouterCmd(commande1);
    	assertTrue(inv.getCommandes().isEmpty());
    	verify(commande1).executer();
    	verifyNoMoreInteractions(commande1);
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasNext());
    	assertTrue(!itPosCourante.hasPrevious());
    }
    
    @Test
    public void testAjouterCmdCmdAnnulable() throws Exception {

    	Invocateur inv = new Invocateur(fenetre);
    	LinkedList<CmdAnnulable> commandes = inv.getCommandes();
    	assertTrue(commandes.isEmpty());
    	ListIterator<CmdAnnulable> itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasPrevious());
    	assertTrue(!itPosCourante.hasNext());
    	
    	CmdAnnulable commande1 = mock(CmdAnnulable.class);
    	doNothing().when(commande1).executer();
    	
    	CmdAnnulable commande2 = mock(CmdAnnulable.class);
    	doNothing().when(commande1).executer();
    	
    	inv.ajouterCmd(commande1);
    	verify(commande1).executer();
    	verifyNoMoreInteractions(commande1);

    	assertTrue(inv.getCommandes().size()==1);
    	assertEquals(inv.getCommandes().get(0),commande1);
    	assertTrue(inv.getItPosCourante().hasPrevious());
    	assertTrue(!inv.getItPosCourante().hasNext());   
    	assertEquals(inv.getItPosCourante().previous(),commande1);  
    	assertEquals(inv.getItPosCourante().next(),commande1);	
    	
    	inv.ajouterCmd(commande2);
    	verify(commande2).executer();
    	verifyNoMoreInteractions(commande2);

    	assertTrue(inv.getCommandes().size()==2);
    	assertEquals(inv.getCommandes().get(0),commande1);
    	assertEquals(inv.getCommandes().get(1),commande2);
    	assertTrue(inv.getItPosCourante().hasPrevious());
    	assertTrue(!inv.getItPosCourante().hasNext());    
    	assertEquals(inv.getItPosCourante().previous(),commande2);     
    	assertEquals(inv.getItPosCourante().previous(),commande1);  
    	
    }

    @Test
    public void testFonctionnel1() throws Exception{

    	//On cree l'invocateur. la liste des commandes est vide.
    	Invocateur inv = new Invocateur(fenetre);
    	LinkedList<CmdAnnulable> commandes = inv.getCommandes();
    	assertTrue(commandes.isEmpty());
    	ListIterator<CmdAnnulable> itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasPrevious());
    	assertTrue(!itPosCourante.hasNext());
    	
    	//On ajoute une commande annulable.
    	CmdAjout ca1 = mock(CmdAjout.class);
    	doNothing().when(ca1).executer();
    	inv.ajouterCmd(ca1);
    	verify(ca1).executer();
    	verifyNoMoreInteractions(ca1);
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==1);
    	assertTrue(commandes.contains(ca1));
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(itPosCourante.hasPrevious());
    	assertTrue(!itPosCourante.hasNext());
    	
    	//On ajoute trois autres commandes annulables.
    	CmdAjout ca2 = mock(CmdAjout.class);
    	doNothing().when(ca2).executer();
    	CmdAjout ca3 = mock(CmdAjout.class);
    	doNothing().when(ca3).executer();
    	CmdAjout ca4 = mock(CmdAjout.class);
    	doNothing().when(ca4).executer();

    	inv.ajouterCmd(ca2);
    	verify(ca2).executer();
    	verifyNoMoreInteractions(ca2);
    	inv.ajouterCmd(ca3);
    	verify(ca3).executer();
    	verifyNoMoreInteractions(ca3);
    	inv.ajouterCmd(ca4);
    	verify(ca4).executer();
    	verifyNoMoreInteractions(ca4);
    	
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==4);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	assertEquals(commandes.get(2),ca3);
    	assertEquals(commandes.get(3),ca4);
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(itPosCourante.hasPrevious());
    	assertTrue(!itPosCourante.hasNext());
    	
    	//On annule la derniere commande.
    	inv.annuler();
    	
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==4);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	assertEquals(commandes.get(2),ca3);
    	assertEquals(commandes.get(3),ca4);
    	
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(itPosCourante.hasNext());
    	assertEquals(itPosCourante.next(),ca4);
    	itPosCourante.previous();
    	assertTrue(itPosCourante.hasNext());
    	
    	//On annule une autre commande.
    	inv.annuler();
    	
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==4);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	assertEquals(commandes.get(2),ca3);
    	assertEquals(commandes.get(3),ca4);
    	
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(itPosCourante.hasNext());
    	assertEquals(itPosCourante.next(),ca3);
    	itPosCourante.previous();
    	
    	//On annule une encore autre commande.
    	inv.annuler();
    	
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==4);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	assertEquals(commandes.get(2),ca3);
    	assertEquals(commandes.get(3),ca4);
    	
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(itPosCourante.hasNext());
    	assertEquals(itPosCourante.next(),ca2);
    	itPosCourante.previous();
    	
    	//On reexecute une commande.
    	inv.reexecuter();
    	
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==4);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	assertEquals(commandes.get(2),ca3);
    	assertEquals(commandes.get(3),ca4);
    	
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(itPosCourante.hasNext());
    	assertEquals(itPosCourante.next(),ca3);
    	itPosCourante.previous();
    	
    	//On ajoute une commande annulable.
    	CmdAjout ca5 = mock(CmdAjout.class);
    	doNothing().when(ca5).executer();
    	inv.ajouterCmd(ca5);
    	verify(ca5).executer();
    	verifyNoMoreInteractions(ca5);
    	
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==3);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	assertEquals(commandes.get(2),ca5);
    	
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasNext());
    	assertTrue(itPosCourante.hasPrevious());
    	
    	//On ajoute une commande NON annulable.
    	CmdSelection cna1 = mock(CmdSelection.class);
    	doNothing().when(cna1).executer();
    	inv.ajouterCmd(cna1);
    	verify(cna1).executer();
    	verifyNoMoreInteractions(cna1);
    	
    	commandes = inv.getCommandes();
    	System.out.print(commandes.size());
    	assertTrue(commandes.size()==3);   	
    	
    	//On ajoute une commande Chargement.
    	CmdChargeDmd cc1 = mock(CmdChargeDmd.class);
    	doNothing().when(cc1).executer();
    	inv.ajouterCmd(cc1);
    	verify(cc1).executer();
    	verifyNoMoreInteractions(cc1);
    	
    	commandes = inv.getCommandes();
    	System.out.print(commandes.size());
    	assertTrue(commandes.size()==0);   	
    	
    }
    
    @Test
    public void testFonctionnel2() throws Exception{
    	
    	//Ce test est pour controler la taille de deque.
    	
    	//On cree l'invocateur. la liste des commandes est vide.
    	Invocateur inv = new Invocateur(fenetre);
    	LinkedList<CmdAnnulable> commandes = inv.getCommandes();
    	assertTrue(commandes.isEmpty());
    	ListIterator<CmdAnnulable> itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasPrevious());
    	assertTrue(!itPosCourante.hasNext());
    	
    	//On cree 20 commandes annulables.
    	CmdAjout ca1 = mock(CmdAjout.class);
    	doNothing().when(ca1).executer();
    	CmdAjout ca2 = mock(CmdAjout.class);
    	doNothing().when(ca2).executer();
    	CmdAjout ca3 = mock(CmdAjout.class);
    	doNothing().when(ca3).executer();
    	CmdAjout ca4 = mock(CmdAjout.class);
    	doNothing().when(ca4).executer();
    	CmdAjout ca5 = mock(CmdAjout.class);
    	doNothing().when(ca5).executer();
    	CmdAjout ca6 = mock(CmdAjout.class);
    	doNothing().when(ca6).executer();
    	CmdAjout ca7 = mock(CmdAjout.class);
    	doNothing().when(ca7).executer();
    	CmdAjout ca8 = mock(CmdAjout.class);
    	doNothing().when(ca8).executer();
    	CmdAjout ca9 = mock(CmdAjout.class);
    	doNothing().when(ca9).executer();
    	CmdAjout ca10 = mock(CmdAjout.class);
    	doNothing().when(ca10).executer();
    	CmdAjout ca11 = mock(CmdAjout.class);
    	doNothing().when(ca11).executer();
    	CmdAjout ca12 = mock(CmdAjout.class);
    	doNothing().when(ca12).executer();
    	CmdAjout ca13 = mock(CmdAjout.class);
    	doNothing().when(ca13).executer();
    	CmdAjout ca14 = mock(CmdAjout.class);
    	doNothing().when(ca14).executer();
    	CmdAjout ca15 = mock(CmdAjout.class);
    	doNothing().when(ca15).executer();
    	CmdAjout ca16 = mock(CmdAjout.class);
    	doNothing().when(ca16).executer();
    	CmdAjout ca17 = mock(CmdAjout.class);
    	doNothing().when(ca17).executer();
    	CmdAjout ca18 = mock(CmdAjout.class);
    	doNothing().when(ca18).executer();
    	CmdAjout ca19 = mock(CmdAjout.class);
    	doNothing().when(ca19).executer();
    	CmdAjout ca20 = mock(CmdAjout.class);
    	doNothing().when(ca20).executer();    	
    	
    	//On ajoute ces 20 commandes annulables dans le deque.
    	inv.ajouterCmd(ca1);    	
    	verify(ca1).executer();
    	verifyNoMoreInteractions(ca1);
    	inv.ajouterCmd(ca2);    	
    	verify(ca2).executer();
    	verifyNoMoreInteractions(ca2);
    	inv.ajouterCmd(ca3);    	
    	verify(ca3).executer();
    	verifyNoMoreInteractions(ca3);
    	inv.ajouterCmd(ca4);    	
    	verify(ca4).executer();
    	verifyNoMoreInteractions(ca4);
    	inv.ajouterCmd(ca5);    	
    	verify(ca5).executer();
    	verifyNoMoreInteractions(ca5);
    	inv.ajouterCmd(ca6);    	
    	verify(ca6).executer();
    	verifyNoMoreInteractions(ca6);
    	inv.ajouterCmd(ca7);    	
    	verify(ca7).executer();
    	verifyNoMoreInteractions(ca7);
    	inv.ajouterCmd(ca8);    	
    	verify(ca8).executer();
    	verifyNoMoreInteractions(ca8);
    	inv.ajouterCmd(ca9);    	
    	verify(ca9).executer();
    	verifyNoMoreInteractions(ca9);
    	inv.ajouterCmd(ca10);    	
    	verify(ca10).executer();
    	verifyNoMoreInteractions(ca10);
    	inv.ajouterCmd(ca11);    	
    	verify(ca11).executer();
    	verifyNoMoreInteractions(ca11);
    	inv.ajouterCmd(ca12);    	
    	verify(ca12).executer();
    	verifyNoMoreInteractions(ca12);
    	inv.ajouterCmd(ca13);    	
    	verify(ca13).executer();
    	verifyNoMoreInteractions(ca13);
    	inv.ajouterCmd(ca14);    	
    	verify(ca14).executer();
    	verifyNoMoreInteractions(ca14);
    	inv.ajouterCmd(ca15);    	
    	verify(ca15).executer();
    	verifyNoMoreInteractions(ca15);
    	inv.ajouterCmd(ca16);    	
    	verify(ca16).executer();
    	verifyNoMoreInteractions(ca16);
    	inv.ajouterCmd(ca17);    	
    	verify(ca17).executer();
    	verifyNoMoreInteractions(ca17);
    	inv.ajouterCmd(ca18);    	
    	verify(ca18).executer();
    	verifyNoMoreInteractions(ca18);
    	inv.ajouterCmd(ca19);    	
    	verify(ca19).executer();
    	verifyNoMoreInteractions(ca19);
    	inv.ajouterCmd(ca20);    	
    	verify(ca20).executer();
    	verifyNoMoreInteractions(ca20);
    	
    	//On verifie que les 20 commandes annulables sont dans le deque.
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==20);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	assertEquals(commandes.get(2),ca3);
    	assertEquals(commandes.get(3),ca4);
    	assertEquals(commandes.get(4),ca5);
    	assertEquals(commandes.get(5),ca6);
    	assertEquals(commandes.get(6),ca7);
    	assertEquals(commandes.get(7),ca8);
    	assertEquals(commandes.get(8),ca9);
    	assertEquals(commandes.get(9),ca10);
    	assertEquals(commandes.get(10),ca11);
    	assertEquals(commandes.get(11),ca12);
    	assertEquals(commandes.get(12),ca13);
    	assertEquals(commandes.get(13),ca14);
    	assertEquals(commandes.get(14),ca15);
    	assertEquals(commandes.get(15),ca16);
    	assertEquals(commandes.get(16),ca17);
    	assertEquals(commandes.get(17),ca18);
    	assertEquals(commandes.get(18),ca19);
    	assertEquals(commandes.get(19),ca20);
    	
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasNext());
    	assertTrue(itPosCourante.hasPrevious());
    	
    	//On ajoute une 21eme commande annulable.
    	CmdAjout ca21 = mock(CmdAjout.class);
    	doNothing().when(ca21).executer();
    	inv.ajouterCmd(ca21);    	
    	verify(ca21).executer();
    	verifyNoMoreInteractions(ca21);

    	//On verifie que la premiere commande est vire et la 21eme commande est ajoute a la fin.
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==20);
    	assertEquals(commandes.get(0),ca2);
    	assertEquals(commandes.get(1),ca3);
    	assertEquals(commandes.get(2),ca4);
    	assertEquals(commandes.get(3),ca5);
    	assertEquals(commandes.get(4),ca6);
    	assertEquals(commandes.get(5),ca7);
    	assertEquals(commandes.get(6),ca8);
    	assertEquals(commandes.get(7),ca9);
    	assertEquals(commandes.get(8),ca10);
    	assertEquals(commandes.get(9),ca11);
    	assertEquals(commandes.get(10),ca12);
    	assertEquals(commandes.get(11),ca13);
    	assertEquals(commandes.get(12),ca14);
    	assertEquals(commandes.get(13),ca15);
    	assertEquals(commandes.get(14),ca16);
    	assertEquals(commandes.get(15),ca17);
    	assertEquals(commandes.get(16),ca18);
    	assertEquals(commandes.get(17),ca19);
    	assertEquals(commandes.get(18),ca20);
    	assertEquals(commandes.get(19),ca21);
    	
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasNext());
    	assertTrue(itPosCourante.hasPrevious());
    	
    }
    
    @Test
    public void testFonctionnrl3() throws Exception{
    	
    	//On cree l'invocateur. la liste des commandes est vide.
    	Invocateur inv = new Invocateur(fenetre);
    	LinkedList<CmdAnnulable> commandes = inv.getCommandes();
    	assertTrue(commandes.isEmpty());
    	ListIterator<CmdAnnulable> itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasPrevious());
    	assertTrue(!itPosCourante.hasNext());    	

    	//On ajoute une premiere commande.
    	CmdAjout ca1 = mock(CmdAjout.class);
    	doNothing().when(ca1).annuler();
    	doNothing().when(ca1).executer();
    	inv.ajouterCmd(ca1);    	
    	verify(ca1).executer();
    	
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==1);
    	assertTrue(commandes.contains(ca1));
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(itPosCourante.hasPrevious());
    	assertTrue(!itPosCourante.hasNext());    	   	

    	//On ajoute une deuxieme commande.
    	CmdAjout ca2 = mock(CmdAjout.class);
    	doNothing().when(ca2).annuler();
    	doNothing().when(ca2).executer();
    	inv.ajouterCmd(ca2);    	
    	verify(ca2).executer();
    	
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==2);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(itPosCourante.hasPrevious());
    	assertTrue(!itPosCourante.hasNext());    	
    	
    	//On annule une commande
    	inv.annuler();   	
    	verify(ca2).annuler();
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==2);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(itPosCourante.hasPrevious());
    	assertTrue(itPosCourante.hasNext());   	
    	
    	//On annule une autre commande
    	inv.annuler();   	
    	verify(ca1).annuler();
    	commandes = inv.getCommandes();
    	assertTrue(commandes.size()==2);
    	assertEquals(commandes.get(0),ca1);
    	assertEquals(commandes.get(1),ca2);
    	itPosCourante = inv.getItPosCourante();
    	assertTrue(!itPosCourante.hasPrevious());
    	assertTrue(itPosCourante.hasNext());

    	
    	
    	
    //	inv.reexecuter();
    //	commandes = inv.getCommandes();
    //	assertTrue(commandes.size()==1);
    //	assertTrue(commandes.contains(ca1));
    //	itPosCourante = inv.getItPosCourante();
    //	assertTrue(itPosCourante.hasPrevious());
    //	assertTrue(!itPosCourante.hasNext());
    	
    	
    	
    }*/

}
