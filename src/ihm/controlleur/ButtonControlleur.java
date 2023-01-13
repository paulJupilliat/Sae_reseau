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
  private String salonDest;

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
    String salonDest
  ) {
    this.chatApplication = chatApplication;
    this.action = action;
    this.client = client;
    this.modele = new ModeleApp(chatApplication, this);
    this.salonDest = salonDest;
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
      this.client.sendMessage("/salon " + this.salonDest);
      this.chatApplication.showChatMode();
    }
  }

  public ClientIHM getClient() {
    return client;
  }
}
