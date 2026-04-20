package devicemanagement.system;

public enum BitArchitecture {
    ARCH_32_BIT(32),
    ARCH_64_BIT(64);

    /**
     * Represents the number of bits CPU register can handle at once
     */
    private final int bits;

    /**
     * Creats a BitArchitecture object and sets the register size based on 
     * the bit archecture enum created.
     * 
     * @param bits
     */
    private BitArchitecture(int bits) {
        this.bits = bits;
    }

    /**
     * Gets the number of bits supported by system.
     * 
     * @return
     */
    public int getBits() {
        return bits;

    }

    /**
     * Create a BitArchitecture object based on the bits passed
     * 
     * @param bits Bit architecture of CPU
     * @return The bit architecture that represents the number of bits
     */
    public static BitArchitecture bitArchitectureFromBits(int bits) {
        return switch (bits) {
            case 32 -> {
                yield ARCH_32_BIT;
            }

            case 64 -> {
                yield ARCH_64_BIT;
            }
            
            default -> {
                throw new IllegalArgumentException(
                    "Bit architecture not supported"
                );
            }
            
        };

    }
}