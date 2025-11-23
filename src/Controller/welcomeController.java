package Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class welcomeController {

    @FXML
    private Button creditsButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button playButton;

    @FXML
    void creditsButtonAction(ActionEvent event) {

    }

    @FXML
    void exitButtonAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void playButtonAction(ActionEvent event) {

    }
    public void initialize() {
        // TODO
    }    
}
