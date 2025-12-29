package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventTypes;

public enum Sw implements EventCode {
    SW_LID(0x00),
    SW_TABLET_MODE(0x01),
    SW_HEADPHONE_INSERT(0x02),
    SW_RFKILL_ALL(0x03),
    
    SW_MICROPHONE_INSERT(0X04),
    SW_DOCK(0X05),
    SW_LINEOUT_INSERT(0X06),
    SW_JACK_PHYSICAL_INSERT(0X07),
    SW_VIDEOOUT_INSERT(0X08),
    SW_CAMERA_LENS_COVER(0X09),
    SW_KEYPAD_SLIDE(0X0a),
    SW_FRONT_PROXIMITY(0x0b),
    SW_ROTATE_LOCK(0x0c),
    SW_LINEIN_INSERT(0x0d),
    SW_MUTE_DEVICE(0x0e),
    SW_PEN_INSERTED(0x0f),
    SW_MACHINE_COVER(0x10),
    SW_USB_INSERT(0x11);    

    private final int value;
    private static final HashMap<Integer, Sw> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Sw eventCode : Sw.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Sw(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    @Override
    public EventTypes getEventType() {
        return EventTypes.SW;
    } 

    // @Override
    // public String getEventCodeName() {
    //     return "sw";
    // }

    public static Sw byValue(int value) {
        return (Sw) VALUE_MAP.get(value);
    }



}
