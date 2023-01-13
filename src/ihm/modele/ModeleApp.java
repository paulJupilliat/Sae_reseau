package ihm.modele;

import ihm.ClientIHM;
import ihm.controlleur.ButtonControlleur;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import launch.ChatApplication;

public class ModeleApp {
  private ButtonControlleur buttonControlleur;
  private ChatApplication chatApplication;
  private ClientIHM client;
  private List<String> salons;
  private final Logger loger = System.getLogger("ModeleApp");

  public ModeleApp(
    ChatApplication chatApplication,
    ButtonControlleur buttonControlleur
  ) {
    this.chatApplication = chatApplication;
    this.buttonControlleur = buttonControlleur;
    this.client = buttonControlleur.getClient();
  }

  public void setSalons(String[] salons) {
    // converti String[] en List<String>
    List<String> list = new ArrayList<>(Arrays.asList(salons));
    this.salons = list;
  }

  public List<String> getAllSalon() {
    this.client.sendMessage("/salons");
    // creer un thread qui attend que chatApplicatioin recoive la liste des salons et l'envoie
    Thread t = new Thread() {

      @Override
      public void run() {
        while (chatApplication.getSalonsTextBrut() == null) {}
        try {
          String res = chatApplication.getSalonsTextBrut();
          String[] nomSalons = res.split("\\|");
          setSalons(nomSalons);
          chatApplication.setSalonsTextBrut(null);
        } catch (Exception e) {
          loger.log(
            System.Logger.Level.ERROR,
            "Erreur dans la recuperation des salons"
          );
          salons = new ArrayList<>();
        }
      }
    };
    t.start();
    try {
      t.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // enlever l'espace en debut de chaine
    for (int i = 0; i < salons.size(); i++) {
      salons.set(i, salons.get(i).trim());
      System.out.println(salons.get(i));
      // supprimer les chaines vides et le salon Config
      if (salons.get(i).equals("") || salons.get(i).equals("Config")) {
        salons.remove(i);
        i--;
      }
    }
    for (String salon : salons) {}
    return this.salons;
  }
}
