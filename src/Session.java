import java.net.Socket;

public class Session {
  private Socket socket;
  private Serveur serveur;
  ServeurEcouter serveurEcouter;

  public Socket getSocket() {
      return socket;
  }
  
  public Session(Socket socket, Serveur serveur) {
    try {
      this.socket = socket;
      this.serveur = serveur;
      this.serveurEcouter = new ServeurEcouter(socket, serveur);
      this.serveurEcouter.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void send(String msg) {
    this.serveurEcouter.send(msg);
  }
}
