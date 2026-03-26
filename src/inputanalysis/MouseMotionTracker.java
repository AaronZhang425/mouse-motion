package inputanalysis;

import java.io.FileNotFoundException;
import java.util.HashMap;

import devicemanagement.*;
import eventclassification.eventcodes.EventCode;
import eventclassification.eventcodes.Rel;

public class MouseMotionTracker {
    private MouseCountsTracker xTracker;
    private MouseCountsTracker yTracker;

    private EventBroker eventBroker;
    private Thread eventBrokerThread;

    public MouseMotionTracker(Mouse mouse) throws FileNotFoundException {
        xTracker = new MouseCountsTracker(mouse, Rel.REL_X);
        yTracker = new MouseCountsTracker(mouse, Rel.REL_Y);

        HashMap<EventCode, InputEventConsumer> eventConsumers = new HashMap<>();

        eventConsumers.put(Rel.REL_X, xTracker);
        eventConsumers.put(Rel.REL_Y, yTracker);

        eventBroker = new EventBroker(
            new InputReader(mouse.getDevice().getHandlerFile()),
            eventConsumers
        );

        eventBrokerThread = new Thread(eventBroker, "Event Broker");
        eventBrokerThread.start();

    }

    public EventBroker getEventBroker() {
        return eventBroker;
        
    }

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
    public int[] getTotalMouseCounts() {
        return new int[]{
            xTracker.getTotalMouseCounts(),
            yTracker.getTotalMouseCounts()
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