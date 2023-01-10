import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServeurEnvoie extends Thread {
  private String message;
  private List<Session> clients;
  private Socket envoyeur;

  public ServeurEnvoie(List<Session> clients, String message, Socket envoyeur) {
    this.clients = clients;
    this.message = message;
    this.envoyeur = envoyeur;
  }

  @Override
  public void run() {
    if (message != null) {
      // Envoi du message à tous les sockets sauf à celui qui l'a envoyé
      for (Session sessionCl : clients) {
          Socket client = sessionCl.getSocket();
          if (client != envoyeur) {
              try {
                  PrintWriter out = new PrintWriter(client.getOutputStream());
                  out.println(message);
                  out.flush();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
    }
  }
}
