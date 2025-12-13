package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventCategory;

public enum Abs implements EventCategory {
    TEMP(0);    

    private final int value;
    private static final HashMap<Integer, Abs> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Abs eventCode : Abs.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Abs(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    public static Abs byValue(int value) {
        return (Abs) VALUE_MAP.get(value);
    }



}
