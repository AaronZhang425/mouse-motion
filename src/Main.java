import devicemanagement.*;
import eventclassification.*;
import eventclassification.eventcodes.*;
import inputanalysis.MouseMotionTracker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {
    public static int DPI = 1000;
    public static void main(String[] args) {
        System.out.println("Program running");

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

        MouseMotionTracker mouseTracker = new MouseMotionTracker(new Mouse(filteredDeviceList.get(0), DPI));

        Thread mouseThread = new Thread(mouseTracker, "Mouse Data Processor");
        mouseThread.start();

        // while (mouseTracker != null) {
        //     double[] motionData = mouseTracker.getDisplacement();


        //     System.out.printf(
        //         "X displacement: %5.4f \t Y displacement: %5.4f\n",
        //         motionData[0],
        //         motionData[1]
        //     );


        // }

        // // Thread termination testing
        // mouseTracker.terminate();

        // try {
        //     // Time bound termination; can be adjusted as needed
        //     mouseThread.join(500);

        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        // System.out.println();
        // System.out.println("Threads");
        // for (Thread thread : threadSet) {
        //     System.out.println(thread.getName());
        // }
    

    }


}
