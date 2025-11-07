
import java.io.IOException;

import java.util.stream.Stream;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        if (isWindows()) {
            System.out.println("This odometer app does not support windows.");
            return;
        }

        // Path inputDevicesDir = Paths.get("/dev/input/");
        Path inputDeviceInfo = Paths.get("/proc/bus/input/devices");
        // Path inputDeviceInfo = Paths.get("/siudhgoaeruihgoiuhg");

        if (!Files.isReadable(inputDeviceInfo)) {
            System.out.println(inputDeviceInfo + " file was not found");
            return;
        }

        // Stream<String> content = null;

        try {
            Stream<String> content = Files.lines(inputDeviceInfo);
            List<String> lines = content.toList();
        
            for (int i = 0; i < lines.size(); i++) {
                System.out.println(lines.get(i));

            }

        } catch (IOException error) {
            System.out.println("Cannot read file");
            return;

        }



        System.out.println("Hello");

        // String os = getOS();
        // System.out.println(os);
        // System.out.println();
    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.startsWith("windows");
    }

}
