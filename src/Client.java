import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
  private Socket clientSocket;
  private String nom;
  protected List<Salon> salonPossible; // la liste des salons qui lui son proposé
  private Salon salon = null; //le salon dans lequel le client discute, se trouve, part default il est a null
    
  public Client(String ip, int port, Salon salon) {
     try {
      clientSocket = new Socket(ip, port);
      this.salon = salon;
      this.salonPossible = new ArrayList<>();
      ClientEnvoyer envoyer = new ClientEnvoyer(this);
      ClientRecevoir recevoir = new ClientRecevoir(clientSocket);
      // demander le nom de la personne et afficher vous êtes connecté en tant que ...
      Scanner sc = new Scanner(System.in);
      System.out.println("Entrez votre nom : ");
      String nomEntre = sc.nextLine();
      this.nom = nomEntre;
      System.out.println("Vous êtes connecté en tant que " + this.nom);
      System.out.println("Vous êtes dans le salon " + this.salon.getNom());

      
      // On lance les threads
      Thread env = new Thread(envoyer);
      Thread rec = new Thread(recevoir);
      rec.start();
      env.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public String getNom() {
      return nom;
  }
  public Salon getSalon() {
      return salon;
  }

  public Socket getClientSocket() {
      return clientSocket;
  }

  
  public static void main(String[] args) {
    Client client = new Client("localhost", 5000,null);
   
  }
}
