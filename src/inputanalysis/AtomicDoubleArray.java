package inputanalysis;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * An AtomicLongArray wrapper to represent atomic double arrays.
 */
public class AtomicDoubleArray implements Collection<Double>{
    /**
     * Represents doubles by putting the bits into a long
     */
    private AtomicLongArray atmoicArr;

    /**
     * Creates a wrapper of an AtomicLongArray to store doubles
     * 
     * @param arr Array of doubles to store in the atomic long
     */
    public AtomicDoubleArray(double[] arr) {
        int length = arr.length;
        
        // Contains doubles represented as longs
        long[] doubleBitRepresentations = new long[length];

        for (int i = 0; i < length; i++) {
            // Convert doubles to longs using the bits
            doubleBitRepresentations[i] = Double.doubleToLongBits(arr[i]);
        }

        // Assign new atomic arr that contains the bits of the doubles
        atmoicArr = new AtomicLongArray(doubleBitRepresentations);

    }

    /**
     * Gets the raw long array that is wrapped by the object
     * 
     * @return Long array wrapped by the object
     */
    public AtomicLongArray getRawArray() {
        return atmoicArr;
        
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


    /**
     * Add a collection of doubles to the atomic double
     * 
     * @param otherCollection Doubles to append to the end of the atomic array
     * @return Status of operation
     */
    @Override
    public boolean addAll(Collection<? extends Double> otherCollection) {
        AtomicLongArray newArr = new AtomicLongArray(
            atmoicArr.length() +
            otherCollection.size()
        );

        int newArrIndex = 0;

        while (newArrIndex < atmoicArr.length()) {
            newArr.set(newArrIndex, atmoicArr.get(newArrIndex));
            newArrIndex++;

        }

        for (Double elem : otherCollection) {
            newArr.set(newArrIndex, Double.doubleToLongBits(elem));
            newArrIndex++;

        }

        atmoicArr = newArr;

        return true;
    }

    /**
     * Append a double to the end of the atmoic array
     * 
     * @param num Number to add to the end of the array
     * @return Status of operation
     */
    @Override
    public boolean add(Double num) {
        int length = atmoicArr.length();

        AtomicLongArray newArr = new AtomicLongArray(length + 1);
        
        for (int i = 0; i < length; i++) {
            newArr.set(i, atmoicArr.get(i));

        }

        newArr.set(length + 1, Double.doubleToLongBits(num));

        atmoicArr = newArr;

        return true;
    }

    /**
     * Removes all elements in the atmoic array and sets it to an empty 
     * atomic array
     */
    @Override
    public void clear() {
        atmoicArr = new AtomicLongArray(0);

    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public boolean containsAll(Collection c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public Iterator iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean removeAll(Collection c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    /**
     * Gets the length of the atomic array
     * 
     * @return Length of atomic array
     */
    @Override
    public int size() {
        return atmoicArr.length();

    }    

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public Object[] toArray(Object[] arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

}
