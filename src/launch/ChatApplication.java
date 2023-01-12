package launch;

import ihm.ClientIHM;
import ihm.controlleur.ButtonCloseControlleur;
import ihm.controlleur.ButtonControlleur;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChatApplication extends Application {
  private BorderPane root;
  private TextArea textArea;
  private TextField textField;
  private Button button;
  private HBox hBox;

  @Override
  public void start(Stage primaryStage) throws Exception {
    ClientIHM client = new ClientIHM("localhost", 5001, "ben", this);
    // Creer une fentre avec un champs textuel et un bouton
    this.root = new BorderPane();
    this.textArea = new TextArea();
    this.textField = new TextField();
    this.button = new Button("Envoyer");
    this.button.setOnAction(new ButtonControlleur(this, "Envoyer", client));
    this.textField.setOnAction(new ButtonControlleur(this, "Envoyer", client));
    this.hBox = new HBox();
    this.hBox.setPadding(new Insets(10));
    this.hBox.setSpacing(10);
    this.hBox.getChildren().addAll(textField, button);
    this.root.setCenter(textArea);
    this.root.setBottom(hBox);
    Scene scene = new Scene(root, 400, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setOnCloseRequest(new ButtonCloseControlleur(client));
  }

  public TextField getTextField() {
    return textField;
  }

  public TextArea getTextArea() {
    return textArea;
  }

  public void putNewMessage(String message) {
    this.textArea.appendText(message + "\n");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
