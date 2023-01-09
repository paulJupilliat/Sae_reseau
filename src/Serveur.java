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

 /**
   * Envoie un message à toutes les sockets de ce serveur sauf à l'envoyeur
   * 
   * @param message {String} Le message à envoyer
   * @param envoyeur {Socket} Le socket de l'envoyeur
   */ 
  public void sendAll(String message, Socket envoyeur) {
    for (Session client : clients) {
      if (!client.getSocket().equals(envoyeur)) {
        client.send(message);
      }
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
