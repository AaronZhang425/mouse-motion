package devicemanagement;

import devicemanagement.system.Endian;
import devicemanagement.system.SystemInfo;

/**
 * This class stores utility methods to convert a byte array into a long or 
 * int
 */
public class ByteArrayConversions {
    /**
     * Converts the byte array to an int. Uses byte order set in SystemInfo as
     * default.
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

    }
    
    /**
     * Convert several bytes in a byte array to an integer. If the start index
     * is less than the end index, big endian will be applied. Otherwise,
     * the number will be interpeted as little endian
     * 
     * @param arr The array containing bytes
     * @param msbIndex the index of the most significant byte
     * @param lsbIndex the index of the least significant byte
     * @return the int represented by the byte array
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

    /**
     * Calculates the int representation of the bytes of the array in the index 
     * range according to the endianness.
     * 
     * @param arr Array containing the bytes
     * @param msbIndex Index of the most significant byte
     * @param lsbIndex Index of the least significant byte
     * @return the int represented by the index range according to the endian
     */
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

    /**
     * Converts a section of a byte array in little endian to an int
     * 
     * @param arr Arrray containing the bytes
     * @param msbIndex The index of the most significant byte in the array
     * @param lsbIndex The index of the least significant byte in the array
     * @return The int represented by the section in the array in little endian
     */
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
    
    /**
     * Converts an array of bytes its long representation according to the
     * endian set in SystemInfo class.
     * 
     * @param arr Array to convert a long
     * @return The long that represents the byte array.
     */
    public static long toLong(byte[] arr) {
        return (
            SystemInfo.getSystemInfo().getEndian().equals(Endian.LITTLE_ENDIAN)
            ? toLongLittleEndian(arr, arr.length - 1, 0)
            : toLongBigEndian(arr, 0, arr.length - 1)
        );
        
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