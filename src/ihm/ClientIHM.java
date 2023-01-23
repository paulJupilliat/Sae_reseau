/**
 * @file ClientIHM.java
 * @brief Classe représantant le client en mode IHM
 * @package ihm
 */
package ihm;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import launch.ChatApplication;
import terminal.serveur.ExceptionSalon;

public class ClientIHM {
  private ChatApplication chatApplication;
  private Socket clientSocket;
  private String nom;
  private String salonActuel;
  private ClientEnvoyerIHM envoyer;
  private ClientRecevoirIHM recevoir;
  private int step;

  /**
   * Constructeur pour la version graphique
   * @param ip l'addresse ip du serveur
   * @param port le port du serveur
   * @param nom Le nom de l'utilisateur
   * @throws ExceptionSalon
   */
  public ClientIHM(
    String ip,
    int port,
    String nom,
    ChatApplication chatApplication
  )
    throws ExceptionSalon {
    try {
      this.chatApplication = chatApplication;
      this.clientSocket = new Socket(ip, port);
      this.envoyer = new ClientEnvoyerIHM(this);
      this.recevoir = new ClientRecevoirIHM(this);
      // On lance les threads
      envoyer.start();
      recevoir.start();
      this.step = 0;
      this.envoyer.send("/salon Config");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      while (this.salonActuel == null) {}
      this.envoyer.send("/username " + nom);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      while (this.step < 2) {
        int i = 0;
        if (this.step < 0) {
          throw new ExceptionSalon("Nom déjà utilisé");
        }
      }
      this.nom = nom;
      System.out.println("Nom : " + this.nom);
      this.envoyer.send("/salon General");
    } catch (IOException e) {
      this.chatApplication.askIp();
    }
  }

  public void setStep(int step) {
    this.step = step;
  }

  public ChatApplication getChatApplication() {
    return chatApplication;
  }

  public String getNom() {
    return nom;
  }

  public Socket getClientSocket() {
    return clientSocket;
  }

  public String getSalonActuel() {
    return salonActuel;
  }

  public void setSalonActuel(String salonActuel) {
    this.salonActuel = salonActuel;
  }

  // sendMessage
  public void sendMessage(String message) {
    this.envoyer.send(message);
  }

  public ClientRecevoirIHM getRecevoir() {
    return recevoir;
  }

  /**
   * Arrête le client
   */
  public void stop() {
    this.envoyer.send("/quit");
    this.envoyer.interrupt();
    this.recevoir.interrupt();
    try {
      this.clientSocket.close();
    } catch (IOException e) {
      System.out.println("Fermeture brut du client");
    }
  }

public List<String> getAllUsers() {
  this.envoyer.send("/users");
  while (this.recevoir.getUsers() == null) {
    System.out.println("Waiting for users");
    try {
      this.chatApplication.showChargement();
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  List<String> users = this.recevoir.getUsers();
  this.recevoir.setUsers(null);
  return users;
}
}
