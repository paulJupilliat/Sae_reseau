import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.BreakIterator;
import java.util.Scanner;

public class ClientRecevoir extends Thread {
  private Client client;
  private String msg;
  private final Socket clientSocket;
  private final PrintWriter out;
  private final BufferedReader in;

  public ClientRecevoir(Client client) throws IOException {
    this.clientSocket = client.getClientSocket();
    this.client = client;
    new Scanner(System.in);
    out = new PrintWriter(clientSocket.getOutputStream());
    in =
      new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  @Override
  public void run() {
    try {
      msg = in.readLine();
      while (msg != null) {
        if (msg.matches("serveur : Vous avez rejoint le salon #.*")) {
          this.client.setSalonActuel(msg.substring(msg.indexOf("#")+1, msg.length()));
        }
        System.out.println(msg);
        msg = in.readLine();
      }
      System.out.println("Serveur déconecté");
      out.close();
      clientSocket.close();
      System.exit(1);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
