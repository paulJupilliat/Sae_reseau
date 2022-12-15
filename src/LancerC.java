import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Contient le main pour lancer le chat du coté client
 * 
 */
public class LancerC {

  public static void main(String[] args) {
    Client client;
    try {
      client = new Client(new Socket("localhost", 5001));
      client.start();
    } catch (UnknownHostException e) {
      System.err.println("Erreur lors de la connexion : " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Erreur lors de la connexion : " + e.getMessage());
    }
  }
}
