package devicemanagement.system;

public enum Endian {
    LITTLE_ENDIAN(),
    BIG_ENDIAN();

    private Endian() {

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
