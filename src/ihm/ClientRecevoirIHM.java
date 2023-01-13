package ihm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientRecevoirIHM extends Thread {
  private ClientIHM client;
  private String msg;
  private final Socket clientSocket;
  private final BufferedReader in;

  public ClientRecevoirIHM(ClientIHM client) throws IOException {
    this.clientSocket = client.getClientSocket();
    this.client = client;
    in =
      new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  @Override
  public void run() {
    try {
      msg = in.readLine();
      while (msg != null) {
        if (msg.matches("serveur : Vous avez rejoint le salon #.*")) {
          this.client.setSalonActuel(
              msg.substring(msg.indexOf("#") + 1, msg.length())
            );
          this.afficherMessage("\n" + msg);
        } else if (msg.indexOf("| Config | General |") != -1) {
          this.client.getChatApplication().setSalonsTextBrut(msg);
        } 
        else {
          this.afficherMessage(msg);
        }
        msg = in.readLine();
      }
      System.out.println("Serveur déconecté");
      clientSocket.close();
      System.exit(1);
    } catch (IOException e) {
      System.out.println("Serveur déconecté");

      System.exit(1);
    }
  }

  public void afficherMessage(String msg) {
    this.client.getChatApplication().putNewMessage(msg);
    System.out.println(msg);
  }
}
