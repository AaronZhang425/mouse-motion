package inputanalysis;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;


// TODO: Rework class to allow several filters and several collections

public class EventFileFilterer implements Runnable {
    private volatile boolean stop = false;

    private EventTypes eventType;
    private EventCode eventCode;
    private InputReader reader;

    private Function<EventData, Boolean> inputEventFilter;

    // private volatile ArrayDeque<EventData> data = new ArrayDeque<>(16);
    private volatile HashMap<EventFilter, ArrayDeque<EventData>> data;

    @Deprecated
    public EventFileFilterer(InputReader reader, EventTypes eventType, EventCode eventCode) {
        this.reader = reader;
        this.eventType = eventType;
        this.eventCode = eventCode;

        configFilter();

    }

    @Deprecated
    public EventFileFilterer(InputReader reader, EventTypes eventType) {
        // this.reader = reader;
        // this.eventType = eventType;
        this(reader, eventType, null);
    }

    public EventFileFilterer(InputReader reader) {
        this(reader, new ArrayList<>());

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
        // Stop file reader to prevent resource leaks
        reader.stop();
        
        // Stop thread runtime loop
        stop = true;
    }

    
    // TODO: Eventually remove
    @Deprecated
    public boolean hasNext() {
        // return true if the size of the deque is greater than 0 and contains
        // non-null values
        ArrayDeque<EventData> data = new ArrayDeque<>();
        return (data.size() > 0) && (data.peekFirst() != null);
    }

    // TODO: implement
    public boolean hasNext(EventFilter filter) {
        // If no such filter exists in the data, return false
        if (!data.containsKey(filter)) {
            return false;
        }

        ArrayDeque<EventData> events = data.get(filter);
        return (events != null) && (events.size() > 0) && (events.peekFirst() != null);
    }

    // TODO: Given a key, get data from the hash map of filters
    public EventData getData(EventFilter filter) {
        synchronized(data) {
            ArrayDeque<EventData> events = data.get(filter);
            if (events != null && events.peekFirst() == null) {
                return null;
            }

            return events.pollFirst();
            
        }

    }

    // TODO: Eventually remove
    @Deprecated
    public EventData getData() {
        synchronized(data) {
            ArrayDeque<EventData> data = new ArrayDeque<>();
            return data.pollFirst();
            // return new EventData(null, eventType, eventCode, 0);
            
        }

    }

    // sets the filter to a lambda that evaluates each event
    @Deprecated
    private void configFilter() {
        if (eventCode == null) {
            // filter only by event type
            inputEventFilter = eventData -> {
                return eventData.eventType().equals(eventType);
            };

        } else {
            // filter by both event type and event code
            inputEventFilter = eventData -> {
                return (
                    eventData.eventType().equals(eventType) &&
                    eventData.eventCode().equals(eventCode)
                );
            };
        }
    }

    @Override
    @Deprecated // out dated logic that will fail; will be removed
    public void run() {
        while (!stop) {
            // Get data and add to deque if passes filtering
            EventData eventData = reader.getEventData();

            if (inputEventFilter.apply(eventData)) {
                // data.addLast(eventData);

            }
        }

    }


}
