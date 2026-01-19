package inputanalysis;

import eventclassification.eventcodes.EventCode;
import eventclassification.EventTypes;
import devicemanagement.EventData;

import java.util.Arrays;
import java.util.HashMap;

public class EventFilter {
    private HashMap<EventTypes, EventCode[]> filter = new HashMap<>();

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

    public boolean isMatch(EventData inputEvent) {
        EventTypes inputEventType = inputEvent.eventType();

        // If the event type of the input event does not exist in the hashmap
        // filter of accepted event types, return false
        if (!filter.containsKey(inputEventType)) {
            return false;
        }

        EventCode[] acceptedEventCodes = filter.get(inputEventType);
        EventCode inputEventCode = inputEvent.eventCode();

        // If no array of accpetable event codes exists, filter only by
        // event type
        if (acceptedEventCodes == null || acceptedEventCodes[0] == null) {
            return true;
        }

        // If accepted event codes contains the event code of the input event
        return Arrays.asList(acceptedEventCodes).contains(inputEventCode);

    }

    public String toString() {
        String temp = "";

        for (EventTypes type : filter.keySet()) {
            temp += "Type: " + type + "\t Code: ";
            
            for (EventCode code : filter.get(type)) {
                temp += code;
            }

            temp += "\n";

        }

        return temp;
    
    }


}
