package inputanalysis;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;

import devicemanagement.EventData;
import devicemanagement.InputReader;

public class EventFileFilterer implements Runnable {
    private volatile boolean stop = false;
    private InputReader reader;

    private volatile HashMap<EventFilter, ArrayDeque<EventData>> data = (
        new HashMap<>()
    );

    public EventFileFilterer(InputReader reader) {
        this.reader = reader;

    }

    public EventFileFilterer(InputReader reader, EventFilter filter) {
        this.reader = reader;
        data.put(filter, new ArrayDeque<>());
    }

    public EventFileFilterer(InputReader reader, Collection<EventFilter> filters) {
        this.reader = reader;

        for (EventFilter filter : filters) {
            data.put(filter, new ArrayDeque<>());
        }
    }

    public void addFilter(EventFilter filter) {
        data.put(filter, new ArrayDeque<>());

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

        ArrayDeque<EventData> events = data.get(filter);
        return (events != null) && (events.size() > 0) && (events.peekFirst() != null);
    }

    public EventData getData(EventFilter filter) {
        synchronized(data) {
            ArrayDeque<EventData> events = data.get(filter);
            if (events != null && events.peekFirst() == null) {
                return null;
            }

            return events.pollFirst();
            
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
                    data.get(filter).addLast(eventData);

                }

            }

        }

    }


}
