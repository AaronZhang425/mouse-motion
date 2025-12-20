package devicemanagement;

import java.io.File;

import eventclassification.EventTypes;

// Record to store device details provided by linux
public record InputDevice(
    int[] id,
    String name,
    File physicalPath,
    File systemFileSystem,
    File handlerFile,
    EventTypes[] possibleEvents
) {}
