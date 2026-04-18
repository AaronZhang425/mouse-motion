package devicemanagement;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import devicemanagement.system.*;
import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;


public class InputReader {
    /**
     * Represents the availablity state of the reader
     */
    private volatile boolean closed = false;

    /**
     * Represents the path of the event file to read
     */
    private File inputFile;

    /**
     * Buffered file reader object to read the stream of the event file
     */
    private BufferedInputStream reader;

    public InputReader(int eventNum) throws FileNotFoundException {
        this(new File("/dev/input/event" + eventNum));

    }

    public InputReader(String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }

    public InputReader(File file) throws FileNotFoundException {
        inputFile = file;
        
        reader = new BufferedInputStream(new FileInputStream(inputFile));
        

    }

    public boolean isClosed() {
        return closed;

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

    /**
     * Reads a single event reported by the /dev/inputx file
     * 
     * @return Single event
     */
    public EventData getEventData() {
        // TODO: Handle different byte architectures and endianness
        byte[] buffer = eventFileReader();

        int bufferSize = buffer.length;

        // Buffer will be null if file stream has ended. If file stream
        // ended, return null
        if (buffer == null || buffer.length == 0) {
            return null;

        }

        int lsbIndex = 0;
        int msbIndex = 0;
        
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

    /**
     * Splits an input event into memebers of the input_event struct in input.h
     * linux file.
     * 
     * @param buffer The buffer containing the data read
     * @return A jagged 2D array with each inner array representing a member
     */
    private byte[][] splitEventData(byte[] buffer) {
        int bufferLength = buffer.length;
        
        // Enforce buffer length to be 24 for 64 bit systems and 16 for 
        // 32 bit systems.
        if (bufferLength != 24 || bufferLength != 16) {
            throw new IllegalArgumentException(
                "Buffer size must be 24 or 16"
            );
        }

        // Define array size constants
        final int timeMemberSize = (bufferLength - 8) / 2;
        final int eventTypeSize = 2;
        final int eventCodeSize = 2;
        final int eventValueSize = 4;

        // Member collection
        byte[][] splitEventData = new byte[][]{
            new byte[timeMemberSize],
            new byte[timeMemberSize],
            new byte[eventTypeSize],
            new byte[eventCodeSize],
            new byte[eventValueSize]
        };

        int bufferIndex = 0;

        // Copy original buffer data into the inner arrays
        for (int row = 0; row < splitEventData.length; row++) {
            for (int col = 0; col < splitEventData[row].length; col++) {
                splitEventData[row][col] = buffer[bufferIndex];
                bufferIndex++;
            }

        }

        return splitEventData;

    }

    /**
     * Get the time of a given event report
     * 
     * @param buffer
     * @return
     */
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
    
    /**
     * Reads a single event report from the event file
     * 
     * @return Event report represented as a byte array
     */
    public byte[] eventFileReader() {
        if (closed) {
            return null;
        }


        // Each event is 24 bytes for 64 bit systesms. 16 for 32 bit systems
        int bufferSize = (
            SystemInfo.getSystemInfo().getArchitecture().equals(
                BitArchitecture.ARCH_64_BIT
            )
            ? 24 
            : 16
        );

        byte[] buffer = new byte[bufferSize];
        
        
        // try to get the event data from the file
        
        try {
            int bytesRead;
            int maxBytesRead = bufferSize;

            int bufferIndexOffset = 0;

            // Ensure a single entire event is read
            // Prevent events being sheered and cut in half
            while (bufferIndexOffset < bufferSize) {
                if (closed) {
                    reader.close();
                    return null;
                }

                bytesRead = reader.read(buffer, bufferIndexOffset, maxBytesRead);

                // The read method returns -1 if the stream has ended
                if (bytesRead == -1) {
                    return null;
                }
                
                bufferIndexOffset += bytesRead;
                maxBytesRead -= bytesRead;

            }
                   
        } catch(IOException e) {
            e.printStackTrace();
            return null;

        }
        
        // return the event data
        return buffer;
                    
    }

    /**
     * Closes the BufferedInputStream to prevent resource leak.
     * 
     * @throws IOException Throws IOException if IO error occurs
     */
    public void close() throws IOException {
        closed = true;

    }

}