package devicemanagement.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.List;

public class CommandRunner {
    private ProcessBuilder builder;

    private Process subProcess = null;

    private List<String> output = null;

    public CommandRunner(String... commandTokens) {
        builder = new ProcessBuilder(commandTokens);
        builder.redirectErrorStream(true);

    }

    public void setNewCommand(String... commandTokens) {
        builder = new ProcessBuilder(commandTokens);

    }

    public Process getSubProcess() {
        return subProcess;
    
    }

    public ProcessBuilder getBuilder() {
        return builder;
    
    }
    
    public List<String> runCommand() throws IOException, InterruptedException {
        subProcess = builder.start();

        try (BufferedReader reader = subProcess.inputReader()) {
            output = reader.lines().toList();
            subProcess.waitFor();
            return output;

        } finally {
            if (subProcess.isAlive()) {
                subProcess.destroyForcibly();
            }

        }
        
    }


}
