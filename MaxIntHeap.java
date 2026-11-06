import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A max-heap of ints stored in a 0-indexed array.
 * <p>
 * The largest value is always at the root, {@code items[0]}.
 * For a node at index i:
 * <ul>
 *   <li>left child is at 2i + 1</li>
 *   <li>right child is at 2i + 2</li>
 *   <li>parent is at (i - 1) / 2</li>
 * </ul>
 */
public class MaxIntHeap implements Heap {
    /** Current length of the backing array. */
    private int capacity = 10;

    /** Number of elements currently in the heap. */
    private int size = 0;

    /** Backing array; only indices 0..size-1 hold heap elements. */
    private int[] items = new int[capacity];

    /** Creates an empty max-heap. */
    public MaxIntHeap() { }

    // ---------- index helpers (0-indexed) ----------
    /**
     * Computes the left child index.
     *
     * @param parentIndex index of a node
     * @return index of its left child
     */
    private int getLeftChildIndex(int parentIndex)  { return 2 * parentIndex + 1; }

    /**
     * Computes the right child index.
     *
     * @param parentIndex index of a node
     * @return index of its right child
     */
    private int getRightChildIndex(int parentIndex) { return 2 * parentIndex + 2; }

    /**
     * Computes the parent index.
     *
     * @param childIndex index of a node (must not be the root)
     * @return index of its parent
     */
    private int getParentIndex(int childIndex)      { return (childIndex - 1) / 2; }

    /**
     * Checks for a left child.
     *
     * @param index index of a node
     * @return true if the node has a left child in the heap
     */
    private boolean hasLeftChild(int index)  { return getLeftChildIndex(index) < size; }

    /**
     * Checks for a right child.
     *
     * @param index index of a node
     * @return true if the node has a right child in the heap
     */
    private boolean hasRightChild(int index) { return getRightChildIndex(index) < size; }

    /**
     * Checks for a parent.
     *
     * @param index index of a node
     * @return true if the node has a parent (every node except the root)
     */
    private boolean hasParent(int index)     { return index > 0; }

    /**
     * Gets the left child value.
     *
     * @param index index of a node that has a left child
     * @return value of its left child
     */
    private int leftChild(int index)  { return items[getLeftChildIndex(index)]; }

    /**
     * Gets the right child value.
     *
     * @param index index of a node that has a right child
     * @return value of its right child
     */
    private int rightChild(int index) { return items[getRightChildIndex(index)]; }

    /**
     * Gets the parent value.
     *
     * @param index index of a node that has a parent
     * @return value of its parent
     */
    private int parent(int index)     { return items[getParentIndex(index)]; }

    // ---------- utilities ----------
    /**
     * Swaps the values at two indices.
     *
     * @param i first index
     * @param j second index
     */
    private void swap(int i, int j) {
        int temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    /**
     * Doubles the backing array if it is full, so there is room to add.
     */
    private void ensureExtraCapacity() {
        if (size == capacity) {
            items = Arrays.copyOf(items, capacity * 2);
            capacity *= 2;
        }
    }

    // ---------- IntHeap methods ----------
    /** {@inheritDoc} */
    @Override
    public int size() { return size; }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() { return size == 0; }

    /**
     * Returns the largest value without removing it. Runs in O(1).
     *
     * @return the largest value
     * @throws NoSuchElementException if the heap is empty
     */
    @Override
    public int peek() {
        if (size == 0) throw new NoSuchElementException("heap is empty");
        return items[0];
    }

    /**
     * Inserts a value: places it at the last position, then heapifies up.
     * Runs in O(log n).
     *
     * @param item the value to insert
     */
    @Override
    public void insert(int item) {
        ensureExtraCapacity();
        items[size] = item;
        size++;
        heapifyUp(size - 1);
    }

    /**
     * Removes and returns the largest value: moves the last element to the
     * root, then heapifies down. Runs in O(log n).
     *
     * @return the largest value
     * @throws NoSuchElementException if the heap is empty
     */
    @Override
    public int remove() {
        if (size == 0) throw new NoSuchElementException("heap is empty");
        int max = items[0];
        items[0] = items[size - 1];
        size--;
        heapifyDown(0);
        return max;
    }

    // ---------- heapify ----------
    /**
     * Restores heap order after an insert by moving the element at index up.
     * If its parent is smaller, swap them and recurse from the parent.
     * Runs in O(log n).
     *
     * @param index index of the element to move up
     */
    private void heapifyUp(int index) {
        if (hasParent(index) && parent(index) < items[index]) {
            swap(getParentIndex(index), index);
            heapifyUp(getParentIndex(index));
        }
    }

    /**
     * Restores heap order after a remove by moving the element at index down.
     * If its larger child is bigger, swap them and recurse from that child.
     * Runs in O(log n).
     *
     * @param index index of the element to move down
     */
    private void heapifyDown(int index) {
        int largest = index;
        if (hasLeftChild(index) && leftChild(index) > items[largest]) {
            largest = getLeftChildIndex(index);
        }
        if (hasRightChild(index) && rightChild(index) > items[largest]) {
            largest = getRightChildIndex(index);
        }
        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    // ---------- quick test ----------
    /**
     * Quick test: inserts the example values, then removes them all.
     * The output should be in descending order.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Heap heap = new MaxIntHeap();
        int[] values = {16, 14, 10, 8, 7, 9, 3, 2, 4, 1, 15};
        for (int v : values) heap.insert(v);

        System.out.println("peek: " + heap.peek());    // expect 16
        while (!heap.isEmpty()) {
            System.out.print(heap.remove() + " ");       // expect 16 15 14 10 9 8 7 4 3 2 1
        }
        System.out.println();
    }
}