import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  private Socket clientSocket;
  private String nom;
  private ClientEnvoyer envoyer;
  private ClientRecevoir recevoir;
  final Scanner sc = new Scanner(System.in);

  public String getNom() {
    return this.nom;

  }

  public Socket getClientSocket() {
    return clientSocket;
  }

  /**
   * Gère la phase d'envoie et validation du nom
   */
  public void attribuerNom() {
    while (recevoir.getMsg() != "accepte") {
      if (recevoir.getMsg() != null) {
        recevoir.clearMsg();
      }
      System.out.println("Entrez votre nom : ");
      this.nom = sc.nextLine();
      envoyer.envoieNom(nom);
      while (recevoir.getMsg() != null) {
      }
      System.out.println(recevoir.getMsg());
    }
    System.out.println("Vous êtes connecté en tant que " + this.nom);
  }

  public Client(String ip, int port) {
    try {
      clientSocket = new Socket(ip, port);
      this.envoyer = new ClientEnvoyer(this);
      this.recevoir = new ClientRecevoir(clientSocket);
      // demander le nom de la personne et afficher vous êtes connecté en tant que ...
      this.attribuerNom();

      // On lance les threads
      envoyer.start();
      recevoir.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Client client = new Client("localhost", 5000);

  }
}
