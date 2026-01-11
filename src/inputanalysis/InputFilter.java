package inputanalysis;

import java.util.ArrayDeque;
import java.util.function.Function;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

public class InputFilter implements Runnable {
    private volatile boolean stop = false;

    private EventTypes eventType;
    private EventCode eventCode;
    private InputReader reader;

    private Function<EventData, Boolean> filter;

    private volatile ArrayDeque<EventData> data = new ArrayDeque<>(16);

    public InputFilter(InputReader reader, EventTypes eventType, EventCode eventCode) {
        this.reader = reader;
        this.eventType = eventType;
        this.eventCode = eventCode;

        configFilter();

    }

    public InputFilter(InputReader reader, EventTypes eventType) {
        // this.reader = reader;
        // this.eventType = eventType;
        this(reader, eventType, null);
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

    public boolean hasNext() {
        // return true if the size of the deque is greater than 0 and contains
        // non-null values
        return (data.size() > 0) && (data.peekFirst() != null);
    }

    public EventData getData() {
        synchronized(data) {
            return data.pollFirst();
            
        }

    }

    // sets the filter to a lambda that evaluates each event
    private void configFilter() {
        if (eventCode == null) {
            // filter only by event type
            filter = eventData -> {
                return eventData.eventType().equals(eventType);
            };

        } else {
            // filter by both event type and event code
            filter = eventData -> {
                return (
                    eventData.eventType().equals(eventType) &&
                    eventData.eventCode().equals(eventCode)
                );
            };
        }
    }

    @Override
    public void run() {
        while (!stop) {
            // Get data and add to deque if passes filtering
            EventData eventData = reader.getEventData();

            if (filter.apply(eventData)) {
                data.addLast(eventData);

            }
        }

    }


}
