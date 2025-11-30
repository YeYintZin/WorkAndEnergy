package Main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WorkAndEnergyMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/welcome.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Ski Simulator - Work and Energy");
        stage.setScene(scene);
        stage.show();
    }
    
    //TODO:
    // Make the ball slide off once its over (depending on velocity), then display the result screen
    // Fix the way the ball follows the path (Initial start, ball is on the line, acceleration, LOW PRIORITY)
    // Implement the result screen (Initial Energy (Potential Energy), Final velocity)
    // Documentation 
    // Other bug fixes (Such as the counter, LOW PRIORITY)
    
}
