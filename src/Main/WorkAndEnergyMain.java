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
    // Documentation 
    // IMPORTANT BUG FIX: RESET/START and the way they interact
    // Implement a place where user can input mass
    // Clean code cuz im a bum
    
}
