package inputanalysis;

import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.lang.invoke.MethodHandles;

public class AtomicDoubleArrayVarHandles {
    private static final VarHandle ELEM_HANDLE;

    private final long[] arr;

    static {
        ELEM_HANDLE = MethodHandles.arrayElementVarHandle(long[].class);
    }

    public AtomicDoubleArrayVarHandles(int size) {
        arr = new long[size];

    }

    public AtomicDoubleArrayVarHandles(double[] arr) {
        this.arr = new long[arr.length];

        for (int i = 0; i < arr.length; i++) {
            this.arr[i] = Double.doubleToRawLongBits(arr[i]);
        
        }

    }

    // public long[] rawCopy() {
    //     return Arrays.copyOf(arr, arr.length);

    // }

    // public double[] copy() {
    //     long[] rawCopy = Arrays.copyOf(arr, arr.length);
    //     double[] copy = new double[rawCopy.length];
        
    //     for (int i = 0; i < copy.length; i++) {
    //         copy[i] = Double.longBitsToDouble(rawCopy[i]);
    //     }

    //     return copy;
    // }

    public double get(int index) {
        return Double.longBitsToDouble((long) ELEM_HANDLE.getVolatile(arr, index));

    }

    public void set(int index, double newValue) {
        ELEM_HANDLE.setVolatile(arr, index, Double.doubleToRawLongBits(newValue));

    }

    public boolean compareAndSet(int index, double expected, double newValue) {
        return ELEM_HANDLE.compareAndSet(
            arr,
            index,
            Double.doubleToRawLongBits(expected),
            Double.doubleToRawLongBits(newValue)
        );

    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            builder.append(Double.longBitsToDouble(arr[i])).append(" ");
        }

        return builder.toString();
    }
    
}