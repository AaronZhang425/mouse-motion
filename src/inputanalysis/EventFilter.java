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
        
        if (eventCode != null) {
            // Add event type and an associated event code array of size 1
            filter.put(eventType, new EventCode[]{eventCode});

        } else {
            // Add empyt event code array if the event code is null    
            filter.put(eventType, new EventCode[0]);

        }



        // eventFilter = configFilter();
    }

    public EventFilter(EventTypes eventType) {
        this(eventType, null);
    }

    public EventFilter(HashMap<EventTypes, EventCode[]> filter) {
        this.filter = filter;
    }

    /**
     * Adds an extra condition to the filter that any new objects passed into
     * to isMatch filter must pass.
     * 
     * @param eventType Additional event type to filter by;
     * @param eventCodes Additional event codes to filter by
     */
    public void addCondition(EventTypes eventType, EventCode[] eventCodes) {
        if (eventCodes != null) {
            filter.put(eventType, eventCodes);

        } else {
            filter.put(eventType, new EventCode[0]);

        }
        
    }

    public void addCondition(EventTypes eventType, EventCode eventCode) {
        if (eventCode != null) {
            filter.put(eventType, new EventCode[]{eventCode});

        } else {
            filter.put(eventType, new EventCode[0]);

        }

    }

    public boolean isMatch(EventData inputEvent) {
        EventTypes inputEventType = inputEvent.getEventType();

        // If the event type of the input event does not exist in the hashmap
        // filter of accepted event types, return false
        if (!filter.containsKey(inputEventType)) {
            return false;
        }

        EventCode[] acceptedEventCodes = filter.get(inputEventType);
        EventCode inputEventCode = inputEvent.getEventCode();

        // If no array of accpetable event codes exists, filter only by
        // event type
        if (
            acceptedEventCodes.length == 0 ||
            acceptedEventCodes[0] == null ||
            acceptedEventCodes == null
        ) {
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
