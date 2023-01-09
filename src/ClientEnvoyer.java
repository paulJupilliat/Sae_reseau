import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * Permet d'envoyer les messages du serveur
 */
public class ClientEnvoyer extends Client implements Runnable {
  private String msg;
  private final Scanner sc;
  private final PrintWriter out;
  private Client client;

  public ClientEnvoyer(Client client ) throws IOException {
    this.client = client;
    sc = new Scanner(System.in);
    out = new PrintWriter(client.getClientSocket().getOutputStream());
    new BufferedReader(new InputStreamReader(client.getClientSocket().getInputStream()));
  }

  @Override
  public void run() {
    while (true) {
      msg = sc.nextLine();
      out.println(this.client.getNom() + " : " + msg);
      out.flush();
    }
  }
}
