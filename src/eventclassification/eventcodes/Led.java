package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventCategory;

public enum Led implements EventCategory {
    TEMP(0);    

    private final int value;
    private static final HashMap<Integer, Led> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Led eventCode : Led.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Led(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    public static Led fromValue(int value) {
        return (Led) VALUE_MAP.get(value);
    }



}
