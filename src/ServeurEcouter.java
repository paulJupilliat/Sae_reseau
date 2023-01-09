import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServeurEcouter extends Thread {
  private String msg;
  private Serveur serveur;
  final Socket clientSocket;
  final BufferedReader in;
  final PrintWriter out;
  Scanner sc;

  public ServeurEcouter(Socket socket, Serveur serveur) throws IOException {
    this.clientSocket = socket;
    this.serveur = serveur;
    this.out = new PrintWriter(this.clientSocket.getOutputStream());
    this.in = new BufferedReader(
        new InputStreamReader(this.clientSocket.getInputStream()));
  }
  
  public void send(String msg) {
    this.out.println(msg);
    this.out.flush();
  }

  @Override
  public void run() {
    try {
      msg = in.readLine();
      System.out.println(msg);
      //tant que le client est connecté
      while (!msg.equals("quit")) {
        System.out.println("Client : " + msg);
        //envoie a tous les clients
        serveur.sendAll(msg, clientSocket);
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
