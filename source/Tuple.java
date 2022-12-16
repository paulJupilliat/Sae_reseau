public class Tuple {
    private final ServeurEcouter recevoir;
    private final ServeurEnvoyer envoyer;

    public Tuple() {
        this.recevoir = null;
        this.envoyer = null;
    }

    public Tuple(ServeurEcouter recevoir, ServeurEnvoyer envoyer) {
        this.recevoir = recevoir;
        this.envoyer = envoyer;
    }

    public ServeurEcouter getRecevoir() {
        return recevoir;
    }

    public ServeurEnvoyer getEnvoyer() {
        return envoyer;
    }
}
