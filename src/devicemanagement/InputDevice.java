package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;
import java.io.File;
import java.util.HashMap;

// Record to store device details provided by linux
public record InputDevice(
    int[] id,
    String name,
    File physicalPath,
    File systemFileSystem,
    File handlerFile,
    HashMap<EventTypes, EventCode[]> capabilities
) {}

// public class InputDevice() {
//     private int[] id;
//     private String name;
//     private File physicalPath;
//     private File systemFileSystem;
//     private File handlerFile;
//     private HashMap<EventTypes, EventCode[]> capabilities;

//     // public InputDevice() {

//     // }

//     public InputDevice(
//         int[] id,
//         String name,
//         File physicalPath,
//         File systemFileSystem,
//         File handlerFile,
//         HashMap<EventTypes, EventCode[]> capabilities
//     ) {
//         this.id = id;
//         this.name = name;
//         this.physicalPath = physicalPath;
//         this.systemFileSystem = systemFileSystem;
//         this.handlerFile = handlerFile;
//         this.capabilities = capabilities;

//     }

//     public int[] getId() {
//         return id;

//     }

//     public int[] id() {
//         return getId();

//     }

//     public String getName() {
//         return name;

//     }

//     public String name() {
//         return getName();

//     }
    
//     public File getPhysicalPath() {
//         return physicalPath;

//     }

//     public File physicalPath() {
//         return getPhysicalPath();

//     }
    
//     public File getSystemFileSystem() {
//         return systemFileSystem;

//     }
    
//     public File systemFileSystem() {
//         return getSystemFileSystem();

//     }

//     public File getHandlerFile() {
//         return handlerFile;

//     }

//     public File handlerFile() {
//         return getHandlerFile();

//     }

//     public HashMap<EventTypes, EventCode[]> getCapabilities() {
//         return capabilities;
//     }

//     public HashMap<EventTypes, EventCode[]> capabilities() {
//         return getCapabilities();

//     }


// }