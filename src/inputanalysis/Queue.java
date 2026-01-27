package inputanalysis;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * ArrayDeque wrapper to ensure usage of an ArrayDeque as a queue and not as a
 * stack to prevent erroneous addition or removal of elements due to ArrayDeque
 * being a double ended array in which elements can be removed or added from
 * both the end and beginning 
 */
public class Queue<T> {
    private ArrayDeque<T> queue;

    public Queue(Collection<? extends T> items) {
        queue = new ArrayDeque<>(items);

    }

    public Queue(int initalSize) {
        queue = new ArrayDeque<>(initalSize);

    }

    public Queue() {
        queue = new ArrayDeque<>(16);

    }

    /**
     * Adds an item to queue. If the queue is not large enough, the queue will
     * automatically expand. 
     * 
     * @param item Item to be added to the queue
     */
    public void add(T item) {
        queue.addLast(item);
    }

    /**
     * Get and remove an item from the queue
     * 
     * @return item at the front of the queue
     */
    public T get() {
        return queue.pollFirst();

    }

    /**
     * Reads the first item from the queue without removing it. If the queue is
     * empty, return null.
     * 
     * @return item at from of queue 
     */
    public T peek() {
        return queue.peekFirst();

    }

    /**
     * Gets the size of the queue
     * 
     * @return size of queue
     */
    public int size() {
        return queue.size();

    }



}
