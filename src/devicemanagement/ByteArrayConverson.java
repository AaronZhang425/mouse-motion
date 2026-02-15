package devicemanagement;

/**
 * This class stores utility methods to convert a byte array into a long or 
 * int
 */
public class ByteArrayConverson {
    public static int toInt(byte[] arr) {
        int num = 0;

        for (byte elem : arr) {
            num = (num << 8) | (elem & 0xFF);
        }

        return num;
    }
    
    // Given an array and a start and end index, convert the number to a int
    public static int toInt(
        byte[] arr,
        int startIdx,
        int endIdx
    ) {
        // Assumed endianness is dependent on the start index relative to the
        // end index
        return (
            startIdx < endIdx 
            ? toIntBigEndian(arr, startIdx, endIdx)
            : toIntLittleEndian(arr, startIdx, endIdx)
        );

    }

    private static int toIntBigEndian(byte[] arr, int startIdx, int endIdx) {
        int num = 0;
        
        for(int i = startIdx; i <= endIdx; i++) {
            num = (num << 8) | (arr[i] & 0xFF);
        }

        return num;
    }

    private static int toIntLittleEndian(byte[] arr, int startIdx, int endIdx) {
        int num = 0;
        
        for(int i = startIdx; i >= endIdx; i--) {
            num = (num << 8) | (arr[i] & 0xFF);
        }

        return num;
    }

    public static long  toLong(byte[] arr) {
        long num = 0;

        for (byte elem : arr) {
            num = (num << 8) | (elem & 0xFF);
        }

        return num;
    }
    
    public static long toLong(
        byte[] arr,
        int startIdx,
        int endIdx
    ) {
        return (
            startIdx < endIdx 
            ? toIntBigEndian(arr, startIdx, endIdx)
            : toIntLittleEndian(arr, startIdx, endIdx)
        );

    }

}