package terminal.serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import launch.Serveur;

public class ServeurEnvoie extends Thread {
  private List<Session> clients;
  private String message;
  private Socket envoyeur;
  private String all;
  private Salon salon;
  private Session destinataire;
  private Serveur serveur;

  /**
   * Constructeur de la classe ServeurEnvoie si il veut envoyer le message à tous le monde
   *
   * @param clients {List<Session>} La liste des clients connectés
   * @param message {String} Le message à envoyer
   * @param envoyeur {Socket} Le socket de l'envoyeur
   * @param salon {String} Le nom du salon
   * @param all {Strong} Si il faut envoyer à tous "all", à l'envoyeur "info", à un socket précis "to"
   */
  public ServeurEnvoie(
    Serveur serveur,
    String message,
    Socket envoyeur,
    String salon,
    String all
  ) {
    this.serveur = serveur;
    this.clients = serveur.getClients();
    this.message = message;
    this.envoyeur = envoyeur;
    this.all = all;
    this.salon = serveur.getSalon(salon);
  }

  /**
   * Constructeur de la classe ServeurEnvoie si il veut envoyer le message à un socket précis
   * @param serveur {Serveur} Le serveur
   * @param message {String} Le message à envoyer
   * @param all {String} Si il faut envoyer à tous "all", à l'envoyeur "info", à un socket précis "to"
   * @param envoyeur {Socket} Le socket de l'envoyeur
   * @param destinataie {String} Le nom du destinataire
   */
  public ServeurEnvoie(
    Serveur serveur,
    String message,
    String all,
    Socket envoyeur,
    String destinataie
  ) {
    this.serveur = serveur;
    this.clients = serveur.getClients();
    this.message = message;
    this.envoyeur = envoyeur;
    this.all = all;
    this.salon = null;
    this.destinataire = serveur.getSessionString(destinataie);
  }

  @Override
  public void run() {
    if (message != null) {
      if (this.all.equals("all")) {
        this.sendAll();
      } else if (this.all.equals("info")) {
        this.sendInfo();
      } else if (this.all.equals("to")) {
        this.sendTo();
      }
      // Réinitialisation de la variable de message
      message = null;
    }
  }

  /**
   * Envoie un message privé
   */
  private void sendTo() {
    if (this.destinataire == null) {
      this.message = "Le destinataire n'existe pas";
      this.sendInfo();
    } else {
      this.destinataire.sendToMe(
          "from " +
          this.serveur.getSession(envoyeur).getNom() +
          " -> " +
          this.message
        );
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
      System.out.println(
        "Message envoyé à " + envoyeur.getInetAddress() + " : " + message
      );
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
      for (Session sessionCl : this.salon.getSessions()) {
        if (sessionCl.getSocket() != envoyeur) {
          this.salon.send(this.message, sessionCl.getSocket());
        }
      }
    }
  }
}
