/**
 * @file ClientEnvoyer.java
 * @brief Classe permettant d'envoyer les messages du client
 * @package terminal.client
 */
package terminal.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientEnvoyer extends Thread {
  private String msg;
  private final Scanner sc;
  private final PrintWriter out;
  private Client client;

  /**
   * Constructeur de la classe ClientEnvoyer
   * @param client {Client} Le client
   * @throws IOException {IOException} Exception
   */
  public ClientEnvoyer(Client client) throws IOException {
    this.client = client;
    sc = new Scanner(System.in);
    out = new PrintWriter(client.getClientSocket().getOutputStream());
    new BufferedReader(
      new InputStreamReader(client.getClientSocket().getInputStream())
    );
  }

  /**
   * Envoie un message au serveur avec le bon formatage
   * @param msg {String} Le message à envoyer
   */
  public void send(String msg) {
    out.println(
      "salon{" +
      this.client.getSalonActuel() +
      "} " +
      this.client.getNom() +
      " : " +
      msg
    );
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
