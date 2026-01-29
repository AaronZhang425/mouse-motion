package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class InputReader {
    // Input file is intialized in the constructor
    // Represents the pseudofile that contains the input events of a device
    private File inputFile;
    private BufferedInputStream reader;

    public InputReader(int eventNum) {
        this(new File("/dev/input/event" + eventNum));

    }

    public InputReader(String filePath) {
        this(new File(filePath));
    }

    public InputReader(File file) {
        inputFile = file;
        
        try {
            reader = new BufferedInputStream(new FileInputStream(inputFile));
        
        } catch(FileNotFoundException e) {
            System.out.println(e);

        }
    }

    public EventData[] getSynReport() {
        // Create array list to store all non-syn events
        ArrayList<EventData> events = new ArrayList<>();
        
        // Get the first event
        EventData event = getEventData(); 

        // Keep adding events to the array list until the event is of type SYN
        while (!event.getEventType().equals(EventTypes.SYN)) {
            events.add(event);
            event = getEventData();
        }

        // Return the arraylist as an array
        return events.toArray(new EventData[events.size()]);

    }

    public EventData getEventData() {
        byte[] buffer = eventFileReader();
        
        // System.out.println();

        // Byte order of the buffer is assumed to be little endian
        long[] time = getEventTime(buffer);
        
        // Event types are represented by bytes 16-17 and are unsigned
        int eventTypeValue = ByteArrayConverson.toInt(buffer, 17, 16);
        
        // Event codes are represented by bytes 18-19 and are unsigned
        int eventCodeValue = ByteArrayConverson.toInt(buffer, 19, 18);

        // The value of the event are represented by bytes 20-23 and are signed
        int value = ByteArrayConverson.toInt(buffer, 23, 20);


        // Get event type constant by the event value obtained from event
        EventTypes eventType = EventTypes.byValue(eventTypeValue);

        // Get event code based on the event code value and the event type 
        EventCode eventCode = eventType.eventCodeByValue(eventCodeValue);

        return new EventData(
            time,
            eventType,
            eventCode,
            value
        );

    }

    private long[] getEventTime(byte[] buffer) {
        // bytes 8-15 represent fractions of a second in microseconds
        // assumed to be little endian (least significant byte on the left)
        long microSeconds = ByteArrayConverson.toLong(
            buffer,
            15,
            8
        );

        // bytes 0-7 represent unix time in seconds
        // assumed to be little endian (least significant byte on the left)
        long seconds = ByteArrayConverson.toLong(
            buffer,
            7,
            0
        );

        // put the unix seconds and microseconds representing fractions of a 
        // second as a long[] array and return
        // long[] time = {seconds, microSeconds};
        // return time;
        return new long[]{seconds, microSeconds};
    }
    
    public byte[] eventFileReader() {
        // Each event is composed of 24 bytes and writes bytes to buffer
        // If buffer is smaller than 24 on 64 bit system, an error will happen
        // Buffer should be 16 bytes if on 32 architecture 
        byte[] buffer = new byte[24];
        
        
        // try to get the event data from the file
        
        try {
            int bytesRead;
            int maxBytesRead = buffer.length;

            int bufferIndexOffset = 0;

            // Ensure a single entire event is read
            // Prevent events being sheered and cut in half
            while (bufferIndexOffset < buffer.length) {
                bytesRead = reader.read(buffer, bufferIndexOffset, maxBytesRead);

                if (bytesRead == -1) {
                    return null;
                }
                
                bufferIndexOffset += bytesRead;
                maxBytesRead -= bytesRead;

            }

            
        
        } catch(IOException e) {
            System.out.println(e);
            return null;

        }
        
        // return the event data
        return buffer;
                    
    }

    public void stop() {
        try {
            reader.close();

        } catch (IOException e) {
            System.out.println(e);

        }

    }

}