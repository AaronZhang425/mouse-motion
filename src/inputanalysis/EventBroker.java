package inputanalysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import devicemanagement.Mouse;
import eventclassification.eventcodes.EventCode;

public class EventBroker implements Runnable {
    /**
     * Flag to signal if running or preparing to stop
     */
    private volatile boolean run = true;

    /**
     * Maps event codes to a consumer
     */
    private HashMap<EventCode, InputEventConsumer> consumerMap;
    
    /**
     * Reads input events
     */
    private InputReader reader;

    public EventBroker(
        InputReader reader,
        HashMap<EventCode, InputEventConsumer> consumerMap
    ) {
        this.consumerMap = consumerMap;
        this.reader = reader;

    }

    public EventBroker(
        Mouse mouse,
        HashMap<EventCode, InputEventConsumer> consumerHashMap
    ) throws FileNotFoundException {

        this.consumerMap = consumerHashMap;
        
        reader = new InputReader(mouse.getDevice().getHandlerFile());

    }

    /**
     * Returns the hashmap that maps event codes to their respective event 
     * consumers
     * 
     * @return
     */
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
