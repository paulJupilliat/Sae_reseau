import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * Permet d'envoyer les messages du serveur
 */
public class ClientEnvoyer extends Thread {
  private String msg;
  private final Scanner sc;
  private final PrintWriter out;
  private Client client;

  public ClientEnvoyer(Client client) throws IOException {
    sc = new Scanner(System.in);
    this.client = client;
    out = new PrintWriter(client.getClientSocket().getOutputStream());
    new BufferedReader(new InputStreamReader(client.getClientSocket().getInputStream()));
  }

  /**
   * Permet d'envoyer un message au serveur
   * @param msg le message à envoyer
   */
  public void envoieMsg(String msg) {
    msg = sc.nextLine();
    out.println(this.client.getNom() + " : " + msg);
    out.flush();
  }

  public void envoieNom(String nom){
    out.println("#nom : " + nom);
    out.flush();
  }

  @Override
  public void run() {
    while (true) {
      this.envoieMsg(msg);
    }
  }
}
