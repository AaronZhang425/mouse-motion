package inputanalysis;

import java.lang.invoke.VarHandle;
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

    public double get(int index) {
        return Double.longBitsToDouble((long) ELEM_HANDLE.getVolatile(arr, index));

    }

    public void set(int index, double newValue) {
        ELEM_HANDLE.setVolatile(arr, index, newValue);

    }

    public boolean compareAndSet(int index, double expected, double newValue) {
        return ELEM_HANDLE.compareAndSet(
            arr,
            index,
            Double.doubleToRawLongBits(expected),
            Double.doubleToRawLongBits(newValue)
        );

    }


    
}
