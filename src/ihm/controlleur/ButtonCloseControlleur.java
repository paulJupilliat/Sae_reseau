package ihm.controlleur;

import ihm.ClientIHM;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class ButtonCloseControlleur implements EventHandler<WindowEvent> {
    private ClientIHM client;

    public ButtonCloseControlleur(ClientIHM client) {
        this.client = client;
    }

    @Override
    public void handle(WindowEvent arg0) {
        client.stop();
    }
    
}
