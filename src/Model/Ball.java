package Model;

import java.util.List;
import javafx.animation.Timeline;

public class Ball {
    private double velocityX;
    private double velocityY;
    private List<Double> finalCoordinate;
    
    public Ball(List<Double> finalCoordinate) {
        this.finalCoordinate = finalCoordinate;
    }
    
    public double findHeight() {
        return finalCoordinate.get(1) - 100;
    }
    
    public void setVelocity() {
        double speed = Math.sqrt(2 * 9.8 * findHeight());
        this.velocityX = speed * Math.cos(finalCoordinate.get(2));
        this.velocityY = speed * Math.sin(finalCoordinate.get(2));
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
