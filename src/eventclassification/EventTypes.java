package eventclassification;

import java.util.HashMap;

import eventclassification.eventcodes.*;

public enum EventTypes implements EventCategory{
    SYN(0x00, Syn.class),
    KEY(0x01),
    REL(0x02, Rel.class),
    ABS(0x03),
    MSC(0x04),
    SW(0x05),
    LED(0x11),
    SND(0x12),
    REP(0x14),
    FF(0x15),
    PWR(0x16),
    FF_STATUS(0x17);

    // public abstract EventCategory getEventCodeSet();

    private final int value;
    private final Class<?> eventCodeSet;
    private static final HashMap<Integer, EventTypes> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (EventTypes eventCode : EventTypes.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private EventTypes(int value, Class<?> eventCodeSet) {
        this.value = value;
        this.eventCodeSet = eventCodeSet;
    }

    // temporary constructor
    private EventTypes(int value) {
        this.value = value;
        eventCodeSet = null;
        // this.eventCodeSet = eventCodeSet;
    }
    
    // public static EventTypes getEventTypeByValue(int value) {
    //     for (EventTypes eventCode : EventTypes.values()) {
    //         if (eventCode.getValue() == value) {
    //             return eventCode;
    //         }
    //     }

    //     return EventTypes.NONE;
        
    // }

    @Override
    public int getValue() {
        return value;
    }

    
    public static EventTypes fromValue(int value) {
        return (EventTypes) VALUE_MAP.get(value);
    }

}
