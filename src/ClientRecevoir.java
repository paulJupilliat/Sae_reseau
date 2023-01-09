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

  public ClientRecevoir(Socket clientSocket) throws IOException {
    this.clientSocket = clientSocket;
    out = new PrintWriter(clientSocket.getOutputStream());
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  public String getMsg() {
    return msg;
  }

  public void clearMsg() {
    this.msg = null;
  }

  @Override
  public void run() {
    try {
      msg = in.readLine();
      while (msg != null) {
        System.out.println(msg);
        msg = in.readLine();
      }
      System.out.println("Serveur déconecté");
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
