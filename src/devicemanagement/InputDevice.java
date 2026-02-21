package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class that wraps several pieces of data about a device. Do not replace with
 * a record to maintain support for older versions of java
 */
public class InputDevice {
    private final int[] id;
    private final String name;
    private final File handlerFile;
    private final HashMap<EventTypes, EventCode[]> capabilities;

    public InputDevice(
        int[] id,
        String name,
        File handlerFile,
        HashMap<EventTypes, EventCode[]> capabilities
    ) {
        this.id = id;
        this.name = name;
        this.handlerFile = handlerFile;
        this.capabilities = capabilities;

    }

    /**
     * Gets the id of the device as an array. First element of the array is the
     * bus ID; second is vendor ID; third is product ID; fourth is version ID
     * 
     * @return id array
     */
    public int[] getId() {
        return id;

    }

    /**
     * Get name of device as reported by the kernel
     * 
     * @return name of device
     */
    public String getName() {
        return name;

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
        return handlerFile;

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
        return capabilities;
    }

    /**
     * Returns true if 2 InputDevices have the same ID, name, handler file, and
     * capabilities
     * 
     * @param other Input device to compare to
     * @return Equality of the 2 input devices
     */
    public boolean equals(InputDevice other) {
        if (other == null) {
            return false;
        }

        return (
            Arrays.equals(id, other.id) &&
            name.equals(other.name) &&
            handlerFile.equals(other.handlerFile) &&
            capabilities.equals(other.capabilities)
        );
    }

    @Override
    public String toString() {
        return (
            "Bus: " + id[0] + "\n" +
            "Vendor: " + id[1] + "\n" +
            "Product: " + id[2] + "\n" +
            "Version: " + id[3] + "\n" +
            "Name: " + name + "\n" +
            "Handler: " + handlerFile

        );
    }


}