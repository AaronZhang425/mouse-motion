package inputanalysis;

import java.util.ArrayDeque;
import java.util.function.Function;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

public class InputFilter implements Runnable {
    private boolean stop = false;

    private EventTypes eventType;
    private EventCode eventCode;
    private InputReader reader;

    private Function<EventData, Boolean> filter;

    private ArrayDeque<EventData> data = new ArrayDeque<>(8);

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
        stop = true;
    }

    public EventData getData() {
        // place holder
        return data.getFirst();
    }

    private void configFilter() {
        if (eventCode == null) {
            filter = eventData -> {
                return eventData.eventType().equals(eventType);
            };

        } else {
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
            EventData eventData = reader.getEventData();
            
            if (filter.apply(eventData)) {
                data.addLast(eventData);
            }
        }

    }


}
