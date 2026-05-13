import devicemanagement.*;
import devicemanagement.system.*;
import eventclassification.eventcodes.*;
import inputanalysis.singletracker.MouseMotionTracker;

import java.io.FileNotFoundException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    public static int DPI = 1000;
    public static void main(String[] args) {
        System.out.println("Program running");
        System.out.println();

        // Detect the cpu data
        try {
            SystemSpecDetector.runDetection();
            
        } catch (Exception e) {
            System.out.println(e);
            throw new UndeclaredThrowableException(e);

        }

        BitArchitecture detectedBits = SystemSpecDetector.getBitArchitecture();
        Endian detectedEndian = SystemSpecDetector.getEndian();

	    // Set up information about machine
        SystemInfo.setUpInfo(
            detectedBits,
            detectedEndian
        );

        ArrayList<EventDevice> filteredDeviceList = (
            EventDevicesManager.getDevices(
                (device) -> {
                    List<EventCode> eventCodes = Arrays.asList(device.getEventCodes());

                    return (
                        eventCodes.contains(Rel.REL_X)
                        && eventCodes.contains(Rel.REL_Y)
                    );

                } 
        
            )
        );
        

        MouseMotionTracker mouseTracker = null;

        try {
            Mouse mouse = new Mouse(filteredDeviceList.get(0), DPI);
            System.out.println(mouse.getDevice().getName());
            mouseTracker = new MouseMotionTracker(mouse);
            mouseTracker.start();
            
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
