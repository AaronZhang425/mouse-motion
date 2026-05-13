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
    private final double MOUNT_ANGLE;

    /**
     * Create a MouseLocationObject that represets the location of a single
     * mouse. The position array must be of length 2. Rightwards is positive x
     * and forwards is positive y. The angle is in the counterclockwise
     * direction. When the mouse and systemm point foward, angle is 0. The
     * angle does not represent the angle from the origin but the angle from
     * the front of the system.
     * 
     * @param position The first element is x and second is y from center
     * @param angle Angle between mouse front and system front
     * @throws IllegalArgumentException Thrown if position is not of length 2
     */
    public MouseLocationData(
        double[] position,
        double angle
    ) throws IllegalArgumentException {
        // position array must be a array of 2 that represents the coordinate
        // from the center of rotation
        if (position.length != 2) {
            throw new IllegalArgumentException(
                "Position array must be of length 2"
            );

        }

        // Copy the array
        POSITION = new double[]{position[0], position[1]};

        MOUNT_ANGLE = angle;

    }

    public double[] getPosition() {
        return new double[]{POSITION[0], POSITION[1]};

    }

    public double getRadius() {
        return Math.hypot(POSITION[0], POSITION[1]);

    }
    
    public double getMountAngle() {
        return MOUNT_ANGLE;

    }

}
