
import eventclassification.EventId;
import eventclassification.EventTypes;

public record EventData(
    long[] time,
    EventTypes eventType,
    EventId eventCode,
    int value
) {}