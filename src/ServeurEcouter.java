import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServeurEcouter extends Thread {
  private String msg;
  private Serveur s;
  final Socket clientSocket;
  final BufferedReader in;
  final PrintWriter out;
  Scanner sc;

    public ServeurEcouter(Socket socket) throws IOException{
        this.clientSocket = socket;
        this.out = new PrintWriter(this.clientSocket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    }
    public void sendAll(String message){
      for(Socket socket : this.getClients())

    }

  
  public void run() {
      try {
        msg = in.readLine();
      System.out.println(msg);
      //tant que le client est connecté
      while (msg != "quit") {
        System.out.println("Client : " + msg);
        //envoie a tous les clients

        out.println(msg);
        out.flush();
        msg = in.readLine();
        
        
        
      }
    
      //sortir de la boucle si le client a déconecté
      System.out.println("Client déconecté");
      //fermer le flux et la session socket
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
