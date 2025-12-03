package eventclassification.eventcodes;

import eventclassification.EventId;

public enum Syn implements EventId {
    REPORT(0),
    CONFIG(1),
    MT_REPORT(2),
    DROPPED(3);    

    private int value;

    private Syn(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }



}
