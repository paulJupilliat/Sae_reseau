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

  public ClientEnvoyer(Socket clientSocket) throws IOException {
    sc = new Scanner(System.in);
    out = new PrintWriter(clientSocket.getOutputStream());
    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }
  
  public void send(String msg) {
    this.out.println(msg);
    this.out.flush();
  }

  @Override
  public void run() {
    while (true) {
      msg = sc.nextLine();
      send(msg);
    }
  }
}
