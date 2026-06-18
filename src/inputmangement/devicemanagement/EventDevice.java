package inputmangement.devicemanagement;

import inputmangement.eventclassification.EventTypes;
import inputmangement.eventclassification.eventcodes.EventCode;
import java.io.File;
import java.io.UncheckedIOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Class that wraps several pieces of data about a device. Do not replace with
 * a record to maintain support for older versions of java
 */
public class EventDevice {
    private final int[] ID;
    private final String NAME;
    private final File HANDLER_FILE;
    private final HashMap<EventTypes, EventCode[]> CAPABILITIES;

    public EventDevice(
        int[] id,
        String name,
        File handlerFile,
        HashMap<EventTypes, EventCode[]> capabilities
    ) {
        ID = id;
        NAME = name;
        
        try {
            HANDLER_FILE = new File(handlerFile.getCanonicalPath());

        } catch (IOException e) {
            throw new UncheckedIOException(e);

        }
        
        
        CAPABILITIES = new HashMap<>(capabilities);

    }

    public EventCode[] getEventCodes() {
        ArrayList<EventCode> eventCodeList = new ArrayList<>();

        // Loop through hashmap and add event codes to list
        for (Entry<EventTypes, EventCode[]> pair : CAPABILITIES.entrySet()) {
            eventCodeList.addAll(Arrays.asList(pair.getValue()));

        }

        return eventCodeList.toArray(EventCode[]::new);

    }


    /**
     * Gets an array of all event types that are atleast partially supported.
     * 
     * @return Array of EventTypes paritally supported by device.
     */
    public EventTypes[] getEventTypes() {
        return CAPABILITIES.keySet().toArray(EventTypes[]::new);

    }

    /**
     * Gets the id of the device as an array. First element of the array is the
     * bus ID; second is vendor ID; third is product ID; fourth is version ID
     * 
     * @return id array
     */
    public int[] getId() {
        return ID;

    }

    /**
     * Get name of device as reported by the kernel
     * 
     * @return name of device
     */
    public String getName() {
        return NAME;

    }

    /**
     * Gets the handler file of an event. The location of an event handler file
     * is most often located at /dev/input/eventX where X denotes any positive
     * integer including 0. While some devices will have handlers not named
     * eventX, this method will only get handlers named eventX and will return
     * null otherwise.
     * 
     * @return device handler file
     */
    public File getHandlerFile() {
        return HANDLER_FILE;

    }

    /**
     * Get the capabilities of device with event types mapping to an array
     * of event codes that the device is capable of. If the device is not 
     * capable of producing an event type, it will not be included in the keys
     * of the hashmap.
     * 
     * @return Mapping of capable event types to respective capable event codes 
     */
    public HashMap<EventTypes, EventCode[]> getCapabilities() {
        return CAPABILITIES;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            Arrays.hashCode(ID),
            NAME,
            HANDLER_FILE,
            CAPABILITIES
        );

    }

    /**
     * Returns true if both InputDevices have the same ID, name, handler file, 
     * and capabilities
     * 
     * @param other Input device to compare to
     * @return Equality of the 2 input devices
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;

        }

        if (!(other instanceof EventDevice)) {
            return false;

        }

        EventDevice otherEventDevice = (EventDevice) other;

        return (
            Arrays.equals(ID, otherEventDevice.ID)
            && NAME.equals(otherEventDevice.NAME)
            && HANDLER_FILE.equals(otherEventDevice.HANDLER_FILE)
            && CAPABILITIES.equals(otherEventDevice.CAPABILITIES)
        );


    }

    @Override
    public String toString() {
        return (
            "Bus: " + ID[0] + "\n" +
            "Vendor: " + ID[1] + "\n" +
            "Product: " + ID[2] + "\n" +
            "Version: " + ID[3] + "\n" +
            "Name: " + NAME + "\n" +
            "Handler: " + HANDLER_FILE

        );
    }


}