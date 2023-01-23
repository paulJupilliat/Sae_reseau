/**
 * @file ButtonCloseControlleur.java
 * @brief Classe permettant de gérer le bouton de fermeture
 * @package ihm.controlleur
 */
package ihm.controlleur;

import ihm.ClientIHM;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class ButtonCloseControlleur implements EventHandler<WindowEvent> {
    private ClientIHM client;

    /**
     * Constructeur de la classe ButtonCloseControlleur
     * @param client {ClientIHM} Le client
     */
    public ButtonCloseControlleur(ClientIHM client) {
        this.client = client;
    }

    @Override
    public void handle(WindowEvent arg0) {
        client.stop();
    }
    
}
