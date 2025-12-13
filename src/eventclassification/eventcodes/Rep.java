package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventCategory;

public enum Rep implements EventCategory {
    TEMP(0);    

    private final int value;
    private static final HashMap<Integer, Rep> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Rep eventCode : Rep.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Rep(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    public static Rep byValue(int value) {
        return (Rep) VALUE_MAP.get(value);
    }



}
