import java.util.ArrayList;
import java.util.List;

public class Salon {
    private String nom;
    private String code;
    private int capaciteMAx;
    private List<Client> clients;
    private Serveur serveur;

    public Salon(String nom, int capaciteMAx, Serveur serveur) {
        this.nom = nom;
        this.capaciteMAx = capaciteMAx;
        this.clients = new ArrayList<>();
        this.serveur = serveur;
    }

    public void ajouterClient(Client client) {
        if (this.clients.size() < this.capaciteMAx) {
            this.clients.add(client);
        } else {
            System.out.println("Le salon est plein");
        }
    }
    public String getNom() {
        return nom;
    }
    
}
