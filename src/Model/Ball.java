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

    public double findHeight() {
        return finalCoordinate.get(1) - 100;
    }
    
    public double findEnergy() {
        return findHeight() * 9.8 * mass;
    }

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
