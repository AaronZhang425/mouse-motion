package inputanalysis;

import java.io.FileNotFoundException;
import java.util.HashMap;

import devicemanagement.*;
import eventclassification.eventcodes.EventCode;
import eventclassification.eventcodes.Rel;

public class MouseMotionTracker {
    MouseCountsTracker xTracker;
    MouseCountsTracker yTracker;
    EventBroker eventBroker;

    public MouseMotionTracker(Mouse mouse) throws FileNotFoundException {
        xTracker = new MouseCountsTracker(mouse, Rel.REL_X);
        yTracker = new MouseCountsTracker(mouse, Rel.REL_Y);

        HashMap<EventCode, InputEventConsumer> eventConsumers = new HashMap<>();

        eventConsumers.put(Rel.REL_X, xTracker);
        eventConsumers.put(Rel.REL_X, yTracker);

        eventBroker = new EventBroker(
            new InputReader(mouse.getDevice().getHandlerFile()),
            eventConsumers
        );
    }
}