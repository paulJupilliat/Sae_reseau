    import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServeurEcoute extends Thread {

  private final Serveur serveur;
  private String msg;
  private Session session;
  private final BufferedReader in;
  private final PrintWriter out;
  Scanner sc;

  public ServeurEcoute(Session session, Serveur serveur)
    throws IOException {
    this.session = session;
    this.serveur = serveur;
    this.out = new PrintWriter(this.session.getClSocket().getOutputStream());
    this.in =
      new BufferedReader(
        new InputStreamReader(this.session.getClSocket().getInputStream())
      );
  }

  @Override
  public void run() {
      try {
          msg = in.readLine();
          //tant que le client est connecté
          while (!msg.equals("/quit")) {
              System.out.println(this.session.getNom() + " : " + msg);
              //envoyer un message au client
              serveur.sendToAll(msg);
              out.flush();
              msg = in.readLine();
          }
          //sortir de la boucle si le client a déconecté
          System.out.println( this.session.getNom() + " déconecté");
          //fermer le flux et la session socket
          out.close();
          this.session.getClSocket().close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  
    public void send(String msg) {
        this.out.println(msg);
        this.out.flush();
    }
}
