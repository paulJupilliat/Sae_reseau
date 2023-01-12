package ihm.controlleur;

import ihm.ClientIHM;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launch.ChatApplication;

public class ButtonControlleur implements EventHandler<ActionEvent> {
    private ChatApplication chatApplication;
    private String action;
    private ClientIHM client;

    public ButtonControlleur(ChatApplication chatApplication, String action, ClientIHM client) {
        this.chatApplication = chatApplication;
        this.action = action;
        this.client = client;
    }

    @Override
    public void handle(ActionEvent event) {
        if (action.equals("Envoyer")) {
            String message = chatApplication.getTextField().getText();
            chatApplication.getTextArea().appendText(message + "\n");
            chatApplication.getTextField().clear();
            client.sendMessage(message);
        }
    }
}
