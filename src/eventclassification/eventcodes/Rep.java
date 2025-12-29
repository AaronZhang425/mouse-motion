package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventTypes;

public enum Rep implements EventCode {
    REP_DELAY(0x00),
    REP_PERIOD(0x01);    

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

    @Override
    public EventTypes getEventType() {
        return EventTypes.REP;
    }

    public String getEventCodeName() {
        return "rep";
    }

    public static Rep byValue(int value) {
        return (Rep) VALUE_MAP.get(value);
    }



}
