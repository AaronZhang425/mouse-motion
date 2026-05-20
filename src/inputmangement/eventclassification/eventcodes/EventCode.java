package inputmangement.eventclassification.eventcodes;

import java.util.HashMap;

import inputmangement.eventclassification.EventCategory;
import inputmangement.eventclassification.EventTypes;

public interface EventCode extends EventCategory{
    public HashMap<Integer, EventCode> VALUE_MAP = new HashMap<>();
    public EventTypes getEventType(); 

    public static EventCode byValue(int value) {
        return VALUE_MAP.get(value);
    }
}