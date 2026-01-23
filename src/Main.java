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

        ArrayList<InputDevice> devices = KernalInputDevices.getDevices();

        HashMap<EventTypes, EventCode[]> fullCapabilitiesFilter = new HashMap<>();
        EventCode[] filter = {Rel.REL_X, Rel.REL_Y};
        EventCode[] eventCodeFilterMsc = null;
        
        fullCapabilitiesFilter.put(EventTypes.REL, filter);
        fullCapabilitiesFilter.put(EventTypes.MSC, eventCodeFilterMsc);

        ArrayList<InputDevice> filteredDeviceList = KernalInputDevices.getDevices(fullCapabilitiesFilter);

        if (filteredDeviceList.size() == 0) {
            throw new IndexOutOfBoundsException();
        }
        
        MouseMotionTracker mouseTracker = new MouseMotionTracker(new Mouse(filteredDeviceList.get(0), 1000));

        Thread mouseThread = new Thread(mouseTracker);
        mouseThread.start();
    
        // InputReader reader = new InputReader("/dev/input/event5");

        // while(true) {
        //     System.out.println(reader.getEventData());

        // }

    }


}
