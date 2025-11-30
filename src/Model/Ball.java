package Model;

import javafx.animation.Timeline;

public class Ball {
    private double projectileX, projectileY;
    private double velocityX, velocityY;
    private final double g = 9.8; // gravity in pixels/sec^2
    private Timeline projectileTimeline;
    private double mass;

    public Ball(double mass) {
        this.mass = mass;
    }
    
    /**
     * Calculates the Gravitational Potential Energy (Ug = mgh)
     * Since there is no external forces, this also equals to final Energy,
     * being Kinetic Energy (Ek = 1/2 mv^2)
     * @param height
     * @return Initial Energy of the system, in J
     */
    public double energy(double height) {
        return mass * g * height;
    }
    
    /**
     * Finds the velocity at point B, with the equation mgh = 1/2 mv^2
     * which equates to sqrt(2gh)
     * @param height Used in mgh
     * @return velocity (m/s)
     */
    public double findVelocity(double height) {
        return Math.sqrt(2 * 9.8 * height);
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public String toString() {
        return "Ball (" + mass + " kg)";
    }
    
}
