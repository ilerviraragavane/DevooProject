package opti_fret_courly.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import opti_fret_courly.controleur.Createur;

/**
 * Cette classe est la fenetre principale de l'application.
 * 
 * @author Iler VIRARAGAVANE
 * @author Franck MPEMBA BONI
 */
public class Fenetre extends JFrame {
	
	/**
	 * La vue contenant les informations 
	 * sur l'élement selectionné.
	 */
    private VueDetailNoeud vueDetailNoeud;
    
    /**
	 * La vue associé à la zone.
	 */
    private VueZone vueZone;
    
    /**
	 * La vue contenant les informations 
	 * textuelle sur l'itinéraire de la tournée.
	 */
    private VueDetailsItineraire vueItineraire;
    
    /**
	 * La vue contenant la boite de messages.
	 */
    private VueZoneDeDialogue vueZoneDeDialogue;
    
    /**
	 * La vue associé aux instructions.
	 */
    private VueDetailsItineraire vueDetailsItineraire;

    /**
	 * Le créateur.
	 */
    private Createur createur;
    
    /**
	 * Le panel contenant les boutons de chargements.
	 */
    private JPanel panelOutilsChargement = new JPanel();
    
    /**
	 * Le panel contenant la vue details de 
	 * l'élement selectionné.
	 */
    private JPanel panelDetailNoeud = new JPanel();
    
    /**
	 * Le panel contenant les commandes principales.
	 */
    private JPanel panelCommande = new JPanel();
    
    /**
	 * Le bouton charger une feuille de plan
	 */
    private JButton chargerPlanButton = new JButton();
    
    /**
	 * Le bouton charger une feuille de livraison.
	 */
    private JButton chargerLivraisonButton = new JButton();
    
    /**
	 * Le bouton annuler une commande
	 */
    private JButton annulerButton = new JButton();
    
    /**
	 * Le bouton retablir une commande.
	 */
    private JButton retablirButton = new JButton();
    
    /**
	 * Le bouton actualiser ou calculer une tournée.
	 */
    private JButton actualiserButton = new JButton();
    
    /**
	 * Le bouton itinéraire afin de générer 
	 * une version textuelle de la tournée
	 */
    private JButton itineraireButton = new JButton();

