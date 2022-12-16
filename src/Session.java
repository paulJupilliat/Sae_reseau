import java.net.Socket;

public class Session {
    private final ServeurEcouter recevoir;
    private final ServeurEnvoyer envoyer;
    private String nom;
    private final Socket clSocket;

    public Session(String nom, Socket clSocket) {
        this.recevoir = null;
        this.envoyer = null;
        this.nom = nom;
        this.clSocket = clSocket;
        
    }

    public Session(ServeurEcouter recevoir, ServeurEnvoyer envoyer, String nom, Socket clSocket) {
        this.recevoir = recevoir;
        this.envoyer = envoyer;
        this.nom = nom;
        this.clSocket = clSocket;
    }

    public ServeurEcouter getRecevoir() {
        return recevoir;
    }

    public ServeurEnvoyer getEnvoyer() {
        return envoyer;
    }
}
