package inputanalysis;

import java.util.Collection;
import java.util.HashMap;

import devicemanagement.EventData;
import devicemanagement.InputReader;

public class InputEventFilterer implements Runnable {

    /**
     * Denotes if thread should stop
     */
    private volatile boolean stop = false;

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
    private volatile HashMap<EventFilter, Queue<EventData>> data = (
        new HashMap<>()
    );

    public InputEventFilterer(InputReader reader) {
        this.reader = reader;

    }

    public InputEventFilterer(InputReader reader, EventFilter filter) {
        this.reader = reader;
        data.put(filter, new Queue<>());
    }

    public InputEventFilterer(InputReader reader, Collection<EventFilter> filters) {
        this.reader = reader;

        for (EventFilter filter : filters) {
            data.put(filter, new Queue<>());
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
        data.put(filter, new Queue<>());

    }

    public boolean isTerminated() {
        return stop;
    }

    public void terminate() {
        reader.stop(); // Stop file reader to prevent resource leaks
        stop = true; // Stop thread runtime loop

    }

    public boolean hasNext(EventFilter filter) {
        // If no such filter exists in the data, return false
        if (!data.containsKey(filter)) {
            return false;
        }

        // Get the deque of events
        Queue<EventData> events = data.get(filter);

        return (events != null) && (events.size() > 0) && (events.peek() != null);
    }

    public EventData getData(EventFilter filter) {
        synchronized(data) {
            // Get the queue associated with fitler
            Queue<EventData> events = data.get(filter);

            // If the queue exists an
            if (events == null || events.peek() == null) {
                return null;

            }

            // get and remove first item
            return events.get();
            
        }

    }

    @Override
    public void run() {
        while (!stop) {
            // Get data and add to deque if passes filtering
            EventData eventData = reader.getEventData();

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
