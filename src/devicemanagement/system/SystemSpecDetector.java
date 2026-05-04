package devicemanagement.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.TimeUnit;
import java.util.List;

public class SystemSpecDetector {
    private static CommandRunner runner;

    private static List<String> cpuInfo = null;

    private static BitArchitecture bitArchitecture = (
        null
    );

    private static Endian endian = null;

    public static void runDetection() throws IOException, InterruptedException {
        runner = new CommandRunner("lscpu");
        
        cpuInfo = runner.runCommand();

        Process subProcess = runner.getSubProcess();

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
        // Op-modes can include both 32 and 64 bits. If 64 bits exist, choose
        // 64 bits. Otherwise, default to 32 bits.
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
