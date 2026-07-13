package inputmangement.devicemanagement.system;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SystemSpecDetector {
    private static CommandRunner runner = null;

    private static List<String> cpuInfo = null;

    private static CpuWordSize wordSize = (
        null
    );

    private static Endian endian = null;

    public static void runDetection() throws IOException, InterruptedException {
        runner = new CommandRunner("lscpu");
        
        // run the lscpu command to list info about cpu
        cpuInfo = runner.runCommand(2, TimeUnit.SECONDS);

        detectBitArchitecture();
        detectEndian();

    }

    public static CpuWordSize getBitArchitecture() {
        return wordSize;

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
                    wordSize = CpuWordSize.SIZE_64_BITS;

                } else if (str.matches(pattern32Bit)) {
                    wordSize = CpuWordSize.SIZE_32_BITS;
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
