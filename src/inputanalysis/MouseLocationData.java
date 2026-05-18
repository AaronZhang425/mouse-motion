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
     * Angle of mouse from front of the system with the counterclockwise 
     * direction being positive. The mount angle does not represent the angle
     * from the mouse from the origin but the angle between the direction the 
     * mouse front and system front.
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

    /**
     * Get the position of the mouse relative to the point of system rotation.
     * The positive x direction is right wards and the positive y direction is 
     * fowards.
     * 
     * @return Position array of the mouse in the format {x, y}
     */
    public double[] getPosition() {
        return new double[]{POSITION[0], POSITION[1]};

    }

    /**
     * Get the distance of the mouse from the center of rotation. 
     * 
     * @return The distance of the mouse from the point of rotation
     */
    public double getRadius() {
        return Math.hypot(POSITION[0], POSITION[1]);

    }
    
    /**
     * Get the angle between the front of the mouse and the front of the 
     * system. The angle is 0 when both the mouse and the entire system is
     * pointing in the same the direction. The angle is positive in 
     * counterclockwise direction.
     * 
     * @return Angle between mouse front and system front in radians
     */
    public double getMountAngle() {
        return MOUNT_ANGLE;

    }

    /**
     * Get the angle of the mouse from the center with the positive direction
     * in the counterclockwise direction in radians.
     * 
     * @return Angle from the right of the center to the mouse.
     */
    public double getAngleFromCenter() {
        return Math.atan2(POSITION[1], POSITION[0]);

    }

}
