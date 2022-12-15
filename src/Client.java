import java.io.IOException;
import java.net.Socket;

public class Client {

  public static void main(String[] args) {
    final Socket clientSocket;


    try {
      clientSocket = new Socket("192.168.13.197", 5001);
      ClientEnvoyer envoyer = new ClientEnvoyer(clientSocket);
      ClientRecevoir recevoir = new ClientRecevoir(clientSocket);
      envoyer.start();
      recevoir.start();

    } catch (IOException e) {
      System.err.println("Erreur lors de la connexion : " + e.getMessage());
    }
  }
}
