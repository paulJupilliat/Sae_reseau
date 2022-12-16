import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServeurEcouter extends Thread {
  private final Serveur serveur;
  private String msg;
  private final Socket clientSocket;
  private final BufferedReader in;
  private final PrintWriter out;
  Scanner sc;

  public ServeurEcouter(Socket socket, Serveur serveur)
    throws IOException {
    this.clientSocket = socket;
    this.serveur = serveur;
    this.out = new PrintWriter(this.clientSocket.getOutputStream());
    this.in =
      new BufferedReader(
        new InputStreamReader(this.clientSocket.getInputStream())
      );
  }

  @Override
  public void run() {
    try {
      msg = in.readLine();
      //tant que le client est connecté
      while (!msg.equals("/quit")) {
        System.out.println("Client : " + msg);
        //envoyer un message au client
        serveur.sendToAll(msg);
        out.flush();
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
