import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.application.Platform;

public class ExerciseController {
    @FXML
    private Button calculate;
    @FXML
    private MenuItem exit;
    @FXML
    private TextField investment;
    @FXML
    private TextField years;
    @FXML
    private TextField annual;
    @FXML
    private TextField future;
    
    @FXML
    protected void onCalculateClick() {
        Double inv = Double.parseDouble(investment.getText());
        Double year = Double.parseDouble(years.getText());
        Double ann = Double.parseDouble(annual.getText()) / 100;
        Double monthly = ann / 12;
        Double fut = inv * (double)Math.pow(1 + monthly, (year * 12));
        String text = String.format("%.2f", fut);
        future.setText("$" + text);
    }
    
    @FXML
    protected void onExitClick() {
        Platform.exit();
    }
}