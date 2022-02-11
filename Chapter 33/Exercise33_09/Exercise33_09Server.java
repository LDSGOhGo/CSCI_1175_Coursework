import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.*;
import java.util.*;
import java.io.*;
import javafx.scene.input.*;
import javafx.event.*;

public class Exercise33_09Server extends Application {
  private TextArea taServer = new TextArea();
  private TextArea taClient = new TextArea();
  DataInputStream inputFromClient;
  DataOutputStream outputToClient;
 
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    taServer.setWrapText(true);
    taServer.setEditable(false);
    taClient.setWrapText(true);
    taServer.setDisable(true);

    BorderPane pane1 = new BorderPane();
    pane1.setTop(new Label("History"));
    pane1.setCenter(new ScrollPane(taServer));
    BorderPane pane2 = new BorderPane();
    pane2.setTop(new Label("New Message"));
    pane2.setCenter(new ScrollPane(taClient));
    
    VBox vBox = new VBox(5);
    vBox.getChildren().addAll(pane1, pane2);

    // Create a scene and place it in the stage
    Scene scene = new Scene(vBox, 200, 200);
    primaryStage.setTitle("Exercise31_09Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage


    new Thread(() -> {
      try {
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() -> {
          taServer.appendText("Server started at " + new Date() + "\n");
        });
        Socket socket = serverSocket.accept();
        
        inputFromClient = new DataInputStream(socket.getInputStream());
        outputToClient = new DataOutputStream(socket.getOutputStream());
        while(true) {
          taServer.appendText("C: " + inputFromClient.readUTF() + "\n");
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
    
    taClient.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent k) {
        try {
          if(k.getCode().equals(KeyCode.ENTER)) {
            String[] lines = taClient.getText().split(System.lineSeparator());
            outputToClient.writeUTF(lines[lines.length - 1]);
            taServer.appendText("S: " + lines[lines.length - 1] + "\n");
            taClient.clear();
          }
        }
        catch(IOException ex) {
          taServer.appendText(ex.toString());
        }
      }
    });
    
    // To complete later
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
