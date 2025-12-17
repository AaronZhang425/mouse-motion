package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventTypes;


public enum Led implements EventCode {
    LED_NUML(0x00),
    LED_CAPSL(0x01),
    LED_SCROLLL(0x02),
    LED_COMPOSE(0x03),
    LED_KANA(0x04),
    LED_SLEEP(0x05),
    LED_SUSPEND(0x06),
    LED_MUTE(0x07),
    LED_MISC(0x08),
    LED_MAIL(0x09),
    LED_CHARGING(0x0a);    

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

    @Override
    public EventTypes getEventType() {
        return EventTypes.LED;
    } 

    public static Led byValue(int value) {
        return (Led) VALUE_MAP.get(value);
    }



}
