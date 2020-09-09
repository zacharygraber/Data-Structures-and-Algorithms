import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

class NoSuchElementE extends Exception {}

public abstract class DequeueArray<E> {
    protected Optional<E>[] elements;
    protected int capacity, front, back, size;
    //
    // Invariants to maintain:
    //
    // * if dequeue is not full, front points to an empty location
    // * if dequeue is not full, back points to an empty location
    // * data stored in locations:
    //   front+1, front+2, ... back-2, back-1 (all mod capacity)
    // * after adding to front, decrease 'front' by 1
    // * after adding to back, increase 'back' by 1
    // * removing does the opposite
    //
    //  +-------------------------------------+
    //  | 4  5  6  _  _  _  _  _  _  1  2  3  |
    //  +-------------------------------------+
    //            /\             /\          /\
    //           back          front       capacity
    //

    @SuppressWarnings("unchecked")
    DequeueArray(int initialCapacity) {
        elements = (Optional<E>[]) Array.newInstance(Optional.class, initialCapacity);
        Arrays.fill(elements, Optional.empty());
        capacity = initialCapacity;
        front = capacity - 1;
        back = 0;
        size = 0;
    }

    /**
     *  Creates a new empty array of elements of the given size
     */
    public void clear(int capacity) {
        // TODO
    }

    public int size () { return size; }

    /**
     *  Adds an elements to the front of the dequeue
     */
    public void addFirst(E elem) {
        // TODO
    }

    /**
     *  Adds an elements to the back of the dequeue
     */
    public void addLast(E elem) {
        // TODO
    }

    /**
     *  Returns the element at the front (if there is one)
     */
    public E getFirst() throws NoSuchElementE {
        return null; // TODO
    }

    /**
     *  Returns the element at the back of the dequeue (if there is one)
     */
    public E getLast() throws NoSuchElementE {
        return null; // TODO
    }

    /**
     *  Removes and returns the element at the front of the dequeue (if there is one)
     */
    public E removeFirst() throws NoSuchElementE {
        return null; // TODO
    }

    /**
     * Removes and returns the element at the back of the dequeue (if there is one)
     */
    public E removeLast() throws NoSuchElementE {
        return null; // TODO
    }

    /**
     * The following method is implemented in subclasses: it determines the policy for
     * growing the array when full
     */
    abstract void grow ();

    // the following methods are for debugging and testing

    Optional<E>[] getElements () { return elements; }

    int getCapacity () { return capacity; }

    int getFront () { return front; }

    int getBack () { return back; }

}

// ---------------------------------------------------------

class DequeueArrayDouble<E> extends DequeueArray<E> {

    DequeueArrayDouble (int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Grows the array by increasing the capacity by a factor of 2
     * (i.e., by doubling the capacity)
     */
    void grow() {
        // TODO
    }
}

// ---------------------------------------------------------

class DequeueArrayOneAndHalf<E> extends DequeueArray<E> {

    DequeueArrayOneAndHalf (int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Grows the array by increasing the capacity by a factor of 1.5
     * More precisely Math.round(1.5f * capacity)
     */
    void grow() {
        // TODO
    }
}

// ---------------------------------------------------------

class DequeueArrayPlusOne<E> extends DequeueArray<E> {

    DequeueArrayPlusOne (int initialCapacity) {
        super(initialCapacity);
    }

    /**
     *  Grows the array by increasing the capacity by 1
     */
    void grow() {
        // TODO
    }
}
