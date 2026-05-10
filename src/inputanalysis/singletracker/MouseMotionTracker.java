package inputanalysis.singletracker;

import java.io.FileNotFoundException;
import java.util.HashMap;

import devicemanagement.*;
import eventclassification.eventcodes.EventCode;
import eventclassification.eventcodes.Rel;

public class MouseMotionTracker {
    /**
     * The mouse being tracked
     */
    private Mouse mouse;

    /**
     * Tracker for x mouse counts.
     */
    private MouseCountsTracker xTracker;

    /**
     * Tracker for y mouse counts
     */
    private MouseCountsTracker yTracker;

    /**
     * System to read mouse events and distribute events that correspond to
     * the mouse trackers to handle
     */
    private EventBroker eventBroker;

    /**
     * The thread that runs the event broker
     */
    private Thread eventBrokerThread;

    /**
     * Creates a tracker for a mouse that tracks x and y movement and converts
     * the mouse counts into physical measurements for displacement in meters
     * and velocity in meters per second.
     * 
     * @param mouse
     * @throws FileNotFoundException
     */
    public MouseMotionTracker(Mouse mouse) throws FileNotFoundException {
        this.mouse = mouse;

        // Create the trackers
        xTracker = new MouseCountsTracker(mouse, Rel.REL_X);
        yTracker = new MouseCountsTracker(mouse, Rel.REL_Y);

        // Map an event code to a tracker that tracks the event code for a 
        // device
        HashMap<EventCode, InputEventConsumer> eventConsumers = new HashMap<>();

        eventConsumers.put(Rel.REL_X, xTracker);
        eventConsumers.put(Rel.REL_Y, yTracker);

        // Read from a device and map its input to a event consumer to handle
        eventBroker = new EventBroker(
            new InputReader(mouse.getDevice().getHandlerFile()),
            eventConsumers
        );

        // Run the EventBroker in a seperate thread
        eventBrokerThread = new Thread(
            eventBroker,
            mouse.getDevice().getName() + " Event Broker"
        );

    }

    /**
     * Gets the mouse that is being tracked
     * 
     * @return A mouse object representing the mouse being tracked
     */
    public Mouse getMouse() {
        return mouse;
    
    }

    /**
     * Gets the event broker of the mouse tracker that governs the distribution
     * of events to handlers
     * 
     * @return Event broker handling mouse data
     */
    public EventBroker getEventBroker() {
        return eventBroker;
        
    }

    /**
     * Gets the displacement of the mouse in meters
     * 
     * @return An array with index 0 representing x component and 1 the y
     */
    public double[] getDisplacement() {
        return new double[]{
            xTracker.getDisplacement(),
            yTracker.getDisplacement()
        };
    
    }

    /**
     * Gets the total mouse counts as componenets of a vector with x being
     * index 0 and y being index 1.
     * 
     * @return Array of {x mouse counts, y mouse counts}
     */
    public int[] getLifetimeCounts() {
        return new int[]{
            xTracker.getLifetimeCounts(),
            yTracker.getLifetimeCounts()
        };

    }

    /**
     * Gets the velocity vector of the mouse in x and y direction. The magnitude
     * of the vectors will never be 0.
     * 
     * @return Array representing components of the vector
     */
    public double[] getVelocity() {
        return new double[]{
            xTracker.getVelocity(),
            yTracker.getVelocity()
        };

    }

    /**
     * Begins mouse tracking
     */
    public void start() {
        eventBrokerThread.start();

    }

    /**
     * Stops mouse reader thread. To stop, at least one byte of data must be 
     * read after the signal or in otherwords, the mouse must move.
     */
    public void terminate() {
        eventBroker.terminate();
        
        try {
            eventBrokerThread.join(10);
        
        } catch (InterruptedException e) {
            e.printStackTrace();    
            
        }
        
    }
}