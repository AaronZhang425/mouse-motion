package devicemanagement.system;

public class SystemInfo {
    private static SystemInfo instance;

    private final BitArchitecture architecture;
    private final Endian endian;

    private SystemInfo(BitArchitecture architecture, Endian endian) {
        this.architecture = architecture;
        this.endian = endian;

    }

    public static void setUpInfo(
        BitArchitecture architecture,
        Endian endian
    ) {
        if (instance != null) {
            throw new UnsupportedOperationException(
                "System properties cannot be set up more than once",
                new InstantiationError("SystemInfo is a singleton")
            );
            
        }

        instance = new SystemInfo(architecture, endian);
    
    }

    public static synchronized SystemInfo getSystemInfo() {
        return instance;
    }

    /**
     * Gets the assigned endianness of the system
     * 
     * @return
     */
    public Endian getEndian() {
        return endian;

    }

    /**
     * Gets the bit architecture of the machine
     * 
     * @return Bit architecture
     */
    public BitArchitecture getArchitecture() {
        return architecture;

    }

}
