
import java.io.IOException;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.stream.Stream;
// import java.util.List;

// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        if (isWindows()) {
            System.out.println("This odometer app does not support windows.");
            return;
        }

        // Path inputDevicesDir = Paths.get("/dev/input/");
        File inputDeviceInfo = new File("/proc/bus/input/devices");
        // Path inputDeviceInfo = Paths.get("/proc/bus/input/devices");
        // Path inputDeviceInfo = Paths.get("/siudhgoaeruihgoiuhg");

        if (!inputDeviceInfo.canRead()) {
            System.out.println(inputDeviceInfo + " file was not found");
            return;
        }

        // Stream<String> content = null;
        try {
            BufferedReader reader = (
                new BufferedReader(new FileReader(inputDeviceInfo))
            );

            Stream<String> lines = reader.lines();



            for (String line : lines.toList()) {
                System.out.println(line);

            }

        
        } catch (IOException error) {
            System.out.println(inputDeviceInfo + " cannnot be read");
            return;
        } 


        // try {
        //     Stream<String> content = Files.lines(inputDeviceInfo);
        //     List<String> lines = content.toList();
        
        //     for (int i = 0; i < lines.size(); i++) {
        //         System.out.println(lines.get(i));

        //     }

        // } catch (IOException error) {
        //     System.out.println("Cannot read file");
        //     return;

        // }



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
