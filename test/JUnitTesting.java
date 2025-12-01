/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import Model.Ball;
import java.util.ArrayList;
import java.util.List;
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
    
    public JUnitTesting() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
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
}
