import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread{
    
    private Socket client;
    private List<String> conv;
    private String convActuelle;

    public ClientHandler(Socket client, List<String> conv) {
        this.client = client;
        this.conv = conv;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
    }

}
