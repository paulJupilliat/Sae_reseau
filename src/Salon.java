import java.net.Socket;
import java.util.List;
import java.io.PrintWriter;

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
  public boolean deco(Socket client) {
    for (Session session : sessions) {
      if (session.getSocket() == client) {
        sessions.remove(session);
        return true;
      }
    }
    return false;
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

  public void send(String message, Socket socket) {
    try{
    PrintWriter out = new PrintWriter(socket.getOutputStream());
    out.println(message);
    out.flush();
    } catch (Exception e) {
      System.out.println("Erreur d'envoie de message pour " + socket.toString());
    }
  }
}
