import java.net.Socket;
import java.util.List;

public class Salon {
  private String nom;
  private String description;
  private int nbMax;
  private int nbActuel;
  private List<Session> sessions;
  private Serveur serveur;

  public Salon(
    String nom,
    String description,
    int nbMax,
    int nbActuel,
    List<Session> sessions,
    Serveur serveur
  ) {
    this.nom = nom;
    this.description = description;
    this.nbMax = nbMax;
    this.nbActuel = nbActuel;
    this.sessions = sessions;
    this.serveur = serveur;
  }

  public void sendAll(String msg, Socket destinataire) {
    for (Session session : sessions) {
      if (session.getSocket() != destinataire) {
        session.send(msg);
      }
    }
  }
  
  public String getNom() {
    return nom;
  }
  
  /**
   * Déconnecte un client du salon
   * @param client Le client à déconecter
   */
  public void deco(Socket client) {
    for (Session session : sessions) {
      if (session.getSocket() == client) {
        sessions.remove(session);
      }
    }
  }
  
  /**
   * Essaye de connecté un client au salon
   * @param client Le sockert du client à connecté
   * @return Si le client est connecté ou non
   */
  public boolean connexion(Socket client) {
      if (nbActuel < nbMax) {
          sessions.add(this.serveur.getSession(client));
          return true;
      }
      return false;
  }
}
