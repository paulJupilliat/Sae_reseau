import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Permet de creer un client qui peut envoyer et recevoir des messages
 * 
 */
public class Client {
  private Socket clientSocket;
  private String nom;
  private String salonActuel;
    
  public Client(String ip, int port) {
     try {
      clientSocket = new Socket(ip, port);
      ClientEnvoyer envoyer = new ClientEnvoyer(this);
      ClientRecevoir recevoir = new ClientRecevoir(this);
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

  
  public static void main(String[] args) {
    Client client = new Client("localhost", 5001);
   
  }
}
