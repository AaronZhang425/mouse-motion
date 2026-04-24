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
            throw new UnsupportedOperationException(
                "Windows not supported"
            );

        } else if (os.equals("linux")) {
            runner.setNewCommand("uname", "-m");
            String[] output = runner.runCommand().toArray(String[]::new);

            if (output.length > 0) {
                // TODO: do something
            }

             

        }

        // Remove later
        return null;

    }

}
