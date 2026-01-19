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
        // System.out.println(inputEvent.eventType());
        EventTypes inputEventType = inputEvent.eventType();

        // System.out.println("liudrgh");
        // If the event type of the input event does not exist in the hashmap
        // fitler of accepted event types, return false
        
        if (!filter.containsKey(inputEventType)) {
            return false;
        }

        // System.out.println("Filter keys: " + filter.keySet());
        // System.out.println("Input event type: " + inputEventType);        


        // boolean eventTypeExists = false;

        // for (EventTypes type : filter.keySet()) {
        //     if (type.equals(inputEventType)) {
        //         eventTypeExists = true;
        //         break;
        //     }

        // }

        // if (!eventTypeExists) {
        //     return false;
        // }

        // System.out.print("oadg");

        EventCode[] acceptedEventCodes = filter.get(inputEventType);
        EventCode inputEventCode = inputEvent.eventCode();

        // If no array of accpetable event codes exists, filter only by
        // event type
        if (acceptedEventCodes == null || acceptedEventCodes[0] == null) {
            return true;
        }

        return Arrays.asList(acceptedEventCodes).contains(inputEventCode);

        // int index = Arrays.asList(acceptedEventCodes).indexOf(inputEventCode);
        // System.out.print(index);

        // // if the index is greater than -1
        // // the event code of the input event exists within the acceptable list
        // if (index > -1) {
        //     return true;
        // }


        // return false;

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
