import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServeurEcouter extends Thread {
  private String msg;
  private Serveur serveur;
  private final Socket clientSocket;
  private final BufferedReader in;
  private final PrintWriter out;

  public ServeurEcouter(Socket socket, Serveur serveur) throws IOException {
    this.clientSocket = socket;
    this.serveur = serveur;
    this.out = new PrintWriter(this.clientSocket.getOutputStream());
    this.in =
      new BufferedReader(
        new InputStreamReader(this.clientSocket.getInputStream())
        );
    this.demandePseudo();
  }

  public void send(String msg) {
    this.out.println(msg);
    this.out.flush();
  }

  public void demandePseudo() {
    this.send("Entrez votre pseudo : ");
  }

  @Override
  public void run() {
    try {
      msg = in.readLine();
      System.out.println(msg);
      //tant que le client est connecté
      while (!msg.equals("quit")) {
        if (msg.contains("pseudo")) {
          String pseudo = msg.split(" ")[1];
          this.send("Vous êtes connecté en tant que " + pseudo);
        } else {
          System.out.println("Client : " + msg);
          //envoie a tous les clients
          serveur.sendAll(msg);
        }
        msg = in.readLine();
      }

      //sortir de la boucle si le client a déconecté
      System.out.println("Client déconecté");
      //fermer le flux et la session socket
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
