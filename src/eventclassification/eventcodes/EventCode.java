package eventclassification.eventcodes;

import eventclassification.EventCategory;
import eventclassification.EventTypes;

public interface EventCode extends EventCategory{
    public EventTypes getEventType();    
}
