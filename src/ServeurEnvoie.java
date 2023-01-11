import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServeurEnvoie extends Thread {
  private List<Session> clients;
  private String message;
  private Socket envoyeur;
  private String all;
  private Salon salon;

  /**
   * Constructeur de la classe ServeurEnvoie si il veut envoyer le message à tous le monde
   *
   * @param clients {List<Session>} La liste des clients connectés
   * @param message {String} Le message à envoyer
   * @param envoyeur {Socket} Le socket de l'envoyeur
   * @param all {Strong} Si il faut envoyer à tous "all", à l'envoyeur "info", à un socket précis "to"
   */
  public ServeurEnvoie(
    Serveur serveur,
    String message,
    Socket envoyeur,
    String salon,
    String all
  ) {
    this.clients = serveur.getClients();
    this.message = message;
    this.envoyeur = envoyeur;
    this.all = all;
    this.salon = serveur.getSalon(salon);
  }

  @Override
  public void run() {
    if (message != null) {
      if (this.all.equals("all")) {
        this.sendAll();
      }
      if (this.all.equals("info")) {
        this.sendInfo();
      }
      // Réinitialisation de la variable de message
      message = null;
    }
  }

  /**
   * Envoie le message à l'envoyeur
   */
  private void sendInfo() {
    // Envoi du message à l'envoyeur
    try {
      PrintWriter out = new PrintWriter(envoyeur.getOutputStream());
      out.println(message);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Envoie le message à tous les sockets de ce serveur sauf à l'envoyeur
   */
  private void sendAll() {
    if (this.salon == null) {
      this.message =
        "Rejoigné un salon avec la commande '/salon <nomSalon>'\n Pour voir tous les salons '/salons'";
      this.sendInfo();
    } else {
      // Envoi du message à tous les sockets
      for (Session sessionCl : clients) {
        if (sessionCl.getSocket() != envoyeur) {
          this.salon.send(this.message, sessionCl.getSocket());
        }
      }
    }
  }
}
