package inputanalysis;

import devicemanagement.EventData;
import devicemanagement.Mouse;
import eventclassification.eventcodes.Rel;

public class MouseCountsTracker extends InputEventConsumer {
    /**
     * Represents total mouse counts that have been counted
     */
    private volatile int totalMouseCounts = 0;

    private volatile double velocity = 0.0;
    
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

    public int getTotalMouseCounts() {
        return totalMouseCounts;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getDisplacement() {
        return mouseCountsToMeters(totalMouseCounts);
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
        if (!inputEvent.getEventCode().equals(eventCode)) {
            throw new IllegalArgumentException(
                "EventData argument eventCode must match that of the tracker"
            );

        }

        int relativeMouseCounts = inputEvent.getValue();
        double displacement = mouseCountsToMeters(relativeMouseCounts);

        totalMouseCounts += relativeMouseCounts;

        velocity = (
            displacement /
            inputEvent.getTotalSeconds()
        );

    }

}
