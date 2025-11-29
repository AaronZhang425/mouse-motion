public enum EventTypes {
    SYN(0x00) {
        enum EventCodes {
            REPORT(0),
            CONFIG(1),
            MT_REPORT(2),
            DROPPED(3),
            MAX(4),
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

    KEY(0x01) {
        enum EventCodes {
            
        }
    },

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

    ABS(0x03) {
        enum EventCodes {
            
        }
    },

    MSC(0x04) {
        enum EventCodes {
            
        }
    },

    SW(0x05) {
        enum EventCodes {
            
        }
    },

    LED(0x11) {
        enum EventCodes {
            
        }
    },
    
    SND(0x12) {
        enum EventCodes {
            
        }
    },
    
    REP(0x14) {
        enum EventCodes {
            
        }
    },
    
    FF(0x15) {
        enum EventCodes {
            
        }
    },
    
    PWR(0x16) {
        enum EventCodes {
            
        }
    },
    
    FF_STATUS(0x17) {
        enum EventCodes {
            
        }
    },
    
    MAX(0x1f) {
        enum EventCodes {
            
        }
    },
    
    CNT(EventTypes.MAX.getEventCode() + 1) {
        enum EventCodes {
            
        }
    },
    
    NONE(-1);

    private int eventTypeValue;

    private EventTypes(int eventTypeValue) {
        this.eventTypeValue = eventTypeValue;
    }

    private EventTypes(byte[] arr) {
        eventTypeValue = ByteArrayConverson.toInt(arr);
    }

    private EventTypes(byte[] arr, int startIdx, int endIdx) {
        eventTypeValue = ByteArrayConverson.toInt(arr, startIdx, endIdx);
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
