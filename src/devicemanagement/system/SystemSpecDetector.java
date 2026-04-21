package devicemanagement.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;

public class SystemSpecDetector {
    public static Stream<String> runCommand (
        String... commandTokens
    ) throws IOException {
        ProcessBuilder commandRunner = new ProcessBuilder(commandTokens);

        Process process = commandRunner.start();
        BufferedReader reader = process.inputReader();
        Stream<String> lines = reader.lines();

        process.destroyForcibly();

        return lines;

    }

}
