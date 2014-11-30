package opti_fret_courly.controleur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import opti_fret_courly.modele.Chemin;
import opti_fret_courly.modele.Entrepot;
import opti_fret_courly.modele.Horaire;
import opti_fret_courly.modele.Livraison;
import opti_fret_courly.modele.PointDArret;
import opti_fret_courly.modele.PointDeLivraison;
import opti_fret_courly.modele.Tournee;
import opti_fret_courly.modele.Troncon;
import opti_fret_courly.modele.Zone;
import opti_fret_courly.vue.VueDetailNoeud;
import opti_fret_courly.vue.VueDetailsItineraire;

/**
 * Cette commande permet d'éditer le fichier d'instructions sur la tournée pour
 * le livreur
 * 
 * @author Elody Catinel
 * 
 */
public class CmdFicInstructions extends CmdNonAnnulable {

	/**
	 * Le fichier dans lequel on souhaite écrire les instructions
	 */
	private String fichier;

	/**
	 * Zone pour laquelle les instructions doivent être éditées
	 */
	private VueDetailsItineraire vueDetailsItineraire;

	/**
	 * Lance l'écriture du fichier d'instructions
	 * 
	 * @param vueDetailsItineraire la vue représentant le détail de la tournée
	 * @param fichier le nom du fichier à écrire
	 */
	public CmdFicInstructions(VueDetailsItineraire vueDetailsItineraire, String fichier) {
		this.vueDetailsItineraire = vueDetailsItineraire;
		this.fichier = fichier;
	}

	/**
	 * Lance l'écriture du fichier d'instructions
	 * @throws IOException si une erreur survient lors de l'écriture dans le 
	 * fichier
	 * @throws IllegalStateException si les instructions ne peuvent pas être 
	 * écrites au moment de l'invocation de la méthode
	 */
	public void executer() throws IOException, IllegalStateException {

		
		if (vueDetailsItineraire == null) {
			throw new IllegalStateException(
					"La vue de détail de la tournée est nulle : impossible d'ecrire les"
					+ " instructions");
		}

		/* On crée le fichier seulement s'il n'existe pas déjà */
		File file = new File(fichier);
		BufferedWriter buff = Files.newBufferedWriter(file.toPath(),
				Charset.forName("UTF-8"), StandardOpenOption.CREATE_NEW);

		try {
			/* Date de la tournée */
			StringBuilder texte = vueDetailsItineraire.ecrire();
			
			buff.write(texte.toString());	

			
		} catch (Exception e) {
			/* On supprime le fichier créé en cas d'erreur à l'exécution */
			file.delete();
			throw (e);
		}
		buff.close();

	}
}
