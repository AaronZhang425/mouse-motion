package inputanalysis;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class to track a collection overtime and calculate the difference
 */
public class Differentiator<T extends Collection<Double>> implements Runnable{
    private volatile boolean run = true;

    private T collectionToTrack;
    private int samplingRate;

    private volatile ArrayList<Double> derivative;


    /**
     * Creates a differentiator object that tracks the rate of change of a
     * collection of doubles
     * 
     * @param collectionToTrack A collection of doubles to track
     * @param samplingRate Rate at which samples are collected in milliseconds
     */
    public Differentiator(T collectionToTrack, int samplingRate) {
        this.collectionToTrack = collectionToTrack;
        this.samplingRate = samplingRate;
    }

    /**
     * Creates a differentiator object that tracks the rate of change of a
     * collection of doubles. Sampling rate defaults to 1000 milliseconds
     * 
     * @param collectionToTrack A collection of doubles to track
     */
    public Differentiator(T collectionToTrack) {
        this.collectionToTrack = collectionToTrack;
        this.samplingRate = 1000;
    }

    /**
     * Get the status of the flag marking the thread to run for long-term.
     * 
     * @return Status of runnable flag
     */
    public boolean isRunning() {
        return run;
    }

    /**
     * Gets the sampling rate of the differentiator
     * 
     * @return The rate at which the differentiator gets data
     */
    public int getSamplingRate() {
        return samplingRate;
    
    }

    /**
     * Gets an array representing the difference of each element over time
     * 
     * @return An ArrayList of doubles of which element is the derivative
     */
    public ArrayList<Double> getDerivative() {
        return derivative;
    
    }

    /**
     * Continuously get the derivative of the data
     */
    @Override
    public void run() {
        // Reserave memory for the intial and final data points
        ArrayList<Double> initialDataPoint = null;
        ArrayList<Double> finalDataPoint = null;

        // Thread run time loop
        while (run) {
            // Get a sample
            initialDataPoint = new ArrayList<>(collectionToTrack);

            // Wait 10 seconds
            try {
                Thread.sleep(samplingRate);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;

            }

            // Get sample at at later time
            finalDataPoint = new ArrayList<>(collectionToTrack);

            // Represents the smallest size of the intial and final data
            // points
            int size = 0;

            // Set size to the size of the smallest collection
            if (initialDataPoint.size() > finalDataPoint.size()) {
                size = finalDataPoint.size();

            } else {
                size = initialDataPoint.size();

            }

            // Create temporary new ArrayList to represent the derivative to 
            // prevent reads to derivative between the creation of a new array
            // and adding data
            ArrayList<Double> tempDerivative = new ArrayList<Double>(size);
            
            for (int i = 0; i < size; i++) {
                // Get the difference and divide by the sampling rate converted
                // to seconds
                tempDerivative.add(
                    (finalDataPoint.get(i) - initialDataPoint.get(i)) /
                    (samplingRate / 1000)
                );

            }

            derivative = tempDerivative;

        }

    }


    
}
