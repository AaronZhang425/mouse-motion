package devicemanagement.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;

public class SystemSpecDetector {
    private static CommandRunner runner;

    static {
        runner = new CommandRunner();
    }

    public static String getOs() {
        return System.getProperty("os.name").toLowerCase();

    }

    public static BitArchitecture getArchitecture() throws IOException {
        String os = getOs();
        
        if (os.contains("win")) {
            throw new UnsupportedOperationException();

        } else if (os.equals("linux")) {
            runner.setNewCommand("uname", "-m");
            Stream<String> output = runner.runCommand();

            // TODO: get cpu arch

        }

        // Remove later
        return null;

    }

}
