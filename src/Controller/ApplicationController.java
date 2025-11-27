package Controller;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class ApplicationController {

    private int pathCount;
    private Path path = new Path();
    private boolean firstPoint = true;
    private double lastX = -1;
    private List<PauseTransition> countdowns = new ArrayList<>();

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
    @FXML
    private Label errorLabel;

    public void initialize() {
        path.setStroke(Color.BLACK);
        path.setStrokeWidth(3);
        path.setFill(null);
        application.getChildren().add(path);
        double startX = 50;
        double startY = 100;
        path.getElements().add(new MoveTo(50, 100));
        Circle startDot = new Circle(50, 100, 3, Color.BLACK);
        application.getChildren().add(startDot);
        lastX = 50;

        lineButton.setOnAction(e -> {
            if (pathCount >= 7) {
                lineButton.setDisable(true);
                arcButton.setDisable(true);
                return;
            }
            lineButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            arcButton.setDisable(true);

            application.setOnMouseClicked(f -> {
                if (f.getX() <= lastX) {
                    return;
                }
                pathCount++;
                path.getElements().add(new LineTo(f.getX(), f.getY()));
                lastX = f.getX();
                application.setOnMouseClicked(null);
                lineButton.setStyle("-fx-background-color: lime; -fx-text-fill: white;");
                arcButton.setDisable(false);
            });
        });

        arcButton.setOnAction(e -> {
            if (pathCount >= 7) {
                lineButton.setDisable(true);
                arcButton.setDisable(true);
                return;
            }

            arcButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            lineButton.setDisable(true);
            application.setOnMouseClicked(f -> {
                if (f.getX() <= lastX) {
                    return;
                }
                pathCount++;
                ArcTo arc = new ArcTo();
                arc.setX(f.getX());
                arc.setY(f.getY());
                arc.setRadiusX(150);
                arc.setRadiusY(150);
                arc.setSweepFlag(true);
                path.getElements().add(arc);
                lastX = f.getX();
                application.setOnMouseClicked(null);
                arcButton.setStyle("-fx-background-color: lime; -fx-text-fill: white;");
                lineButton.setDisable(false);
            });
        });

        start.setOnAction(e -> {
            if (pathCount != 7) {
                errorLabel.setText("Path not complete!");
                errorLabel.setOpacity(1);
                PauseTransition pt1 = new PauseTransition(Duration.seconds(1));
                pt1.play();
                pt1.setOnFinished(f -> {
                    errorLabel.setOpacity(0);
                });
                return;
            }
            countdown.setOpacity(1);
            PauseTransition pt1 = new PauseTransition(Duration.seconds(1));
            PauseTransition pt2 = new PauseTransition(Duration.seconds(1));
            PauseTransition pt3 = new PauseTransition(Duration.seconds(1));
            PauseTransition pt4 = new PauseTransition(Duration.millis(500));
            countdowns.add(pt1);
            countdowns.add(pt2);
            countdowns.add(pt3);
            countdowns.add(pt4);
            pt1.playFromStart();
            pt1.setOnFinished(f -> {
                countdown.setText("2");
                pt2.play();
            });
            pt2.setOnFinished(f -> {
                countdown.setText("1");
                pt3.play();
            });
            pt3.setOnFinished(f -> {
                countdown.setText("GO!");
                pt4.play();
            });
            pt4.setOnFinished(f -> {
                countdown.setText("");
            });
            if (start.getText().equals("Start")) {
                start.setText("Stop");
                reset.setDisable(true);
            } else {
                for (PauseTransition cd : countdowns) {
                    cd.pause();
                }
                start.setText("Start");
                reset.setDisable(false);
            }
        });

        reset.setOnAction(e -> {
            path.getElements().clear();
            application.getChildren().removeIf(n -> n instanceof Circle);
            pathCount = 0;
            path.getElements().add(new MoveTo(startX, startY));
            Circle newStart = new Circle(startX, startY, 3, Color.BLACK);
            application.getChildren().add(newStart);
            lastX = startX;
            lineButton.setDisable(false);
            arcButton.setDisable(false);
            countdown.setOpacity(0);
        });

    }
    
}
