import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serveur {
  private ServerSocket serveurSocket;
  private boolean quit = false;
  private final List<Session> clients;
  private List<Salon> salons;

  public Serveur(int port) {
    this.clients = new ArrayList<>();
    Salon general = new Salon(
      "General",
      "Salon général",
      100,
      0,
      this
    );
    this.salons = new ArrayList<>(Arrays.asList(general));
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
      "info"
    );
    serveurEnvoie.start();
  }

  public void launch() {
    while (!quit) {
      try {
        clients.add(new Session(serveurSocket.accept(), this));
        System.out.println("Client connecté");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public List<Session> getClients() {
    return this.clients;
  }

  public List<Salon> getSalons() {
    return salons;
  }

  public void createSalon(String nom, Socket client) {
    Salon newSalon = new Salon(
      nom,
      nom,
      10,
      0,
      this
    );
    // Si le nom n'est pas déjà pris
    if (this.getSalon(newSalon.getNom()) == null) {
      this.salons.add(newSalon);
      this.changeSalon(client, null, nom);
      this.sendInfo("Salon créé", client);
    } else {
      this.sendInfo("Le nom du salon est déjà pris", client);
    }
  }

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

  public void changeSalon(Socket client, String oldSalon, String newSalon) {
    ServeurGestSalon ajouteur = new ServeurGestSalon(
      newSalon,
      oldSalon,
      client,
      this
    );
    ajouteur.start();
  }

  public Session getSession(Socket client) {
    for (Session session : this.clients) {
      if (session.getSocket() == client) {
        return session;
      }
    }
    return null;
  }

  public static void main(String[] test) {
    Serveur serveur = new Serveur(5001);
    serveur.launch();
  }
}
