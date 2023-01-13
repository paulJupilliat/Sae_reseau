package ihm;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import launch.ChatApplication;
import terminal.client.Client;

public class ClientIHM {
  private ChatApplication chatApplication;
  private Socket clientSocket;
  private String nom;
  private String salonActuel;
  private ClientEnvoyerIHM envoyer;
  private ClientRecevoirIHM recevoir;
    
  /**
   * Constructeur pour la version graphique
   * @param ip l'addresse ip du serveur
   * @param port le port du serveur
   * @param nom Le nom de l'utilisateur
   */
  public ClientIHM(String ip, int port, String nom, ChatApplication chatApplication) {
    try {
      this.chatApplication = chatApplication;
      this.clientSocket = new Socket(ip, port);
      this.envoyer = new ClientEnvoyerIHM(this);
      this.recevoir = new ClientRecevoirIHM(this);
      this.nom = nom;
      // On lance les threads
      envoyer.start();
      recevoir.start();
      this.envoyer.send("/salon Config");
    } catch (IOException e) {
      e.printStackTrace();
    }
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
      try{
          this.clientSocket.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  
  public static void main(String[] args) {
    Client client = new Client("localhost", 5001);
   
  }
}
