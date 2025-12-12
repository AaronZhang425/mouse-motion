package eventclassification;

import eventclassification.eventcodes.*;
import java.util.HashMap;
import java.util.function.Function;

public enum EventTypes implements EventCategory{
    SYN(0x00),
    KEY(0x01),
    REL(0x02),
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

    private final int VALUE;
    // private final Class<? extends EventCategory> eventCodeSet;
    private static final HashMap<Integer, EventTypes> VALUE_MAP;
    private static final HashMap<EventTypes, Function<Integer, EventCategory>> EVENTCODES;

    static {
        VALUE_MAP = new HashMap<>();
        for (EventTypes eventCode : EventTypes.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

        EVENTCODES = new HashMap<>();

        // methods manually put in to avoid java reflection due to
        // memory overhead

        // Syn
        EVENTCODES.put(
            EventTypes.SYN, num -> {
                return Syn.fromValue(num);
            }
        );

        // Key
        EVENTCODES.put(
            EventTypes.KEY, num -> {
                return Key.fromValue(num);
            }
        );

    }

    private EventTypes(int value) {
        this.VALUE = value;
        // this.eventCodeSet = eventCodeSet;
    }

    @Override
    public int getValue() {
        return VALUE;
    }

    
    public static EventTypes fromValue(int value) {
        return (EventTypes) VALUE_MAP.get(value);
    }

}
