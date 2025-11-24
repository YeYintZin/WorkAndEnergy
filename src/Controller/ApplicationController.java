package Controller;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class ApplicationController {

    private int pathCount;
    private final List<Shape> createdShapes = new ArrayList<>();

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
            if (createdShapes.size() >= 7) {
                lineButton.setDisable(true);
                arcButton.setDisable(true);
                return;
            }
            lineButton.setStyle("-fx-background-color: red;" + "-fx-text-fill: white;");
            arcButton.setDisable(true);
            application.setOnMouseClicked(f -> {
                pathCount++;
                Line line = new Line(f.getX(), f.getY(), f.getX() + 100, f.getY() + 100);
                line.setStroke(Color.BLACK);
                line.setStrokeWidth(3);
                createdShapes.add(line);
                application.getChildren().add(line);
                application.setOnMouseClicked(null);
                lineButton.setStyle("-fx-background-color: lime;" + "-fx-text-fill: white;");
                arcButton.setDisable(false);
            });
        });
        arcButton.setOnAction(e -> {
            if (createdShapes.size() >= 7) {
                lineButton.setDisable(true);
                arcButton.setDisable(true);
                return;
            }
            arcButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            lineButton.setDisable(true);
            application.setOnMouseClicked(f -> {
                pathCount++;
                Arc arc = new Arc();
                arc.setCenterX(f.getX() + 50);
                arc.setCenterY(f.getY());
                arc.setRadiusX(50);
                arc.setRadiusY(75);
                arc.setStartAngle(180);
                arc.setLength(90);
                arc.setType(ArcType.OPEN);
                arc.setStroke(Color.BLACK);
                arc.setStrokeWidth(3);
                arc.setFill(Color.TRANSPARENT);
                createdShapes.add(arc);
                application.getChildren().add(arc);
                application.setOnMouseClicked(null);
                arcButton.setStyle("-fx-background-color: lime; -fx-text-fill: white;");
                lineButton.setDisable(false);
            });
        });
        start.setOnAction(e -> {
            countdown.setOpacity(1);
            PauseTransition pt = new PauseTransition(Duration.seconds(1));
            if (start.getText().equals("Start")) {
                start.setText("Stop");
                reset.setDisable(true);
            } else {
                start.setText("Start");
                reset.setDisable(false);
            }

        });
        reset.setOnAction(e -> {
            application.getChildren().removeAll(createdShapes);
            createdShapes.clear();
            pathCount = 0;
            lineButton.setDisable(false);
            arcButton.setDisable(false);
        });
    }

}
