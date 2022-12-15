import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Serveur {
  private ServerSocket serveurSocket;
  private final Scanner sc;
  private boolean quit = false;
  private Map<Socket, Tuple> clients;

  public Serveur(int port) {
    try {
      this.serveurSocket = new ServerSocket(port);
    } catch (IOException e1) {
      System.err.println("Erreur lors de la connexion : " + e1.getMessage());
    }
    System.out.println("Serveur démarré");
    this.clients = new HashMap<>();
    this.sc = new Scanner(System.in);
  }

  public void start() {
    try {
      while (this.quit == false) {
        Socket client = this.serveurSocket.accept();
        this.clients.put(
            client,
            new Tuple(new ServeurEcouter(client, this), new ServeurEnvoyer(client)));
        this.clients.get(client).getEnvoyer().start();
        this.clients.get(client).getRecevoir().start();
        System.out.println("Client connecté");
      }
    } catch (IOException e) {
      System.err.println("Erreur lors de la connexion : " + e.getMessage());
    }
  }
  
  public void stop() {
    this.quit = true;
  }

  /**
   * Envoie un message à tous les clients
   * @param msg (String) le message à envoyer
   */
  public void sendToAll(String msg) {
    for (Socket client : this.clients.keySet()) {
      this.clients.get(client).getEnvoyer().send(msg);
    }
  }

  public static void main(String[] test) {
    Serveur serveur = new Serveur(5001);
    serveur.start();
  }
}
