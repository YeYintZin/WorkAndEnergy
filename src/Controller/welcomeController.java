package Controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class welcomeController {
    private Stage stage;
    private Scene scene;

    @FXML
    private Button exitButton;

    @FXML
    private Button playButton;

    @FXML
    void exitButtonAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void playButtonAction(ActionEvent event) {
        switchToApplication();
    }
    
    public void initialize() {
        // TODO
    }    
    
    public void switchToApplication() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/application.fxml"));
            Parent root = loader.load();

            ApplicationController controller = loader.getController();
            
//            controller.setRunners(runners);
            
            stage = (Stage) playButton.getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Work and Energy Simulator");
            stage.setScene(scene);
            stage.show();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
