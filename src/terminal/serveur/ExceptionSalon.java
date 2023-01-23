/**
 * @file ExceptionSalon.java
 * @brief Classe qui permet de gérer les exceptions des salons
 */

package terminal.serveur;

public class ExceptionSalon extends Exception {
    /**
     * Constructeur de la classe ExceptionSalon
     * @param message {String} Le message d'erreur
     */
    public ExceptionSalon(String message) {
        super(message);
    }    
}
