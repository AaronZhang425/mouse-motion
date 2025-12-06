package eventclassification.eventcodes;

import eventclassification.EventCategory;
import java.util.HashMap;

public enum Key implements EventCategory {
    TEMP(89324793);

    private final int value;
    private static final HashMap<Integer, Key> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Key eventCode : Key.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Key(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }           

    public static Key fromValue(int value) {
        return (Key) VALUE_MAP.get(value);
    }


}
