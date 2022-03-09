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

public class TicTacToeServerController {
	@FXML
	private Button connect;
	@FXML
	Label draw;
	@FXML
	GridPane gridPane;
	@FXML
	Pane zero, one, two, three, four, five, six, seven, eight;
	DataInputStream inputFromClient;
	DataOutputStream outputToClient;
	int turn;
	int tile;
	Boolean[] isFilled = {false, false, false, false, false, false, false, false, false};
	char[][] board = { {'n', 'n', 'n'}, {'n', 'n', 'n'}, {'n', 'n', 'n'} };
	boolean started = false;
	
	protected void isDraw() {
		for(int i = 0; i < isFilled.length; i++) {
			if(!isFilled[i]) {
				return;
			}
		}
		Pane[] panes = {zero, one, two, three, five, six, seven, eight};
		for(int i = 0; i < panes.length; i++) {
			panes[i].setVisible(false);
		}
		four.getChildren().clear();
		four.setStyle(null);
		four.getChildren().add(draw);
		draw.setVisible(true);
	}
	protected void isWon() {
		boolean xWon = false;
		boolean oWon = false;
		for(int i = 0; i < 3; i++) {
			if(board[i][0] == 'x' && board[i][1] == 'x' && board[i][2] == 'x') {
				xWon = true;
			}
		}
		for(int i = 0; i < 3; i++) {
			if(board[0][i] == 'x' && board[1][i] == 'x' && board[2][i] == 'x') {
				xWon = true;
			}
		}
		if(board[0][0] == 'x' && board[1][1] == 'x' && board[2][2] == 'x') {
			xWon = true;
		}
		if(board[0][2] == 'x' && board[1][1] == 'x' && board[2][0] == 'x') {
			xWon = true;
		}
		
		for(int i = 0; i < 3; i++) {
			if(board[i][0] == 'o' && board[i][1] == 'o' && board[i][2] == 'o') {
				oWon = true;
			}
		}
		for(int i = 0; i < 3; i++) {
			if(board[0][i] == 'o' && board[1][i] == 'o' && board[2][i] == 'o') {
				oWon = true;
			}
		}
		if(board[0][0] == 'o' && board[1][1] == 'o' && board[2][2] == 'o') {
			oWon = true;
		}
		if(board[0][2] == 'o' && board[1][1] == 'o' && board[2][0] == 'o') {
			oWon = true;
		}
		
		if(xWon) {
			Pane[] panes = {zero, one, two, three, five, six, seven, eight};
			for(int i = 0; i < panes.length; i++) {
				panes[i].setVisible(false);
			}
			four.getChildren().clear();
			four.setStyle(null);
			four.getChildren().add(draw);
			draw.setText("Game Over! Server Won");
			draw.setVisible(true);
		}
		if(oWon) {
			Pane[] panes = {zero, one, two, three, five, six, seven, eight};
			for(int i = 0; i < panes.length; i++) {
				panes[i].setVisible(false);
			}
			four.getChildren().clear();
			four.setStyle(null);
			four.getChildren().add(draw);
			draw.setText("Game Over! Client Won");
			draw.setVisible(true);
		}
	}
	protected void fillBoard(boolean turn, int tile) {
		if(turn) {
			switch(tile) {
				case 0:
				board[0][0] = 'x';
				break;
				case 1:
				board[0][1] = 'x';
				break;
				case 2:
				board[0][2] = 'x';
				break;
				case 3:
				board[1][0] = 'x';
				break;
				case 4:
				board[1][1] = 'x';
				break;
				case 5:
				board[1][2] = 'x';
				break;
				case 6:
				board[2][0] = 'x';
				break;
				case 7:
				board[2][1] = 'x';
				break;
				case 8:
				board[2][2] = 'x';
				break;
			}
		}
		else {
			switch(tile) {
				case 0:
				board[0][0] = 'o';
				break;
				case 1:
				board[0][1] = 'o';
				break;
				case 2:
				board[0][2] = 'o';
				break;
				case 3:
				board[1][0] = 'o';
				break;
				case 4:
				board[1][1] = 'o';
				break;
				case 5:
				board[1][2] = 'o';
				break;
				case 6:
				board[2][0] = 'o';
				break;
				case 7:
				board[2][1] = 'o';
				break;
				case 8:
				board[2][2] = 'o';
				break;
			}
		}
	}
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
				System.out.println("something went wrong");
				return;
		}
		Ellipse circle = new Ellipse(pane.getWidth() / 2 - 10, pane.getHeight() / 2 - 10);
		circle.centerXProperty().bind(pane.widthProperty().divide(2));
		circle.centerYProperty().bind(pane.heightProperty().divide(2));
		circle.radiusXProperty().bind(pane.widthProperty().divide(2).subtract(30));
		circle.radiusYProperty().bind(pane.heightProperty().divide(2).subtract(10));
		circle.setStroke(Color.RED);
		circle.setFill(Color.TRANSPARENT);
		pane.getChildren().addAll(circle);
		isFilled[tile] = true;
		fillBoard(false, tile);
		isWon();
		isDraw();
	}
	@FXML
	protected void onClick(MouseEvent event) {
		if(!started) {
			return;
		}
		if(isFilled[getTile(event)]) {
			return;
		}
		if(turn == 2) {
			return;
		} else if(turn == 0) {
			Pane pane = (Pane) event.getTarget();
			tile = getTile(event);
			Line one = new Line(10, 10, pane.getWidth() - 10, pane.getHeight() - 10);
			one.endXProperty().bind(pane.widthProperty().subtract(10));
			one.endYProperty().bind(pane.heightProperty().subtract(10));
			Line two = new Line(10, pane.getHeight() - 10, pane.getWidth() - 10, 10);
			two.startYProperty().bind(pane.heightProperty().subtract(10));
			two.endXProperty().bind(pane.widthProperty().subtract(10));
			pane.getChildren().addAll(one, two);
			isFilled[tile] = true;
			fillBoard(true, tile);
			turn += 2;
		}
		else {
			Pane pane = (Pane) event.getTarget();
			tile = getTile(event);
			Line one = new Line(10, 10, pane.getWidth() - 10, pane.getHeight() - 10);
			one.endXProperty().bind(pane.widthProperty().subtract(10));
			one.endYProperty().bind(pane.heightProperty().subtract(10));
			Line two = new Line(10, pane.getHeight() - 10, pane.getWidth() - 10, 10);
			two.startYProperty().bind(pane.heightProperty().subtract(10));
			two.endXProperty().bind(pane.widthProperty().subtract(10));
			pane.getChildren().addAll(one, two);
			isFilled[tile] = true;
			turn++;
			fillBoard(true, tile);
			isWon();
			isDraw();
		}
		try {
			outputToClient.writeInt(turn);
			outputToClient.writeInt(tile);
		}
		catch(IOException ex) {
			System.out.println(ex.toString());
		}
	}
	@FXML 
	protected void connectButton(MouseEvent event) {
		new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> {
					System.out.println("Server started at " + new Date());
				});
				connect.setVisible(false);
				Socket socket = serverSocket.accept();
				inputFromClient = new DataInputStream(socket.getInputStream());
				outputToClient = new DataOutputStream(socket.getOutputStream());
				turn = 0;
				started = true;
				while(true) {
					turn = inputFromClient.readInt();
					tile = inputFromClient.readInt();
					Platform.runLater(() -> {
						lastMove(tile);
					});
				}
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}).start();
	}
}