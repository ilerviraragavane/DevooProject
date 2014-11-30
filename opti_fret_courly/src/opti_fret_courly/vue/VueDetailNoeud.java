package opti_fret_courly.vue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import opti_fret_courly.controleur.Createur;
import opti_fret_courly.modele.Destinataire;
import opti_fret_courly.modele.EltCarte;
import opti_fret_courly.modele.Entrepot;
import opti_fret_courly.modele.EtatLivraison;
import opti_fret_courly.modele.Horaire;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.Noeud;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.outil.Lecteur;
import opti_fret_courly.outil.exception.SemantiqueException;

/**
 * Cette classe est la vue d'afficher les détails lié à un élement selectionné.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public class VueDetailNoeud extends JPanel {

	/**
	 * Le noeud courant selectionné.
	 */
    private Noeud noeudCourant;
    

    /**
     * La date de la tournee calculee
     */
    private Calendar dateTournee;
    
    /**
	 * L'element courant selectionné.
	 */
    private EltCarte eltCourant;
    
    /**
	 * Le point de livraison selectionné.
	 */
    private PointDeLivraison pointDeLivraisonCourant;
    
    /**
	 * Le créateur.
	 */
    private Createur createur;
    
    /**
	 * Le liste des plages disponible pour la tournée.
	 */
    private List<Plage> listePlage = new ArrayList<Plage>();

    /**
	 * Le panel contenant le titre de la vue.
	 */
    private JPanel panelTitre = new JPanel();
    
    /**
	 * Le label contenant le titre de la vue.
	 */
    private JLabel labelTitre = new JLabel();
    
    /**
	 * Le panel contenant les commandes de la vue.
	 */
    private JPanel panelDetailNoeudCommande = new JPanel();
    
    /**
	 * Le panel contenant les informations 
	 * sur l'élement selectionnée.
	 */
    private JPanel panelInformationNoeud = new JPanel();
    
    /**
	 * Le panel contenant l'information adresse.
	 */
    private JPanel panelAdresse = new JPanel();
    
    /**
	 * Le panel contenant l'information client.
	 */
    private JPanel panelClient = new JPanel();
    
    /**
	 * Le panel contenant l'information 
	 * du type de plage.
	 */
    private JPanel panelChoixPlage = new JPanel();
    
    /**
	 * Le panel contenant l'information 
	 * de la plage existante.
	 */
    private JPanel panelPlageExistant = new JPanel();
    
    /**
	 * Le panel contenant l'information 
	 * de la nouvelle plage.
	 */
    private JPanel panelNouveauPlage = new JPanel();
    
    /**
	 * Le panel contenant l'information de 
	 * l'horaire de passage.
	 */
    private JPanel panelHeurePassage = new JPanel();
    
    /**
	 * Le bouton ajouter une livraison.
	 */
    private JButton ajouter = new JButton();
    
    /**
	 * Le bouton supprimer une livraison.
	 */
    private JButton supprimer = new JButton();
    
    /**
	 * Le bouton reinitialiser les champs.
	 */
    private JButton reinitialiser = new JButton();
    
    /**
	 * Le label de l'adresse.
	 */
    private JLabel labelAdresse = new JLabel();
    
    /**
	 * Le champs texte de l'adresse.
	 */
    private JTextField champsAdresse = new JTextField();
    
    /**
	 * Le label de l'id client.
	 */
    private JLabel labelClient = new JLabel();
    
    /**
	 * Le champs texte de l'id client.
	 */
    private JTextField champsClient = new JTextField();
    
    /**
	 * Le bouton de choix d'une plage existante.
	 */
    private JRadioButton buttonPlageExistant = new JRadioButton();
    
    /**
	 * Le bouton de choix d'une nouvelle plage.
	 */
    private JRadioButton buttonNouvellePlage = new JRadioButton();
    
    /**
	 * La liste déroulante contenant les 
	 * plages existantes.
	 */
    private JComboBox<Plage> listeDeroulantePlage = new JComboBox<Plage>();
    
    /**
	 * Le panel contenant l'information 
	 * de la plage de debut.
	 */
    private JPanel panelPlageDebut = new JPanel();
    
    /**
	 * Le panel contenant l'information 
	 * de la plage de fin.
	 */
    private JPanel panelPlageFin = new JPanel();
    
    /**
	 * Le label de la plage de debut.
	 */
    private JLabel labelPlageDebut = new JLabel();
    
    /**
	 * Le champs texte de la plage de debut.
	 */
    private JTextField champsPlageDebut = new JTextField();
    
    /**
	 * Le label de la plage de fin.
	 */
    private JLabel labelPlageFin = new JLabel();
    
    /**
	 * Le champs texte de la plage de fin.
	 */
    private JTextField champsPlageFin = new JTextField();
    
    /**
	 * Le panel contenant l'information 
	 * de l'heure d'arrivée.
	 */
    private JPanel panelHeureArrive = new JPanel();
    
    /**
	 * Le panel contenant l'information 
	 * de l'heure depart.
	 */
    private JPanel panelHeureDepart = new JPanel();
    
    /**
	 * Le label titre de l'heure d'arrivée.
	 */
    private JLabel labelHeureArriveTitre = new JLabel();
    
    /**
	 * Le label titre de l'heure de depart.
	 */
    private JLabel labelHeureDepartTitre = new JLabel();
    
    /**
	 * Le label de l'heure d'arrivée.
	 */
    private JLabel labelHeureArrive = new JLabel();
    
    /**
	 * Le label de l'heure de depart.
	 */
    private JLabel labelHeureDepart = new JLabel();

    /**
	 * Constructeur de la classe <code>VueDetailNoeud</code> 
	 * dans lequelle on va initialiser et positionner 
	 * les composants de la vue.
	 * @param createur Le créateur.
	 */
    public VueDetailNoeud(Createur createur) {
    	this.createur=createur;
    	this.eltCourant=null;
        this.setLayout(new GridBagLayout());
        panelInformationNoeud.setLayout(new GridBagLayout());
        panelAdresse.setLayout(new GridLayout());
        panelClient.setLayout(new GridLayout());
        panelChoixPlage.setLayout(new GridLayout(2,1));
        panelNouveauPlage.setLayout(new GridBagLayout());
        panelHeurePassage.setLayout(new GridLayout());
        ajouter.setText("Ajouter");
        ajouter.setIcon(
        			new ImageIcon("src/opti_fret_courly/icon/accept.png"));
        ajouter.setVerticalTextPosition(AbstractButton.BOTTOM);
        ajouter.setHorizontalTextPosition(AbstractButton.CENTER);
        ajouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ajouter_actionPerformed(e);
            }
        });
        supprimer.setText("Supprimer");
        supprimer.setIcon(
        			new ImageIcon("src/opti_fret_courly/icon/supprimer.png"));
        supprimer.setVerticalTextPosition(AbstractButton.BOTTOM);
        supprimer.setHorizontalTextPosition(AbstractButton.CENTER);
        supprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	supprimer_actionPerformed(e);
            }
        });
        reinitialiser.setText("Réinitialiser");
        reinitialiser.setIcon(
        			new ImageIcon("src/opti_fret_courly/icon/retour.png"));
        reinitialiser.setVerticalTextPosition(AbstractButton.BOTTOM);
        reinitialiser.setHorizontalTextPosition(AbstractButton.CENTER);
        reinitialiser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	reinitialiser_actionPerformed(e);
            }
        });
        labelAdresse.setText("Adresse : ");
        labelAdresse.setHorizontalAlignment(SwingConstants.CENTER);
        champsAdresse.setColumns(15);
        labelClient.setText("Client : ");
        labelClient.setHorizontalAlignment(SwingConstants.CENTER);
        champsClient.setColumns(15);
        buttonPlageExistant.setText("Plage existante");
        buttonPlageExistant.setSelected(true);
        buttonPlageExistant.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPlageExistant.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonPlageExistant_actionPerformed(e);
            }
        });
        buttonNouvellePlage.setText("Nouvelle plage");
        buttonNouvellePlage.setHorizontalAlignment(SwingConstants.CENTER);
        buttonNouvellePlage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonNouvellePlage_actionPerformed(e);
            }
        });
        panelPlageDebut.setLayout(new GridLayout(2, 1));
        panelPlageFin.setLayout(new GridLayout(2, 1));
        labelPlageDebut.setText("Plage début");
        labelPlageDebut.setHorizontalAlignment(SwingConstants.CENTER);
        labelPlageFin.setText("Plage fin");
        labelPlageFin.setHorizontalAlignment(SwingConstants.CENTER);
        panelHeureArrive.setLayout(new GridLayout(2, 1));
        panelHeureDepart.setLayout(new GridLayout(2, 1));
        labelHeureArriveTitre.setText("Heure arrivée");
        labelHeureArriveTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelHeureDepartTitre.setText("heure depart");
        labelHeureDepartTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelHeureArrive.setText("--:--");
        labelHeureArrive.setHorizontalAlignment(SwingConstants.CENTER);
        labelHeureDepart.setText("--:--");
        labelHeureDepart.setHorizontalAlignment(SwingConstants.CENTER);
        panelDetailNoeudCommande.add(ajouter, null);
        panelDetailNoeudCommande.add(supprimer, null);
        panelDetailNoeudCommande.add(reinitialiser, null);
        this.add(panelDetailNoeudCommande,
                 new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, 
                		 				GridBagConstraints.SOUTH, 
                		 				GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 0, 0), 0, 0));
        panelTitre.setBorder(
        				BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panelTitre.setLayout(new FlowLayout(1, 2, 2));
        labelTitre.setText("Détails du noeud selectionné");
        panelTitre.add(labelTitre, null);
        this.add(panelTitre,
                 new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 
                		 				GridBagConstraints.NORTH, 
                		 				GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 0, 0), 100, 0));
        this.add(panelInformationNoeud,
                 new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 
                		 				GridBagConstraints.NORTH, 
                		 				GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 0, 0), 0, 100));
        panelAdresse.add(labelAdresse, null);
        panelAdresse.add(champsAdresse, null);
        panelClient.add(labelClient, null);
        panelClient.add(champsClient, null);
        panelChoixPlage.add(buttonPlageExistant, null);
        panelChoixPlage.add(buttonNouvellePlage, null);
        champsPlageDebut.setText("HH:MM");
        champsPlageDebut.setForeground(Color.GRAY);
        champsPlageDebut.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
            	champsPlageDebut_keyTyped(e);
            }
        });
        champsPlageDebut.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	champsPlageDebut_mouseClicked(e);
            }
        });
        panelPlageDebut.add(labelPlageDebut, null);
        panelPlageDebut.add(champsPlageDebut, null);
        champsPlageFin.setText("HH:MM");
        champsPlageFin.setForeground(Color.GRAY);
        champsPlageFin.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
            	champsPlageFin_keyTyped(e);
            }
        });
        champsPlageFin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	champsPlageFin_mouseClicked(e);
            }
        });
        panelPlageFin.add(labelPlageFin, null);
        panelPlageFin.add(champsPlageFin, null);
        panelNouveauPlage.add(panelPlageDebut,
        				new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 
        									   GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL, 
                                               new Insets(0, 5, 0, 5), 0, 0));
        panelNouveauPlage.add(panelPlageFin,
                        new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, 
                        					   GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL, 
                                               new Insets(0, 5, 0, 5), 0, 0));
        panelHeureArrive.add(labelHeureArriveTitre, null);
        panelHeureArrive.add(labelHeureDepart, null);
        panelHeureDepart.add(labelHeureDepartTitre, null);
        panelHeureDepart.add(labelHeureArrive, null);
        panelHeurePassage.add(panelHeureArrive, null);
        panelHeurePassage.add(panelHeureDepart, null);
        panelInformationNoeud.add(panelAdresse,
                        new GridBagConstraints(0, 0, 1, 1, 1, 1, 
                        					   GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL, 
                                               new Insets(5, 5, 0, 5), 0, 0));
        panelInformationNoeud.add(panelClient,
                        new GridBagConstraints(0, 1, 1, 1, 1, 1, 
                        					   GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL, 
                                               new Insets(0, 5, 0, 5), 0, 0));
        panelInformationNoeud.add(panelChoixPlage,
                        new GridBagConstraints(0, 2, 1, 1, 1, 1, 
                        					   GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL, 
                                               new Insets(0, 5, 0, 5), 0, 0));
        panelPlageExistant.add(listeDeroulantePlage, null);
        panelInformationNoeud.add(panelPlageExistant,
                        new GridBagConstraints(0, 3, 1, 1, 1, 1, 
                        					   GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL, 
                                               new Insets(0, 5, 0, 5), 0, 0));
        panelInformationNoeud.add(panelNouveauPlage,
                       	new GridBagConstraints(0, 4, 1, 1, 1, 1, 
                       						   GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL, 
                                               new Insets(0, 5, 0, 5), 0, 0));
        panelInformationNoeud.add(panelHeurePassage,
                        new GridBagConstraints(0, 5, 1, 1, 1, 1, 
                        					   GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL, 
                                               new Insets(0, 5, 0, 5), 0, 0));
        panelNouveauPlage.setVisible(false);
        panelHeurePassage.setVisible(false); 
    }

    /**
	 * Setter permettant d'assigner un noeud donnée à la vue courante
	 * @param noeudCourant Un noeud qui sera associé à la vue.
	 */
    public void setNoeudCourant(Noeud noeudCourant) {
        this.noeudCourant = noeudCourant;
    }
    
    /**
	 * Setter permettant d'assigner un point de livraison donnée à la vue 
	 * courante
	 * @param pointDeLivraisonCourant Un point de livraison qui sera associé 
	 * à la vue.
	 */
    public void setPointDeLivraison(PointDeLivraison pointDeLivraisonCourant) {
        this.pointDeLivraisonCourant = pointDeLivraisonCourant;
    }
    
    /**
	 * Setter permettant d'assigner un element donnée à la vue courante 
	 * et d'actualiser la vue en consequent
	 * @param eltCourant Un element qui sera associé à la vue.
	 */
    public void setEltCourant(EltCarte eltCourant) {
    	this.eltCourant=eltCourant;
    	/*
    	 * On regarde si l'élement est une instance de Noeud
    	 * */
    	if (eltCourant instanceof Noeud) {
    		this.setNoeudCourant((Noeud)eltCourant);
            labelTitre.setText("Détails du noeud selectionné");
            if( listePlage.size() == 0 ){
            	buttonPlageExistant.setSelected(false);
            	buttonPlageExistant.setEnabled(false);
            	buttonNouvellePlage.setSelected(true);
                panelPlageExistant.setVisible(false);
                panelNouveauPlage.setVisible(true);
        		champsPlageDebut.setText("HH:MM");
        		champsPlageDebut.setForeground(Color.GRAY);
        		champsPlageFin.setText("HH:MM");
        		champsPlageFin.setForeground(Color.GRAY);
            	
            } else {
            	buttonPlageExistant.setSelected(true);
            	buttonPlageExistant.setEnabled(true);
            	buttonNouvellePlage.setSelected(false);
                panelPlageExistant.setVisible(true);
                panelNouveauPlage.setVisible(false);
            }
            panelHeurePassage.setVisible(false);
            panelChoixPlage.setVisible(true);
            champsAdresse.setText("" + noeudCourant.getId());
            champsClient.setText("");
            this.champsAdresse.setEnabled(false);
    		this.champsClient.setEnabled(true);
    		this.champsPlageDebut.setEnabled(true);
    		this.champsPlageFin.setEnabled(true);
    		this.panelClient.setVisible(true);
    		this.panelChoixPlage.setVisible(true);
    		this.supprimer.setEnabled(false);
    		this.ajouter.setEnabled(true);
    		this.reinitialiser.setEnabled(true);
    		
    		/*
        	 * On regarde si l'élement est une instance de Entrepot
        	 * */
    	} else if (eltCourant instanceof Entrepot) {
            labelTitre.setText("Détails de l'entrepot selectionné");
    		Entrepot entrepotCourant = (Entrepot)(eltCourant);
    		this.champsAdresse.setText("" + entrepotCourant.getLieu().getId());
    		this.champsAdresse.setEnabled(false);
    		this.panelClient.setVisible(false);
    		this.panelChoixPlage.setVisible(false);
    		this.panelHeurePassage.setVisible(true);
    		if (!entrepotCourant.estPlanifiee()) {
    			this.labelHeureDepart.setText("--:--");
    			this.labelHeureArrive.setText("--:--");
    		} else {
    			this.labelHeureDepart.setText(
    					Horaire.formaterHeureArrondie(
    							entrepotCourant.getHeuresPassage().getFin()));
        		this.labelHeureArrive.setText(
        				Horaire.formaterHeureArrondie(
        						entrepotCourant.getHeuresPassage().getDebut()));
    		}
    		this.panelNouveauPlage.setVisible(false);
    		this.panelPlageExistant.setVisible(false);
    		this.supprimer.setEnabled(false);
    		this.ajouter.setEnabled(false);
    		this.reinitialiser.setEnabled(false);
    		
    		/*
        	 * On regarde si l'élement est une instance de PointDeLivraison
        	 * */
    	} else if (eltCourant instanceof PointDeLivraison) {
            labelTitre.setText("Détails de la livraison selectionnée");
    		this.setPointDeLivraison((PointDeLivraison)eltCourant);
    		this.champsAdresse.setText("" +
    							this.pointDeLivraisonCourant.getLieu().getId());
    		Livraison livraisonCourant = 
    								this.pointDeLivraisonCourant.getLivraison();
    		this.champsClient.setText("" + 
    								livraisonCourant.getDestinataire().getId());
    		this.champsPlageDebut.setText("" + 
    			Horaire.formaterHeureArrondie(
    			   livraisonCourant.getPlageHoraire().getCreneau().getDebut()));
    		this.champsPlageFin.setText("" + 
    			Horaire.formaterHeureArrondie(
    			   livraisonCourant.getPlageHoraire().getCreneau().getFin()));
    		if (livraisonCourant.getEtat() == EtatLivraison.NOUVELLE) {
    			this.labelHeureDepart.setText("--:--");
    			this.labelHeureArrive.setText("--:--");
    		} else {
    			this.labelHeureDepart.setText(
    					Horaire.formaterHeureArrondie(
    						livraisonCourant.getHeuresPassage().getDebut()));
        		this.labelHeureArrive.setText(
        				Horaire.formaterHeureArrondie(
        					livraisonCourant.getHeuresPassage().getFin()));
    		}
    		this.champsAdresse.setEnabled(false);
    		this.champsClient.setEnabled(false);
    		this.champsPlageDebut.setEnabled(false);
    		this.champsPlageFin.setEnabled(false);
    		this.panelClient.setVisible(true);
    		this.panelChoixPlage.setVisible(false);
    		this.panelPlageExistant.setVisible(false);
            this.panelNouveauPlage.setVisible(true);
            this.panelHeurePassage.setVisible(true);
            this.supprimer.setEnabled(true);
    		this.ajouter.setEnabled(false);
    		this.reinitialiser.setEnabled(false);
    	}
        this.eltCourant = eltCourant;
    }

   
    /**
	 * Méthode permettant de créer une livraison à parti des 
	 * coordonnées entrées
	 * @return La nouvelle livraison créée.
	 */
    public Livraison creerLivraison() throws IllegalArgumentException{
    	try{
	    	PointDeLivraison nouveauPointDeLivraion = 
	    									new PointDeLivraison(noeudCourant);
	    	int id_client = Integer.parseInt(champsClient.getText());
	    	if(id_client < 0){
	    		createur.afficherEchec("L'ID du client doit être positif");
	    	}
			Destinataire nouveauDestinataire = new Destinataire(id_client);
	    	Livraison nouvelleLivraison = new Livraison(nouveauDestinataire, 
	    												nouveauPointDeLivraion);
	    	nouveauPointDeLivraion.setLivraison(nouvelleLivraison);
	    	return nouvelleLivraison;
    	}
    	catch(NumberFormatException e){
    		throw new IllegalArgumentException("L'ID du client doit être un "
    																+ "entier");
    	}
    }
    
    /**
     * Méthode qui permet d'actualise les informations affichées sur 
     * la vue avec celle de l'element selectionné
     */
    public void actualiser(){
    	this.setEltCourant(eltCourant);
    }
    
    /**
	 * Getter permettant de recuperer le noeud associé à la vue
	 * @return le noeud courant associé à la vue.
	 */
    public Noeud getNoeudCourant() {
        return this.noeudCourant;
    }
    
    /**
	 * Getter permettant de recuperer le point de livraison associé à la vue
	 * @return le point de livraison courant associé à la vue.
	 */
    public PointDeLivraison getPointDeLivraisonCourant() {
        return this.pointDeLivraisonCourant;
    }

    /**
     * Méthode associé au bouton de choix d'une plage existant. 
     * Elle permet d'actualiser la vue selon son mode
     * @param e ActionEvent
     */
    private void buttonPlageExistant_actionPerformed(ActionEvent e) {
        if (buttonPlageExistant.isSelected()) {
            buttonNouvellePlage.setSelected(false);
            panelPlageExistant.setVisible(true);
            panelNouveauPlage.setVisible(false);
            panelHeurePassage.setVisible(false);
        }
    }

    /**
     * Méthode associé au bouton de choix d'une nouvelle plage. 
     * Elle permet d'actualiser la vue selon son mode
     * @param e ActionEvent
     */
    private void buttonNouvellePlage_actionPerformed(ActionEvent e) {
        if (buttonNouvellePlage.isSelected()) {
            buttonPlageExistant.setSelected(false);
            panelPlageExistant.setVisible(false);
            panelNouveauPlage.setVisible(true);
            panelHeurePassage.setVisible(false);
            champsPlageDebut.setText("HH:MM");
        	champsPlageDebut.setForeground(Color.GRAY);
        	champsPlageFin.setText("HH:MM");
        	champsPlageFin.setForeground(Color.GRAY);
        }
    }
    
    /**
	 * Setter permettant d'assigner une liste de plage à celle de la vue.
	 * @param listePlage Un liste de plage donnée pour la vue.
	 */
    public void setListePlage(List<Plage> listePlage){
    	this.listePlage = listePlage;
    }
    
    /**
     * Méthode associé au bouton ajouter permetant d'ajouter une 
     * nouvelle livraison dans la zone.
     * @param e ActionEvent
     */
    private void ajouter_actionPerformed(ActionEvent e) {
    	try {
    		Calendar plageDebut = Calendar.getInstance();
    		Calendar plageFin = Calendar.getInstance();
    		Plage plageHoraire = null;
    		/*
    		 * On regarde que mode de plage on choisit
    		 * Plage existant ou nouvelle plage
    		 * */
    		if (buttonPlageExistant.isSelected()) {
    			/*
    			 * On recupère la plage existante depuis la liste deroulante
    			 * */
    			String chaineCaracterePlage = 
    							(String)listeDeroulantePlage.getSelectedItem();
    			String chaineCaracterePlageDebut = 
    									chaineCaracterePlage.split(" - ")[0];
    			String chaineCaracterePlageFin = 
    									chaineCaracterePlage.split(" - ")[1];
    			plageDebut = Lecteur.extraireHeureArrondie(
    												chaineCaracterePlageDebut);
        		plageFin = Lecteur.extraireHeureArrondie(
        											chaineCaracterePlageFin);

        		plageHoraire = 
        				listePlage.get(listeDeroulantePlage.getSelectedIndex());
    		} else {
    			/*
    			 * On crée une nouvelle plage
    			 * */
    			plageDebut = Lecteur.extraireHeureArrondie(
    												champsPlageDebut.getText());
        		plageFin = Lecteur.extraireHeureArrondie(
        											champsPlageFin.getText());
        		
        		
        		Horaire creneau = new Horaire(plageDebut,plageFin);
        		creneau.completerDate(dateTournee);
        		
        		if (!plageDebut.before(plageFin)){
        			throw new SemantiqueException(
        					"L'heure de debut doit etre inferieure a l'heure "
        															+ "de fin");
        		}
        		plageHoraire = new Plage(creneau);	
    		}
    		
    		/*
			 * On appelle la commande d'ajout d'un nouvelle livraison
			 * */
			if (createur.dmdAjout(this.creerLivraison(), plageHoraire, 
											buttonNouvellePlage.isSelected())) {
				/*
				 * On modifie l'affichage de la vue
				 * */
				ajouter.setEnabled(false);
				supprimer.setEnabled(true);
				reinitialiser.setEnabled(false);
				this.champsAdresse.setEnabled(false);
				this.champsClient.setEnabled(false);
				this.champsPlageDebut.setEnabled(false);
				this.champsPlageFin.setEnabled(false);
				this.panelClient.setVisible(true);
				this.panelChoixPlage.setVisible(false);
				this.panelPlageExistant.setVisible(false);
		        this.panelNouveauPlage.setVisible(true);
		        this.champsPlageDebut.setText(
		        					Horaire.formaterHeureArrondie(plageDebut));
		        this.champsPlageFin.setText(
		        					Horaire.formaterHeureArrondie(plageFin));
		        this.panelHeurePassage.setVisible(true);
			}
			
			
    	} catch ( Exception exp ) {
    		/*
			 * On recupère l'erreur pour l'afficher dans la boite de message
			 * */

			exp.printStackTrace();
			createur.afficherEchec(exp.getMessage());
    	}
		
    }
    
    /**
     * Méthode associé au bouton supprimer permetant de supprimer 
     * une livraison dans la zone.
     * @param e ActionEvent
     */
    private void supprimer_actionPerformed(ActionEvent e) {
    
		/*
		 * On fait une demande de suppression de la livraison selectionnée
		 * */
		if (createur.dmdSuppression(
								this.pointDeLivraisonCourant.getLivraison())) {
			/*
			 * On modifie l'affichage de la vue
			 * */
			ajouter.setEnabled(true);
			supprimer.setEnabled(false);
			reinitialiser.setEnabled(true);
			this.champsClient.setText("");
			this.champsPlageDebut.setText("");
			this.champsPlageFin.setText("");
			this.champsAdresse.setEnabled(true);
			this.champsClient.setEnabled(true);
			this.champsPlageDebut.setEnabled(false);
			this.champsPlageFin.setEnabled(false);
			this.panelChoixPlage.setVisible(true);
			this.buttonPlageExistant.setSelected(true);
			this.buttonNouvellePlage.setSelected(false);
			this.panelPlageExistant.setVisible(true);
	        this.panelNouveauPlage.setVisible(false);
	        this.panelHeurePassage.setVisible(false);
	        this.setVisible(false);
		}
    	
    }
    
    /**
     * Méthode associé au bouton reinitialiser permetant de 
     * vider les différents champs
     * @param e ActionEvent
     */
    private void reinitialiser_actionPerformed(ActionEvent e) {
    	this.champsClient.setText("");
    	champsPlageDebut.setText("HH:MM");
    	champsPlageDebut.setForeground(Color.GRAY);
    	champsPlageFin.setText("HH:MM");
    	champsPlageFin.setForeground(Color.GRAY);
		this.buttonPlageExistant.setSelected(true);
		this.buttonNouvellePlage.setSelected(false);
		this.panelPlageExistant.setVisible(true);
        this.panelNouveauPlage.setVisible(false);
    }
    
    /**
     * Getter permettant de recupperer la liste deroulante de la vue
     * @return la liste deroulante des plages existantes de la vue
     */
    public JComboBox<Plage> getListeDeroulantePlage() {
		return listeDeroulantePlage;
	}
    
    /**
     * Setter permettant d'assigner une liste deroulante à la vue
     * @param listeDeroulantePlage la liste deroulante des 
     * plages existantes pour la vue
     */
    public void setListeDeroulantePlage(JComboBox<Plage> listeDeroulantePlage) {
		this.listeDeroulantePlage=listeDeroulantePlage;
	}
    
    /**
     * Méthode permettant de visualiser une aide à la saisie 
     * pour le champs de texte lié à la plage de debut
     * @param e KeyEvent
     */
    private void champsPlageDebut_keyTyped(KeyEvent e) {
    	if (champsPlageDebut.getText().isEmpty()) {
        	champsPlageDebut.setText("HH:MM");
        	champsPlageDebut.setForeground(Color.GRAY);
            champsPlageDebut.setCaretPosition(0);
        } else if (champsPlageDebut.getText().equals("HH:MM")) {
        	champsPlageDebut.setText("");
        	champsPlageDebut.setForeground(Color.BLACK);
        } else {
        	champsPlageDebut.setForeground(Color.BLACK);
        }
    }
    
    /**
     * Méthode permettant de revenir au debut de la ligne 
     * pour le champs de texte lié à la plage de debut
     * @param e MouseEvent
     */
    private void champsPlageDebut_mouseClicked(MouseEvent e) {
        if (champsPlageDebut.getText().equals("HH:MM")) {
        	champsPlageDebut.setCaretPosition(0);
        }
    }
    
    /**
     * Méthode permettant de visualiser une aide à la saisie 
     * pour le champs de texte lié à la plage de fin
     * @param e KeyEvent
     */
    private void champsPlageFin_keyTyped(KeyEvent e) {
    	if (champsPlageFin.getText().isEmpty()) {
    		champsPlageFin.setText("HH:MM");
    		champsPlageFin.setForeground(Color.GRAY);
    		champsPlageFin.setCaretPosition(0);
        } else if (champsPlageFin.getText().equals("HH:MM")) {
        	champsPlageFin.setText("");
        	champsPlageFin.setForeground(Color.BLACK);
        } else {
        	champsPlageDebut.setForeground(Color.BLACK);
        }
    }
    
    /**
     * Méthode permettant de revenir au debut de la ligne 
     * pour le champs de texte lié à la plage de fin
     * @param e MouseEvent
     */
    private void champsPlageFin_mouseClicked(MouseEvent e) {
        if (champsPlageFin.getText().equals("HH:MM")) {
        	champsPlageFin.setCaretPosition(0);
        }
    }
    
    /**
     * Setter permettant d'assigner la date de la tournée à la vue
     * @param dateTournee la date de la tournée
     */
    public void setDateTournee(Calendar dateTournee) {
		this.dateTournee = dateTournee;
	}
}
