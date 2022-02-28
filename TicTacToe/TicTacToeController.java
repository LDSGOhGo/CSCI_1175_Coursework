import javafx.fxml.FXML;
import javafx.application.Application;
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

public class TicTacToeController {
	@FXML
	private TextArea chatbox;
	
	@FXML 
	protected void clickHandle(MouseEvent event) {
		Pane pane = (Pane) event.getTarget();
		Line one = new Line(10, 10, pane.getWidth() - 10, pane.getHeight() - 10);
		one.endXProperty().bind(pane.widthProperty().subtract(10));
		one.endYProperty().bind(pane.heightProperty().subtract(10));
		Line two = new Line(10, pane.getHeight() - 10, pane.getWidth() - 10, 10);
		two.startYProperty().bind(pane.heightProperty().subtract(10));
		two.endXProperty().bind(pane.widthProperty().subtract(10));
		pane.getChildren().addAll(one, two);
	}
}