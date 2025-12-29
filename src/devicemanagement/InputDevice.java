package devicemanagement;

import java.io.File;
import java.util.HashMap;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

// Record to store device details provided by linux
public record InputDevice(
    int[] id,
    String name,
    File physicalPath,
    File systemFileSystem,
    File handlerFile,
    HashMap<EventTypes, EventCode[]> capabilities
) {}
