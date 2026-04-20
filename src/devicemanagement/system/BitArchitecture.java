package devicemanagement.system;

public enum BitArchitecture {
    ARCH_32_BIT(32),
    ARCH_64_BIT(64);

    private final int bits;

    private BitArchitecture(int bits) {
        this.bits = bits;
    }

    public int getBits() {
        return bits;

    }

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