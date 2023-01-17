package terminal.serveur;

import java.net.Socket;

import launch.Serveur;

public class ChatPrive extends Salon {
    private String destinataire;
    
    public ChatPrive(String nom, String description, int nbMax, int nbActuel, Serveur serveur, String destinataire, String envoyeur) {
        super(nom, description, 2, 2, serveur);
        this.destinataire = destinataire;
        super.sessions.add(serveur.getSessionString(destinataire));
        super.sessions.add(serveur.getSessionString(envoyeur));
    }
    
    public String getDestinataire() {
        return destinataire;
    }
    
    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    /**
     * Ne déconnècte pas le client du salon car c'est un privé
     * @param client Uniquement pour respecter la signature
     * @return true
     */
    @Override
    public boolean deco(Socket client) {
        return true;
    }

    @Override
    public boolean connexion(Socket client) {
        if (super.sessions.contains(this.serveur.getSession(client))) {
            return true;
        }
        return false;
    }
    
    @Override
    public void sendAll(String msg, Socket envoyeur) {
        ServeurEnvoie envoie = new ServeurEnvoie(
            this.serveur,
            msg,
            envoyeur,
            this.nom,
            "to");
        envoie.start();
    }
}
