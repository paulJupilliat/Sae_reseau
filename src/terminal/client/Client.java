/**
 * @file Client.java
 * @brief Classe représentant un client
 * @package terminal.client
 */
package terminal.client;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  private Socket clientSocket;
  private String nom;
  private String salonActuel;
  private ClientEnvoyer envoyer;
  private ClientRecevoir recevoir;
  
  /**
   * Constructeur de la classe Client
   * @param ip {String} L'adresse IP du serveur
   * @param port {int} Le port du serveur
   */
  public Client(String ip, int port) {
    try {
      clientSocket = new Socket(ip, port);
      this.envoyer = new ClientEnvoyer(this);
      this.recevoir = new ClientRecevoir(this);
      // demander le nom de la personne et afficher vous êtes connecté en tant que ...
      Scanner sc = new Scanner(System.in);
      System.out.println("Entrez votre nom : ");
      String nomEntre = sc.nextLine();
      this.nom = nomEntre;
      System.out.println("Vous êtes connecté en tant que " + this.nom);
      // On lance les threads
      envoyer.start();
      recevoir.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
  
  /**
   * Envoie un message au serveur
   * @param message {String} Le message à envoyer
   */
  public void sendMessage(String message) {
    this.envoyer.send(message);
  }

  public ClientRecevoir getRecevoir() {
      return recevoir;
  }

  
  public static void main(String[] args) {
    Client client = new Client("localhost", 5001);
   
  }
}
