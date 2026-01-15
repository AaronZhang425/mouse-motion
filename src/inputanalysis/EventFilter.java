package inputanalysis;

import eventclassification.eventcodes.EventCode;
import eventclassification.EventTypes;
import devicemanagement.EventData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.*;

// TODO: Implement class
// This class represents a single filter for events by event type
// or by both event type and code
public class EventFilter {
    private EventTypes eventType;
    private EventCode eventCode;


    private Function<EventData, Boolean> eventFilter;

    public static ArrayList<EventFilter> createFilterGroup(EventTypes eventType, EventCode eventCode) {
        return new ArrayList<>();

    }

    public static ArrayList<EventFilter> createFilterGroup(HashMap<EventTypes, EventCode> fullCapabilities) {
        return new ArrayList<>();
    
    }


    public EventFilter(EventTypes eventType, EventCode eventCode) {
        this.eventType = eventType;
        this.eventCode = eventCode;

        eventFilter = configFilter();
    }

    public EventFilter(EventTypes eventType) {
        this(eventType, null);
    }

    private Function<EventData, Boolean> configFilter() {
        Function<EventData, Boolean> filter;

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

        return filter;

    }

    public boolean isMatch(EventData eventData) {
        return eventFilter.apply(eventData);
   
    }
    


}
