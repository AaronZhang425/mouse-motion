package inputanalysis;

import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles;

public class AtomicDoubleArrayVarHandles {
    private static final VarHandle arrElemHandle;

    private double[] arr;

    static {
        arrElemHandle = MethodHandles.arrayElementVarHandle(double[].class);
    }

    public AtomicDoubleArrayVarHandles(int size) {
        arr = new double[size];

    }

    public AtomicDoubleArrayVarHandles(double[] arr) {
        System.arraycopy(arr, 0, this.arr, 0, arr.length);

    }


    
}
