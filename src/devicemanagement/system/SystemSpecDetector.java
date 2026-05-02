package devicemanagement.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.stream.Stream;

public class SystemSpecDetector {
    private static CommandRunner runner;
    private static Stream<String> cpuInfo;

    private static BitArchitecture bitArchitecture = (
        BitArchitecture.ARCH_32_BIT
    );

    private static Endian endian = null;

    static {
        runner = new CommandRunner("lscpu");
        
        try {
            runner.runCommand();
            
        } catch (IOException e) {
            // TODO: handle exception
            throw new UncheckedIOException(e);

        }
        
        
        cpuInfo = runner.getStdOut();
        runner.killForcibly();

    }

    // public static BitArchitecture getArchitecture() {
    //     // BitArchitecture bits;

    //     // Op-modes can include both 32 and 64 bits. If 64 bits exist, choose
    //     // 64 bits. Otherwise, default to 32 bits.
    //     System.out.print(cpuInfo.count());
    //     cpuInfo.forEach(
    //         (str) -> {
    //             str = str.toLowerCase();
    //             if (str.matches(".*cpu op-mode.*64")) {
    //                 bitArchitecture = BitArchitecture.ARCH_64_BIT;

    //             }
    //         }
    //     );

    //     return bitArchitecture;

    // }

    // public static Endian getEndian() {
    //     // Endian endian;

    //     cpuInfo.forEach(
    //         (str) -> {
    //             str = str.toLowerCase();
    //             if (str.matches(".*byte order.*little endian")) {
    //                 endian = Endian.LITTLE_ENDIAN;

    //             } else if (str.matches(".*byte order.*big endian")) {
    //                 endian = Endian.BIG_ENDIAN;

    //             }
    //         }
    //     );

    //     return endian;

    // }

}
