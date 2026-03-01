package inputanalysis;

import java.util.Collection;

/**
 * Class to track a collection overtime and calculate the difference
 */
public class Differentiator<T extends Collection<Double>> implements Runnable{
    private volatile boolean run = true;

    private T collectionToTrack;
    private int samplingRate;


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

    @Override
    public void run() {
        T intialDataPoint;
        T finalDataPoint;
        

        while (run) {
            // TODO: Do something
            throw new UnsupportedOperationException("Unimplemented method 'run'");
        }

    }


    
}
