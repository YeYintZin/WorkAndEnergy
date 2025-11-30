package Controller;

import Model.Ball;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class ApplicationController {

    private PathTransition pt;
    private int pathCount;
    private Path path = new Path();
    private Path invispath = new Path();
    private double lastX = -1;
    private double lastY = -1;
    private List<Double> finalCoordinate = new ArrayList<>();
    private List<Circle> container = new ArrayList<>();

    @FXML
    private Pane application;
    @FXML
    private Button lineButton;
    @FXML
    private Label pathCountLabel;
    @FXML
    private Pane resultPane;
    @FXML
    private Label resultLabel;
    @FXML
    private Button reset;
    @FXML
    private Button start;
    @FXML
    private Label countdown;
    @FXML
    private Label errorLabel;
    @FXML
    private Slider slopeAngle;
    @FXML
    private Slider slopeLength;

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
        invispath.getElements().add(new MoveTo(50, 75));
        Circle startinvisDot = new Circle(50, 75, 3, null);
        application.getChildren().add(startinvisDot);
    }

    public void extendPath() {
        if (pathCount >= 7) {
            lineButton.setDisable(true);
            return;
        }
        lineButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        application.setOnMouseClicked(f -> {
            if (f.getX() <= lastX || f.getY() <= lastY) {
                return;
            }
            pathCount++;
            path.getElements().add(new LineTo(f.getX(), f.getY()));
            lastX = f.getX();
            lastY = f.getY();
            lineButton.setStyle("-fx-background-color: lime; -fx-text-fill: white;");
            //
            invispath.getElements().add(new LineTo(f.getX() + 15, f.getY() - 15));
            application.setOnMouseClicked(null);
            lineButton.setStyle("-fx-background-color: lime; -fx-text-fill: white;");
        });
    }

    public void animationHandle() {
        Circle ball = new Circle();
        ball.setRadius(20);
        ball.setStroke(Color.BLACK);
        ball.setFill(null);
        application.getChildren().add(ball);
        container.add(ball);

        pt = new PathTransition();
        pt.setDuration(Duration.seconds(2));
        pt.setPath(invispath);
        pt.setNode(ball);
        pt.play();
        pt.setOnFinished(e -> {
            projectileAnimation(25);
//            showResult();
//            start.setText("Start");
//            reset.setDisable(false);
        });
    }

    public void projectileAnimation(double totalTime) {
        double x0 = finalCoordinate.get(0);
        double y0 = finalCoordinate.get(1);
        Ball ball = new Ball(finalCoordinate);
        double vx = ball.getVelocityX();
        double vy = ball.getVelocityY();
        double g = - 9.8;
        Path curvePath = new Path();
        curvePath.getElements().add(new MoveTo(x0, y0));
        int steps = 200;
        double dt = totalTime / steps;

        for (int i = 1; i <= steps; i++) {
            double t = i * dt;

            double x = x0 + vx * t;
            double y = y0 + vy * t - 0.5 * g * t * t;

            curvePath.getElements().add(new LineTo(x, y));
        }
        pt = new PathTransition();
        pt.setDuration(Duration.seconds(5));
        pt.setPath(curvePath);
        pt.setNode(container.getFirst());
        pt.play();

    }

    public void pauseAnimation() {
        pt.pause();
    }

    public void startHandle() {
        if (start.getText().equals("Pause")) {
            pauseAnimation();
            start.setText("Start");
            return;
        }

        if (pt != null && pt.getStatus() == Animation.Status.PAUSED) {
            pt.play();
            start.setText("Pause");
            reset.setDisable(true);
            return;
        }

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

        addSlopeSegment();

        // Countdown
        countdown.setText("3");
        countdown.setOpacity(1);
        PauseTransition pt1 = new PauseTransition(Duration.seconds(1));
        PauseTransition pt2 = new PauseTransition(Duration.seconds(1));
        PauseTransition pt3 = new PauseTransition(Duration.seconds(1));
        PauseTransition pt4 = new PauseTransition(Duration.millis(500));

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
            start.setText("Pause");
            reset.setDisable(true);
        });
    }

    public void showResult() {
        resultPane.setOpacity(1);
        resultPane.toFront();
    }

    public void resetHandle() {
        start.setText("Start");
        path.getElements().clear();
        invispath.getElements().clear();
        application.getChildren().removeIf(n -> n instanceof Circle);
        pathCount = 0;
        path.getElements().add(new MoveTo(50, 100));
        invispath.getElements().add(new MoveTo(50, 75));
        Circle newStart = new Circle(50, 100, 3, Color.BLACK);
        application.getChildren().add(newStart);
        Circle invisnewStart = new Circle(50, 75, 3, Color.BLACK);
        invisnewStart.setOpacity(0);
        application.getChildren().add(invisnewStart);
        lastX = 50;
        lastY = 100;
        lineButton.setDisable(false);
        countdown.setOpacity(0);
        pathCountLabel.setText("Path required: " + (7 - pathCount));
        resultPane.setOpacity(0);
    }

    private void addSlopeSegment() {
        double angleDeg = slopeAngle.getValue();
        double length = slopeLength.getValue();

        double angleRad = Math.toRadians(angleDeg);
        double dx = length * Math.cos(angleRad);
        double dy = -length * Math.sin(angleRad);

        double startX = lastX;
        double startY = lastY;

        double endX = startX + dx;
        double endY = startY + dy;
        finalCoordinate.add(endX);
        finalCoordinate.add(endY);
        finalCoordinate.add(angleDeg);

        path.getElements().add(new LineTo(endX, endY));
        invispath.getElements().add(new LineTo(endX, endY - 15));

        lastX = endX;
        lastY = endY;
    }

}
