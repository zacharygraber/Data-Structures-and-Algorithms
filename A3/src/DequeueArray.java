import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;
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
        this.elements = (Optional<E>[]) Array.newInstance(Optional.class, capacity);
        Arrays.fill(elements, Optional.empty());
        this.capacity = capacity;
    }

    public int size () { return size; }

    /**
     *  Adds an elements to the front of the dequeue
     */
    public void addFirst(E elem) {
        // If the dequeue is full, then we need to resize
        if (this.size == this.capacity) {
            this.grow();
        }
        this.elements[this.front] = Optional.of(elem);
        this.front = Math.floorMod((this.front - 1), this.capacity);
        this.size += 1;
    }

    /**
     *  Adds an elements to the back of the dequeue
     */
    public void addLast(E elem) {
        // If the dequeue is full, then we need to resize
        if (this.size == this.capacity) {
            this.grow();
        }
        this.elements[this.back] = Optional.of(elem);
        this.back = (this.back + 1) % this.capacity;
        this.size += 1;
    }

    /**
     *  Returns the element at the front (if there is one)
     */
    public E getFirst() throws NoSuchElementE {
        return this.elements[(this.front + 1) % this.capacity].orElseThrow(NoSuchElementE::new);
    }

    /**
     *  Returns the element at the back of the dequeue (if there is one)
     */
    public E getLast() throws NoSuchElementE {
        return this.elements[Math.floorMod((this.back - 1), this.capacity)].orElseThrow(NoSuchElementE::new);
    }

    /**
     *  Removes and returns the element at the front of the dequeue (if there is one)
     */
    public E removeFirst() throws NoSuchElementE {
        E firstElement = this.elements[(this.front + 1) % this.capacity].orElseThrow(NoSuchElementE::new);
        this.elements[(this.front + 1) % this.capacity] = Optional.empty();
        this.front = (this.front + 1) % this.capacity;
        this.size -= 1;
        return firstElement;
    }

    /**
     * Removes and returns the element at the back of the dequeue (if there is one)
     */
    public E removeLast() throws NoSuchElementE {
        E firstElement = this.elements[Math.floorMod((this.back - 1), this.capacity)].orElseThrow(NoSuchElementE::new);
        this.elements[Math.floorMod((this.back - 1), this.capacity)] = Optional.empty();
        this.back = Math.floorMod((this.back - 1), this.capacity);
        this.size -= 1;
        return firstElement;
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
        Optional<E>[] newElemsArr = new Optional[this.capacity * 2];
        for (int i = 0; i < this.capacity; i++) {
            try {
                // Go through and build the new array IN ORDER
                newElemsArr[i] = Optional.of(this.removeFirst());
            } catch (NoSuchElementE e) { System.out.println("SOMETHING WENT VERY VERY WRONG! This should never happen."); }
        }
        // Populate the rest with empty Optionals
        for (int i = this.capacity; i < newElemsArr.length; i++) {
            newElemsArr[i] = Optional.empty();
        }

        /* The new back should be after all the original elements are inserted.
           Luckily, we know that elements end at this.capacity - 1, so the new
           back will be at the index of the old capacity!                       */
        this.back = this.capacity;
        this.size = this.capacity; // Removing elements borks up the size, so we reset it.
        this.capacity *= 2;
        this.front = this.capacity - 1;
        this.elements = newElemsArr;
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
        Optional<E>[] newElemsArr = new Optional[Math.round(1.5f * this.capacity)];
        for (int i = 0; i < this.capacity; i++) {
            try {
                // Go through and build the new array IN ORDER
                newElemsArr[i] = Optional.of(this.removeFirst());
            } catch (NoSuchElementE e) { System.out.println("SOMETHING WENT VERY VERY WRONG! This should never happen."); }
        }
        // Populate the rest with empty Optionals
        for (int i = this.capacity; i < newElemsArr.length; i++) {
            newElemsArr[i] = Optional.empty();
        }

        /* The new back should be after all the original elements are inserted.
           Luckily, we know that elements end at this.capacity - 1, so the new
           back will be at the index of the old capacity!                       */
        this.back = this.capacity;
        this.size = this.capacity; // Removing elements borks up the size, so we reset it.
        this.capacity = Math.round(1.5f * this.capacity);
        this.front = this.capacity - 1;
        this.elements = newElemsArr;
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
        Optional<E>[] newElemsArr = new Optional[this.capacity + 1];
        for (int i = 0; i < this.capacity; i++) {
            try {
                // Go through and build the new array IN ORDER
                newElemsArr[i] = Optional.of(this.removeFirst());
            } catch (NoSuchElementE e) { System.out.println("SOMETHING WENT VERY VERY WRONG! This should never happen."); }
        }
        // Populate the rest with empty Optionals
        newElemsArr[this.capacity] = Optional.empty();

        /* The new back should be after all the original elements are inserted.
           Luckily, we know that elements end at this.capacity - 1, so the new
           back will be at the index of the old capacity!                       */
        this.back = this.capacity;
        this.size = this.capacity; // Removing elements borks up the size, so we reset it.
        this.capacity += 1;
        this.front = this.capacity - 1;
        this.elements = newElemsArr;
    }
}
