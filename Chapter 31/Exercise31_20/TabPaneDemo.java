import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.beans.value.*;
import javafx.geometry.*;

public class TabPaneDemo extends Application {   
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {   
    TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("Line");
    StackPane pane1 = new StackPane();
    pane1.getChildren().add(new Line(10, 10, 80, 80));
    tab1.setContent(pane1);
    Tab tab2 = new Tab("Rectangle");
    tab2.setContent(new Rectangle(10, 10, 200, 200));
    Tab tab3 = new Tab("Circle");
    tab3.setContent(new Circle(50, 50, 20));    
    Tab tab4 = new Tab("Ellipse");
    tab4.setContent(new Ellipse(10, 10, 100, 80));
    tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
    BorderPane radioPane = new BorderPane();
    ToggleGroup group = new ToggleGroup();
    RadioButton top = new RadioButton();
    top.setText("Top");
    top.setToggleGroup(group);
    top.setSelected(true);
    RadioButton left = new RadioButton();
    left.setText("Left");
    left.setToggleGroup(group);
    RadioButton bottom = new RadioButton();
    bottom.setText("Bottom");
    bottom.setToggleGroup(group);
    RadioButton right = new RadioButton();
    right.setText("Right");
    right.setToggleGroup(group);
    HBox box = new HBox(top, left, bottom, right);
    box.setSpacing(25);
    radioPane.setBottom(box);
    radioPane.setTop(tabPane);
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      public void changed(ObservableValue<? extends Toggle> changed, Toggle oldVal, Toggle newVal) {
        RadioButton rb = (RadioButton) newVal;
        if(rb.getText() == "Top") {
          tabPane.setSide(Side.TOP);
        }
        else if(rb.getText() == "Left") {
          tabPane.setSide(Side.LEFT);
        }
        else if(rb.getText() == "Bottom") {
          tabPane.setSide(Side.BOTTOM);
        }
        else if(rb.getText() == "Right") {
          tabPane.setSide(Side.RIGHT);
        }
      }
    });
    Scene scene = new Scene(radioPane, 310, 270);  
    primaryStage.setTitle("DisplayFigure"); // Set the window title
    primaryStage.setScene(scene); // Place the scene in the window
    primaryStage.show(); // Display the window
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   * line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}