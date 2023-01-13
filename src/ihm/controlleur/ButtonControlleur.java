package ihm.controlleur;

import ihm.ClientIHM;
import ihm.modele.ModeleApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launch.ChatApplication;

public class ButtonControlleur implements EventHandler<ActionEvent> {
  private ChatApplication chatApplication;
  private String action;
  private ClientIHM client;
  private ModeleApp modele;
  private String data;

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
      chatApplication.getTextArea().appendText(message + "\n");
      chatApplication.getTextField().clear();
      client.sendMessage(message);
    } else if (action.equals("AllSalon")) {
      this.chatApplication.setListSalons(this.modele.getAllSalon());
      this.chatApplication.showAllSalon(this.modele.getAllSalon());
    } else if (action.equals("Salon")) {
      this.client.sendMessage("/salon " + this.data);
      this.chatApplication.showChatMode();
    }
    else if (action.equals("NewSalonAsk")) {
      this.chatApplication.popUpAskSalon();
    }
    else if (action.equals("NewSalon")) {
      this.client.sendMessage("/createsalon " + this.data);
    }
    else if(action.equals("Username")){
      this.client.sendMessage("/username " + this.data);}
  }

  public ClientIHM getClient() {
    return client;
  }
}