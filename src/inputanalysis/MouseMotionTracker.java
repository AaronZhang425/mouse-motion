package inputanalysis;

import java.util.concurrent.atomic.AtomicIntegerArray;

import devicemanagement.InputReader;
import devicemanagement.Mouse;
import eventclassification.EventTypes;
import eventclassification.eventcodes.Rel;

// In original system, they consume data, leading to inaccuracies in both.

public class MouseMotionTracker implements Runnable {
    /**
     * Use this variable to control the termination of thread. Set to true
     * to stop thread.
     */
    private volatile boolean stop = false;
    
    /**
     * Represents a mouse object that wraps a InputDevice object and has a 
     * DPI field.
     */
    private final Mouse mouse;

    private final InputEventFilterer eventFilterer;

    private final EventFilter xFilter;
    private final EventFilter yFilter;
    
    /**
     * Thread the runs the input reader and gathers data. Does not process 
     * events beyond converting it into a more manageable representation
     */
    private final Thread readerRunner;

    /**
     * First element represents mouse counts in the x direction. Second element
     * represents mouse counts in the y direction. These numbers do not directly
     * represent physical measurements.
     */
    private final AtomicIntegerArray mouseCounts = new AtomicIntegerArray(
        new int[]{0, 0}
    );

    /**
     * Fisrt element holds the position offset in the x direction. The second
     * element represents the offset in the y direction.
     */
    private final double[] positionOffset = new double[2];

    public MouseMotionTracker(Mouse mouse, double[] positionOffset) {
        this.mouse = mouse;
        
        eventFilterer = new InputEventFilterer(
            new InputReader(this.mouse.getDevice().getHandlerFile())
        );

        // Create data filters for x and y
        xFilter = new EventFilter(EventTypes.REL, Rel.REL_X);
        yFilter = new EventFilter(EventTypes.REL, Rel.REL_Y);

        // Add and register filters to event reader
        eventFilterer.addFilter(xFilter);
        eventFilterer.addFilter(yFilter);
        
        // If start is null, set initial displacement to 0
        // Otherwise, set it to the values of the array
        if (positionOffset == null) {
            this.positionOffset[0] = 0;
            this.positionOffset[1] = 0;

        } else {
            this.positionOffset[0] = positionOffset[0];
            this.positionOffset[1] = positionOffset[1];

        }

        readerRunner = new Thread(eventFilterer, "Mouse Data Getter");
        readerRunner.start();

    }

    public MouseMotionTracker(Mouse mouse) {
        this(mouse, null);
    }

    /**
     * Flag the mouse data processor to stop and clean up the data getter
     * thread.
     */
    public void terminate() {
        // Set this data processor thread to stop
        stop = true;

        // Stop data getter thread
        eventFilterer.terminate();

        try {
            // Time bound termination; can be adjusted as needed
            readerRunner.join(500);

        } catch (InterruptedException e) {
            System.out.print("Event file reader terminated");
            System.out.print(e);
            

        }

    }

    /**
     * Get the state of the flag marking thread to end
     * 
     * @return termination flag
     */
    public boolean isTerminated() {
        return stop;
    }

    /**
     * Gets the displacement of the mosue in meters.
     * 
     * @return displacement of mouse in meters
     */
    public double[] getDisplacement() {
        // Convert mouse counts to meters and add the original offsets to 
        // position
        return new double[]{
            mouseCountsToMeters(mouseCounts.get(0)) + positionOffset[0],
            mouseCountsToMeters(mouseCounts.get(1)) + positionOffset[1]
        };

    }

    /**
     * This method defines the runtime behavior of the thread during runtime.
     * Do not run this method directly to start a thread. Create an object of
     * the MouseMotionTracker class and pass it into the constructor of the 
     * Thread constructor. On the thread object, call start().
     */
    @Override
    public void run() {
        // Loop if the method has not been marked to stop
        while (!stop) {
            // If there is any data associated with the x filter
            if (eventFilterer.hasNext(xFilter)) {
                // Add mouse counts to the x value of the atomic integer array
                mouseCounts.getAndAdd(
                    0,
                    eventFilterer.getData(xFilter).getValue()
                );

            }
            
            // If there is any data associated with the y filter
            if (eventFilterer.hasNext(yFilter)) {
                // Add mouse counts to the y value of the atomic integer array
                mouseCounts.getAndAdd(
                    1,
                    eventFilterer.getData(yFilter).getValue()
                );
                
            }
            
        }



        System.out.print("Thread ended");

            // Output to console the displacement in terms of meters
            // System.out.printf(
            //     "X displacement: %5.4f \t Y displacement: %5.4f\n",
            //     motionData[0][0],
            //     motionData[0][1]
            // );

    }

    /**
     * Given a DPI and mouse counts or dots, convert the number of counts to
     * meters using the given DPI.
     * 
     * @param counts Number of counts recorded by the mouse
     * @param dpi The DPI of the mouse
     * @return The number of meters the counts is equivalent to given the dpi
     */
    private double mouseCountsToMeters(int counts, int dpi) {
        // return (double) counts / 1000.0;
        // return (double) counts / dpi;
        return (double) counts / dpi * 0.0254;

        // counts inch meters
        // counts inch
    }

    /**
     * Returns the results of converting counts to meters
     * 
     * @param counts The number of dots or counts the mouse has detected
     * @return Displacement in meters based on mouse counts
     */
    private double mouseCountsToMeters(int counts) {
        return mouseCountsToMeters(counts, mouse.getDpi());
        // return (1.0 * counts / mouse.dpi()) * 0.0254;
    }

}
