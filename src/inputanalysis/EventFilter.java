package inputanalysis;

import eventclassification.eventcodes.EventCode;
import eventclassification.EventTypes;
import devicemanagement.EventData;

import java.util.function.*;

// TODO: Implement class
// This class represents a single filter for events by event type
// or by both event type and code
public class EventFilter {
    private EventTypes eventTypes;
    private EventCode eventCode;

    private Function<EventData, Boolean> filter;

    public EventFilter(EventTypes eventType, EventCode eventCode) {

    }

    private void configFilter() {

    }

    public boolean isMatch(EventData eventData) {
        return filter.apply(eventData);
   
    }
    


}
