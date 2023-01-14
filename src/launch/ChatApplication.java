package launch;

import ihm.ClientIHM;
import ihm.controlleur.ButtonCloseControlleur;
import ihm.controlleur.ButtonControlleur;
import java.lang.ModuleLayer.Controller;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import terminal.serveur.Salon;

public class ChatApplication extends Application {
  private BorderPane root;
  private TextArea textArea;
  private TextField textField;
  private Button button;
  private HBox hBox;
  private Button buttonNewSalon;
  private Button btnAllSallon;
  private ClientIHM client;
  private String salonsTextBrut; //tous les salons reçus
  private List<String> listSalons; //tous les salons reçus
  private Text salonActuel;
  private Text username;

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Creer une fentre avec un champs textuel et un bouton
    this.root = new BorderPane();
    Scene scene = new Scene(root, 400, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
    this.salonActuel = new Text(); //nom du salon courant
    this.textArea = new TextArea();
    this.textField = new TextField();
    this.username = new Text("Anonyme");
    this.button = new Button("Envoyer");
    boolean otherval = false;
    while (this.username.getText().equals("Anonyme")) {
      this.showChargement();
      this.username.setText(this.popUpAskUsername(otherval));
      otherval = true;
      try {
        this.client =
          new ClientIHM("localhost", 5001, this.username.getText(), this);
      } catch (Exception e) {
        this.username.setText("Anonyme");
      }
    }
    this.topMenu();
    this.setupChatMode();
    this.button.setOnAction(new ButtonControlleur(this, "Envoyer", client));
    this.textField.setOnAction(new ButtonControlleur(this, "Envoyer", client));
    primaryStage.setOnCloseRequest(new ButtonCloseControlleur(client));
  }

  /**
   * Met en place le mode chat (affichage)
   */
  public void setupChatMode() {
    this.showChatMode();
    // phase style
    this.hBox.setPadding(new Insets(10));
    this.hBox.setSpacing(10);
    this.textArea.setEditable(false);
  }

  /**
   * Affiche le mode chat
   */
  public void showChatMode() {
    this.hBox = new HBox();
    this.hBox.getChildren().addAll(textField, button);
    this.btnAllSallon.disableProperty().set(false);
    this.root.setBottom(hBox);
    this.root.setCenter(textArea);
    this.salonActuel.setText("salon: " + this.client.getSalonActuel());
  }

  public void askIp() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Donnez l'adresse IP du serveur");
    dialog.setHeaderText("Donnez l'adresse IP du serveur");
    dialog.setContentText("IP:");
    dialog.showAndWait();
    new ButtonControlleur(
      null,
      "ChangeIP",
      client,
      dialog.getEditor().getText()
    );
  }

  public void setClient(ClientIHM client) {
    this.client = client;
  }

  public void showChargement() {
    Text chargement = new Text("Chargement...");
    this.root.setCenter(chargement);
  }

  /**
   * Affiche les boutons du menu
   */
  public void topMenu() {
    HBox topMenu = new HBox();
    this.buttonNewSalon = new Button("Nouveau salon");
    this.buttonNewSalon.setOnAction(
        new ButtonControlleur(this, "NewSalonAsk", client)
      );
    this.btnAllSallon = new Button("Tous les salons");
    this.btnAllSallon.setOnAction(
        new ButtonControlleur(this, "AllSalon", client)
      );
    // Met en à droite le nom du salon courant
    topMenu
      .getChildren()
      .addAll(this.buttonNewSalon, this.btnAllSallon, this.salonActuel);
    this.root.setTop(topMenu);
  }

  /**
   * Affiche tous les salons disponibles
   * @param salons liste des salons
   */
  public void showAllSalon(List<String> salons) {
    VBox allSalon = new VBox();
    for (String salon : this.listSalons) {
      Button btn = new Button(salon);
      btn.setOnAction(new ButtonControlleur(this, "Salon", client, salon));
      allSalon.getChildren().add(btn);
    }
    this.btnAllSallon.disableProperty().set(true);
    this.root.setCenter(allSalon);
  }

  public void setListSalons(List<String> listSalons) {
    this.listSalons = listSalons;
  }

  public void setSalonsTextBrut(String salonsTextBrut) {
    this.salonsTextBrut = salonsTextBrut;
  }

  public String getSalonsTextBrut() {
    return this.salonsTextBrut;
  }

  public TextField getTextField() {
    return textField;
  }

  public TextArea getTextArea() {
    return textArea;
  }

  /**
   * Ajoute un message dans la zone de texte
   * @param message message à ajouter
   * @param color couleur du message
   */
  public void putNewMessage(String message, String color) {
    this.textArea.appendText(message + "\n");
  }

  /**
   * Pop up qui demande d'entré le nom du salon
   */
  public void popUpAskSalon() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Nouveau salon");
    dialog.setHeaderText("Créer un nouveau salon");
    dialog.setContentText("Entrez le nom du salon:");
    dialog.showAndWait();
    new ButtonControlleur(
      this,
      "NewSalon",
      client,
      dialog.getEditor().getText()
    )
    .handle(null);
  }

  public String popUpAskUsername(boolean other) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Username");
    dialog.setHeaderText("Donnez un nom d'utilisateur");
    if (other) {
      dialog.setContentText(
        "Entrez votre nom d'utilisateur:\n (different car déjà pris)"
      );
    } else {
      dialog.setContentText("Entrez votre nom d'utilisateur:");
    }
    dialog.showAndWait();
    // quand clique sur anuler
    if (dialog.getEditor().getText().equals("")) {
      new ButtonControlleur(null, "Close", client).handle(null);
    }
    return dialog.getEditor().getText();
  }

  public String getNomUser() {
    return this.username.getText();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
