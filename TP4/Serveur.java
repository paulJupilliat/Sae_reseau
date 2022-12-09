import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.net.ServerSocket;

public class Serveur{
    private List<String> conv;

    public Serveur() {
        this.conv = new ArrayList<String>();
    }

    /**
     * Fonction qui permet de lancer le serveur et de le faire tourner 
     * @param port le port sur lequel le serveur va tourner
     */
    public void mainServeur(int port){
        try{
            ServerSocket socket = new ServerSocket(port);
            System.out.println("Le serveur est lancé");

            while(true){
                Socket client = socket.accept();
                System.out.println("Un client s'est connecté");
                ClientHandler clientHandler = new ClientHandler(client, conv);
                clientHandler.start();
            }


        } catch (Exception e) {
            System.out.println("Le port est déjà utilisé");
        }


    }
    
    
}
