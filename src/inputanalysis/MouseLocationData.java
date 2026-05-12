package inputanalysis;

/**
 * Represents the location of a single mouse relative to the origin in a mouse
 * system. The front of the system is the positive y direction and the right
 * of the system is the positive x direction. Counterclockwise is the positive
 * direction. Angle 0 is when the front of the mouse faces the same direction as 
 * the front of the system.
 */
public class MouseLocationData {
    /**
     * First element is x and second element is y. The front of the mass is 
     * the positive y direction and the right of the mass is the positive x
     * direction.
     */
    private final double[] POSITION;

    /**
     * Angle of mouse from front of the mass with the counter clockwise 
     * direction being positive.
     */
    private final double ANGLE;

    public MouseLocationData(
        double[] position,
        double angle
    ) throws IllegalArgumentException {
        if (position.length != 2) {
            throw new IllegalArgumentException(
                "Position array must be of length 2"
            );

        }

        // Copy the array
        this.POSITION = new double[]{position[0], position[1]};

        this.ANGLE = angle;

    }

    public double[] getPosition() {
        return new double[]{POSITION[0], POSITION[1]};

    }

    public double getRadius() {
        return Math.hypot(POSITION[0], POSITION[1]);

    }
    
    public double getAngle() {
        return ANGLE;

    }

}
