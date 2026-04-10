package devicemanagement;

public class SystemInfo {
    private SystemInfo instance = null;

    private boolean isLittleEndian = false;

    private BitArchitecture architecture = BitArchitecture.ARCH_64_BIT;

    private enum BitArchitecture {
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

    private SystemInfo() {

    }

    public SystemInfo getInstance() {
        if (instance == null) {
            instance = new SystemInfo();

        }

        return instance;

    }
}
