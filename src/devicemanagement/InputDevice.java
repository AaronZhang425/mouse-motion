package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

// Record to store device details provided by linux
// public record InputDevice(
//     int[] id,
//     String name,
//     File physicalPath,
//     File systemFileSystem,
//     File handlerFile,
//     HashMap<EventTypes, EventCode[]> capabilities
// ) {}

public class InputDevice {
    private final int[] id;
    private final String name;
    private final File physicalPath;
    private final File systemFileSystem;
    private final File handlerFile;
    private final HashMap<EventTypes, EventCode[]> capabilities;

    public InputDevice(
        int[] id,
        String name,
        File physicalPath,
        File systemFileSystem,
        File handlerFile,
        HashMap<EventTypes, EventCode[]> capabilities
    ) {
        this.id = id;
        this.name = name;
        this.physicalPath = physicalPath;
        this.systemFileSystem = systemFileSystem;
        this.handlerFile = handlerFile;
        this.capabilities = capabilities;

    }

    public int[] getId() {
        return id;

    }

    @Deprecated
    public int[] id() {
        return getId();

    }

    public String getName() {
        return name;

    }

    @Deprecated
    public String name() {
        return getName();

    }
    
    public File getPhysicalPath() {
        return physicalPath;

    }

    @Deprecated
    public File physicalPath() {
        return getPhysicalPath();

    }
    
    public File getSystemFileSystem() {
        return systemFileSystem;

    }
    
    @Deprecated
    public File systemFileSystem() {
        return getSystemFileSystem();

    }

    public File getHandlerFile() {
        return handlerFile;

    }

    @Deprecated
    public File handlerFile() {
        return getHandlerFile();

    }

    public HashMap<EventTypes, EventCode[]> getCapabilities() {
        return capabilities;
    }

    @Deprecated
    public HashMap<EventTypes, EventCode[]> capabilities() {
        return getCapabilities();

    }

    public boolean equals(InputDevice other) {
        if (other == null) {
            return false;
        }

        return (
            Arrays.equals(id, other.id) &&
            name.equals(other.name) &&
            physicalPath.equals(other.physicalPath) &&
            systemFileSystem.equals(other.systemFileSystem) &&
            handlerFile.equals(other.handlerFile) &&
            capabilities.equals(other.capabilities)
        );
    }


}