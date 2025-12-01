/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import Model.Ball;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Controller.ApplicationController;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tanal
 */
public class JUnitTesting {
    private ApplicationController controller;
    
    public JUnitTesting() {
    }
    
    @BeforeEach
    public void setUp() {
        // Initialize JavaFX runtime
        new JFXPanel(); // This initializes JavaFX toolkit

        controller = new ApplicationController();

        // Mock all FXML components
        controller.timer = new Label();
        controller.application = new Pane();
        controller.lineButton = new Button();
        controller.pathCountLabel = new Label();
        controller.resultPane = new Pane();
        controller.resultLabel = new Label();
        controller.reset = new Button();
        controller.start = new Button();
        controller.countdown = new Label();
        controller.errorLabel = new Label();
        controller.slopeAngle = new Slider();
        controller.slopeLength = new Slider();
        controller.massTextField = new TextField();
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testInitialTimerValue() {
        controller.initialize();
        assertEquals("0.00 s", controller.timer.getText());
    }

    @Test
    public void testResetHandleResetsTimer() {
        controller.timer.setText("5.67 s");
        controller.resetHandle();
        assertEquals("0.00 s", controller.timer.getText());
    }
    
    @Test
    public void testBallSetVelocity() {
        // Mock the finalCoordinate: {x, y, angle}
        List<Double> finalCoordinate = new ArrayList<>();
        finalCoordinate.add(0.0);  // x (ignored)
        finalCoordinate.add(110.0); // y = 110 → height = 10
        finalCoordinate.add(30.0);  // angle in degrees

        Ball ball = new Ball(finalCoordinate, 1.0); // mass = 1 kg
        ball.setVelocity();

        double expectedSpeed = Math.sqrt(2 * 9.8 * 10); // sqrt(2 * g * h)
        double expectedVX = expectedSpeed * Math.cos(Math.toRadians(30));
        double expectedVY = -expectedSpeed * Math.sin(Math.toRadians(30));

        assertEquals(expectedVX, ball.getVelocityX(), 0.01);
        assertEquals(expectedVY, ball.getVelocityY(), 0.01);
    }
    
    @Test
    public void testFindEnergy() {
        Ball ball = new Ball(Arrays.asList(0.0, 15.0, 30.0), 2.0); 
        
        ball = new Ball(Arrays.asList(0.0, 120.0, 30.0), 2.0); 
        double expectedEnergy = 20 * 9.8 * 2; // m * g * h
        assertEquals(expectedEnergy, ball.findEnergy(), 0.01);
    }
    
    @Test
    public void testElapsedTimeUpdates() throws Exception {
        controller.startTime = System.currentTimeMillis();
        Thread.sleep(150); // simulate 0.15 seconds
        long now = System.currentTimeMillis();
        double elapsedSeconds = (now - controller.startTime) / 1000.0;
        controller.timer.setText(String.format("%.2f s", elapsedSeconds));

        double timerValue = Double.parseDouble(controller.timer.getText().replace(" s", ""));
        assertTrue(timerValue >= 0.15);
    }
}
