package opti_fret_courly.controleur;

import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import opti_fret_courly.modele.EltCarte;
import opti_fret_courly.modele.Horaire;
import opti_fret_courly.modele.Plage;
import opti_fret_courly.modele.Tournee;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueZone;

/**
 * Cette commande se charge de selectionner l'element du modele qui est clique.
 * 
 * @author Iler VIRARAGAVANE
 * 
 */
public class CmdSelection extends CmdNonAnnulable {
	
	/**
	 * Définit la vue qui contient les informations concernant 
	 * l'élément selectionné.
	 */
	private VueDetailNoeud vueDetailNoeud;
	
	/**
	 * Définit la vue associée à la <code>zone</code>.
	 */
	private VueZone vueZone;

	/**
	 * Définit l'élément de la carte qui est cliqué.
	 */
	private EltCarte eltClique;

	/**
	 * Constructeur de la classe <code>CmdSelection</code>
	 * @param vueDetailNoeud Une vue détail dans laquelle seront affichées les 
	 * informations lie à l'element.
	 * @param vueZone Une vue zone dans laquelle la sélection sera appliqué.
	 * @param eltClique Un element de la carte qui est cliqué.
	 */
	public CmdSelection(VueDetailNoeud vueDetailNoeud, VueZone vueZone, 
			EltCarte eltClique) {
		this.vueDetailNoeud = vueDetailNoeud;
		this.vueZone = vueZone;
		this.eltClique = eltClique;
	}

	/**
	 * Permet d'exécuter la commande CmdSelection et donc d'obtenir l'élément
	 * sélectionné.
	 */
	public void executer() {
		/* On vérifie si un élement à été cliqué ou non afin de 
		 * l'associé à la vue détail qui se chargera d'afficher les 
		 * informations lié à l'élement de la carte
		 */
		if (eltClique != null) {
			this.vueDetailNoeud.setVisible(true);
			/* On remplie la liste déroulante associé au plages 
			 * horaires avec les plages horaires existantes
			 */
			remplirPlagesExistant();
		} else {
			this.vueDetailNoeud.setVisible(false);
		}
		this.vueDetailNoeud.setEltCourant(eltClique);
	}

	/**
	 * Permet de remplir la liste déroulante contenant les plages horaires 
	 * existante dans la vue détail.
	 */
	public void remplirPlagesExistant(){
		Vector<String> elementsListe = new Vector<String>();
		Tournee tournee = vueZone.getZone().getTournee(); 
		if (tournee != null){
			/* On parcourt les différentes plages horaire de la tournée et on 
			 * les ajoute à la liste déroulante sous la forme HH:MM - HH:MM
			 */
			List<Plage> plagesHoraires = tournee.getPlagesHoraires();
			for (int i = 0; i < plagesHoraires.size(); i++){
				elementsListe.add(
						Horaire.formaterHeureArrondie(plagesHoraires.get(i).
								getCreneau().getDebut())
						+" - "+
						Horaire.formaterHeureArrondie(plagesHoraires.get(i).
								getCreneau().getFin()));
			}
			this.vueDetailNoeud.getListeDeroulantePlage().
					setModel(new DefaultComboBoxModel(elementsListe));
			this.vueDetailNoeud.setListePlage(tournee.getPlagesHoraires());
		}
	}
}
