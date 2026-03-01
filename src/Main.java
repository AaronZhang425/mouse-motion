import devicemanagement.*;
import eventclassification.*;
import eventclassification.eventcodes.*;
import inputanalysis.MouseMotionTracker;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {
    public static int DPI = 1000;
    public static void main(String[] args) {
        System.out.println("Program running");
        System.out.println();

        // Get mouse using filter
        HashMap<EventTypes, EventCode[]> fullCapabilitiesFilter = (
            new HashMap<>()
        );

        EventCode[] filter = {Rel.REL_X, Rel.REL_Y};
        EventCode[] eventCodeFilterMsc = {};
        
        fullCapabilitiesFilter.put(EventTypes.REL, filter);
        fullCapabilitiesFilter.put(EventTypes.MSC, eventCodeFilterMsc);

        ArrayList<InputDevice> filteredDeviceList = (
            KernalInputDevices.getDevices(fullCapabilitiesFilter)
        );

        MouseMotionTracker mouseTracker = null;

        try {
            Mouse mouse = new Mouse(filteredDeviceList.get(0), DPI);
            mouse.getDevice().getName();
            mouseTracker = new MouseMotionTracker(mouse);
            
        } catch (FileNotFoundException e) {
            System.out.println(e);
            
        }

        Thread mouseThread = null;

        // Create and start thread
        if (mouseTracker != null) {
            mouseThread = new Thread(mouseTracker, "Mouse Data Processor");
            mouseThread.start();

        }

        // Output mouse data
        // while (mouseTracker != null) {
        //     double[] motionData = mouseTracker.getDisplacement();


        //     System.out.printf(
        //         "X displacement: %5.4f \t Y displacement: %5.4f\n",
        //         motionData[0],
        //         motionData[1]
        //     );


        // }

    
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        System.out.println("Original Threads:");
        for (Thread thread : threadSet) {
            System.out.println(thread.getName());
        }

        System.out.println();
        // Thread termination testing
        mouseTracker.terminate();

        try {
            // Time bound termination; can be adjusted as needed
            mouseThread.join(500);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadSet = Thread.getAllStackTraces().keySet();

        System.out.println();
        System.out.println("Threads After Termination:");
        for (Thread thread : threadSet) {
            System.out.println(thread.getName());
        }
    

    }


}