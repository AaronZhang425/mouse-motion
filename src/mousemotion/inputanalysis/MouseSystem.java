package mousemotion.inputanalysis;

import inputmangement.eventclassification.eventcodes.Rel;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;
import mousemotion.inputanalysis.singletracker.MouseMotionTracker;

// TODO: THIS CLASS HAS NOT BEEN TESTED BECAUSE I DO NOT HAVE THE HARDWARE
public class MouseSystem implements Runnable{
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
    private volatile double angle = 0;

    private volatile boolean run = true;

    public MouseSystem(
        SystemComponent[][] componentPairs
    ) throws FileNotFoundException, IllegalArgumentException {
        TRACKERS = new HashMap<>();

        // Create a copy of the array
        int numPairs = componentPairs.length;
        COMPONENT_PAIRS = new SystemComponent[numPairs][2];
        
        // Copy the component pairings
        for (int pair = 0; pair < numPairs; pair++) {
            // If at any point the inner arrray is of length 2 and does not
            // represent a pair, throw an error
            if (componentPairs[pair].length != 2) {
                throw new IllegalArgumentException(
                    "The inner arrays must be of length 2, representing a pair"
                );
            }

            // Copy the components
            SystemComponent component1 = componentPairs[pair][0];
            SystemComponent component2 = componentPairs[pair][1];

            COMPONENT_PAIRS[pair][0] = component1;
            COMPONENT_PAIRS[pair][1] = component2;

            // Register the componenet and map it to a tracker
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
    public HashMap<SystemComponent, MouseMotionTracker> getTrackers() {
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
    public void terminate() throws InterruptedException {
        for (
            Entry<SystemComponent, MouseMotionTracker> pair 
            : TRACKERS.entrySet()
        ) {
            pair.getValue().terminate();

        }

    }


    public double[] getCrossProduct2D(double[] vector1, double[] vector2) {
        if (vector1.length != 2 || vector2.length != 2) {
            throw new IllegalArgumentException(
                "Both vectors must be of length 2"
            );
        
        }

        return new double[]{
            0,
            0,
            vector1[0] * vector2[1] - vector1[1] * vector2[0]
        };


    }

    /**
     * Get the cross product for any 2D or 3D vector pair
     * 
     * @param vector1 An array of length 2 or 3
     * @param vector2 An array of length 2 or 3
     * @return Cross product
     * @throws IllegalArgumentException If vectors are not 2D or 3D
     */
    public double[] crossProdut(
        double[] vector1,
        double[] vector2
    ) throws IllegalArgumentException {
        if (
            (vector1.length != 2 && vector1.length != 3)
            || (vector2.length != 2 && vector2.length != 3)
        ) {
            throw new IllegalArgumentException(
                "Vectors must be of length 2 or 3"
            );

        }

        if (vector1.length == 2) {
            vector1 = new double[]{vector1[0], vector1[1], 0};

        }
        
        if (vector2.length == 2) {
            vector2 = new double[]{vector2[0], vector2[1], 0};

        }

        // Calculate the cross product
        return new double[]{
            vector1[1] * vector2[2] - vector1[2] * vector2[1],
            vector1[2] * vector2[0] - vector1[0] - vector2[2],
            vector1[0] * vector2[1] - vector1[1] * vector2[0]

        };


    }

    @Deprecated
    public double[] displacementDeltaCrossProduct(
        SystemComponent[] components
    ) throws IllegalArgumentException {
        if (components.length != 2) {
            throw new IllegalArgumentException(
                "components must be of length 2"
            );
        }

        // Get vector 1
        double[] displacementDelta1 = getDisplacementDeltaRotated(
            components[0]
        );

        // Get vector 2
        double[] displacementDelta2 = getDisplacementDeltaRotated(
            components[1]
        );

        // Calculate the cross product. Because the 2 vectors are in 2D space,
        // the x and y components are always 0. The z can be anything.
        return new double[]{
            0,
            0,
            (
                displacementDelta1[0] * displacementDelta2[1] 
                - displacementDelta1[1] * displacementDelta2[0]
            )
        };

    }

    public double[] getDisplacementDeltaRotated(SystemComponent component) {
        MouseMotionTracker tracker = TRACKERS.get(component);

        double[] displacementDelta = tracker.getDisplacementDelta();

        // Angle from y axis, not x axis
        // Represents angle between heading of mouse and system
        double mountAngle = component.getLocationData().getMountAngle();

        double changeBasesX = (
            displacementDelta[0] * Math.sin(mountAngle)
            + displacementDelta[1] * Math.sin(mountAngle)
        );

        double changeBasesY = (
            displacementDelta[0] * Math.cos(mountAngle)
            + displacementDelta[1] * Math.cos(mountAngle)
        ); 

        return new double[]{changeBasesX, changeBasesY};

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
    private double getDotProduct2D(double[] vector1, double[] vector2) {
        if (vector1.length != 2 || vector2.length != 2) {
            throw new IllegalArgumentException(
                "Vectors must be both of length 2"
            );
        }

        return vector1[0] * vector2[0] + vector1[1] + vector2[1];

    }

    @Override
    public void run() {
        // Still need to figure out formula to determine position after both
        // rotational and translational movment of mouse
        while (run) {
            System.out.println("Running");

        }

        try {
            terminate();
            
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        }

    }

}
