package terminal.serveur;

import java.net.Socket;
import java.util.List;

import launch.Serveur;

public class ServeurGestSalon extends Thread {
  private List<Salon> salons;
  private Salon newSalon;
  private Salon oldSalon;
  private Socket client;
  private Serveur serveur;

  public ServeurGestSalon(
    String newSalon,
    String oldSalon,
    Socket client,
    Serveur serveur
  ) {
    this.client = client;
    this.serveur = serveur;
    this.salons = serveur.getSalons();
    this.newSalon = this.findSalon(newSalon);
    this.oldSalon = this.findSalon(oldSalon);
  }

  public Salon findSalon(String nom) {
    for (Salon salon : this.salons) {
      if (salon.getNom().equals(nom)) {
        return salon;
      }
    }
    return null;
  }

  /**
   * Envoie un message à un client
   * @param msg
   */
  public void sendInfo(String msg) {
    this.serveur.sendInfo(msg, this.client);
  }

  @Override
  public void run() {
    // Si les salons n'existent pas
    if (this.newSalon == null) {
      try {
        throw new ExceptionSalon("Le salon n'existe pas");
      } catch (ExceptionSalon e) {
        this.sendInfo(e.getMessage());
      }
    }
    // On veut rejoindre un nouveau salon
    try {
      this.changeSalon();
      this.sendInfo("serveur : Vous avez rejoint le salon #" + this.newSalon.getNom());
    } catch (ExceptionSalon e) {
      this.sendInfo(e.getMessage());
    }
  }

  /**
   * Permet de changer de salon
   * Si le salon que l'on veut rejoindre est plein, on génère une exception
   */
  private void changeSalon() throws ExceptionSalon {
    if (newSalon.connexion(this.client)) {
      if (oldSalon != null) {
        oldSalon.deco(this.client);
      }
    } else {
      throw new ExceptionSalon("Le salon est plein");
    }
  }
}
