import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientRecevoir extends Thread {
  private String msg;
  private final Socket clientSocket;
  private final PrintWriter out;
  private final BufferedReader in;
  private Client client;

  public ClientRecevoir(Socket clientSocket, Client client) throws IOException {
    this.clientSocket = clientSocket;
    this.client = client;
    out = new PrintWriter(clientSocket.getOutputStream());
    in =
      new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  @Override
  public void run() {
    try {
      msg = in.readLine();
      while (msg != null) {
        // Si on demande le pseudo
        if (msg.equals("Entrez votre pseudo : ")) {
          // On demande le pseudo
          Scanner sc = new Scanner(System.in);
          String pseudo = sc.nextLine();
          // On envoie le pseudo
          this.out.println("pseudo " + pseudo);
          this.out.flush();
          // On enregistre le pseudo
          this.client.setNom(pseudo);
          System.out.println("Vous êtes connecté en tant que " + pseudo);
          sc.close();
          msg = in.readLine();
        } else {
          System.out.println("Client1 : " + msg);
          msg = in.readLine();
        }
        System.out.println("Serveur déconecté");
        out.close();
        clientSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
