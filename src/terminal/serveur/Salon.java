package terminal.serveur;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import launch.Serveur;

public class Salon {
  private String nom;
  private String description;
  private int nbMax;
  private int nbActuel;
  private List<Session> sessions;
  private Serveur serveur;
  private double startTime;

  public Salon(
    String nom,
    String description,
    int nbMax,
    int nbActuel,
    Serveur serveur
  ) {
    this.nom = nom;
    this.description = description;
    this.nbMax = nbMax;
    this.nbActuel = nbActuel;
    this.sessions = new ArrayList<>();
    this.serveur = serveur;
    this.startTime = System.currentTimeMillis();

  }

  /**
   * Donne le temps que le salon à été crée
   * @return le temps que le salon à été crée
   */
  public String getTime() {
    double elapsedTime = System.currentTimeMillis() - this.startTime;
    int hours = (int) (elapsedTime / (60 * 60 * 1000));
    int minutes = (int) (elapsedTime / (60 * 1000)) % 60;
    int seconds = (int) (elapsedTime / 1000) % 60;
    return String.format("%d heure(s), %d minute(s) et %d seconde(s)", hours, minutes, seconds);

  }

  public void sendAll(String msg, Socket destinataire) {
    ServeurEnvoie envoie = new ServeurEnvoie(
      this.serveur,
      msg,
      destinataire,
      this.nom,
      "all"
    );
    envoie.start();
  }

  public String getNom() {
    return nom;
  }

  /**
   * Vérifie si un utilisateur est dans le salon (en créant un thread)
   * @param user Le nom de l'utilisateur à vérifier
   * @return Si l'utilisateur est dans le salon ou non
   */
  public boolean userIn(String user) {
    AtomicBoolean res = new AtomicBoolean(false);
    Thread thread = new Thread(() -> {
      for (Session session : this.sessions) {
        if (session.getNom().equals(user)) {
          res.set(true);
        }
      }
    });
    thread.start();
    try {
      thread.join();
    } catch (Exception e) {
      System.out.println("Erreur de vérification d'utilisateur");
    }
    return res.get();
  }

  /**
   * Met à jour le nombre actuel de client dans le salon et le donne
   */
  public int getNbActuel() {
    this.nbActuel = this.sessions.size();
    return nbActuel;
  }

  public List<Session> getSessions() {
    return sessions;
  }

  /**
   * Déconnecte un client du salon
   * @param client Le client à déconecter
   */
  public boolean deco(Socket client) {
    for (Session session : this.sessions) {
      if (session.getSocket() == client) {
        this.sessions.remove(session);
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
      this.sessions.add(this.serveur.getSession(client));
      return true;
    }
    return false;
  }

  public void send(String message, Socket socket) {
    try {
      PrintWriter out = new PrintWriter(socket.getOutputStream());
      out.println(message);
      out.flush();
    } catch (Exception e) {
      System.out.println(
        "Erreur d'envoie de message pour " + socket.toString()
      );
    }
  }
}
