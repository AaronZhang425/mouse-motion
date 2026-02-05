package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;
import java.util.Arrays;

/**
 * This class holds data of each event created by the device. The structure of 
 * the event is outlined by the struct input_event in the file input.h
 * 
 * This file can be seen here:
 * https://github.com/torvalds/linux/blob/master/include/uapi/linux/input.h
 * or alternatively at /usr/src/(linux-version)/include/uapi/linux/input.h
 * 
 * Instead of representing the values of event type and event code using macros,
 * this program uses enums instead
 */
public class EventData {
    private final long[] time;
    private final EventTypes eventType;
    private final EventCode eventCode;
    private final int value;

    /**
     * Creates an event data object to represent an event created by a 
     * device
     * 
     * @param time
     * @param eventType
     * @param eventCode
     * @param value
     */
    public EventData(long[] time, EventTypes eventType, EventCode eventCode, int value) {
        this.time = time;
        this.eventType = eventType;
        this.eventCode = eventCode;
        this.value = value;
    }

    /**
     * Get time array
     * 
     * @return long array representing time with index 0 being whole seconds and
     * index 1 as fractional seconds as microseconds
     */
    public long[] getTime() {
        return time;
    
    }

    /**
     * Get whole seconds of event representing the occurance of the event
     * 
     * @return whole seconds at which event occured
     */
    public long getSeconds() {
        return time[0];
        
    }

    /**
     * Get fractional seconds as a microsecond of event representing the
     * occurance of the event
     * 
     * @return whole seconds at which event occured
     */
    public long getMicroseconds() {
        return time[1];

    }

    /**
     * Get the event type of the input event this object represents
     * 
     * @return event type of input event
     */
    public EventTypes getEventType() {
        return eventType;

    }

    public EventCode getEventCode() {
        return eventCode;

    }

    public int getValue() {
        return value;
    
    }

    public boolean equals(EventData other){
        if (other == null) {
            return false;
        }

        return (
            Arrays.equals(time, other.time) &&
            eventType.equals(other.eventType) &&
            eventCode.equals(other.eventCode) &&
            value == other.value
        );
    }

    @Override
    public String toString() {
        return (
            "Input Event Info: \n" +
            "Seconds: " + time[0] + "\n" +
            "Microseconds: " + time[1] + "\n" +
            "Event Type: " + eventType + "\n" +
            "Event Code: " + eventCode + "\n" +
            "Value: " + value + "\n"
        );
     
    }


}