import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

public class Client {
  private Socket clientSocket;
  private String nom;
    
  public Client(String ip, int port) {
     try {
      clientSocket = new Socket(ip, port);
      ClientEnvoyer envoyer = new ClientEnvoyer(this);
      ClientRecevoir recevoir = new ClientRecevoir(clientSocket);
      try (// demander le nom de la personne et afficher vous êtes connecté en tant que ...
      Scanner sc = new Scanner(System.in)) {
        System.out.println("Entrez votre nom : ");
        String nomEntre = sc.nextLine();
        this.nom = nomEntre;
      }
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

  
  public static void main(String[] args) {
    Client client = new Client("localhost", 5000);
   
  }
}
