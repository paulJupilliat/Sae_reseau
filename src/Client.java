import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Permet de creer un client qui peut envoyer et recevoir des messages
 * 
 */
public class Client {
  private String nom;
  private final Socket clSocket;
  private ClientEnvoyer envoyer;
  private ClientRecevoir recevoir;

  public Client(Socket clSocket) {
    this.clSocket = clSocket;
    // demander le nom d'utilisateur
    Scanner sc = new Scanner(System.in);
    System.out.println("Entrez votre nom d'utilisateur : ");
    this.nom = sc.nextLine();
    sc.close();
  }
  
  /**
   * Creer les threads pour que le client puisse envoyer et recevoir des messages
   */
  public void start() {
    try{
      this.envoyer = new ClientEnvoyer(this.clSocket);
      this.recevoir = new ClientRecevoir(this.clSocket);
      this.envoyer.start();
      this.recevoir.start();
    } catch (IOException e) {
      System.err.println("Erreur lors de la connexion : " + e.getMessage());
    }
  }
}
