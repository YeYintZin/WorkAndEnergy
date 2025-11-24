package Controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class ApplicationController {
    private int pathCount;
    
    @FXML
    private Pane application;
    @FXML
    private Button arcButton;
    @FXML
    private Button lineButton;
    @FXML
    private Button reset;
    @FXML
    private Button start;
    @FXML
    private Label countdown;
    
    public void initialize() {
        lineButton.setOnAction(e -> {
            if (pathCount > 4) {
                lineButton.setDisable(true);
                return;
            }
            lineButton.setStyle("-fx-background-color: red;" + "-fx-text-fill: white;");
            application.setOnMouseClicked(f -> {
                pathCount++;
                Line line = new Line(f.getX(), f.getY(), f.getX() + 100, f.getY() + 100);
                line.setStroke(Color.BLACK);
                line.setStrokeWidth(3);
                application.getChildren().add(line);
                application.setOnMouseClicked(null);
                lineButton.setStyle("-fx-background-color: lime;" + "-fx-text-fill: white;");
            });
        });
        start.setOnAction(e -> {
            countdownHandler();
            if (start.getText().equals("Start")) {
                start.setText("Stop");
                reset.setDisable(false);
            } else {
                start.setText("Start");
                reset.setDisable(true);
            }
            
        });
        reset.setOnAction(e -> {
            for (int i = 1; i < pathCount + 1; i++) {
                application.getChildren().remove(application.getChildren().size() - i);
            }
            pathCount = 0;
            lineButton.setDisable(false);
        });
    }
    
    private void countdownHandler() {
        countdown.setOpacity(1);
        PauseTransition pt = new PauseTransition(Duration.seconds(1));
    }
    
}
