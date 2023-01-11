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
   * Récupére le salon de l'utilisateur
   * @return {String} Le nom de L'ancien salon
   */
  public String getSalon() {
    int startIndex = this.msg.indexOf("salon{") + 1;
    int endIndex = this.msg.indexOf("}", startIndex);
    return this.msg.substring(startIndex, endIndex);
  }

  /**
   * Obtient le salon en paramètre de la commande
   * @param commande {String} La commande
   * @return Le nom du salon à rejoindre
   */
  public String getNameSalon(String commande) {
    commande += " ";
    int startIndex = this.msg.indexOf("/")+1;
    for (int ind = 0; ind < commande.length(); ind++) {
      if (this.msg.charAt(startIndex + ind) != commande.charAt(ind)) {
        System.out.println(this.msg.charAt(startIndex + ind));
        System.out.println(commande.charAt(ind));
        return null;
      }
    }
    return msg.substring(startIndex+commande.length(), msg.length());
  }

  @Override
  public void run() {
    try {
      msg = in.readLine();
      System.out.println(msg);
      //tant que le client est connecté
      while (!msg.matches(".* : /quit")) {
        // On recupère le solon du client
        String salonActuel = this.getSalon();
        // si demande la liste de tous les salons
        if (msg.matches(".* : /salons")) {
          // envoi de la liste des salons
          serveur.sendInfo(serveur.getSalonsString(), clientSocket);
        }
        // commande pour rejoindre un salon
        else if (msg.matches(".* : /salon .*")) {
          // récupération du nom du salon voulu qui peut faire au maximum 20 caractères
          String newSalon = this.getNameSalon("salon");
          // envoi du message de changement de salon
          this.serveur.changeSalon(clientSocket, salonActuel, newSalon);
        }
        //  si veux creer un salon
        else if (msg.matches(".* : /createsalon .*")) {
          // récupération du nom du salon
          String salon = this.getNameSalon("createsalon");
          if (salon == null) {
            this.serveur.sendInfo("Commande invalide", clientSocket);
          } else {
            this.serveur.createSalon(salon, this.clientSocket);
            System.out.println("cree");
          }
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
