package inputanalysis;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import devicemanagement.EventData;
import devicemanagement.InputReader;

public class InputEventFilterer implements Runnable {

    /**
     * Flag that represents if thread can run
     */
    private volatile boolean run = true;

    /**
     * File reader that reads input events found in the file /dev/input/eventX
     * where x denotes any positivie integer including 0.
     */
    private InputReader reader;

    /**
     * Holds several filters and an associated queue. If an event is found 
     * matching the filter, add to the associated queue. Can be modified in
     * multiple threads
     */
    private ConcurrentHashMap<EventFilter, ConcurrentLinkedQueue<EventData>> data = (
        new ConcurrentHashMap<>()
    );

    public InputEventFilterer(InputReader reader) {
        this.reader = reader;

    }

    public InputEventFilterer(InputReader reader, EventFilter filter) {
        this.reader = reader;
        data.put(filter, new ConcurrentLinkedQueue<>());
    }

    public InputEventFilterer(InputReader reader, Collection<EventFilter> filters) {
        this.reader = reader;

        for (EventFilter filter : filters) {
            data.put(filter, new ConcurrentLinkedQueue<>());
        }
    }
    
    /**
     * Adds filter to the hash map of filters and creates an associated queue
     * of events that match the filter
     * 
     * @param filter filter to add to categorize events
     */
    public void addFilter(EventFilter filter) {
        // Add the filter to the hashmap and an associated array deque
        data.put(filter, new ConcurrentLinkedQueue<>());

    }

    /**
     * Gets the status if the thread is flagged to run
     * 
     * @return running flag
     */
    public boolean isRunning() {
        return run;
    }

    /**
     * Prepares the thread to stop and finish the thread loop
     */
    public void terminate() {
        try {
            reader.close(); // Stop file reader to prevent resource leaks
            
        } catch (IOException e) {
            System.out.println(e);

        } catch (NullPointerException e) {
            System.out.println(e);
            System.out.println("Check permissions in /dev/input/");

        }

        run = false; // Stop thread runtime loop

    }

    public boolean hasNext(EventFilter filter) {
        // Get the deque of events
        ConcurrentLinkedQueue<EventData> events = data.get(filter);

        return (events != null) && (events.peek() != null);
    }

    public EventData getData(EventFilter filter) {
        // Get the queue associated with fitler
        ConcurrentLinkedQueue<EventData> events = data.get(filter);

        // If the queue exists or contains nothing, return null
        if (events == null || events.size() == 0) {
            return null;

        }

        // get and remove first item
        return events.poll();
        
        

    }

    /**
     * Defines the the behavior of the thread. Constantly get event data and 
     * add to the queues associated with any filters the event data matches
     * with
     */
    @Override
    public void run() {
        while (run) {
            // Get data and add to deque if passes filtering
            EventData eventData = reader.getEventData();
            
            // Event data will be null if the file reader has stopped due to 
            // an error or is closed
            if (eventData == null) {
                break;
            }

            // Iterate through the hashmap of data
            for (EventFilter filter : data.keySet()) {
                // If the event data matches with filter
                if (filter.isMatch(eventData)) {
                    // Add event to array deque associated with filter
                    data.get(filter).add(eventData);

                }

            }

        }

    }


}