package inputanalysis;


import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;

import eventclassification.eventcodes.Rel;
import inputanalysis.singletracker.MouseMotionTracker;

// TODO: Handle displacement in a rotating system
public class MouseSystem {
    private final HashMap<SystemComponent, MouseMotionTracker> TRACKERS;

    /**
     * Represents the pairs of components in the system. Used to calculate 
     * cross products
     */
    private final SystemComponent[][] COMPONENT_PAIRS;

    /**
     * Represents the angle of the system from the starting position with 
     * counterclockwise being the positive direction and clockwise being the 
     * negative direction in radians. 
     */
    private double angle = 0;

    private boolean run = true;

    public MouseSystem(
        SystemComponent[] components
    ) throws FileNotFoundException {
        TRACKERS = new HashMap<>();
        int length = components.length;

        int pairs = (int) (length / 2 + 0.5);

        COMPONENT_PAIRS = new SystemComponent[pairs][2];
    
        for (int i = 0; i < components.length - 1; i += 2) {
            int pairIdx = i / 2;

            SystemComponent component1 = components[i];
            SystemComponent component2 = components[(i + 1) % pairs];

            COMPONENT_PAIRS[pairIdx][0] = component1; 
            COMPONENT_PAIRS[pairIdx][1] = component2;

            TRACKERS.put(
                component1,
                new MouseMotionTracker(component1.getMouse())
            );
            
            TRACKERS.put(
                component2,
                new MouseMotionTracker(component2.getMouse())
            );

        }

    }

    public MouseSystem(
        SystemComponent[][] componentPairs
    ) throws FileNotFoundException {
        TRACKERS = new HashMap<>();

        int numPairs = componentPairs.length;
        COMPONENT_PAIRS = new SystemComponent[numPairs][2];
        
        for (int pair = 0; pair < numPairs; pair++) {
            SystemComponent component1 = componentPairs[pair][0];
            SystemComponent component2 = componentPairs[pair][1];

            COMPONENT_PAIRS[pair][0] = component1;
            COMPONENT_PAIRS[pair][1] = component2;

            TRACKERS.put(
                component1,
                new MouseMotionTracker(component1.getMouse())
            );
            
            TRACKERS.put(
                component2,
                new MouseMotionTracker(component2.getMouse())
            );
            
        }

    }

    /**
     * Get a copy of mouse arrangement hashmap
     * 
     * @return Copy of hashmap mapping mouse trackers to positions
     */
    public HashMap<SystemComponent, MouseMotionTracker> getTrackerArrangement() {
        return new HashMap<>(TRACKERS);
        
    }

    public SystemComponent[][] getComponentPairs() {
        SystemComponent[][] pairsCopy = (
            new SystemComponent[COMPONENT_PAIRS.length][2]
        );

        for (int pair = 0; pair < COMPONENT_PAIRS.length; pair++) {
            pairsCopy[pair][0] = COMPONENT_PAIRS[pair][0];
            pairsCopy[pair][1] = COMPONENT_PAIRS[pair][1];
        }

        return pairsCopy;

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
        for (
            Entry<SystemComponent, MouseMotionTracker> pair 
            : TRACKERS.entrySet()
        ) {
            pair.getValue().terminate();

        }

    }

    public double displacementCrossProduct() {
        throw new UnsupportedOperationException("Unimplented");
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
    @Deprecated
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

    @Deprecated
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

    @Deprecated
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
    @Deprecated
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
    @Deprecated
    private double getDotProduct2D(double[] vector1, double[] vector2) {
        return vector1[0] * vector2[0] + vector1[1] + vector2[1];

    }

    public void run() {
        // Still need to figure out formula to determine position after both
        // rotational and translational movment of mouse
        while (run) {
            System.out.println("Running");

        }

        terminate();

    }

}
