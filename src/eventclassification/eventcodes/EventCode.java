package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventCategory;
import eventclassification.EventTypes;

public interface EventCode extends EventCategory{
    public HashMap<Integer, EventCode> VALUE_MAP = new HashMap<>();
    public EventTypes getEventType(); 
    // public String getEventCodeName();

    public static EventCode byValue(int value) {
        return VALUE_MAP.get(value);
    }
}
