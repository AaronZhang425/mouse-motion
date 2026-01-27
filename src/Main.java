import devicemanagement.*;
import eventclassification.*;
import eventclassification.eventcodes.*;
import inputanalysis.MouseMotionTracker;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {       
        // InputReader input = new InputReader(5);

        // int num = Key.TEMP.getValue();
        System.out.println("Program running");

        // ArrayList<InputDevice> devices = KernalInputDevices.getDevices();

        HashMap<EventTypes, EventCode[]> fullCapabilitiesFilter = new HashMap<>();
        EventCode[] filter = {Rel.REL_X, Rel.REL_Y};
        EventCode[] eventCodeFilterMsc = null;
        
        fullCapabilitiesFilter.put(EventTypes.REL, filter);
        fullCapabilitiesFilter.put(EventTypes.MSC, eventCodeFilterMsc);

        ArrayList<InputDevice> filteredDeviceList = KernalInputDevices.getDevices(fullCapabilitiesFilter);

        MouseMotionTracker mouseTracker = null;

        if (!filteredDeviceList.isEmpty()) {
            mouseTracker = new MouseMotionTracker(new Mouse(filteredDeviceList.get(0), 1000));
    
            Thread mouseThread = new Thread(mouseTracker);
            mouseThread.start();

        } else {
            System.out.println("No mouse has been detected");
            System.exit(0);
        }

        while (mouseTracker != null) {
            double[][] motionData = mouseTracker.getMotionData();

            System.out.printf(
                "X displacement: %5.4f \t Y displacement: %5.4f\n",
                motionData[0][0],
                motionData[0][1]
            );


        }
    

    }


}
