package EventCodes;

public enum Rel implements EventCode {
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
    HWEEL_HI_RES(12);

    private int eventCodeValue;

    private Rel(int eventCodeValue) {
        this.eventCodeValue = eventCodeValue;
    }

    public int getEventCodeValue(){
        return eventCodeValue;

    }           
}
