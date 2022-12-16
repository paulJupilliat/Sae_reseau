import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serveur {
  private ServerSocket serveurSocket;
  private boolean quit = false;
  private final List<Session> clients;

  public Serveur(int port) {
    this.clients = new ArrayList<>();
    try {
      serveurSocket = new ServerSocket(port);
      System.out.println("Serveur démarré");
    } catch (IOException e) {
      System.out.println("Erreur lors de la création du serveur");
    }
    
  }

  public void sendAll(String message) {
    for (Session client : clients) {
      client.send(message);
    }
  }

  public void launch() {
    while (!quit) {
      try {
        clients.add(new Session(serveurSocket.accept(), this));
        System.out.println("Client connecté");
        
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public List<Session> getClients() {
    return this.clients;
  }

  public static void main(String[] test) {
    Serveur serveur = new Serveur(5000);
    serveur.launch();
  }
}
