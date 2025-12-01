package Model;

import java.util.List;
import javafx.animation.Timeline;

public class Ball {
    private double velocityX;
    private double velocityY;
    private List<Double> finalCoordinate;
    private double mass;

    public Ball(List<Double> finalCoordinate, double mass) {
        this.finalCoordinate = finalCoordinate;
        this.mass = mass;
    }

    /**
     * get height of the ball position
     * @return the height
     */
    public double findHeight() {
        return finalCoordinate.get(1) - 100;
    }
    
    /**
     * get the energy of the ball with formula mgh
     * @return energy value
     */
    public double findEnergy() {
        return findHeight() * 9.8 * mass;
    }

    /**
    * Calculates and sets the initial velocity of the ball based on the slope height and angle.
    *
    * The speed is determined using the formula for energy conversion:
    *     speed = √(2 * g * h), where g = 9.8 m/s² (gravity) and h = vertical height of the slope.
    *
    * The velocity is then split into horizontal (velocityX) and vertical (velocityY) components:
    *     velocityX = speed * cos(angle)
    *     velocityY = -speed * sin(angle)
    * The negative sign for velocityY is because in JavaFX, Y increases downward.
    */
    public void setVelocity() {
        double speed = Math.sqrt(2 * 9.8 * findHeight());
        double angleRad = Math.toRadians(finalCoordinate.get(2));
        this.velocityX = speed * Math.cos(angleRad);
        this.velocityY = -speed * Math.sin(angleRad);
    }

    public double getVelocityX() {
        setVelocity();
        return velocityX;
    }

    public double getVelocityY() {
        setVelocity();
        return velocityY;
    }
}
