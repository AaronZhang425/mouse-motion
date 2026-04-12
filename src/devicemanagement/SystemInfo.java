package devicemanagement;

import javax.sound.midi.InvalidMidiDataException;

public class SystemInfo {
    private static Endianness endianness = null;

    private static BitArchitecture architecture = null;

    public enum Endianness {
        LITTLE_ENDIAN(0),
        BIG_ENDIAN(1);

        private final int indexFactor;

        private Endianness(int indexFactor) {
            this.indexFactor = indexFactor;

        }

        public int getLeastSignificantByteIndex(byte[] arr) {
            return (arr.length - 1) * indexFactor;

        }

        public int getLeastSignificantByteIndex(
            byte[] arr,
            int index1,
            int index2
        ) {
            // Throw error if either number is negative
            if (
                (index1 < 0 || index2 < 0) && 
                (Math.max(index1, index2) < arr.length)
            ) {
                throw new IllegalArgumentException(
                    "Both indexes must be positive"
                );

            }

            return (
                (Math.abs(index2 - index1) * indexFactor) +
                Math.min(index1, index2)
            );

        }

    }

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

    /**
     * Gets the assigned endianness of the system
     * 
     * @return
     */
    public static Endianness getEndianness() {
        return endianness;

    }

    /**
     * Sets the endianness of the system
     * 
     * @param endianness Endianness to set
     */
    public static void setEndianness(Endianness endianness) {
        SystemInfo.endianness = endianness;
    
    }

    /**
     * Gets the bit architecture of the machine
     * 
     * @return Bit architecture
     */
    public static BitArchitecture getArchitecture() {
        return architecture;

    }

    public static void setArchitecture(BitArchitecture architecture) {
        SystemInfo.architecture = architecture;

    }

}
