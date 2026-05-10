package inputanalysis.singletracker;

import java.util.function.Function;

import devicemanagement.EventData;
import devicemanagement.Mouse;
import eventclassification.eventcodes.Rel;

public class MouseCountsTracker extends InputEventConsumer {
    /**
     * Represents total mouse counts that have been counted
     */
    private volatile int lifetimeCounts = 0;

    /**
     * Represents the total displacement in the axis the object was assigned to
     */
    private volatile double displacement = 0;

    /**
     * Represents the velocity of the mouse. Once the mouse moves, velocity
     * will never return to 0. At best, the velocity will approach 0
     */
    private volatile double velocity = 0.0;

    /**
     * Represents the number of mouse counts reported in the latest read
     */
    private volatile int lastCountReading = 0;
    
    /**
     * Represents a the transformation of the displacement. Used for adjusting
     * axis when turning
     */
    private volatile Function<Double, Double> transformationFunction = (
        (Double num) -> num
    );

    /**
     * Represents the mouse being tracked
     */
    private Mouse mouse;

    public MouseCountsTracker(Mouse mouse, Rel eventCode) {
        super(eventCode);

        if (!eventCode.equals(Rel.REL_X) && !eventCode.equals(Rel.REL_Y)) {
            throw new IllegalArgumentException(
                "Event code must either Rel.REL_X or Rel.REL_Y"
            );

        }

        this.mouse = mouse;

    }

    /**
     * Sets the transformation function
     * 
     * @param transformationFunction Function that transform the displacement
     */
    public void setTransformationFunction(
        Function<Double, Double> transformationFunction
    ) {
        this.transformationFunction = transformationFunction;
    }

    /**
     * Get the number of counts recorded since tracking began
     * 
     * @return Total counts since tracking began
     */
    public int getLifetimeCounts() {
        return lifetimeCounts;
    }

    /**
     * Gets the velocity of the latest record. Will never be 0 once the moouse
     * recieves reports
     * 
     * @return Non-zero velocity
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Gets the displacement of the mouse and accounts for transformations
     * such as those applied to account for rotation
     * 
     * @return Displacement from original position. Accounts for transformations
     */
    public double getDisplacement() {
        return displacement;
    }

    /**
     * Converts the last counts reading from the mouse into displacement
     * 
     * @return The displacement from only the latest reading.
     */
    public double lastDisplacementReading() {
        return mouseCountsToMeters(lastCountReading);
    }

    private double mouseCountsToMeters(int mouseCounts) {
        return (double) mouseCounts / mouse.getDpi() * 0.0254;
    }

    /**
     * Defines how an InputEventConsumer should handle input events
     * 
     * @throws IllegalArgumentException Thrown if event event code mismatch
     */
    @Override
    public void consume(EventData inputEvent) throws IllegalArgumentException {
        // If the event codes do not match, throw error
        if (!inputEvent.getEventCode().equals(eventCode)) {
            throw new IllegalArgumentException(
                "EventData argument eventCode must match that of the tracker"
            );

        }

        lastCountReading = inputEvent.getValue();
        
        displacement += transformationFunction.apply(
            mouseCountsToMeters(lastCountReading)
        );

        lifetimeCounts += lastCountReading;

        velocity = (
            displacement /
            inputEvent.getTotalSeconds()
        );

    }

}
