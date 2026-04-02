package inputanalysis;

import java.util.HashMap;

import eventclassification.eventcodes.Rel;

public class MouseSystem {
    private HashMap<MouseMotionTracker, double[]> mouseArrangement;

    public MouseSystem(HashMap<MouseMotionTracker, double[]> mouseArrangement) {
        this.mouseArrangement = (
            new HashMap<MouseMotionTracker, double[]>(mouseArrangement)
        );

    }

    public HashMap<MouseMotionTracker, double[]> getMouseArrangement() {
        return new HashMap<>(mouseArrangement);
        
    }

    /**
     * Determines if the given mice are going opposite directions. Such a case
     * can occur if either the mice are traveling in opposite directions 
     * linearly or if the mice are both rotating about a point
     * 
     * @param mouse1 A mouse tracker
     * @param mouse2 A mouse tracker for a different mouse
     * @return True if the mice are going in oppsite directions
     */
    public boolean isGoingOppositeDirection(
        MouseMotionTracker mouse1,
        MouseMotionTracker mouse2,
        double tolerance,
        Rel axis
    ) throws IllegalArgumentException {
        // Only axis to be checked is x and y direction
        if (!axis.equals(Rel.REL_X) && !axis.equals(Rel.REL_Y)) {
            throw new IllegalArgumentException(
                "Axis must be Rel.REL_X or Rel.REL_Y"
            );

        }

        // The value of the event code is equivalent to the index in the 
        // velocity array
        int axisIdx = axis.getValue();

        double[] mouse1Velocity = mouse1.getVelocity();
        double[] mouse2Velocity = mouse2.getVelocity();

        return (
            Math.abs(mouse1Velocity[axisIdx] + mouse2Velocity[axisIdx]) <=
            tolerance
        );

    }

    public boolean isGoingOppositeDirection(
        MouseMotionTracker mouse1,
        MouseMotionTracker mouse2,
        Rel axis
    ) throws IllegalArgumentException {
        // If the axis is not x or y, throw error
        if (!axis.equals(Rel.REL_X) && !axis.equals(Rel.REL_Y)) {
            throw new IllegalArgumentException(
                "Axis must be Rel.REL_X or Rel.REL_Y"
            );

        }

        // The numeric axis of the event code is the same as the index of
        // the vector that should be used in calculations
        int axisIdx = axis.getValue();

        // Represents velocity vector of mouse 1
        double[] mouse1Velocity = mouse1.getVelocity();

        // Represents velocity vector of mouse 2
        double[] mouse2Velocity = mouse2.getVelocity();

        // Return if the velocities are going in opposite directions
        return (mouse1Velocity[axisIdx] * mouse2Velocity[axisIdx]) < 0;

    }   

    public double getVelocityDotProduct(
        MouseMotionTracker mouse1,
        MouseMotionTracker mouse2
    ) {
        return getDotProduct2D(mouse1.getVelocity(), mouse2.getVelocity());

    }

    /**
     * Gets the dot product of the total displacement of the mice
     * 
     * @param mouse1 A mouse tracker
     * @param mouse2 A mouse tracker representing another mouse
     * @return Dot product of the total displacement of the mice
     */
    public double getTotalDisplacementDotProduct(
        MouseMotionTracker mouse1,
        MouseMotionTracker mouse2
    ) {
        return getDotProduct2D(
            mouse1.getDisplacement(),
            mouse2.getDisplacement()
        );

    }

    /**
     * Gets the dot product of 2 vectors that are 2D
     * 
     * @param vector1 A 2 element array representing a vector
     * @param vector2 A 2 element array representing a vector
     * @return The dot product of the two inputted vectors
     */
    private double getDotProduct2D(double[] vector1, double[] vector2) {
        return vector1[0] * vector2[0] + vector1[1] + vector2[1];

    }

}
