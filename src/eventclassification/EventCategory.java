package eventclassification;

import java.util.HashMap;

public interface EventCategory {
    public static HashMap<Integer, EventCategory> VALUE_MAP = new HashMap<>();

    public int getValue();

    public static EventCategory byValue(int value) {
        return VALUE_MAP.get(value);
    }
    
}