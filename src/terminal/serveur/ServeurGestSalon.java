package terminal.serveur;

import java.net.Socket;
import java.util.List;
import launch.Serveur;

public class ServeurGestSalon extends Thread {
  private List<Salon> salons;
  private String nomNewSalon;
  private Salon newSalon;
  private Salon oldSalon;
  private Socket client;
  private Serveur serveur;
  private String action;
  private boolean used;

  public ServeurGestSalon(
    String newSalon,
    String oldSalon,
    Socket client,
    Serveur serveur,
    String action
  ) {
    this.client = client;
    this.serveur = serveur;
    this.salons = serveur.getSalons();
    this.nomNewSalon = newSalon;
    this.newSalon = this.findSalon(newSalon);
    this.oldSalon = this.findSalon(oldSalon);
    this.action = action;
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
    if (this.action.equals("join")) {
      this.joinSalon();
    } else if (this.action.equals("create")) {
      this.createSalon();
    } else if (this.action.equals("change")) {
      try {
        this.changeSalon();
        this.sendConfirmation();
      } catch (ExceptionSalon e) {
        this.sendInfo(e.getMessage());
      }
    } else if (this.action.equals("delete")) {
      this.deleteSalon();
    } else if (this.action.equals("verif")) {
      this.used = this.verifUser();
    }
  }

  private boolean verifUser() {
    for (Session client : this.serveur.getClients()) {
      if (client.getNom().equals(this.newSalon)) {
        this.sendInfo("serveur : " + client.getNom() + " est déjà dans le chat");
        return true;
      }
    }
    return false;
  }

  private void deleteSalon() {
    if (this.newSalon == null) {
      this.sendInfo("Le salon n'existe pas");
    } else if (this.newSalon.getNbActuel() > 0) {
      this.sendInfo("Le salon n'est pas vide");
    } else {
      this.salons.remove(this.newSalon);
      this.sendInfo("Salon supprimé");
    }
  }

  public boolean isUsed() {
      return used;
  }

  private void createSalon() {
    Salon nvSalon = new Salon(
      this.nomNewSalon,
      this.nomNewSalon,
      10,
      0,
      this.serveur
    );
    // Si le nom n'est pas déjà pris
    if (this.serveur.getSalon(nvSalon.getNom()) == null) {
      this.salons.add(nvSalon);
      this.newSalon = nvSalon;
      try {
        this.changeSalon();
        this.sendConfirmation();
      } catch (ExceptionSalon e) {
        this.sendInfo(e.getMessage());
        this.serveur.sendInfo("Salon créé", this.client);
      }
    } else {
      this.serveur.sendInfo("Le nom du salon est déjà pris", client);
    }
  }

  /**
   * Envoie une confirmation de connexion au salon
   */
  private void sendConfirmation() {
    this.sendInfo(
        "serveur : Vous avez rejoint le salon #" + this.newSalon.getNom()
      );
  }

  public void joinSalon() {
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
      this.sendInfo(
          "serveur : Vous avez rejoint le salon #" + this.newSalon.getNom()
        );
    } catch (ExceptionSalon e) {
      this.sendInfo(e.getMessage());
    }
  }

  /**
   * Permet de changer de salon
   * Si le salon que l'on veut rejoindre est plein, on génère une exception
   */
  private void changeSalon() throws ExceptionSalon {
    try {
      if (this.newSalon.connexion(this.client)) {
        if (this.oldSalon != null) {
          this.oldSalon.deco(this.client);
        }
      } else {
        throw new ExceptionSalon("Le salon est plein");
      }
    } catch (Exception e) {
      throw new ExceptionSalon("Le salon n'existe pas");
    }
  }
}
