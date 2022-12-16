import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Serveur {
  private ServerSocket socketServeur;
  private List<Session> clients;
  private boolean quit;

  public Serveur(int port) throws IOException {
    this.socketServeur = new ServerSocket(port);
    this.clients = new ArrayList<>();
    this.quit = false;
  }

  public void start() {
    System.out.println("Serveur démarré sur le port " + this.socketServeur.getLocalPort());
    while (!this.quit) {
      try {
        Socket socketClient = this.socketServeur.accept();
        this.clients.add(new Session(socketClient, this));
        this.clients.get(this.clients.size() - 1).start();
      } catch (IOException e) {
        e.printStackTrace();
      }
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
    for (Session client : this.clients) {
      client.getRecevoir().send(msg);
    }
  }

  public ServerSocket getSocketServeur() {
      return this.socketServeur;
  }
}
