public enum EventTypes {
    SYN(0x00),

    KEY(0x01),

    REL(0x02) {
        enum EventCodes {
            X(0),
            Y(1),
            Z(2),
            RX(3),
            RY(4),
            RZ(5),
            HWWEEL(6),
            DIAL(7),
            WHEEL(8),
            MISC(9),
            RESERVED(10),
            WHEEL_HI_RES(11),
            HWEEL_HI_RES(12),
            MAX(16),
            CNT(EventCodes.MAX.getEventCodeValue() + 1);

            int eventCodeValue;

            EventCodes(int eventCodeValue) {
                this.eventCodeValue = eventCodeValue;
            }

            int getEventCodeValue() {
                return eventCodeValue;
            }           
        }
    },

    ABS(0x03),

    MSC(0x04),

    SW(0x05),

    LED(0x11),
    
    SND(0x12),
    
    REP(0x14),
    
    FF(0x15),
    
    PWR(0x16),
    
    FF_STATUS(0x17),
    
    MAX(0x1f),
    
    CNT(EventTypes.MAX.getEventCode() + 1),
    
    NONE(-1);

    private int eventTypeValue;

    private EventTypes(int eventTypeValue) {
        this.eventTypeValue = eventTypeValue;
    }
    
    public EventTypes getEventTypeByValue(int value) {
        for (EventTypes eventCode : EventTypes.values()) {
            if (eventCode.getEventCode() == value) {
                return eventCode;
            }
        }

        return EventTypes.NONE;
        
    }

    public int getEventCode() {
        return eventTypeValue;
    }

}
