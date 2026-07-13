package inputmangement.devicemanagement.system;

public enum CpuWordSize {
    SIZE_32_BITS(32),
    SIZE_64_BITS(64);

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
    private CpuWordSize(int bits) {
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
    public static CpuWordSize cpuWordSizeFromBits(int bits) {
        return switch (bits) {
            case 32 -> {
                yield SIZE_32_BITS;
            }

            case 64 -> {
                yield SIZE_64_BITS;
            }
            
            default -> {
                throw new IllegalArgumentException(
                    "Bit architecture not supported"
                );
            }
            
        };

    }

    @Override
    public String toString() {
        return this.name();

    }
    
}