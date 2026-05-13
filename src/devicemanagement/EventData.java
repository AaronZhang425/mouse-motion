package devicemanagement;

import java.util.Arrays;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

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
    private final long[] TIME;
    private final EventTypes EVENT_TYPE;
    private final EventCode EVENT_CODE;
    private final int VALUE;

    /**
     * Creates an event data object to represent an event created by a 
     * device
     * 
     * @param time
     * @param eventType
     * @param eventCode
     * @param value
     */
    public EventData(
        long[] time,
        EventTypes eventType,
        EventCode eventCode,
        int value
    ) throws IllegalArgumentException {
        if (time.length != 2) {
            throw new IllegalArgumentException(
                "Length of time array muse be 2"
            );

        }

        TIME = time;
        EVENT_TYPE = eventType;
        EVENT_CODE = eventCode;
        VALUE = value;
    }

    /**
     * Get time array
     * 
     * @return long array representing time with index 0 being whole seconds and
     * index 1 as fractional seconds as microseconds
     */
    public long[] getTime() {
        return new long[]{TIME[0], TIME[1]};
    
    }

    /**
     * Get whole seconds of event representing the occurance of the event
     * 
     * @return whole seconds at which event occured
     */
    public long getSeconds() {
        return TIME[0];
        
    }

    /**
     * Get fractional seconds as a microsecond of event representing the
     * occurance of the event
     * 
     * @return whole seconds at which event occured
     */
    public long getMicroseconds() {
        return TIME[1];

    }

    /**
     * Gets the total time in seconds. Since calculation is done on seconds
     * and microseconds represented as a long since epoch, this method is
     * susceptible to breaking in the future
     * 
     * @return
     */
    public double getTotalSeconds() {
        return TIME[0] + ((double) TIME[1] / 1e-6);
    }

    /**
     * Get the event type of the input event this object represents
     * 
     * @return event type of input event
     */
    public EventTypes getEventType() {
        return EVENT_TYPE;

    }

    /**
     * Get the event code of the input event this object represents
     * 
     * @return event code of input event
     */
    public EventCode getEventCode() {
        return EVENT_CODE;

    }

    /**
     * Get the value of the event
     * 
     * @return Value of event
     */
    public int getValue() {
        return VALUE;
    
    }

    public boolean equals(EventData other){
        if (other == null) {
            return false;
        }

        return (
            Arrays.equals(TIME, other.TIME) &&
            EVENT_TYPE.equals(other.EVENT_TYPE) &&
            EVENT_CODE.equals(other.EVENT_CODE) &&
            VALUE == other.VALUE
        );
    }

    @Override
    public String toString() {
        return (
            "Input Event Info: \n" +
            "Seconds: " + TIME[0] + "\n" +
            "Microseconds: " + TIME[1] + "\n" +
            "Event Type: " + EVENT_TYPE + "\n" +
            "Event Code: " + EVENT_CODE + "\n" +
            "Value: " + VALUE + "\n"
        );
     
    }


}