package inputanalysis;

import java.util.ArrayDeque;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

public class InputFilter implements Runnable {
    EventTypes eventType;
    EventCode eventCode;
    InputReader reader;

    ArrayDeque<EventData> data;

    public InputFilter(InputReader reader, EventTypes eventType, EventCode eventCode) {
        this.reader = reader;
        this.eventType = eventType;
        this.eventCode = eventCode;

    }

    public InputFilter(InputReader reader, EventTypes eventType) {
        this.reader = reader;
        this.eventType = eventType;
    }

    @Override
    public void run() {

    }


}
