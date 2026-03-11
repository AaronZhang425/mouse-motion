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
    private volatile AtomicLongArray atomicArr;

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
            doubleBitRepresentations[i] = Double.doubleToRawLongBits(arr[i]);
        }

        // Assign new atomic arr that contains the bits of the doubles
        atomicArr = new AtomicLongArray(doubleBitRepresentations);

    }

    /**
     * Gets the raw long array that is wrapped by the object
     * 
     * @return Long array wrapped by the object
     */
    public AtomicLongArray getRawArray() {
        return atomicArr;

    }

    /**
     * Gets the item at the specified index
     * 
     * @param index index of item
     * @return The item at the index
     */
    public double get(int index) {
        return Double.longBitsToDouble(atomicArr.get(index));

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
        return atomicArr.getAndSet(index, Double.doubleToRawLongBits(value));

    }


    /**
     * Add a collection of doubles to the atomic double
     * 
     * @param otherCollection Doubles to append to the end of the atomic array
     * @return Status of operation
     */
    @Override
    public synchronized boolean addAll(Collection<? extends Double> otherCollection) {
        // Create new array to accomdate for the combinded size of the original
        // array and the new collection
        AtomicLongArray newArr = new AtomicLongArray(
            atomicArr.length() +
            otherCollection.size()
        );

        // Index to add elements in the new array
        int newArrIndex = 0;

        // Add all the elements of the original array to the new array
        while (newArrIndex < atomicArr.length()) {
            newArr.set(newArrIndex, atomicArr.get(newArrIndex));
            newArrIndex++;

        }

        // Add the new elements to the end
        for (Double elem : otherCollection) {
            newArr.set(newArrIndex, Double.doubleToRawLongBits(elem));
            newArrIndex++;

        }

        // Set the atomic array to the new array with all the new additions
        atomicArr = newArr;

        return true;
    }

    /**
     * Append a double to the end of the atmoic array
     * 
     * @param num Number to add to the end of the array
     * @return Status of operation
     */
    @Override
    public synchronized boolean add(Double num) {
        int originalLength = atomicArr.length();

        AtomicLongArray newArr = new AtomicLongArray(originalLength + 1);
        
        for (int i = 0; i < originalLength; i++) {
            newArr.set(i, atomicArr.get(i));

        }

        newArr.set(originalLength, Double.doubleToRawLongBits(num));

        atomicArr = newArr;

        return true;
    }

    /**
     * Removes all elements in the atmoic array and sets it to an empty 
     * atomic array
     */
    @Override
    public synchronized void clear() {
        atomicArr = new AtomicLongArray(0);

    }

    /**
     * Returns if the object is in the array
     * 
     * @param otherObject Object to check if contained within the double array
     * @return True if object is in array
     */
    @Override
    public boolean contains(Object otherObject) {
        // If the object passed in is not a double, fail it
        if (!(otherObject instanceof Double)) {
            return false;
        }

        // Search through each item. If found, return true
        for (int i = 0; i < atomicArr.length(); i++) {
            if (otherObject.equals(
                Double.longBitsToDouble(atomicArr.get(i))
            )) {
                return true;

            }

        }

        // Return false if not found
        return false;

    
    }

    /**
     * Determines if all elements in the collection are in the array
     * 
     * @param collection Collection of objects to check for occurances in array
     * @return True if all collection elements are in array, including 0 sized
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        // Always true if empty like other collections
        if (collection.isEmpty()) {
            return true;
        
        }

        // Check if each item in the collection is in the array
        for (Object item : collection) {
            for (int i = 0; i < atomicArr.length(); i++) {
                if (!item.equals(Double.longBitsToDouble(atomicArr.get(i)))) {
                    return false;

                }
            }

        }

        return true;

    }

    /**
     * Returns whether the array is empty or not
     */
    @Override
    public boolean isEmpty() {
        return (atomicArr.length() == 0);

    }

    @Override
    public Iterator iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public synchronized boolean remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public synchronized boolean removeAll(Collection c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public synchronized boolean retainAll(Collection c) {
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
        return atomicArr.length();

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
