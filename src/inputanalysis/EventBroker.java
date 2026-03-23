package inputanalysis;

import java.io.IOException;
import java.util.HashMap;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import eventclassification.eventcodes.EventCode;

public class EventBroker implements Runnable{
    private volatile boolean run = true;

    private HashMap<EventCode, InputEventConsumer> consumerMap;
    private InputReader reader;

    public EventBroker(InputReader reader, HashMap<EventCode, InputEventConsumer> consumerMap) {
        this.consumerMap = consumerMap;
        this.reader = reader;

    }

    public HashMap<EventCode, InputEventConsumer> getConsumers() {
        return consumerMap;
    
    }

    /**
     * Prepares the thread to stop and finish the thread loop
     */
    public void terminate() {
        run = false;

        try {
            reader.close(); // Stop file reader to prevent resource leaks
            
        } catch (IOException e) {
            System.out.println(e);
            System.out.println(e.getCause());

        } catch (NullPointerException e) {
            throw new NullPointerException("Check the permissions of /dev/input");

        }
    }

    public boolean isRunning() {
        return run;
    }    

    @Override
    public void run() {
        EventData data = null;

        while (run) {
            data = reader.getEventData();

            if (data == null) {
                continue;
            }


            InputEventConsumer eventConsumer = consumerMap.get(data.getEventCode());

            if (eventConsumer != null) {
                eventConsumer.consume(data);

            }

        }

    }    


}
