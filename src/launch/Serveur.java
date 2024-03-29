/**
 * @file Serveur.java
 * @brief Classe représentant le serveur
 * @package launch
 */
package launch;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import terminal.serveur.Salon;
import terminal.serveur.ServeurEnvoie;
import terminal.serveur.ServeurGestSalon;
import terminal.serveur.Session;

public class Serveur {
  private ServerSocket serveurSocket;
  private boolean quit = false;
  private final List<Session> clients;
  private List<Salon> salons;

  /**
   * Constructeur de la classe Serveur
   * @param port {int} Le port sur lequel le serveur va écouter
   */
  public Serveur(int port) {
    this.clients = new ArrayList<>();
    Salon general = new Salon("General", "Salon général", 100, 0, this);
    Salon config = new Salon("Config", "Salon de configuration", 100, 0, this);
    Salon chanel1 = new Salon("Chanel1", "Salon Chanel 1", 100, 0, this);
    Salon chanel2 = new Salon("Chanel2", "Salon Chanel 2", 100, 0, this);
    this.salons = new ArrayList<>(Arrays.asList(config, general, chanel1, chanel2));
    try {
      serveurSocket = new ServerSocket(port);
      System.out.println("Serveur démarré");
    } catch (IOException e) {
      System.out.println("Erreur lors de la création du serveur");
    }
  }

  /**
   * Envoie un message à toutes les sockets de ce serveur sauf à l'envoyeur
   *
   * @param message {String} Le message à envoyer
   * @param envoyeur {Socket} Le socket de l'envoyeur
   */
  public void sendAll(String message, Socket envoyeur, String salon) {
    ServeurEnvoie serveurEnvoie = new ServeurEnvoie(
      this,
      message,
      envoyeur,
      salon,
      "all"
    );
    serveurEnvoie.start();
  }

  /**
   * Envoie un message à un socket de ce serveur
   *
   * @param message {String} Le message à envoyer
   * @param envoyeur {Socket} Le socket de l'envoyeur
   */
  public void sendInfo(String msg, Socket socket) {
    ServeurEnvoie serveurEnvoie = new ServeurEnvoie(
        this,
        msg,
        socket,
        null,
        "info");
    serveurEnvoie.start();
  }

  /**
   * Démare le serveur
   */
  public void launch() {
    while (!quit) {
      try {
        clients.add(new Session(serveurSocket.accept(), this));
        System.out.println("Client connecté");
      } catch (IOException e) {
        System.out.println("Erreur lors de la connexion d'un client");
      }
    }
  }

  public List<Session> getClients() {
    return this.clients;
  }

  /**
   * Récupère le nom de tous les clients connectés
   * @return {String} Le nom de tous les clients connectés
   */
  public String getAllUsername() {
    AtomicReference<String> res = new AtomicReference<>("");
    Thread thread = new Thread(
      () -> {
          for (Session client : this.clients) {
            res.set(res + client.getNom() + ",");
          }
        res.set(res + "]");
      }
    );
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return res.get();
  }

  public List<Salon> getSalons() {
    return salons;
  }

  public void createSalon(String nom, Socket client) {
    ServeurGestSalon creation = new ServeurGestSalon(
      nom,
      nom,
      client,
      this,
      "create"
    );
    creation.start();
  }

  public void deleteSalon(String nom, Socket client) {
    ServeurGestSalon suppression = new ServeurGestSalon(
      nom,
      nom,
      client,
      this,
      "delete"
    );
    suppression.start();
  }

  /**
   * Récupère la liste des salons sous forme de string
   * @return La liste des salons
   */
  public String getSalonsString() {
    String res = "";
    for (Salon salon : this.salons) {
      res += "| " + salon.getNom() + " ";
    }
    return res + "|";
  }

  /**
   * Récupère un *salon à partir d'un nom
   * @param salon Le nom du salon recherché
   * @return Le salon recherché si il existe
   */
  public Salon getSalon(String salon) {
    for (Salon salonRes : this.salons) {
      if (salonRes.getNom().equals(salon)) {
        return salonRes;
      }
    }
    return null;
  }

  /**
   * Lance un thread qui gère le changement de salon
   * @param client Le client qui change de salon
   * @param oldSalon Le salon d'origine
   * @param newSalon Le salon de destination
   */
  public void changeSalon(Socket client, String oldSalon, String newSalon) {
    ServeurGestSalon ajouteur = new ServeurGestSalon(
      newSalon,
      oldSalon,
      client,
      this,
      "change"
    );
    ajouteur.start();
  }

  /**
   * Récupère une session à partir d'un socket
   * @param client Le socket du client
   * @return La session du client
   */
  public Session getSession(Socket client) {
    AtomicReference<Session> res = new AtomicReference<>();
    Thread thread = new Thread(
      () -> {
        for (Session session : this.clients) {
          if (session.getSocket().equals(client)) {
            res.set(session);
          }
        }
      }
    );
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return res.get();
  }

  public static void main(String[] test) {
    Serveur serveur = new Serveur(5001);
    serveur.launch();
  }

  /**
   * Lance un thread qui vérifie si un nom d'utilisateur est déjà utilisé
   * @param username Le nom d'utilisateur à vérifier
   * @return Vrai si le nom d'utilisateur est déjà utilisé
   */
  public boolean isUsernameUsed(String username) {
    ServeurGestSalon verif = new ServeurGestSalon(
        username,
        null,
        null,
        this,
        "verif");
    verif.start();
    try {
      verif.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return verif.isUsed();
  }

  /**
   * Lance un thread qui gère la déconnexion d'un client
   * @param client Le socket du client
   * @param salon Le salon du client
   */
  public void deco(Socket client, String salon) {
    ServeurGestSalon deco = new ServeurGestSalon(
      null,
      salon,
      client,
      this,
      "deco"
    );
    deco.start();
  }

  /**
   * Lance un thread qui envoie un message à un client
   * @param clientSocket Le socket du client
   * @param msg Le message à envoyer
   * @param destinataire Le nom d'utilisateur du destinataire
   */
  public void sendTo(Socket clientSocket, String msg, String destinataire) {
    ServeurEnvoie serveurEnvoie = new ServeurEnvoie(
      this,
      msg,
      "to",
      clientSocket,
      destinataire
    );
    serveurEnvoie.start();
  }

  /**
   * Récupère une session à partir d'un nom d'utilisateur
   * @param destinataie Le nom d'utilisateur
   * @return La session du client
   */
  public Session getSessionString(String destinataie) {
    ServeurGestSalon find = new ServeurGestSalon(
      destinataie,
      null,
      null,
      this,
      "find"
    );
    find.start();
    try {
      find.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return find.getSession();
  }

  public String getNbUser() {
    return String.valueOf(this.clients.size());
  }

}
