package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventTypes;

public enum Rel implements EventCode {
    REL_X(0x00),
    REL_Y(0x01),
    REL_Z(0x02),
    REL_RX(0x03),
    REL_RY(0x04),
    REL_RZ(0x05),
    REL_HWHEEL(0x06),
    REL_DIAL(0x07),
    REL_WHEEL(0x08),
    REL_MISC(0x09),

    REL_RESERVED(0x0a),
    REL_WHEEL_HI_RES(0x0b),
    REL_HWHEEL_HI_RES(0x0c);

    private final int value;
    private static final HashMap<Integer, Rel> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Rel eventCode : Rel.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Rel(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }
    
    @Override
    public EventTypes getEventType() {
        return EventTypes.REL;
    } 

    // @Override
    // public String getEventCodeName() {
    //     return "rel";
    // }

    public static Rel byValue(int value) {
        return (Rel) VALUE_MAP.get(value);
    }


}
