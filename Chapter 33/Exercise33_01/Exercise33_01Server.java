// Exercise31_01Server.java: The server can communicate with
// multiple clients concurrently using the multiple threads
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;import java.io.*;
import java.net.*;


public class Exercise33_01Server extends Application {
  // Text area for displaying contents
  private TextArea ta = new TextArea();

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ta.setWrapText(true);
   
    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 400, 200);
    primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread(() -> {
      try {
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() -> {
          ta.appendText("Server started at " + new Date() + "\n");
        });
        Socket socket = serverSocket.accept();
        
        DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
        while(true) {
          double interestRate = inputFromClient.readDouble();
          int years = inputFromClient.readInt(); 
          double loan = inputFromClient.readDouble();
          Loan theLoan = new Loan(interestRate, years, loan);
          double monthlyPayment = theLoan.getMonthlyPayment();
          double totalPayment = theLoan.getTotalPayment();
          ta.appendText("Annual Interest Rate: " + interestRate + "\n");
          ta.appendText("Number of Years: " + years + "\n");
          ta.appendText("Loan Amount: " + loan + "\n");
          ta.appendText("Monthly Payment: " + monthlyPayment + "\n");
          ta.appendText("Total Payment: " + totalPayment + "\n");
          outputToClient.writeDouble(monthlyPayment);
          outputToClient.writeDouble(totalPayment);
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }
    
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
