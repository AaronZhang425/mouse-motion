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

    private ArrayList<Double> derivative;


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

        while (run) {   
            initialDataPoint = new ArrayList<>(collectionToTrack);

            try {
                Thread.sleep(samplingRate);
                
            } catch (Exception e) {
                System.out.println(e);
                return;

            }

            finalDataPoint = new ArrayList<>(collectionToTrack);

            if (initialDataPoint == null || finalDataPoint == null) {
                continue;
            }

            int size = 0;

            // Set size to the size of the smallest collection
            if (initialDataPoint.size() > finalDataPoint.size()) {
                size = finalDataPoint.size();

            } else {
                size = initialDataPoint.size();

            }

            derivative = new ArrayList<Double>(size);
            for (int i = 0; i < size; i++) {
                derivative.add(
                    (finalDataPoint.get(i) - initialDataPoint.get(i)) /
                    (samplingRate / 1000)
                );

            }


        }

    }


    
}
