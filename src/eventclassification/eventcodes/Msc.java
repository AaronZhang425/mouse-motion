package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventCategory;

public enum Msc implements EventCategory {
    TEMP(0);    

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

    public static Msc fromValue(int value) {
        return (Msc) VALUE_MAP.get(value);
    }



}
