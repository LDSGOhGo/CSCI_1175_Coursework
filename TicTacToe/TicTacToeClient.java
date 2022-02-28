import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.*;
import java.util.*;
import javafx.scene.control.*;
import java.io.*;

public class TicTacToeClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TicTacToe.fxml"));
        primaryStage.setTitle("TicTacToe Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        try {
            Socket socket = new Socket("localhost", 8000);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
