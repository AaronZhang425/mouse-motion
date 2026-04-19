package devicemanagement;

import devicemanagement.system.Endian;
import devicemanagement.system.SystemInfo;

/**
 * This class stores utility methods to convert a byte array into a long or 
 * int
 */
public class ByteArrayConversions {
    /**
     * Converts the byte array to an int. Uses byte order set in SystemInfo.
     * 
     * @param arr Byte array to convert to int
     * @return int representing byte in set byte order
     */
    public static int toInt(byte[] arr) {
        return (
            SystemInfo.getSystemInfo().getEndian().equals(Endian.LITTLE_ENDIAN)
            ? toIntLittleEndian(arr, arr.length - 1, 0)
            : toIntBigEndian(arr, 0, arr.length - 1)
        );

        // int num = 0;

        // for (byte elem : arr) {
        //     num = (num << 8) | (elem & 0xFF);
        // }

        // return num;
    }
    
    /**
     * Convert several bytes in a byte array to an integer. If the start index
     * is less than the end index, big endian will be applied. Otherwise,
     * the number will be interpeted as little endian
     * 
     * @param arr The array containing bytes
     * @param msbIndex the index of the most significant byte
     * @param lsbIndex the index of the least significant byte
     * @return the integer represented by the byte array
     */
    public static int toInt(
        byte[] arr,
        int msbIndex,
        int lsbIndex
    ) {
        // Assumed endianness is dependent on the start index relative to the
        // end index
        return (
            msbIndex < lsbIndex 
            ? toIntBigEndian(arr, msbIndex, lsbIndex)
            : toIntLittleEndian(arr, msbIndex, lsbIndex)
        );

    }

    private static int toIntBigEndian(
        byte[] arr,
        int msbIndex,
        int lsbIndex
    ) {
        int num = 0;
        
        for (int i = msbIndex; i <= lsbIndex; i++) {
            num = (num << 8) | (arr[i] & 0xFF);
        }

        return num;
    }

    private static int toIntLittleEndian(
        byte[] arr,
        int msbIndex,
        int lsbIndex
    ) {
        int num = 0;
        
        for (int i = msbIndex; i >= lsbIndex; i--) {
            num = (num << 8) | (arr[i] & 0xFF);
        }

        return num;
    }
    
    public static long toLong(
        byte[] arr,
        int msbIndex,
        int lsbIndex
    ) {
        return (
            msbIndex < lsbIndex 
            ? toIntBigEndian(arr, msbIndex, lsbIndex)
            : toIntLittleEndian(arr, msbIndex, lsbIndex)
        );

    }

    public static long toLong(byte[] arr) {
        return (
            SystemInfo.getSystemInfo().getEndian().equals(Endian.LITTLE_ENDIAN)
            ? toLongLittleEndian(arr, arr.length - 1, 0)
            : toLongBigEndian(arr, 0, arr.length - 1)
        );
        // long num = 0;

        // for (byte elem : arr) {
        //     num = (num << 8) | (elem & 0xFF);
        // }

        // return num;
    }
    
    private static long toLongBigEndian(
        byte[] arr,
        int msbIndex,
        int lsbIndex
    ) {
        long num = 0;
        
        for (int i = msbIndex; i <= lsbIndex; i++) {
            num = (num << 8) | (arr[i] & 0xFF);
        }

        return num;

    }

    public static long toLongLittleEndian(
        byte arr[],
        int msbIndex,
        int lsbIndex
    ) {
        long num = 0;
        
        for (int i = msbIndex; i >= lsbIndex; i--) {
            num = (num << 8) | (arr[i] & 0xFF);
        }

        return num;
    }

}