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

    /**
     * Determine if the input event has the same event code as the event code
     * designated to the consumer. If the event code of the consumer is null,
     * the method is event code insensitive and will accept anything.
     * 
     * @param inputEvent The input event to check for equality in event code
     * @return True if event code is null or the event codes are equal
     */
    public boolean eventCodeCheck(EventData inputEvent) {
        return eventCode == null || inputEvent.getEventCode().equals(eventCode);

    }

    /**
     * Determine if the input event code is the same as the set event code.
     * If set event code is null interpret as event code insensitive and accept
     * any thing.
     * 
     * @param otherEventCode The other event cod to compare to
     * @return True if set event code is null or the event codes are the same.
     */
    public boolean eventCodeCheck(EventCode otherEventCode) {
        return eventCode == null || eventCode.equals(otherEventCode);

    }

    /**
     * Abstract method for methods that take EventData as an input and does 
     * something with it.
     * 
     * @param inputEvent
     */
    public abstract void consume(EventData inputEvent);

}
