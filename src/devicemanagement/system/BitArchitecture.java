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
}