import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServeurEnvoie extends Thread {
  private List<Session> clients;
  private String message;
  private Socket envoyeur;

  /**
   * Constructeur de la classe ServeurEnvoie si il veut envoyer le message à tous le monde
   *
   * @param clients {List<Session>} La liste des clients connectés
   * @param message {String} Le message à envoyer
   * @param envoyeur {Socket} Le socket de l'envoyeur
   */
  public ServeurEnvoie(List<Session> clients, String message, Socket envoyeur) {
    this.clients = clients;
    this.message = message;
    this.envoyeur = envoyeur;
  }

  @Override
  public void run() {
    if (message != null) {
      // Envoi du message à tous les sockets
      for (Session sessionCl : clients) {
        if (sessionCl.getSocket() != envoyeur) {
          Socket client = sessionCl.getSocket();
          try {
            PrintWriter out = new PrintWriter(client.getOutputStream());
            out.println(message);
            out.flush();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      // Réinitialisation de la variable de message
      message = null;
    }
  }
}
