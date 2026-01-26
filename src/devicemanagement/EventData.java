package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;
import java.util.Arrays;

// Linux stores event data as a struct
// 
// time is represented as an inner struct to represent seconds and fractional
// seconds in microseconds
//
// eventType is represented with an unsigned short
//
// eventCode is represented with an unsigned short
//
// value is represented with a signed integer
//
// The implementation of representing this struct in java is done using an
// long[] array to represent time and enums for event type and code. The value
// of an event is stored as an int

// public record EventData (
//     long[] time,
//     EventTypes eventType,
//     EventCode eventCode,
//     int value
// ) {

//     public String toString() {
//         return (
//             "Input Event Info: " + "\n" +
//             "Seconds: " + time[0] + "\n" +
//             "Microseconds: " + time[1] + "\n" +
//             "Event Type: " + eventType + "\n" +
//             "Event Code: " + eventCode + "\n" +
//             "Value: " + value + "\n"
//         );

//     }

// }

public class EventData {
    private final long[] time;
    private final EventTypes eventType;
    private final EventCode eventCode;
    private final int value;

    public EventData(long[] time, EventTypes eventType, EventCode eventCode, int value) {
        this.time = time;
        this.eventType = eventType;
        this.eventCode = eventCode;
        this.value = value;
    }

    public long[] getTime() {
        return time;
    
    }

    @Deprecated
    public long[] time() {
        return getTime();
    }

    public EventTypes getEventType() {
        return eventType;

    }

    @Deprecated
    public EventTypes eventType() {
        return getEventType();
    }

    public EventCode getEventCode() {
        return eventCode;

    }

    @Deprecated
    public EventCode eventCode() {
        return getEventCode();
    
    }

    public int getValue() {
        return value;
    
    }

    @Deprecated
    public int value() {
        return getValue();

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