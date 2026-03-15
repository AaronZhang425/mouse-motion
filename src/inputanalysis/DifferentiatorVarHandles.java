package inputanalysis;

public class DifferentiatorVarHandles implements Runnable {
    private volatile boolean run = true;

    private final AtomicDoubleArrayVarHandles atomicArr;

    public DifferentiatorVarHandles(AtomicDoubleArrayVarHandles atomicArr) {
        this.atomicArr = atomicArr;

    }

    public AtomicDoubleArrayVarHandles getAtomicArr() {
        return atomicArr;
    
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    public String toString() {
        return atomicArr.toString();
    }



}
