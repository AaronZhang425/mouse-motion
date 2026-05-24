package inputmangement.devicemanagement.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandRunner {
    private ProcessBuilder builder;

    private Process process = null;

    private List<String> output = null;


    public CommandRunner(String... commandTokens) {
        builder = new ProcessBuilder(commandTokens);
        // Merge stderr with stdout
        builder.redirectErrorStream(true);

    }

    public void setNewCommand(String... commandTokens) {
        builder = new ProcessBuilder(commandTokens);

    }

    public Process getProcess() {
        return process;
    
    }

    public ProcessBuilder getBuilder() {
        return builder;
    
    }

    public List<String> getLastOutput() {
        if (output == null) {
            return null;
        }

        return new ArrayList<>(output);

    }
   
     public List<String> runCommand(
        long time,
        TimeUnit timeUnit
    ) throws IOException, InterruptedException {
        // Create a process
        process = builder.start();

        // Read output from processesor
        try (BufferedReader reader = process.inputReader()) {
            output = reader.lines().toList(); // Read
            
            // If the process does not finish in time, kill
            if (!process.waitFor(time, timeUnit)) {
                process.destroyForcibly();

            }

            return output;

        } finally {
            if (process.isAlive()) {
                process.destroyForcibly();

            }

        }
        
    }   

    public List<String> runCommand() throws IOException, InterruptedException {
        // Create a process
        process = builder.start();

        // Read output from processesor
        try (BufferedReader reader = process.inputReader()) {
            output = reader.lines().toList(); // Read
            process.waitFor(); // Wait for process to finish 
            return output;

        } finally {
            if (process.isAlive()) {
                process.destroyForcibly();
            }

        }
        
    }


}
