package devicemanagement.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.List;

public class SystemSpecDetector {
    private static CommandRunner runner = null;

    private static List<String> cpuInfo = null;

    private static BitArchitecture bitArchitecture = (
        null
    );

    private static Endian endian = null;

    public static void runDetection() throws IOException, InterruptedException {
        runner = new CommandRunner("lscpu");
        
        // run the lscpu command to list info about cpu
        cpuInfo = runner.runCommand();

        Process subProcess = runner.getSubProcess();

        // If the process does not complete in 2 seconds, kill it.
        if (!subProcess.waitFor(2, TimeUnit.SECONDS)) {
            subProcess.destroyForcibly();

        }

        detectBitArchitecture();
        detectEndian();

    }

    public static BitArchitecture getBitArchitecture() {
        return bitArchitecture;

    }


    public static Endian getEndian() {
        return endian;

    }

    private static void detectBitArchitecture() {
        // Op-modes can include both 32 and 64 bits. If 64-bit is listed,
        // assign to 64 bit. If only 32 bit is listed, assign to 32 bit
        cpuInfo.forEach(
            (str) -> {
                str = str.toLowerCase();
                
                String pattern64Bit = ".*cpu op-mode.*64-bit.*";
                String pattern32Bit = "(?m).*cpu op-mode.*32-bit\\s*$";

                if (str.matches(pattern64Bit)) {
                    bitArchitecture = BitArchitecture.ARCH_64_BIT;

                } else if (str.matches(pattern32Bit)) {
                    bitArchitecture = BitArchitecture.ARCH_32_BIT;
                }
            }
        );

    }

    private static void detectEndian() {
        // Search through the cpu information for endian and set detected
        // endian.
        cpuInfo.forEach(
            (str) -> {
                str = str.toLowerCase();
                if (str.matches(".*byte order.*little endian")) {
                    endian = Endian.LITTLE_ENDIAN;

                } else if (str.matches(".*byte order.*big endian")) {
                    endian = Endian.BIG_ENDIAN;

                }
            }
        );

    }

}
