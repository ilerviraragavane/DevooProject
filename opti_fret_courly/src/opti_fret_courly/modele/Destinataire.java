package opti_fret_courly.modele;

/**
 * La classe Destinataire représente le destinataire d'un colis.
 * 
 * @author florentboisselier
 *
 */
public class Destinataire {
	
    /**
     * id du destinataire.
     */
    private int id;

    /**
     * Constructeur de la classe Destinataire.
     * @param id : id du destinataire à instancier.
     */
    public Destinataire(int id) {
        this.id = id;
    }

    /**
     * Getter
     * @return retourne l'id du destinataire
     */
    public int getId() {
        return id;
    }

    /**
     * @return une description textuelle d'un destinataire.
     */
    public String toString() {
        return "Destinataire : " + id;
    }

}
