package inputanalysis;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * An AtomicLongArray wrapper to represent atomic double arrays.
 */
public class AtomicDoubleArray {
    /**
     * Represents doubles by putting the bits into a long
     */
    private AtomicLongArray atmoicArr;

    public AtomicDoubleArray(double[] arr) {
        int length = arr.length;
        
        long[] doubleBitRepresentations = new long[length];

        for (int i = 0; i < length; i++) {
            doubleBitRepresentations[i] = Double.doubleToLongBits(arr[i]);
        }

        atmoicArr = new AtomicLongArray(doubleBitRepresentations);

    }

    /**
     * Gets the length of the atomic array
     * 
     * @return length
     */
    public int length() {
        return atmoicArr.length();

    }

    /**
     * Gets the item at the specified index
     * 
     * @param index index of item
     * @return The item at the index
     */
    public double get(int index) {
        return Double.longBitsToDouble(atmoicArr.get(index));

    }

    /**
     * Gets the value originally at the index and store a new value at the
     * index
     * 
     * @param index Index at which the new value will be replace
     * @param value The value that will replace the original value
     * @return The original value at the index
     */
    public double set(int index, double value) {
        return atmoicArr.getAndSet(index, Double.doubleToLongBits(value));

    }

}
