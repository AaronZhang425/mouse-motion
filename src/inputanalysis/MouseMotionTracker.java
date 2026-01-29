package inputanalysis;

import java.util.concurrent.atomic.AtomicIntegerArray;

import devicemanagement.InputReader;
import devicemanagement.Mouse;
import eventclassification.EventTypes;
import eventclassification.eventcodes.Rel;

// In original system, they consume data, leading to inaccuracies in both.

public class MouseMotionTracker implements Runnable {
    // variable used to control termination of thread
    private volatile boolean stop = false;
    
    private final Mouse mouse;

    // private final InputReader reader;
    private final InputEventFilterer eventFilterer;

    private final EventFilter xFilter;
    private final EventFilter yFilter;
    
    private final Thread readerRunner;
    // private final EventFileFilterer xValues;
    // private final EventFileFilterer yValues;

    // private final Thread xValuesThread;
    // private final Thread yValuesThread;

    /**
     * First element represents mouse counts in the x direction. Second element
     * represents mouse counts in the y direction. These numbers do not directly
     * represent physical measurements.
     */
    private final AtomicIntegerArray mouseCounts = new AtomicIntegerArray(
        new int[]{0, 0}
    );

    // outer: first array is displacement seconds is velocity; third is acceleration
    // inner: components of  the vector (x, y)
    // Temporarily unused
    private final double[][] motionData = new double[3][2];

    public MouseMotionTracker(Mouse mouse, double[] start) {
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
        if (start == null) {
            motionData[0][0] = 0;
            motionData[0][1] = 0;

        } else {
            motionData[0][0] = start[0];
            motionData[0][1] = start[1];

        }

        readerRunner = new Thread(eventFilterer, "Mouse Data Getter");
        readerRunner.start();

    }

    public MouseMotionTracker(Mouse mouse) {
        this(mouse, null);
    }

    public void terminate() {
        stop = true;

        eventFilterer.terminate();

        try {
            // Time bound termination; can be adjusted as needed
            readerRunner.join(500);

        } catch (InterruptedException e) {
            System.out.print("Event file reader terminated");
            System.out.print(e);
            

        }

    }

    public boolean isTerminated() {
        return stop;
    }

    public double[] getDisplacement() {
        return new double[]{
            mouseCountsToMeters(mouseCounts.get(0)),
            mouseCountsToMeters(mouseCounts.get(1))
        };

    }

    @Override
    public void run() {
        while (!stop) {
            // If there is any data associated with the x filter
            if (eventFilterer.hasNext(xFilter)) {
                // Convert the value of the event associated with the x filter
                // to meters and add to x displacement
                mouseCounts.getAndAdd(
                    0,
                    eventFilterer.getData(xFilter).getValue()
                );


                // mouseCounts[0] += mouseCountsToMeters(
                //     eventFilterer.getData(xFilter).getValue()
                // );

            }
            
            // If there is any data associated with the y filter
            if (eventFilterer.hasNext(yFilter)) {
                // Convert the value of the event associated with the y filter
                // to meters and add to y displacement
                mouseCounts.getAndAdd(
                    1,
                    eventFilterer.getData(yFilter).getValue()
                );
                
                // mouseCounts[1] += mouseCountsToMeters(
                //     eventFilterer.getData(yFilter).getValue()
                // );
                
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

    private double mouseCountsToMeters(int counts, int dpi) {
        // return (double) counts / 1000.0;
        // return (double) counts / dpi;
        return (double) counts / dpi * 0.0254;

        // counts inch meters
        // counts inch
    }

    private double mouseCountsToMeters(int counts) {
        return mouseCountsToMeters(counts, mouse.getDpi());
        // return (1.0 * counts / mouse.dpi()) * 0.0254;
    }

    // // TODO: Reimplement
    // private double totalDisplacement(EventData[] event) {
    //     double displacement = 0;

    //     for (EventData data : event) {
    //         displacement += getDisplacement(data);
    //     }

    //     return displacement;

    // }

    // // TODO: Reimplement
    // private double getDisplacement(EventData event) {
    //     return mouseCountsToMeters(event.getValue());

    // }

    // // TODO: Reimplement
    // private double getVelocity(
    //         double displacementDifference,
    //         double timeDifference) {

    //     return displacementDifference / timeDifference;

    // }

    // // TODO: Reimplement
    // private double getVelocity(
    //         double intialDisplacement,
    //         double finalDisplacemenet,
    //         double timeDifference) {

    //     return (finalDisplacemenet - intialDisplacement) / timeDifference;

    // }

    // // data is a parameter that represents an initial and final component
    // // of a vector
    // // TODO: Reimplement
    // private double getAcceleration(
    //         double intialVelocity,
    //         double finalVelocity,
    //         double timeDifference) {
    //     return (finalVelocity - intialVelocity) / timeDifference;

    // }

    // // TODO: Reimplement
    // private double getTimeDifference(EventData intialData, EventData finalData) {
    //     long initialSeconds = intialData.getTime()[0];
    //     long initialMicroseconds = intialData.getTime()[1];

    //     long finalSeconds = finalData.getTime()[0];
    //     long finalMicroseconds = finalData.getTime()[1];

    //     // long initialSeconds = data[0].time()[0];
    //     // long intialMicroseconds = data[0].time()[1];

    //     // long finalSeconds = data[1].time()[0];
    //     // long finalMicroseconds = data[1].time()[1];

    //     // in seconds
    //     double timeDifference = ((finalSeconds - initialSeconds) +
    //             (finalMicroseconds - initialMicroseconds) / 1.0E60);

    //     return timeDifference;

    // }

}
