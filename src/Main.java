import devicemanagement.*;
import inputanalysis.*;
import eventclassification.*;
import eventclassification.eventcodes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {

    public static void main(String[] args) {       
        // InputReader input = new InputReader(5);

        // int num = Key.TEMP.getValue();
        System.out.println("Program running");

        ArrayList<InputDevice> devices = KernalInputDevices.getDevices();

        // EventTypes[] filter = {EventTypes.REL, EventTypes.MSC};
        HashMap<EventTypes, EventCode[]> fullCapabilitiesFilter = new HashMap<>();
        EventCode[] filter = {Rel.REL_X, Rel.REL_Y};
        EventCode[] eventCodeFilterMsc = null;
        
        fullCapabilitiesFilter.put(EventTypes.REL, filter);
        fullCapabilitiesFilter.put(EventTypes.MSC, eventCodeFilterMsc);

        ArrayList<InputDevice> filteredDeviceList = KernalInputDevices.getDevices(fullCapabilitiesFilter);
        // ArrayList<InputDevice> filteredDeviceList = KernalInputDevices.getDevices(filter);

        // InputDevice deviceToUse = filteredDeviceList.get(0);
        // Mouse mouse = new Mouse(deviceToUse, 1000);
        // System.out.println(deviceToUse.name());
        // System.out.println("Please wait");

        // try {
        //     Thread.sleep(2000);

        // } catch (Exception e) {
        //     System.out.println(e);

        // }
        
        
        // Thread tracker = new Thread(new MouseMotionTracker(mouse));

        // tracker.start();

        // for (int i = 0; i < filteredDeviceList.size(); i++) {
        //     InputDevice device = filteredDeviceList.get(i);

        //     System.out.println(device.name());
        //     Set<EventTypes> keySet = device.capabilities().keySet();

        //     for (EventTypes eventType : keySet) {
        //         System.out.println(eventType);
        //     }

        // }


        // int index = 8;

        // KernalInputDevices.update();
        // ArrayList<InputDevice> devices = KernalInputDevices.getDevices();
        // HashMap<EventTypes, EventCode[]> capabilities = devices.get(index).capabilities();
        // Set<EventTypes> keys = capabilities.keySet();

        // System.out.println(devices.get(index).name());
        // System.out.println(devices.get(index).handlerFile());

        // for (EventTypes eventType : keys) {
        //     System.out.println(eventType);
            
        //     EventCode[] eventCodes = capabilities.get(eventType);

        //     for (EventCode eventCode : eventCodes) {
        //         System.out.print(eventCode + " ");
        //     }

        //     System.out.println();

        // }



    }


}
