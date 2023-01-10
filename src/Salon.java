import java.util.ArrayList;
import java.util.List;


public class Salon {
    private String nom;
    private List<Session> clientsSalon;

    public Salon(String nom) {
        this.nom = nom;
        this.clientsSalon = new ArrayList<>();
    }

    public void addClient(Session client) {
        this.clientsSalon.add(client);
    }

    public void removeClient(Session client) {
        this.clientsSalon.remove(client);
    }

    public void sendAll(String message, Session envoyeur) {
        for (Session client : clientsSalon) {
            if (!client.equals(envoyeur)) {
                client.send(message);
            }
        }
    }

    public String getNom() {
        return nom;
    }

    public List<Session> getClientsSalon() {
        return clientsSalon;
    }
}
