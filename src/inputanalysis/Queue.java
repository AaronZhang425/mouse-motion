package inputanalysis;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

/**
 * ArrayDeque wrapper to ensure usage of an ArrayDeque as a queue and not as a
 * stack to prevent erroneous addition or removal of elements
 */
public class Queue<T> {
    private ArrayDeque<T> queue;

    public Queue(int initalSize, Collection<? extends T> items) {
        queue = new ArrayDeque<>(initalSize);
        
        for (T item : items) {
            queue.addLast(item);

        }

    }

    public Queue(int initalSize) {
        this(initalSize, new ArrayList<T>());

    }

    public Queue() {
        this(16, new ArrayList<T>());

    }

    public void add(T item) {
        queue.addLast(item);
    }

    public T get() {
        return queue.pollFirst();

    }

    public T peek() {
        return queue.getFirst();

    }

    public int size() {
        return queue.size();

    }



}
