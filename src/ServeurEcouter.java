import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServeurEcouter extends Thread {
  private String msg;
  private Serveur serveur;
  final Socket clientSocket;
  final BufferedReader in;
  final PrintWriter out;
  Scanner sc;

  public ServeurEcouter(Socket socket, Serveur serveur) throws IOException {
    this.clientSocket = socket;
    this.serveur = serveur;
    this.out = new PrintWriter(this.clientSocket.getOutputStream());
    this.in =
      new BufferedReader(
        new InputStreamReader(this.clientSocket.getInputStream())
      );
  }

  /**
   * Récupére l'ancien salon
   * @return {String} Le nom de L'ancien salon
   */
  public String getOldSalon() {
    // Récupére l'ancien salon
    int startIndex = this.msg.indexOf("{") + 1;
    int endIndex = this.msg.indexOf("}", startIndex);
    String oldSalon = this.msg.substring(startIndex, endIndex);
    return oldSalon;
  }

  @Override
  public void run() {
    try {
      msg = in.readLine();
      System.out.println(msg);
      //tant que le client est connecté
      while (!msg.matches(".* : /quit")) {
        // si demande la liste de tous les salons
        if (msg.matches(".* : /salons")) {
          // envoi de la liste des salons
          serveur.sendInfo(serveur.getSalonsString(), clientSocket);
        }
        // commande pour rejoindre un salon
        else if (msg.matches(".* : /salon .*")) {
          // récupération de l'ancien salon
          String oldSalon = getOldSalon();
          // récupération du nom du salon
          String newSalon = msg.substring(msg.indexOf("/salon ") + 7);
          // envoi du message de changement de salon
          this.serveur.changeSalon(clientSocket, oldSalon, newSalon);
        }
        //  si veux creer un salon
        else if (msg.matches(".* : /createsalon *")) {
          // récupération du nom du salon
          String salon = msg.substring(msg.indexOf("/createsalon ") + 13);
          // enlever cette partie du message
          msg = msg.substring(0, msg.indexOf("/createsalon "));
        }
        // si le message n'est pas une commande
        else {
          // récupération des infos du salon
          String salon = msg.substring(msg.indexOf("{") + 1, msg.indexOf("}"));
          // enlever cette partie du message
          msg = msg.substring(msg.indexOf("}") + 1);
          System.out.println(msg);
          //envoie a tous les clients
          serveur.sendAll(msg, clientSocket);
        }
        msg = in.readLine();
      }
      //sortir de la boucle si le client a déconecté
      System.out.println("Client déconecté");
      //fermer le flux et la session socket
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
