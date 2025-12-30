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

        int index = 8;

        KernalInputDevices.update();
        ArrayList<InputDevice> devices = KernalInputDevices.getDevices();
        HashMap<EventTypes, EventCode[]> capabilities = devices.get(index).capabilities();
        Set<EventTypes> keys = capabilities.keySet();

        System.out.println(devices.get(index).name());
        System.out.println(devices.get(index).handlerFile());

        for (EventTypes eventType : keys) {
            System.out.println(eventType);
            
            EventCode[] eventCodes = capabilities.get(eventType);

            for (EventCode eventCode : eventCodes) {
                System.out.print(eventCode + " ");
            }

            System.out.println();

        }


        // Kernel input device class testing
        // KernalInputDevices list = new KernalInputDevices();

        // list.update();

        // ArrayList<InputDevice> devices = list.getDevices();
        // System.out.println(devices.size());

        // InputDevice inputDevice = devices.get(5);

        // System.out.print(inputDevice.name());
        // for (EventTypes eventType : inputDevice.possibleEvents()) {
        //     System.out.print(eventType + " ");
        // }

        // System.out.println();
        // System.out.println(inputDevice.handlerFile());


        // for (InputDevice device : devices) {
        //     System.out.println(device.name());
            
        //     for (EventTypes eventType : device.possibleEvents()) {
        //         System.out.print(eventType + " ");
        //     }

        // }

        // while (true) {
        //     EventData inputData = input.getEventData();
        //     System.out.println(inputData);
        //     System.out.println();

        //     // byte[] buffer = input.eventFileReader();

        //     // for (byte elem : buffer) {
        //     //     System.out.print(elem + " ");
        //     // }
            
        //     // // System.out.println(num);


        // }


    }


}
