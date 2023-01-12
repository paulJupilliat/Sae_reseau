package ihm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * Permet d'envoyer les messages du serveur
 */
public class ClientEnvoyerIHM extends Thread {
  private String msg;
  private final Scanner sc;
  private final PrintWriter out;
  private ClientIHM client;

  public ClientEnvoyerIHM(ClientIHM client) throws IOException {
    this.client = client;
    sc = new Scanner(System.in);
    out = new PrintWriter(client.getClientSocket().getOutputStream());
    new BufferedReader(new InputStreamReader(client.getClientSocket().getInputStream()));
  }
  
  public void send(String msg) {
      out.println("salon{" + this.client.getSalonActuel() + "} " + this.client.getNom() + " : " + msg);
      out.flush();
  }

  @Override
  public void run() {
    while (true) {
      msg = sc.nextLine();
      this.send(msg);
    }
  }
}
