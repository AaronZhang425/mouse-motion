package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

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

public record EventData (
    long[] time,
    EventTypes eventType,
    EventCode eventCode,
    int value
) {

    public String toString() {
        return (
            "Input Event Info: " + "\n" +
            "Seconds: " + time[0] + "\n" +
            "Microseconds: " + time[1] + "\n" +
            "Event Type: " + eventType + "\n" +
            "Event Code: " + eventCode + "\n" +
            "Value: " + value + "\n"
        );

    }

}

// public class EventData {
//     private long[] time;
//     private EventTypes eventType;
//     private EventCode eventCode;
//     private int value;

//     public EventData(long[] time, EventTypes eventType, EventCode eventCode, int value) {
//         this.time = time;
//         this.eventType = eventType;
//         this.eventCode = eventCode;
//         this.value = value;
//     }


//     public boolean equals(EventData other){
//         if (other == null) {
//             return false;
//         }

//         return (
//             Arrays.equals(time, other.time) &&
//             eventType.equals(other.eventType) &&
//             eventCode.equals(other.eventCode) &&
//             value == other.value
//         );
//     }

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