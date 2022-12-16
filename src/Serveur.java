import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Serveur {
  private ServerSocket serveurSocket;
  private final Scanner sc;
  private boolean quit = false;
  private List<Session> clients;

  public Serveur(int port) {
    try {
      this.serveurSocket = new ServerSocket(port);
    } catch (IOException e1) {
      System.err.println("Erreur lors de la connexion : " + e1.getMessage());
    }
    System.out.println("Serveur démarré sur le port: " + port);
    this.clients = new ArrayList<Session>();
    this.sc = new Scanner(System.in);
  }

  public void start() {
    try {
      while (this.quit == false) {
        Socket client = this.serveurSocket.accept();
        String nom = this.sc.nextLine();
        this.clients.add(new Session(new ServeurEcouter(client, this), new ServeurEnvoyer(client, this), nom, client));
        this.clients.get().getEnvoyer().start();
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
}
