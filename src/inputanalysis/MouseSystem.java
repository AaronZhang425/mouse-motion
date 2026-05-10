package inputanalysis;


import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.HashMap;

import devicemanagement.Mouse;
import eventclassification.eventcodes.Rel;
import inputanalysis.singletracker.MouseMotionTracker;

// TODO: Handle displacement in a rotating system
public class MouseSystem {
    /**
     * Represents the arangement of mice being tracked as a map with the key
     * being the tracker and the value being the distance from the center
     * of the system as components of a vector 
     */
    private HashMap<MouseMotionTracker, MouseLocationData> mouseTrackerArrangement;

    /**
     * Represents the angle of the system from the starting position with 
     * counterclockwise being the positive direction and clockwise being the 
     * negative direction in radians. 
     */
    private double angle = 0;

    private boolean run = true;

    /**
     * Creates a MouseSystem given a hashmap representing the arrangment of 
     * mice and stores a hashmap with mouse trackers and positions relative to
     * the center of rotation. Starts every mouse tracker immediately upon 
     * object creation.
     * 
     * @param rawMouseArrangement
     * @throws UncheckedIOException
     */
    public MouseSystem(
        HashMap<Mouse, MouseLocationData> rawMouseArrangement
    ) throws UncheckedIOException {
        mouseTrackerArrangement = new HashMap<>();
        
        // For each entry within the raw mouse arrangement
        rawMouseArrangement.forEach(
            (Mouse mouse, MouseLocationData positions) -> {
                // Try to create a tracker for each mouse and place it the 
                // mouse tracker map with its respective position
                try {
                    MouseMotionTracker tracker = new MouseMotionTracker(mouse);
                    mouseTrackerArrangement.put(tracker, positions);
                    
                } catch (FileNotFoundException e) {
                    // If file not found, throw rethrow as unchecked 
                    throw new UncheckedIOException(
                        new FileNotFoundException(e.getMessage())
                    );

                }

            }

        );

    }

    /**
     * Get a copy of mouse arrangement hashmap
     * 
     * @return Copy of hashmap mapping mouse trackers to positions
     */
    public HashMap<MouseMotionTracker, MouseLocationData> getMouseArrangement() {
        return new HashMap<>(mouseTrackerArrangement);
        
    }

    /**
     * Gets the heading of the mouse system. Counter-clockwise rotation is 
     * represented by a positive heading. Clockwise rotation is represented by
     * negative heading.
     * 
     * @return The angle from its original position to the current position
     */
    public double getAngle() {
        return angle;
    
    }

    /**
     * Prepares all the tracker threads to stop.
     */
    public void terminate() {
        for (MouseMotionTracker tracker : mouseTrackerArrangement.keySet()) {
            tracker.terminate();

        }

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
            Math.abs(mouse1Velocity[axisIdx] + mouse2Velocity[axisIdx]) 
            <= tolerance
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

        if (mouse1Velocity[axisIdx] + mouse2Velocity[axisIdx] == 0) {
            return false;

        }

        // Return if the velocities are going in opposite directions
        return (mouse1Velocity[axisIdx] * mouse2Velocity[axisIdx]) <= 0;

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
    
    public MouseLocationData getMouseLocationData(MouseMotionTracker mouse) {
        return mouseTrackerArrangement.get(mouse);

    }

    public void run() {
        // Still need to figure out formula to determine position after both
        // rotational and translational movment of mouse
        while (run) {
            if (isGoingOppositeDirection(null, null, null)) {
                System.out.println("turning");
            }

        }

        terminate();

    }

}
