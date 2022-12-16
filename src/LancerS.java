import java.io.IOException;

public class LancerS {
    public static void main(String[] test) {
        Serveur serveur;
        try {
          serveur = new Serveur(5001);
          serveur.start();

        } catch (IOException e) {
          e.printStackTrace();
        }
  }
}
