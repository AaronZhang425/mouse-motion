package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

public record EventData (
    long[] time,
    EventTypes eventType,
    EventCode eventCode,
    int value
) {

    public EventData() {
        long[] defaultTime = {0, 0};
        this(defaultTime, null, null, 0);
    }

}