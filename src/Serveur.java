import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Serveur {

  public static void main(String[] test) {
    final ServerSocket serveurSocket;
    final BufferedReader in;
    final PrintWriter out;
    final Scanner sc = new Scanner(System.in);
    final boolean quit = false;
    final List<Socket> clients = new ArrayList<Socket>();

    try {
      serveurSocket = new ServerSocket(5001);
      System.out.println("Serveur démarré");

      // On cherche à se connécter en permanance a un nouveau client
      while (!quit) {
        clients.add(serveurSocket.accept());
        // Quand un client se connecte on l'ajoute a la liste des clients
        System.out.println("Client connecté");
        ServeurEnvoyer envoi = new ServeurEnvoyer(
          clients.get(clients.size() - 1)
        );
        envoi.start();

        ServeurEcouter recevoir = new ServeurEcouter(
          clients.get(clients.size() - 1)
        );
        recevoir.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
