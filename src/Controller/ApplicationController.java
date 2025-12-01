package Controller;

import Model.Ball;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
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
    private boolean slopeAdded = false;
    private Timeline timeline;
    private long startTime;

    @FXML
    private Label timer;
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
    @FXML
    private TextField massTextField;

    /**
     * Initializes the controller state and sets up validation for the mass input.
     */
    public void initialize() {
        initializePath();
        application.setOnMouseReleased(e -> {
            if (pathCount == 7) {
                pathCountLabel.setText("Path required: " + 0);
                return;
            }
            pathCountLabel.setText("Path required: " + (6 - pathCount));
        });
        
        start.setDisable(true);

        //Mass input listener
        massTextField.textProperty().addListener((obs, oldText, newText) -> {
        if (newText.trim().isEmpty()) {
            start.setDisable(true);
            errorLabel.setText("");
        } else {
            try {
                Double.parseDouble(newText);
                start.setDisable(false);
                errorLabel.setText("");
            } catch (NumberFormatException e) {
                start.setDisable(true);
                errorLabel.setText("Mass must be a number!");
            }
        }
        });
    }

    /**
     * Initializes path for the ball.
     */
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

    /**
     * Void method that extends path from each click.
     */
    public void extendPath() {
        lineButton.setDisable(true);
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
            //
            invispath.getElements().add(new LineTo(f.getX() + 15, f.getY() - 15));
            if (pathCount == 7) {
                application.setOnMouseClicked(null);
                lineButton.setStyle("-fx-background-color: lime; -fx-text-fill: white;");
            }
        });
    }

    /**
     * Handles the path transition of the ball.
     */
    public void animationHandle() {
        if (container.size() > 0) {
            container.clear();
        }
        
        Circle ball = new Circle();
        ball.setRadius(20);
        ball.setStroke(Color.BLACK);
        ball.setFill(Color.WHITE);
        application.getChildren().add(ball);
        container.add(ball);
        
        Ball ball2 = new Ball(finalCoordinate, Double.parseDouble(massTextField.getText()));
        pt = new PathTransition();
        pt.setDuration(Duration.seconds(2));
        pt.setPath(invispath);
        pt.setNode(ball);
        projectileAnimation(25, ball2);
        pt.playFromStart();
        pt.setOnFinished(e -> {
            showResult(ball2);
            start.setText("Start");
            reset.setDisable(false);
        });
    }

    /**
     * 
     * @param totalTime 
     * @param ball the ball object
     */
    public void projectileAnimation(double totalTime, Ball ball) {
        double x0 = finalCoordinate.get(0);
        double y0 = finalCoordinate.get(1);
        double vx = ball.getVelocityX();
        double vy = ball.getVelocityY();
        double g = 9.8;
        Path curvePath = new Path();
        curvePath.getElements().add(new MoveTo(x0, y0));
        int steps = 200;
        double dt = totalTime / steps;

        for (int i = 1; i <= steps; i++) {
            double t = i * dt;
            double x = x0 + vx * t;
            double y = y0 + vy * t + 0.5 * g * t * t;
            invispath.getElements().add(new LineTo(x, y));
        }
        pt = new PathTransition();
        pt.setDuration(Duration.seconds(5));
        pt.setPath(invispath);
        if (!container.isEmpty()) {
            pt.setNode(container.get(0)); 
        }
    }

    /**
     * Pauses animation.
     */
    public void pauseAnimation() {
        pt.pause();
    }
    
    @FXML
    /**
     * Handles start button, when clicked, it starts the program 
     * with the ball falling down the slope into projectile motion.
     */
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

        if (pt != null) {
            pt.stop();
        }
        
        startTime = System.currentTimeMillis();
        timeline = new Timeline(new javafx.animation.KeyFrame(Duration.millis(100),event -> {
                    long now = System.currentTimeMillis();
                    double elapsedSeconds = (now - startTime) / 1000.0;
                    timer.setText(String.format("%.2f s", elapsedSeconds));
                }
            )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

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

        if (!slopeAdded) {
            addSlopeSegment();
            slopeAdded = true;
        }

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
            startTime = System.currentTimeMillis(); // start timer
            animationHandle();
            start.setText("Pause");
            reset.setDisable(true);
        });
    }
    
    /**
     * Shows the result of the velocity x and y.
     * @param ball the object ball to find energy
     */
    public void showResult(Ball ball) {
        long endTime = System.currentTimeMillis();
        long elapsed = (endTime - startTime) / 1000;
        
        if (timeline != null) {
            timeline.stop();
        }
        
        resultPane.setOpacity(1);
        resultPane.toFront();
        resultLabel.setText("Results: \n"
                + "Velocity in x: " + (Math.round(ball.getVelocityX() * 100)) / 100.00 + " pixels/s \n"  
                + "Velocity in y: " + (Math.round(ball.getVelocityY() * 100)) / 100.00 +" pixels/s \n" 
                + "Initial and Final Mechanical energy of the system: " + (Math.round(ball.findEnergy() * 100)) / 100.00  + " J\n"
                + "Elapsed time: " + elapsed + " s");
    }

    @FXML
    /**
     * Handles button reset, resets everything when button is clicked.
     */
    public void resetHandle() {
        if (pt != null) {
            pt.stop();
            pt = null;
        }
        
        start.setText("Start");
        lineButton.setDisable(false);
        
        path.getElements().clear();
        invispath.getElements().clear();
        
        lastX = 50;
        lastY = 100;
        pathCount = 0;
        
        application.getChildren().removeIf(n -> n instanceof Circle);
        container.clear();
        
        path.getElements().add(new MoveTo(50, 100));
        invispath.getElements().add(new MoveTo(50, 75));
       
        Circle newStart = new Circle(50, 100, 3, Color.BLACK);
        application.getChildren().add(newStart);
        
        Circle invisnewStart = new Circle(50, 75, 3, Color.BLACK);
        invisnewStart.setOpacity(0);
        application.getChildren().add(invisnewStart);
        
        countdown.setOpacity(0);
        finalCoordinate.clear();
        pathCountLabel.setText("Path required: " + (7 - pathCount));
        resultPane.setOpacity(0);
        massTextField.clear();
        slopeAngle.setValue(0);
        slopeLength.setValue(0);
        slopeAdded = false;
        timer.setText("0.00 s");
    }

    /**
     * Adds slope based on slope angle and slope length values of the sliders.
     */
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
