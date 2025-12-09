package devicemanagement;

import java.io.File;

// to be implemented
public record InputDevice(
    int[] id,
    String name,
    File physicalPath,
    File systemFileSystem
) {}
