package inputanalysis;

import java.util.concurrent.atomic.AtomicLongArray;

public class AtomicDoubleArray {
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

    public double set(int index, double value) {
        return atmoicArr.getAndSet(index, Double.doubleToLongBits(value));

    }

}
