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
    this.hBox = new HBox();
    hBox.setPadding(new Insets(10));
    hBox.setSpacing(10);
    hBox.getChildren().addAll(textField, button);
    root.setCenter(textArea);
    root.setBottom(hBox);
    Scene scene = new Scene(root, 400, 400);
    primaryStage.setScene(scene);
    primaryStage.show();

    // Ajouter un evenement sur le bouton
    button.setOnAction(e -> {
      String message = textField.getText();
      textArea.appendText(message + "\n");
      textField.clear();
      client.sendMessage(message);
    });

    // Ajouter un evenement sur le champ textuel
    textField.setOnAction(e -> {
      String message = textField.getText();
      textArea.appendText(message + "\n");
      textField.clear();
      client.sendMessage(message);
    });

    // Ajouter un evenement sur le bouton
    button.setOnAction(e -> {
      String message = textField.getText();
      System.out.println(message);
      textArea.appendText(message + "\n");
      textField.clear();
      client.sendMessage(message);
    });
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
