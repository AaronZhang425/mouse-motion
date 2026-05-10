package inputanalysis.singletracker;

import devicemanagement.EventData;
import eventclassification.eventcodes.EventCode;

public abstract class InputEventConsumer {
    protected EventCode eventCode;
    
    public InputEventConsumer(EventCode eventCode) {
        this.eventCode = eventCode;
   
    }

    public EventCode getEventCode() {
        return eventCode;
    
    }

    public boolean eventCodeCheck(EventData inputEvent) {
        return inputEvent.getEventCode().equals(eventCode);

    }

    public boolean eventCodeCheck(EventCode otherEventCode) {
        return eventCode.equals(otherEventCode);

    }

    /**
     * Abstract method for methods that take EventData as an input and does 
     * something with it.
     * 
     * @param inputEvent
     */
    public abstract void consume(EventData inputEvent);

}
