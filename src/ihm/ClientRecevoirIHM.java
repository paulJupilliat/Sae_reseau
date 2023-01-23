/**
 * @file ClientRecevoirIHM.java
 * @brief Classe permettant de recevoir les messages du client
 * @package ihm
 */
package ihm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class ClientRecevoirIHM extends Thread {
  private ClientIHM client;
  private String msg;
  private final Socket clientSocket;
  private final BufferedReader in;
  private List<String> users;

  /**
   * Constructeur de la classe ClientRecevoirIHM
   * @param client {ClientIHM} Le client
   * @throws IOException {IOException} Exception
   */
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
        System.out.println(msg);
        if (msg.matches("serveur : Vous avez rejoint le salon #.*")) {
          this.client.setSalonActuel(
              msg.substring(msg.indexOf("#") + 1, msg.length())
            );
          if (!this.client.getSalonActuel().equals("Config")) {
            this.afficherMessage("\n" + msg);
          }
          this.client.setStep(1);
        } else if (msg.indexOf("| Config | General |") != -1) {
          this.client.getChatApplication().setSalonsTextBrut(msg);
        } else if (msg.matches("Ce nom d'utilisateur est déjà utilisé")) {
          this.client.setStep(-1);
        } else if (msg.matches("Bienvenue .*")) {
          this.client.setStep(2);
        } else if (msg.matches("from .* -> .*")) {
          String envoyeur = this.getEnvoyeurPvr(msg);
          this.addPvrMessage(envoyeur + " : " + this.getMessPvr(msg), envoyeur);
          this.client.getChatApplication().addNotif(this.getEnvoyeurPvr(msg));
          // met le bouton en rouge
          this.client.getChatApplication()
            .getBtnMessPvr()
            .setStyle("-fx-background-color: red");
        } else if (msg.matches("users: .*")) {
          this.users = getUsersMess(msg);
        } else {
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

  /**
   * Donne tous les utilisateurs connectés
   * @param msg2 {String} Le message à traiter
   * @return {List<String>} La liste des utilisateurs
   */
  private List<String> getUsersMess(String msg2) {
    int startIndex = msg2.indexOf("users:") + 7;
    int endIndex = msg2.indexOf("]") - 1;
    String users = msg2.substring(startIndex, endIndex);
    return List.of(users.split(","));
  }

  /**
   * Récupère le message privé
   * @param msg2 {String} Le message à traiter
   * @return {String} Le message privé
   */
  private String getMessPvr(String msg2) {
    int startIndex = msg2.indexOf(" -> ") + 4;
    return msg2.substring(startIndex, msg2.length());
  }

  /**
   * Récupère l'enoyeur du message privé
   * @param msg2 {String} Le message à traiter
   * @return {String} L'envoyeur du message privé
   */
  private String getEnvoyeurPvr(String msg2) {
    int startIndex = msg2.indexOf("from ") + 5;
    int endIndex = msg2.indexOf(" -> ");
    return msg2.substring(startIndex, endIndex);
  }

  private void addPvrMessage(String msg2, String username) {
    this.client.getChatApplication().addPvrMessage(msg2, username);
  }

  public void afficherMessage(String msg) {
    this.afficherMessage(msg, "black");
  }

  /**
   * Affiche le message dans la console et dans l'application
   * @param msg {String} Le message à afficher
   */
  public void afficherMessage(String msg, String color) {
    this.client.getChatApplication().putNewMessage(msg, color);
    System.out.println(msg);
  }

  public List<String> getUsers() {
    return this.users;
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }
}
