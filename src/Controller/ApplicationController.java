package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ApplicationController {

    @FXML
    private Button reset;
    @FXML
    private Button start;
    @FXML
    private Label countdown;
    
    public void initialize() {
        start.setOnAction(e -> {
            if (start.getText().equals("Start")) {
                start.setText("Stop");
                reset.setDisable(false);
            } else {
                start.setText("Start");
                reset.setDisable(true);
            }
            
        });
        reset.setOnAction(e -> {
            resetPressed();
        });
    }
    
    private void resetPressed() {
        System.out.println("reset");
    }
    
}
