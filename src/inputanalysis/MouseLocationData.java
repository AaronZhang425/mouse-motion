package inputanalysis;

/**
 * Represents the location of a single mouse relative to the origin
 */
public class MouseLocationData {
    /**
     * First element is x and second element is y. The front of the mass is 
     * the positive y direction and the right of the mass is the positive x
     * direction.
     */
    private double[] position;

    /**
     * Angle of mouse from front of the mass with the counter clockwise 
     * direction being positive.
     */
    private double angle;

    public MouseLocationData(
        double[] position,
        double angle
    ) throws IllegalArgumentException {
        setPosition(position);
        this.angle = angle;

    }

    public double[] getPosition() {
        return new double[]{position[0], position[1]};

    }

    public void setPosition(double[] position) {
        if (position.length != 2) {
            throw new IllegalArgumentException(
                "Position array must be of length 2"
            );

        }

        this.position = new double[]{position[0], position[1]};

    }

    public double getRadius() {
        return Math.sqrt(position[0] * position[0] + position[1] * position[1]);

    }
    
    public double getAngle() {
        return angle;

    }

    public void setAngle(double angle) {
        this.angle = angle;

    }



}
