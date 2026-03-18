package inputanalysis;

import devicemanagement.EventData;
import eventclassification.eventcodes.EventCode;

public interface InputEventConsumer<T extends EventCode> {
    public void consume(EventData inputEvent);
    
}
