import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;

public class Odometer {
    Mice mice;
    int dpi;

    public Odometer(int dpi) {
        this.dpi = dpi;
    }

    public Odometer(int dpi, int eventNum) {
        this(dpi, new File("/dev/input/event" + eventNum));

    }

    public Odometer(int dpi, String filePath) {
        this(dpi, new File(filePath));
    }

    public Odometer(int dpi, File file) {
        this.dpi = dpi;
        mice = new Mice(file);
    }

    public EventData getEventData() {
        byte[] buffer = eventFileReader();

        getEventTime(buffer);
        int type = byteArrayToUnsignedInt(buffer, 16, 17);
        int eventCode = byteArrayToUnsignedInt(buffer, 18, 19);
        int value = byteArrayToSignedInt(buffer, 20, 23);

        return new EventData(
            getEventTime(buffer), type, eventCode, value
        );

    }

    private Time getEventTime(byte[] buffer) {
        long microSeconds = byteArrayToUnsignedLong(
            buffer,
            8,
            15
        );

        long seconds = byteArrayToUnsignedLong(
            buffer,
            0,
            7
        );


        return new Time(seconds, microSeconds);
    }
    
    public byte[] eventFileReader() {
        byte[] buffer = new byte[24];
        
        try {
            
            FileInputStream reader = new FileInputStream(
            mice.getMouseHandlerFile());
            
            reader.read(buffer);
            reader.close();
            
            // while (true) {
                // reader.read(buffer);
                
                // for (byte input : buffer) {
                    // System.out.print(input + ", ");
                    // }
                    
                    // System.out.println();
                    
                    // }
                    
        } catch (IOException error) {
            System.out.println(error);
            
        }
        
        return buffer;
                    
    }
                

    private long byteArrayToUnsignedLong(
        byte[] buffer,
        int startIdx,
        int endIdx
    ) {
    
        long unsingedLong = 0;
        
        for(int i = endIdx; i >= startIdx; i--) {
            unsingedLong = (unsingedLong << 8) | (buffer[i] & 0xFF);
        }

        return unsingedLong;

    }

    private int byteArrayToUnsignedInt(
        byte[] buffer,
        int startIdx,
        int endIdx
    ) {
    
        int unsingedInt = 0;
        
        for(int i = endIdx; i >= startIdx; i--) {
            unsingedInt = (unsingedInt << 8) | (buffer[i] & 0xFF);
        }

        return unsingedInt;

    }

    private int byteArrayToSignedInt(
        byte[] buffer,
        int startIdx,
        int endIdx
    ) {

        int signedInt = 0;

        for (int i = endIdx; i >= startIdx; i--) {
            signedInt = (signedInt << 8) | (buffer[i] & 0xFF);
        }

        return signedInt;
    }




}