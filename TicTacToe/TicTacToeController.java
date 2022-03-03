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
	private Button connect;
	@FXML
	GridPane gridPane;
	@FXML
	Pane zero, one, two, three, four, five, six, seven, eight;
	DataInputStream fromServer;
	DataOutputStream toServer;
	int turn;
	int tile;
	Boolean[] isFilled = {false, false, false, false, false, false, false, false, false};
	
	protected int getTile(MouseEvent event) {
		Pane pane = (Pane) event.getTarget();
		int column, row;
		if(gridPane.getColumnIndex(pane) == null) {
			column = 0;
		} else {
			column = gridPane.getColumnIndex(pane);
		}
		if(gridPane.getRowIndex(pane) == null) {
			row = 0;
		} else {
			row = gridPane.getRowIndex(pane);
		}
		switch(column) {
			case 0:
			switch(row) {
				case 0:
					return 0;
				case 1:
					return 3;
				case 2:
					return 6;
			}
			break;
			case 1:
			switch(row) {
				case 0:
					return 1;
				case 1:
					return 4;
				case 2:
					return 7;
			}
			break;
			case 2:
			switch(row) {
				case 0:
					return 2;
				case 1:
					return 5;
				case 2:
					return 8;
			}
			break;
		}
		return 9;
	}
	
	protected void lastMove(int tile) {
		Pane pane;
		switch(tile) {
			case 0:
				pane = zero;
				break;
			case 1:
				pane = one;
				break;
			case 2:
				pane = two;
				break;
			case 3:
				pane = three;
				break;
			case 4:
				pane = four;
				break;
			case 5:
				pane = five;
				break;
			case 6:
				pane = six;
				break;
			case 7:
				pane = seven;
				break;
			case 8:
				pane = eight;
				break;
			default:
				return;
		}
		Line one = new Line(10, 10, pane.getWidth() - 10, pane.getHeight() - 10);
		one.endXProperty().bind(pane.widthProperty().subtract(10));
		one.endYProperty().bind(pane.heightProperty().subtract(10));
		Line two = new Line(10, pane.getHeight() - 10, pane.getWidth() - 10, 10);
		two.startYProperty().bind(pane.heightProperty().subtract(10));
		two.endXProperty().bind(pane.widthProperty().subtract(10));
		pane.getChildren().addAll(one, two);
	}
	@FXML
	protected void onClick(MouseEvent event) {
		if(isFilled[getTile(event)]) {
			return;
		}
		if(turn != 2) {
			return;
		}
		isFilled[tile] = true;
		Pane pane = (Pane) event.getTarget();
		tile = getTile(event);
		Ellipse circle = new Ellipse(pane.getWidth() / 2 - 10, pane.getHeight() / 2 - 10);
		circle.centerXProperty().bind(pane.widthProperty().divide(2));
		circle.centerYProperty().bind(pane.heightProperty().divide(2));
		circle.radiusXProperty().bind(pane.widthProperty().divide(2).subtract(30));
		circle.radiusYProperty().bind(pane.heightProperty().divide(2).subtract(10));
		circle.setStroke(Color.RED);
		circle.setFill(Color.TRANSPARENT);
		pane.getChildren().addAll(circle);
		turn--;
		isFilled[tile] = true;
		try {
			toServer.writeInt(turn);
			toServer.writeInt(tile);
		}
		catch(IOException ex) {
			System.out.println(ex.toString());
		}
	}
	@FXML 
	protected void checkTurn(MouseEvent event) {
		if(turn == 0) {
			return;
		}
		else if(turn == 2) {
			for(int i = 0; i < isFilled.length; i++) {
				System.out.print(isFilled[i] + " ");
			}
			Pane pane = (Pane) event.getTarget();
			System.out.println("Tile placed by server: " + tile);
			isFilled[tile] = true;
			lastMove(tile);
			if(isFilled[getTile(event)]) {
				System.out.println(getTile(event) + " is filled");
				return;
			}
			tile = getTile(event);
			Ellipse circle = new Ellipse(pane.getWidth() / 2 - 10, pane.getHeight() / 2 - 10);
			circle.centerXProperty().bind(pane.widthProperty().divide(2));
			circle.centerYProperty().bind(pane.heightProperty().divide(2));
			circle.radiusXProperty().bind(pane.widthProperty().divide(2).subtract(30));
			circle.radiusYProperty().bind(pane.heightProperty().divide(2).subtract(10));
			circle.setStroke(Color.RED);
			circle.setFill(Color.TRANSPARENT);
			pane.getChildren().addAll(circle);
			turn--;
			try {
				toServer.writeInt(turn);
				toServer.writeInt(turn);
			}
			catch(IOException ex) {
				System.out.println(ex.toString());
			}
		}
		else {
			return;
		}
	}
	@FXML 
	protected void connectButton(MouseEvent event) {
		new Thread(() -> {
		try {
			Socket socket = new Socket("localhost", 8000);
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
			connect.setVisible(false);
			turn = 0;
			System.out.println("Connected to server");
			while(true) {
				turn = fromServer.readInt();
				tile = fromServer.readInt();
				Platform.runLater(() -> {
					lastMove(tile);
				});
			}
		}
		catch(IOException ex) {
			System.out.println("Failed to connect to server");
			System.out.println(ex.toString());
		}
		}).start();
	}
	
}