    /**
	 * Constructeur de la classe <code>Fenetre</code>
	 */
    public Fenetre() {
        this.createur= new Createur(this);
        this.vueZone = new VueZone(this.createur);
        this.vueZoneDeDialogue = new VueZoneDeDialogue(createur);
        this.vueDetailNoeud = new VueDetailNoeud(this.createur);
        this.vueDetailsItineraire = new VueDetailsItineraire();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
	 * Méthode jbInit de <code>Fenetre</code> permettant 
	 * d'initialiser et de position les différents 
	 * composants de la fenetre.
	 * @throws Exception
	 */
    private void jbInit() throws Exception {
        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(new Dimension(1000, 600));
        this.setTitle("Opti Fret COURLY H4103");
        panelOutilsChargement.setLayout(new FlowLayout(-1, 20, 2));
        panelOutilsChargement.setBorder(
        				BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        panelDetailNoeud.setBorder(
        				BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        panelDetailNoeud.setPreferredSize(new Dimension(250, 14));
        panelDetailNoeud.setLayout(new BorderLayout());
        panelCommande.setBorder(
        				BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        panelCommande.setLayout(new GridBagLayout());
        panelCommande.setPreferredSize(new Dimension(150, 14));
        chargerPlanButton.setText("Charger le plan");
        chargerPlanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chargerPlanButton_actionPerformed(e);
            }
        });
        chargerLivraisonButton.setText("Charger la liste des livraisons");
        chargerLivraisonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chargerLivraisonButton_actionPerformed(e);
            }
        });
        annulerButton.setText("Annuler");
        annulerButton.setIcon(
        				new ImageIcon("src/opti_fret_courly/icon/undo.png"));
        annulerButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        annulerButton.setHorizontalTextPosition(AbstractButton.CENTER);
        annulerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	annulerButton_actionPerformed(e);
            }
        });
        retablirButton.setText("Rétablir");
        retablirButton.setIcon(
        				new ImageIcon("src/opti_fret_courly/icon/redo.png"));
        retablirButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        retablirButton.setHorizontalTextPosition(AbstractButton.CENTER);
        retablirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	retablirButton_actionPerformed(e);
            }
        });
        actualiserButton.setText("Calculer/Actualiser");
        actualiserButton.setIcon(
        				new ImageIcon("src/opti_fret_courly/icon/refresh.png"));
        actualiserButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        actualiserButton.setHorizontalTextPosition(AbstractButton.CENTER);
        actualiserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	actualiserButton_actionPerformed(e);
            }
        });
        itineraireButton.setText("Itinéraire");
        itineraireButton.setIcon(
        				new ImageIcon("src/opti_fret_courly/icon/carte.png"));
        itineraireButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        itineraireButton.setHorizontalTextPosition(AbstractButton.CENTER);
        itineraireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	itineraireButton_actionPerformed(e);
            }
        });
        panelOutilsChargement.add(chargerPlanButton, null);
        panelOutilsChargement.add(chargerLivraisonButton, null);
        
        //panelOutilsChargement.add(vueZone.getLabelSouris(), null);
        
        this.getContentPane().add(panelOutilsChargement, BorderLayout.NORTH);
        panelDetailNoeud.add(vueDetailNoeud, BorderLayout.CENTER);
        this.getContentPane().add(panelDetailNoeud, BorderLayout.EAST);
        this.getContentPane().add(vueZoneDeDialogue, BorderLayout.SOUTH);
        panelCommande.add(annulerButton,
                          new GridBagConstraints(0, 0, 1, 1, 0, 0, 
                        		  				 GridBagConstraints.NORTH,
                                                 GridBagConstraints.HORIZONTAL, 
                                                 new Insets(0, 5, 0, 5), 0, 0));
        panelCommande.add(retablirButton,
                          new GridBagConstraints(0, 1, 1, 1, 0, 0, 
                        		  				 GridBagConstraints.NORTH,
                                                 GridBagConstraints.HORIZONTAL, 
                                                 new Insets(5, 5, 0, 5), 0, 0));
        panelCommande.add(actualiserButton,
                          new GridBagConstraints(0, 2, 1, 1, 0, 0, 
                        		  				 GridBagConstraints.NORTH,
                                                 GridBagConstraints.HORIZONTAL, 
                                                 new Insets(5, 5, 0, 5), 0, 0));
        panelCommande.add(itineraireButton,
                          new GridBagConstraints(0, 3, 1, 1, 0, 0, 
                        		  				 GridBagConstraints.NORTH,
                                                 GridBagConstraints.HORIZONTAL, 
                                                 new Insets(5, 5, 0, 5), 0, 0));
        this.getContentPane().add(panelCommande, BorderLayout.WEST);
        this.getContentPane().add(vueZone, BorderLayout.CENTER);
        chargerLivraisonButton.setEnabled(false);
        annulerButton.setEnabled(false);
        retablirButton.setEnabled(false);
        actualiserButton.setEnabled(false);
        itineraireButton.setEnabled(false);
        vueDetailNoeud.setVisible(false);
    }

    /**
	 * Methode associé au bouton Charger le plan permettant de charger 
	 * un fichier plan au format XML dans l'application.
	 * @param e ActionEvent.
	 */
    private void chargerPlanButton_actionPerformed(ActionEvent e) {
    	/*
    	 * On crée un selectionneur de fichier
    	 * */
        JFileChooser selectionneurDeFichier = new JFileChooser();
        
        /*
    	 * On précise un dossier par defaut
    	 * */
        selectionneurDeFichier.setCurrentDirectory(new File("src/test/plans"));
        selectionneurDeFichier.setDialogTitle("Charger un plan");
        
        /*
    	 * On met en place un système de filtre pour n'utiliser que 
    	 * des fichier XML
    	 * */
        FileNameExtensionFilter filtreFichier;
        filtreFichier = new FileNameExtensionFilter("Fichiers xml", "xml");
        selectionneurDeFichier.setFileFilter(filtreFichier);
        selectionneurDeFichier.setApproveButtonText("Charger");

        /*
    	 * On affiche la boite de dialogue
    	 * */
        if (selectionneurDeFichier.showOpenDialog(null) == 
        										JFileChooser.APPROVE_OPTION) {
        	String fichier = 
        			selectionneurDeFichier.getSelectedFile().getAbsolutePath();  

        	if (createur.dmdChargementZone(fichier)) {
                chargerLivraisonButton.setEnabled(true);
        	}
        }
    }

    /**
     * Methode associé au bouton Charger la liste des livraison 
     * permettant de charger un fichier de livraison au format 
     * XML dans l'application.
     * @param e ActionEvent.
     */
    private void chargerLivraisonButton_actionPerformed(ActionEvent e) {
    	/*
    	 * On crée un selectionneur de fichier
    	 * */
        JFileChooser selectionneurDeFichier = new JFileChooser();
        
        /*
    	 * On précise un dossier par defaut
    	 * */
        selectionneurDeFichier.setCurrentDirectory(
        										new File("src/test/demandes"));
        selectionneurDeFichier.setDialogTitle("Charger une liste de "
        										+ "livraisons");
        
        /*
    	 * On met en place un système de filtre pour n'utiliser que des 
    	 * fichier XML
    	 * */
        FileNameExtensionFilter filtreFichier;
        filtreFichier = new FileNameExtensionFilter("Fichiers xml", "xml");
        selectionneurDeFichier.setFileFilter(filtreFichier);
        selectionneurDeFichier.setApproveButtonText("Charger");
        
        /*
    	 * On affiche la boite de dialogue
    	 * */
        if (selectionneurDeFichier.showOpenDialog(null) == 
        										JFileChooser.APPROVE_OPTION) {
        	String fichier = 
        			selectionneurDeFichier.getSelectedFile().getAbsolutePath();  
        	
        	if (createur.dmdChargementDemandes(fichier)) {
                actualiserButton.setEnabled(true);
                itineraireButton.setEnabled(false);
        	}
        }
    }

    /**
     * Methode associé au bouton Annuler permettant d'annuler 
     * la dernière commande et les possibles commandes qui précèdent.
     * @param e ActionEvent.
     */
    private void annulerButton_actionPerformed(ActionEvent e) {
    	createur.dmdAnnulation();
    }
    
    /**
     * Methode associé au bouton Retablir permettant de reproduire 
     * la dernière commande annulée et les possibles commandes qui suivent.
     * @param e ActionEvent.
     */
    private void retablirButton_actionPerformed(ActionEvent e) {
    	createur.dmdRefaire();
    }
    
    /**
     * Methode associé au bouton Calculer permettant 
     * de calculer une tournée.
     * @param e ActionEvent.
     */
    private void actualiserButton_actionPerformed(ActionEvent e) {
    	if (createur.dmdCalculer()) {
    		itineraireButton.setEnabled(true);
    	}
    }
    
    /**
     * Methode permettant d'activer ou non le bouton Annuler.
     * @param active Etat qui indique s'il faut activer le bouton ou non
     */
    public void activerAnnuler(boolean active) {
    	annulerButton.setEnabled(active);
    }
    
    /**
     * Methode permettant d'activer ou non le bouton Retablir.
     * @param active Etat qui indique s'il faut activer le bouton ou non
     */
    public void activerRetablir(boolean active) {
    	retablirButton.setEnabled(active);
    }
    
    /**
     * Methode permettant d'activer ou desactiver le bouton Itineraire
     * @param active Etat qui indique s'il faut activer le bouton ou non
     */
    public void activerItineraire(boolean active) {
		itineraireButton.setEnabled(active);
	}
    /**
	 * Getter permettant de recuperer la vue de la zone
	 * @return la vue associé à la zone.
	 */
    public VueZone getVueZone() {
        return vueZone;
    }

    /**
	 * Getter permettant de recuperer la vue associé au details 
	 * concerant l'itinéraire.
	 * @return la vue associé à l'itinéraire.
	 */
    public VueDetailsItineraire getVueItineraire() {
        return vueItineraire;
    }

    /**
	 * Getter permettant de recuperer la vue associé au details 
	 * concerant le l'element sélectionné.
	 * @return la vue associé au details de l'element selectionné.
	 */
    public VueDetailNoeud getVueDetailNoeud() {
        return vueDetailNoeud;
    }

    /**
     * Methode associé au bouton Itinéraire permettant d'avoir un 
     * récapitulatif de la tournée sous format textuelle.
     * @param e ActionEvent.
     */
    private void itineraireButton_actionPerformed(ActionEvent e) {
    	/*
    	 * On crée un selectionneur de fichier
    	 * */
    	JFileChooser selectionneurDeFichier = new JFileChooser();
        selectionneurDeFichier.setDialogTitle("Sauvegarder l'itinéraire");
        
        /*
    	 * On met en place un système de filtre pour n'utiliser que des 
    	 * fichiers TXT
    	 * */
        FileNameExtensionFilter fileFilter;
        fileFilter =
                new FileNameExtensionFilter("Fichier texte", "txt");
        selectionneurDeFichier.setFileFilter(fileFilter);
        selectionneurDeFichier.setApproveButtonText("Enregistrer");
        
        /*
    	 * On affiche la boite de dialogue
    	 * */
        if (selectionneurDeFichier.showSaveDialog(null) == 
        										JFileChooser.APPROVE_OPTION) {
        	/*
        	 * On crée un fichier avec le chemin à la racine et son extension
        	 * */
        	String fichier = 
        			selectionneurDeFichier.getSelectedFile().getAbsolutePath();  
        	fichier+="."+fileFilter.getExtensions()[0];
        	vueDetailsItineraire.setTournee(vueZone.getZone().getTournee());
        	if(createur.dmdFicInstructions(vueDetailsItineraire,fichier)){
        		this.vueZoneDeDialogue.afficherMessage("Le fichier "
        												+ fichier 
        												+ " a été édité", 
        												false);
        	}
        }
    }
    
    /**
	 * Getter permettant de recuperer la vue associé à 
	 * la boite de message
	 * @return la vue associé à la boite de message
	 */
	public VueZoneDeDialogue getVueZoneDeDialogue() {
		return vueZoneDeDialogue;
	}
	
    
}
