package inputanalysis;

import java.util.HashMap;

import devicemanagement.InputReader;
import eventclassification.eventcodes.EventCode;

public class EventBroker implements Runnable{
    private volatile boolean run = true;

    private HashMap<EventCode, InputEventConsumer<?>> consumers;
    private InputReader reader;

    public EventBroker(InputReader reader, HashMap<EventCode, InputEventConsumer<?>> consumers) {
        this.consumers = consumers;
        this.reader = reader;

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Unimplemented");

    }

    


}
