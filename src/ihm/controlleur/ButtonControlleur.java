/**
 * @file ButtonControlleur.java
 * @brief Classe permettant de gérer les boutons
 * @package ihm.controlleur
 */
package ihm.controlleur;

import ihm.ClientIHM;
import ihm.modele.ModeleApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launch.ChatApplication;
import terminal.serveur.ExceptionSalon;

public class ButtonControlleur implements EventHandler<ActionEvent> {
  private ChatApplication chatApplication;
  private String action;
  private ClientIHM client;
  private ModeleApp modele;
  private String data;

  /**
   * Constructeur de la classe ButtonControlleur
   * @param chatApplication L'application ihm
   * @param action L'action à effectuer
   * @param client Le client
   */
  public ButtonControlleur(
    ChatApplication chatApplication,
    String action,
    ClientIHM client
  ) {
    this.chatApplication = chatApplication;
    this.action = action;
    this.client = client;
    this.modele = new ModeleApp(chatApplication, this);
  }

  /**
   * Constructeur de la classe ButtonControlleur avec data
   * @param chatApplication L'application ihm
   * @param action L'action à effectuer
   * @param client Le client
   * @param data Les données
   */
  public ButtonControlleur(
    ChatApplication chatApplication,
    String action,
    ClientIHM client,
    String data
  ) {
    this.chatApplication = chatApplication;
    this.action = action;
    this.client = client;
    this.modele = new ModeleApp(chatApplication, this);
    this.data = data;
  }

  @Override
  public void handle(ActionEvent event) {
    if (action.equals("Envoyer")) {
      String message = chatApplication.getTextField().getText();
      chatApplication.putNewMessage(message, "black");
      chatApplication.getTextField().clear();
      client.sendMessage(message);
    }
    else if (action.equals("EnvoyerPvr")) {
      String message = chatApplication.getTextField().getText();
      chatApplication.putNewMessage(message, "black");
      chatApplication.getTextField().clear();
      String destinaire = chatApplication.getDestinatairePvr().getText();
      client.sendMessage("@" + destinaire + " " + message);
      this.chatApplication.addPvrMessage(message, destinaire);
    }
    else if (action.equals("Close")) {
      this.client.sendMessage("/quit");
    }
    else if (action.equals("AllSalon")) {
      this.chatApplication.showChargement();
      this.chatApplication.setListSalons(this.modele.getAllSalon());
      this.chatApplication.showAllSalon(this.modele.getAllSalon());
    } else if (action.equals("Salon")) {
      this.client.sendMessage("/salon " + this.data);
      this.chatApplication.showChatMode();
    } else if (action.equals("NewSalonAsk")) {
      this.chatApplication.showChatMode();
      this.chatApplication.popUpAskSalon();
    } else if (action.equals("NewSalon")) {
      this.client.sendMessage("/createsalon " + this.data);
      
    } else if (action.equals("Username")) {
      this.client.sendMessage("/username " + this.data);
    }
    else if (action.equals("addMessPvr")) {
      this.chatApplication.showMessPvrWith(this.chatApplication.getDestinatairePvr().getText());
    }
    else if (action.equals("MessPvr")) {
      this.chatApplication.showMessPvr();
      this.chatApplication.getBtnMessPvr().setStyle("-fx-background-color: #f5f5f5;");
    }
    else if (action.equals("MessPvrWith")) {
      this.chatApplication.showMessPvrWith(this.data);
      this.chatApplication.setDestinatairePvr(this.data);
      this.chatApplication.deleteNotif(this.data);
    }
    else if (action.equals("Home")) {
      this.chatApplication.showChatMode();
    }
    else if (action.equals("ChangeIP")) {
      try {
        this.chatApplication.setClient(
            new ClientIHM(this.data, 5001, this.chatApplication.getNomUser(), this.chatApplication)
          );
      } catch (ExceptionSalon e) {
        e.printStackTrace();
      }
    }
  }

  public ClientIHM getClient() {
    return client;
  }
}
