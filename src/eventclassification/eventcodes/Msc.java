package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventTypes;

public enum Msc implements EventCode {
    MSC_SERIAL(0x00),
    MSC_PULSELED(0x01),
    MSC_GESTURE(0x02),
    MSC_RAW(0x03),
    MSC_SCAN(0x04),
    MSC_TIMESTAMP(0x05);    

    private final int value;
    private static final HashMap<Integer, Msc> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Msc eventCode : Msc.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Msc(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    @Override
    public EventTypes getEventType() {
        return EventTypes.MSC;
    } 

    public static Msc byValue(int value) {
        return (Msc) VALUE_MAP.get(value);
    }



}
