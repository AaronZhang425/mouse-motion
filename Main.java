import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            System.out.println("This odometer app does not support windows");
            return;
        }

        String inputDevDir = "/dev/input/";

        

        // String os = getOS();
        // System.out.println(os);
        // System.out.println();
    }

}
