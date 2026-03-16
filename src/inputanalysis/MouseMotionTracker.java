package inputanalysis;

import java.io.FileNotFoundException;

import devicemanagement.InputReader;
import devicemanagement.Mouse;
import eventclassification.EventTypes;
import eventclassification.eventcodes.Rel;

public class MouseMotionTracker implements Runnable {
    /**
     * Use this variable to control the termination of thread. Set to false
     * to stop thread. Denotes if thread is to run.
     */
    private volatile boolean run = true;
    
    /**
     * Represents a mouse object that wraps a InputDevice object and has a 
     * DPI field.
     */
    private final Mouse mouse;

    /**
     * Reads input and creates a hashmap of filters to map to a concurrent queue
     * that holds events that match the filter
     */
    private final InputEventCollection evemtCollection;

    /**
     * Filter that filters for relative x values of the mouse
     */
    private final EventFilter xFilter;

    /**
     * Filter that filters for relative y values of the mouse
     */
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
    private final int[] mouseCounts = {0, 0};
    
    /**
     * Represents the actual mouse displacement of the mouse in meters.
     * AtomicDoubleArray is used instead of a volatile double[] becuase must be
     * final to ensure differentiator objects on this array work.
     */
    private final AtomicDoubleArray displacement = new AtomicDoubleArray(
        new double[]{0, 0}
    );

    /**
     * Fisrt element holds the position offset in the x direction. The second
     * element represents the offset in the y direction.
     */
    private final double[] positionOffset = {0.0, 0.0};

    public MouseMotionTracker(Mouse mouse, double[] positionOffset) throws FileNotFoundException {
        this.mouse = mouse;
        
        evemtCollection = new InputEventCollection(
            new InputReader(this.mouse.getDevice().getHandlerFile())
        );

        // Create data filters for x and y
        xFilter = new EventFilter(EventTypes.REL, Rel.REL_X);
        yFilter = new EventFilter(EventTypes.REL, Rel.REL_Y);

        // Add and register filters to event reader
        evemtCollection.addFilter(xFilter);
        evemtCollection.addFilter(yFilter);
        
        // If start is null, set initial displacement to 0
        // Otherwise, set it to the values of the array
        if (positionOffset != null) {
            this.positionOffset[0] = positionOffset[0];
            this.positionOffset[1] = positionOffset[1];
        }

        readerRunner = new Thread(evemtCollection, "Mouse Data Getter");
        readerRunner.start();

    }

    public MouseMotionTracker(Mouse mouse) throws FileNotFoundException {
        this(mouse, null);
    }

    /**
     * Gets the mouse object that describes the mouse from which data is being
     * retrieved from
     * 
     * @return Mouse being read from
     */
    public Mouse getMouse() {
        return mouse;
        
    }

    public void trackVelocity() {
        

    }

    public void trackAcceleration() {


    }

    /**
     * Flag the mouse data processor to stop and clean up the data getter
     * thread.
     */
    public void terminate() {
        // Set this data processor thread to stop
        run = false;

        // Stop data getter thread
        evemtCollection.terminate();

        try {
            // Time bound termination; can be adjusted as needed
            readerRunner.join(500);

        } catch (InterruptedException e) {
            System.out.print("Event file reader terminated");
            System.out.print(e);
            

        }

    }

    /**
     * Get the state of the flag marking thread to run. Thread can be not 
     * running.
     * 
     * @return termination flag
     */
    public boolean isRunning() {
        return run;
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
            displacement.get(0),
            displacement.get(1)
        };


        // return new double[]{
        //     mouseCountsToMeters(mouseCounts.get(0)) + positionOffset[0],
        //     mouseCountsToMeters(mouseCounts.get(1)) + positionOffset[1]
        // };

    }

    /**
     * This method defines the runtime behavior of the thread. This will only
     * run properly as a thread when an objects of this class is passed into
     * a thread constructor and start() is called on the thread object
     */
    @Override
    public void run() {
        // Loop if the method has not been marked to stop
        while (run) {
            // If there is any data associated with the x filter
            if (evemtCollection.hasNext(xFilter)) {
                // Add mouse counts to the x value of the atomic integer array
                mouseCounts[0] += evemtCollection.getData(xFilter).getValue();

                // Recalculate displacement with new mouse counts
                displacement.set(
                    0,
                    mouseCountsToMeters(mouseCounts[0]) + positionOffset[0]
                );
                
            }
            
            // If there is any data associated with the y filter
            if (evemtCollection.hasNext(yFilter)) {
                // Add mouse counts to the y value of the atomic integer array
                mouseCounts[1] += evemtCollection.getData(yFilter).getValue();

                // Recalculate displacement with new mouse counts
                displacement.set(
                    1,
                    mouseCountsToMeters(mouseCounts[1]) + positionOffset[1]
                );
                
            }
            
        }



        System.out.print("Thread ended");
        System.out.println();

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
