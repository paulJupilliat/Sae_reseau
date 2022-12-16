import java.io.IOException;
import java.net.Socket;

public class Client {
  private Socket clientSocket;
  private String nom;
  private ClientRecevoir recevoir;
  private ClientEnvoyer envoyer;

  public String getNom() {
    return this.nom;
  }
  
  public void setNom(String nom) {
    this.nom = nom;
  }
  
  public void send(String msg) {
    this.envoyer.send(msg);
  }
    
  public Client(String ip, int port) {
     try {
      clientSocket = new Socket(ip, port);
      this.envoyer = new ClientEnvoyer(clientSocket);
      this.recevoir = new ClientRecevoir(clientSocket, this);
      // On lance les threads
      envoyer.start();
      recevoir.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  
  public static void main(String[] args) {
    Client client = new Client("localhost", 5000);
   
  }
}
