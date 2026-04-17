package devicemanagement;

import devicemanagement.SystemInfo.BitArchitecture;
import devicemanagement.SystemInfo.Endianness;

public class SystemInfo {
    private static Endianness endianness = null;

    private static BitArchitecture architecture = null;

    public enum Endianness {
        LITTLE_ENDIAN(),
        BIG_ENDIAN();

        private Endianness() {

        }

        public int getLeastSignificantByteIndex(byte[] arr) {
            return equals(BIG_ENDIAN) ? arr.length - 1 : 0;
            // return (arr.length - 1) * indexFactor;

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
                    "Both indexes must be positive and less than array length"
                );

            }

            int maxIndex = Math.max(index1, index2);
            int minIndex =  Math.min(index1, index2);

            return equals(BIG_ENDIAN) ? maxIndex : minIndex;

            // return (
            //     (Math.abs(index2 - index1) * indexFactor)
            //     + Math.min(index1, index2)
            // );

        }

        public int getMostSignificantByteIndex(byte[] arr) {
            return equals(BIG_ENDIAN) ? 0 : arr.length - 1;
            // return (arr.length - 1) * (indexFactor - 1) * -1;

        }

        public int getMostSignificantByteIndex(
            byte[] arr,
            int index1,
            int index2
        ) throws IllegalArgumentException {
            if (
                (index1 < 0 || index2 < 0) 
                || (Math.max(index1, index2) < arr.length)
            ) {
                throw new IllegalArgumentException(
                    "Both indexes must be positive and less than array length"
                );

            }

            int maxIndex = Math.max(index1, index2);
            int minIndex =  Math.min(index1, index2);

            return equals(BIG_ENDIAN) ? minIndex : maxIndex;

            // return (
            //     Math.abs(index2 - index1) * (indexFactor - 1) * -1 
            //     + Math.min(index1, index2)
            // ); 

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

    /**
     * Sets the bit architecture of the machine
     * 
     * @param architecture
     */
    public static void setArchitecture(BitArchitecture architecture) {
        SystemInfo.architecture = architecture;

    }

}
