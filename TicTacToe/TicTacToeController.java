import javafx.fxml.FXML;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class TicTacToeController {
	@FXML
	private TextArea chatbox, message;
	@FXML
	private Button connect;
	@FXML
	private TextField name;
	DataInputStream inputFromClient, fromServer;
	DataOutputStream outputToClient, toServer;
	
	@FXML 
	protected void serverTurn(MouseEvent event) {
			Pane pane = (Pane) event.getTarget();
			Line one = new Line(10, 10, pane.getWidth() - 10, pane.getHeight() - 10);
			one.endXProperty().bind(pane.widthProperty().subtract(10));
			one.endYProperty().bind(pane.heightProperty().subtract(10));
			Line two = new Line(10, pane.getHeight() - 10, pane.getWidth() - 10, 10);
			two.startYProperty().bind(pane.heightProperty().subtract(10));
			two.endXProperty().bind(pane.widthProperty().subtract(10));
			pane.getChildren().addAll(one, two);
	}
	@FXML 
	protected void clientTurn(MouseEvent event) {
			Pane pane = (Pane) event.getTarget();
			Ellipse circle = new Ellipse(pane.getWidth() / 2 - 10, pane.getHeight() / 2 - 10);
			circle.centerXProperty().bind(pane.widthProperty().divide(2));
			circle.centerYProperty().bind(pane.heightProperty().divide(2));
			circle.radiusXProperty().bind(pane.widthProperty().divide(2).subtract(30));
			circle.radiusYProperty().bind(pane.heightProperty().divide(2).subtract(10));
			circle.setStroke(Color.RED);
			circle.setFill(Color.TRANSPARENT);
			pane.getChildren().addAll(circle);
	}
	
	@FXML 
	protected void serverConnectButton(MouseEvent event) {
		new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> {
					chatbox.appendText("Server started at " + new Date() + '\n');
				});
				connect.setVisible(false);
				Socket socket = serverSocket.accept();
				inputFromClient = new DataInputStream(socket.getInputStream());
				outputToClient = new DataOutputStream(socket.getOutputStream());
				while(true) {
					chatbox.appendText(inputFromClient.readUTF());
				}
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}).start();
	}
	@FXML 
	protected void connectButton(MouseEvent event) {
		new Thread(() -> {
		try {
			Socket socket = new Socket("localhost", 8000);
			chatbox.appendText("Connected To Server \n");
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
			connect.setVisible(false);
			while(true) {
				chatbox.appendText(fromServer.readUTF());
			}
		}
		catch(IOException ex) {
			chatbox.appendText("Failed To Connect To Server \n");
			chatbox.appendText(ex.toString() + '\n');
		}
		}).start();
	}
	@FXML
	protected void serverSendButton(MouseEvent event) {
		try {
			String[] lines = message.getText().split(System.lineSeparator());
			outputToClient.writeUTF(name.getText() + ": " + lines[lines.length - 1] + "\n");
			chatbox.appendText(name.getText() + ": " + lines[lines.length - 1] + "\n");
			message.clear();
		}
		catch(IOException ex) {
			chatbox.appendText(ex.toString() + "\n");
		}
	}
	@FXML
	protected void serverEnterKey(KeyEvent event) {
		try {
			if(event.getCode().equals(KeyCode.ENTER)) {
				String[] lines = message.getText().split(System.lineSeparator());
				outputToClient.writeUTF(name.getText() + ": " + lines[lines.length - 1] + "\n");
				chatbox.appendText(name.getText() + ": " + lines[lines.length - 1] + "\n");
				message.clear();
			}
		}
		catch(IOException ex) {
			chatbox.appendText(ex.toString() + "\n");
		}
	}
	@FXML protected void sendButton(MouseEvent event) {
		try {
			String[] lines = message.getText().split(System.lineSeparator());
			toServer.writeUTF(name.getText() + ": " + lines[lines.length - 1] + "\n");
			chatbox.appendText(name.getText() + ": " + lines[lines.length - 1] + "\n");
			message.clear();
		}
		catch(IOException ex) {
			chatbox.appendText(ex.toString());
		}
	}
	@FXML protected void enterKey(KeyEvent event) {
		try {
			if(event.getCode().equals(KeyCode.ENTER)) {
				String[] lines = message.getText().split(System.lineSeparator());
				toServer.writeUTF(name.getText() + ": " + lines[lines.length - 1] + "\n");
				chatbox.appendText(name.getText() + ": " + lines[lines.length - 1] + "\n");
				message.clear();
			}
		}
		catch(IOException ex) {
			chatbox.appendText(ex.toString());
		}
	}
	
}