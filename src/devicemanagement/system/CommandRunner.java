package devicemanagement.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;

public class CommandRunner {
    private ProcessBuilder builder;
    private Process subProcess;

    private Stream<String> stdOut;
    private Stream<String> stdErr;

    public CommandRunner(String... commandTokens) {
        builder = new ProcessBuilder(commandTokens);

    }

    public Process getSubProcess() {
        return subProcess;
    
    }

    public ProcessBuilder getBuilder() {
        return builder;
    
    }

    public Stream<String> getStdOut() {
        return stdOut;

    }

    public Stream<String> getStdErr() {
        return stdErr;
        
    }
    
    public Stream<String> runCommand() throws IOException {
        subProcess = builder.start();
        
        BufferedReader stdOutReader = subProcess.inputReader();
        BufferedReader stdErrReader = subProcess.errorReader();

        stdOut = stdOutReader.lines();
        stdErr = stdErrReader.lines();

        stdOutReader.close();
        stdErrReader.close();
        
        return stdOut;
    }

    public void kill() {
        subProcess.destroy();

    }

    public void killForcibly() {
        subProcess.destroyForcibly();

    }

}
