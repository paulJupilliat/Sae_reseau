import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Session {
  private ServeurEcoute recevoir;
  private String nom;
  private Socket clSocket;
  private Serveur serveur;

  public Session(Socket clSocket, Serveur serveur) {
    this.clSocket = clSocket;
    this.serveur = serveur;
  }

  public void start() {
    try {
      this.recevoir = new ServeurEcoute(this, this.serveur);
      this.recevoir.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getNom() {
      return this.nom;
  }
  
    public ServeurEcoute getRecevoir() {
        return this.recevoir;
    }

  public Socket getClSocket() {
    return this.clSocket;
  }
}
