package terminal.serveur;

import java.net.Socket;

import launch.Serveur;

public class Session {
  private Socket socket;
  private Serveur serveur;
  private ServeurEcouter serveurEcouter;
  private String nom;

  public Session(Socket socket, Serveur serveur) {
    try {
      this.socket = socket;
      this.serveur = serveur;
      this.nom = "Anonyme";
      this.serveurEcouter = new ServeurEcouter(socket, serveur);
      this.serveurEcouter.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Socket getSocket() {
    return socket;
  }

  /**
   * Envoie un message à tous les clients
   * @param msg {String} Le message à envoyer
   */
  public void send(String msg) {
    this.serveur.sendAll(msg, socket, null);
  }

  public void sendToMe(String msg) {
    this.serveur.sendInfo(msg, socket);
  }

  public void setNom(String nom) {
    this.nom = nom;
  }
  
  public String getNom() {
      return nom;
  }
}
