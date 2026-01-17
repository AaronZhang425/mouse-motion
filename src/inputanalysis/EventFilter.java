package inputanalysis;

import eventclassification.eventcodes.EventCode;
import eventclassification.EventTypes;
import devicemanagement.EventData;

import java.util.Arrays;
import java.util.HashMap;

public class EventFilter {
    private HashMap<EventTypes, EventCode[]> filter;

    public EventFilter(EventTypes eventType, EventCode eventCode) {
        // this.eventType = eventType;
        // this.eventCode = eventCode;
        EventCode[] eventCodes = {eventCode};

        filter.put(eventType, eventCodes);

        // eventFilter = configFilter();
    }

    public EventFilter(EventTypes eventType) {
        this(eventType, null);
    }

    public EventFilter(HashMap<EventTypes, EventCode[]> filter) {
        this.filter = filter;
    }

    public boolean filter(EventData inputEvent) {
        EventTypes inputEventType = inputEvent.eventType();

        // If the event type of the input event does not exist in the hashmap
        // fitler of accepted event types, return false
        if (!filter.containsKey(inputEventType)) {
            return false;
        }

        EventCode[] acceptedEventCodes = filter.get(inputEventType);
        EventCode inputEventCode = inputEvent.eventCode();

        // If no array of accpetable event codes exists, filter only by
        // event type
        if (acceptedEventCodes == null) {
            return true;
        }

        int index = Arrays.asList(acceptedEventCodes).indexOf(inputEventCode);

        // if the event code of input cannot be found in list of accepted
        // event codes, return false
        if (index < 0) {
            return false;
        }


        return true;

    }


}
