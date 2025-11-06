import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            System.out.println("This odometer app does not support windows");
            return;
        }

        Path inputDevicesDir = Paths.get("/dev/input/");
        Path inputDeviceInfo = Paths.get("/proc/bus/input/devies");

    

        // String os = getOS();
        // System.out.println(os);
        // System.out.println();
    }

}
