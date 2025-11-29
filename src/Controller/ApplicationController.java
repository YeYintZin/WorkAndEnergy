package Controller;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class ApplicationController {

    private int pathCount;
    private Path path = new Path();
    private Path invispath = new Path();
    private double startX = 50;
    private double startY = 100;
    private double lastX = -1;
    private double lastY = -1;
    private List<PauseTransition> countdowns = new ArrayList<>();

    @FXML
    private Pane application;
    @FXML
    private Button lineButton;
    @FXML
    private Label pathCountLabel;
    @FXML
    private Button reset;
    @FXML
    private Button start;
    @FXML
    private Label countdown;
    @FXML
    private Label errorLabel;

    public void initialize() {
        initializePath();
        // Keeping count of the path
        application.setOnMouseReleased(e -> {
            if (pathCount == 7) {
                pathCountLabel.setText("Path required: " + 0);
                return;
            }
            pathCountLabel.setText("Path required: " + (6 - pathCount));
        });
        // Making the path
        lineButton.setOnAction(e -> {
            extendPath();
        });
        // Start the simulation
        start.setOnAction(e -> {
            startHandle();
        });
        reset.setOnAction(e -> {
            resetHandle();
        });
    }

    public void initializePath() {
        path.setStroke(Color.BLACK);
        path.setStrokeWidth(3);
        path.setFill(null);
        application.getChildren().add(path);
        path.getElements().add(new MoveTo(50, 100));
        Circle startDot = new Circle(50, 100, 3, Color.BLACK);
        application.getChildren().add(startDot);
        lastX = 50;
        lastY = 100;
        //
        invispath.setStroke(Color.AQUAMARINE);
        invispath.setOpacity(0);
        invispath.setStrokeWidth(3);
        invispath.setFill(null);
        application.getChildren().add(invispath);
        invispath.getElements().add(new MoveTo(50, 80));
        Circle startinvisDot = new Circle(50, 80, 3, null);
        application.getChildren().add(startinvisDot);
    }

    public void extendPath() {
        if (pathCount >= 7) {
            lineButton.setDisable(true);
            return;
        }
        lineButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        application.setOnMouseClicked(f -> {
            if (f.getX() <= lastX) {
                return;
            }
            if (f.getY() <= lastY) {
                return;
            }
            pathCount++;
            path.getElements().add(new LineTo(f.getX(), f.getY()));
            lastX = f.getX();
            lastY = f.getY();
            application.setOnMouseClicked(null);
            lineButton.setStyle("-fx-background-color: lime; -fx-text-fill: white;");
            //
            invispath.getElements().add(new LineTo(f.getX() + 15, f.getY() - 15));
            application.setOnMouseClicked(null);
            lineButton.setStyle("-fx-background-color: lime; -fx-text-fill: white;");
        });
    }

    public void startHandle() {
        // If path isnt complete
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
        // Countdown
        countdown.setText("3");
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
            animationHandle();
        });
        if (start.getText().equals("Start")) {
            start.setText("Stop");
            reset.setDisable(true);
        } else {
            for (PauseTransition cd : countdowns) {
                cd.pause();
            }
            countdown.setOpacity(0);
            start.setText("Start");
            reset.setDisable(false);
        }
    }
    
    public void animationHandle() {
        Circle ball = new Circle();
        ball.setRadius(20);
        ball.setStroke(Color.BLACK);
        ball.setFill(null);
        application.getChildren().add(ball);
        
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.seconds(5));
        pt.setPath(invispath);
        pt.setNode(ball);
        pt.play();
    }

    public void resetHandle() {
        path.getElements().clear();
        application.getChildren().removeIf(n -> n instanceof Circle);
        pathCount = 0;
        path.getElements().add(new MoveTo(startX, startY));
        Circle newStart = new Circle(startX, startY, 3, Color.BLACK);
        application.getChildren().add(newStart);
        lastX = startX;
        lastY = startY;
        lineButton.setDisable(false);
        countdown.setOpacity(0);
        pathCountLabel.setText("Path required: " + (7 - pathCount));
    }

}
