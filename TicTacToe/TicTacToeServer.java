import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.*;
import java.util.*;
import javafx.scene.control.*;
import java.io.*;

public class TicTacToeServer extends Application {
private TextArea chatbox;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TicTacToe.fxml"));
        primaryStage.setTitle("TicTacToe Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        new Thread(() -> {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            Platform.runLater(() -> {
                chatbox.appendText("Server started at " + new Date() + "\n");
            });
            Socket socket = serverSocket.accept();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        }).start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
