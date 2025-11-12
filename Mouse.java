
import java.io.IOException;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

// import java.util.stream.Stream;

public class Mouse {
    private final File inputDeviceInfo = new File("/proc/bus/input/devices");
    private List<String> lines;
    private File mouseHandlerFile;

    public Mouse() {
        if (isWindows()){
            System.out.println("This app cannot run on windows.");
            return;
        }

        try {
            BufferedReader reader = (
                new BufferedReader(new FileReader(inputDeviceInfo))
            );

            lines = reader.lines().toList();

        
        } catch (IOException error) {
            System.out.println(inputDeviceInfo + " cannnot be read");
            return;
        } 

        parseMouseDriver();

    }

    public List<String> getLines() {
        return lines;
    }

    public File getMouseHandlerFile() {
        return mouseHandlerFile;
    }

    public void parseMouseDriver() {
        int i = 0;

        while (i < lines.size()) {
            String line = lines.get(i).toLowerCase();

            if (line.matches(".*mouse.*") && line.startsWith("n")) {
                String handlerLine = lines.get(i + 4);

                System.out.println(lines.get(i + 4));
                break;
            }

            i++;
        }


        mouseHandlerFile = new File("/dev/bus/input/event0");

    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.startsWith("windows");
    }

}
