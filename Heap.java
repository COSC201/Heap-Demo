/**
 * ADT for a heap (a priority queue). This version stores ints.
 * <p>
 * Implementations decide whether the top element is the largest
 * (max-heap) or the smallest (min-heap).
 */
public interface Heap {
    /**
     * Returns the top element without removing it.
     *
     * @return the top element (max or min)
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    int peek();

    /**
     * Removes and returns the top element.
     * <p>
     * Also commonly called {@code poll}, {@code extractMax} / {@code extractMin},
     * or {@code removeMax} / {@code removeMin}.
     *
     * @return the top element (max or min)
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    int remove();

    /**
     * Inserts a new value into the heap.
     * <p>
     * Also commonly called {@code add} or {@code offer}.
     *
     * @param item the value to insert
     */
    void insert(int item);

    /**
     * Returns the number of elements in the heap.
     *
     * @return the number of elements
     */
    int size();

    /**
     * Checks whether the heap has no elements.
     *
     * @return true if the heap is empty, false otherwise
     */
    boolean isEmpty();
}