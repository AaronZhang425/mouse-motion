package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventTypes;

public enum Syn implements EventCode {
    SYN_REPORT(0),
    SYN_CONFIG(1),
    SYN_MT_REPORT(2),
    SYN_DROPPED(3);    

    private final int value;
    private static final HashMap<Integer, Syn> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Syn eventCode : Syn.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Syn(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    @Override
    public EventTypes getEventType() {
        return EventTypes.SYN;
    } 

    // @Override
    // public String getEventCodeName() {
    //     return "syn";
    // }

    public static Syn byValue(int value) {
        return (Syn) VALUE_MAP.get(value);
    }



}
