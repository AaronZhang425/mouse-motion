package devicemanagement;


import eventclassification.EventCategory;
import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KernalInputDevices {
    // This file lists all devices and their details
    private static final File INPUT_DEVICE_INFO = new File("/proc/bus/input/devices");
    // private final File INPUT_DEVICE_FOLDER = new File("/sys/class/input");

    // List of devices
    private static ArrayList<InputDevice> devices = new ArrayList<>();

    static {
        update();
    }

    // public KernalInputDevices() {
    //     System.out.println("Auto-update list");;
    //     update();
    // }

    
    // get devices with that have the event types listed in the parameters
    // to be implemented
    private static ArrayList<InputDevice> getDevices(HashMap<EventTypes, EventCode> fullCapabilities) {
        return new ArrayList<>(devices);
    }


    // get devices with that have the event types listed in the parameters
    // to be implemented
    public static ArrayList<InputDevice> getDeivces(ArrayList<EventTypes> eventTypes) {
        ArrayList<InputDevice> filtered = new ArrayList<>();
        
        for (InputDevice inputDevice : devices) {
            ArrayList<EventTypes> possibleEvents = new ArrayList<>(Arrays.asList(inputDevice.possibleEvents()));

            if (possibleEvents.containsAll(eventTypes)) {
                filtered.add(inputDevice);
            }

        }

        return filtered;
    }
    
    public static ArrayList<InputDevice> getDevices() {
        // System.out.println("Hello");
        // System.out.println(devices.size());
        return new ArrayList<>(devices);

    }

    // update list of devices
    public static void update() {

        List<String> lines = readDeviceList();
        System.out.println(lines.size());
        
        int[] id = new int[4];
        String name = null;
        File physicalPath = null;
        File systemFileSystem = null;
        File eventFile = null;
        EventTypes[] possiableEventTypes = null;


        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).toLowerCase();


            if (line.startsWith("i:")) {
                id = getDeviceId(line);

            } else if (line.startsWith("n")) {
                name = getDeviceName(line);

            } else if (line.startsWith("h")) {
                eventFile = getHandlers(line);

            } else if (line.startsWith("b: ev=")) {
                possiableEventTypes = getPossibleEvents(line);

            } else if (line.equals("")) {
                devices.add(new InputDevice(
                    id,
                    name,
                    physicalPath,
                    systemFileSystem,
                    eventFile,
                    possiableEventTypes
                ));                
            }

        }

    }

    private static String getDeviceName(String line) {
        String regex = "\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";

    }

    // to be implemented
    private static int[] getDeviceId(String line) {
        return new int[4];
    }

    private static File getHandlers(String line) {
        String regEx = "event[0-9]+";
        Pattern eventRegEx = Pattern.compile(regEx);
        Matcher matcher = eventRegEx.matcher(line);

        if (matcher.find()) {
            return new File("/dev/bus/input/" + matcher.group(0));

        }

        return null;



    }

    private static EventTypes[] getPossibleEvents(String line) {
        String regex = "(?<=ev=)[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (!matcher.find()) {
            return null;
        }

        String ev = matcher.group(0);
        // System.out.println(ev);

        int bitMap = 0;

        int bitShiftAmount = 0;

        int index = ev.length() - 1;
    
        while (index >= 0) {
            int num = Character.digit(ev.charAt(index), 16);
            bitMap |= (num << 4 * bitShiftAmount);

            index--;
            bitShiftAmount++;

        }

        // System.out.println("BitMap: " + bitMap);
        // String binaryString = Integer.toBinaryString(bitMap);
        // String paddedBinaryString = String.format("%32s", binaryString).replaceAll(" ", "0");
        // System.out.println("Padded binary string: " + paddedBinaryString);

        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < Integer.SIZE; i++) {
            // System.out.println((bitMap & 1) == 1);
            if ((bitMap & 1) == 1) {
                indices.add(i);
                // System.out.println("Index value: " + i);
            }

            bitMap >>>= 1;

        }

        EventTypes[] possibleEvents = new EventTypes[indices.size()];

        for (int i = 0; i < indices.size(); i++) {
            possibleEvents[i] = EventTypes.byValue(indices.get(i));
        }

        return possibleEvents;
    }

    private static List<String> readDeviceList() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(INPUT_DEVICE_INFO)
        )) {

            return reader.lines().toList();

        } catch (IOException error) {
            System.out.println(INPUT_DEVICE_INFO + " cannnot be read");
            return new ArrayList<>();

        }

    }

}
