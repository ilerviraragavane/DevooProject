package opti_fret_courly.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import opti_fret_courly.controleur.Createur;

/**
 * Cette classe est la vue permettant de représenter 
 * boite de message dans l'application.
 * 
 * @author Iler VIRARAGAVANE
 */
public class VueZoneDeDialogue extends JPanel {

	/**
	 * Le créateur.
	 */
	Createur createur;
	
	/**
	 * Le panel deroulante principale.
	 */
    private JScrollPane panelDeroulanteMessage = new JScrollPane();
    
    /**
	 * Le sous panel contenant le message
	 */
    private JTextPane contenuMessage = new JTextPane();
    
    /**
	 * Le panel ayant le titre de la vue.
	 */
    private JPanel panelTitre = new JPanel();
    
    /**
	 * Le label contenant le titre.
	 */
    private JLabel labelTitre = new JLabel();

    /**
	 * Constructeur de la classe <code>VueZoneDeDialogue</code>
	 * @param createur Le createur qui sera associé à la vue.
	 */
    public VueZoneDeDialogue(Createur createur) {
    	this.createur=createur;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(100, 100));
        panelDeroulanteMessage.setBorder(
        				BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        contenuMessage.setBackground(new Color(240,240,240));
        contenuMessage.setEditable(false);
        panelDeroulanteMessage.getViewport().add(contenuMessage, null);
        panelTitre.setBorder(
        				BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panelTitre.setLayout(new FlowLayout(1, 1, 1));
        labelTitre.setText("Historique des messages");
        panelTitre.add(labelTitre, null);
        this.add(panelDeroulanteMessage, BorderLayout.CENTER);
        this.add(panelTitre, BorderLayout.NORTH);
    }

    /**
	 * Méthode afficherMessage de <code>VueZoneDeDialogue</code> 
	 * permettant de d'afficher le message recu dans 
	 * la boite de message
	 * @param message Chaine de caractère qui compose le message.
	 * @param erreur Booléen qui indique si il s'agit d'un message erreur.
	 */
    public void afficherMessage (String message, boolean erreur) {
    	//JTextPane pane = new JTextPane();
       // StyledDocument doc = pane.getStyledDocument();

        StyledDocument doc = contenuMessage.getStyledDocument();
        ajouterStylesDansDocument(doc);

        try {
        	if (erreur) {
        		doc.insertString(0, " - "+message+"\n", 
        										doc.getStyle("styleErreur"));
        	} else {
        		doc.insertString(0, " - "+message+"\n", 
        										doc.getStyle("gras"));
        	}
        	contenuMessage.setCaretPosition(0);
        } catch (BadLocationException ble) {
        }

        contenuMessage.setDocument(doc);
    }

    /**
	 * Méthode de la vue permettant d'ajouter des styles pour 
	 * les documents associé
	 * @param document Un docuement aux quelle sera 
	 * associé les styles.
	 */
    public void ajouterStylesDansDocument(StyledDocument document) {
        //Initialisation des styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style normal = document.addStyle("normal", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style gras = document.addStyle("gras", normal);
        StyleConstants.setBold(gras, true);

        Style styleErreur = document.addStyle("styleErreur", gras);
        StyleConstants.setForeground(styleErreur, Color.RED);

        styleErreur = document.addStyle("small", normal);
        StyleConstants.setFontSize(styleErreur, 10);

        styleErreur = document.addStyle("large", normal);
        StyleConstants.setFontSize(styleErreur, 16);
    }

}
