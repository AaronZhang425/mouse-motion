import devicemanagement.*;
import devicemanagement.system.*;
import eventclassification.*;
import eventclassification.eventcodes.*;
import inputanalysis.MouseMotionTracker;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Main {
    public static int DPI = 1000;
    public static void main(String[] args) {
        System.out.println("Program running");
        System.out.println();

	// Set up information about machine
        SystemInfo.setUpInfo(
            BitArchitecture.ARCH_64_BIT,
            Endian.LITTLE_ENDIAN
        );

        ArrayList<EventDevice> filteredDeviceList = (
            EventDevicesManager.getDevices(
                (device) -> {
                    List<EventCode> eventCodes = Arrays.asList(device.getEventCodes());

                    if (
                        eventCodes.contains(Rel.REL_X) 
                        && eventCodes.contains(Rel.REL_Y)
                    ) {
                        return true;

                    }

                    return false;

                } 
        
            )
        );
        

        MouseMotionTracker mouseTracker = null;

        try {
            Mouse mouse = new Mouse(filteredDeviceList.get(0), DPI);
            System.out.println(mouse.getDevice().getName());
            mouseTracker = new MouseMotionTracker(mouse);
            
        } catch (FileNotFoundException e) {
            System.out.println(e);
            
        }

        // Output mouse data
        while (mouseTracker != null) {
            double[] motionData = mouseTracker.getDisplacement();

            System.out.printf(
                "X displacement: %5.4f \t Y displacement: %5.4f\n",
                motionData[0],
                motionData[1]
            );


        }

    
        // Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        // System.out.println("Original Threads:");
        // for (Thread thread : threadSet) {
        //     System.out.println(thread.getName());
        // }

        // System.out.println();
        // // Thread termination testing
        // mouseTracker.terminate();

        // threadSet = Thread.getAllStackTraces().keySet();

        // System.out.println();
        // System.out.println("Threads After Termination:");
        // for (Thread thread : threadSet) {
        //     System.out.println(thread.getName());
        // }
    

    }


}
