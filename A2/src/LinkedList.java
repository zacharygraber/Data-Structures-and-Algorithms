
interface StackI<E> {
    StackI<E> push (E elem);
    StackI<E> pop () throws EmptyListE;
    E top () throws EmptyListE;
}

interface QueueI<E> {
    QueueI<E> enqueue (E elem);
    QueueI<E> dequeue () throws EmptyListE;
    E front () throws EmptyListE;
}

interface DequeueI<E> {
    DequeueI<E> enqueueBack (E elem);
    DequeueI<E> dequeueFront () throws EmptyListE;
    DequeueI<E> enqueueFront (E elem);
    DequeueI<E> dequeueBack () throws EmptyListE;
    E front () throws EmptyListE;
    E back () throws EmptyListE;
}

class EmptyListE extends Exception {}

// -------------------------------------------------------------

/**
 * The following class hierarchy will implement a linked list allowing
 * access to all positions at cost O(N). This is general
 * enough to implement the stack interface, the queue interface,
 * and the dequeue interface.
 *
 * For this assignment we are not concerned with satisfying the
 * required O(1) access for stacks, queues, and dequeues.
 *
 * Following the interpreter pattern that we used in class,
 * a linked list will be implemented using one abstract class
 * and two sublcasses for an empty list and a non-empty list.
 * The non-empty list class will have maintain two fields:
 * an element of type E and the rest of the list.
 */


public abstract class LinkedList<E> implements StackI<E>, QueueI<E>, DequeueI<E> {
    // private helper methods implemented in the subclasses
    protected abstract LinkedList<E> getRest () throws EmptyListE;
    protected abstract int length ();

    // As a general strategy, I recommend implementing all the required
    // methods in his class except for the two helper methods above and
    // insertAt, removeAt, and getAt. This will avoid a lot of code
    // repetition. I will call these 5 methods, the common methods.

    // Stack. Implement by delegating to the common methods
    public LinkedList<E> push (E elem) {
        try {
            return this.insertAt(0, elem);
        }
        // This should never be reached, but it won't compile without this, since this method does not throw EmptyListE
        catch (EmptyListE emptyListE) {
            emptyListE.printStackTrace();
            return null;
        }
    }

    public LinkedList<E> pop () throws EmptyListE {
        return this.removeAt(0);
    }

    public E top () throws EmptyListE {
        return this.getAt(0); // Calling this.getAt(0) is the same as just saying this.front
    };

    // Linked List
    abstract LinkedList<E> insertAt (int i, E elem) throws EmptyListE;
    abstract LinkedList<E> removeAt (int i) throws EmptyListE;
    abstract E getAt (int i) throws EmptyListE;

    // Queue. Implement by delegating to the common methods.
    public LinkedList<E> enqueue (E elem) {
        // For a queue, we'll enqueue at the back of the LinkedList, and dequeue at the front.
        // We once again run into issues caused by a lack of "throws EmptyListE" in the definition of this method
        try {
            return this.insertAt(this.length(), elem);
        } catch (EmptyListE emptyListE) {
            emptyListE.printStackTrace();
            return null;
        }
    }

    public LinkedList<E> dequeue () throws EmptyListE {
        // Dequeue from the front of the LinkedList
        return this.removeAt(0);
    }

    public E front () throws EmptyListE {
        // Since we are dequeueing from the front of the LinkedList, we get from index 0
        return this.getAt(0);
    }

    // Dequeue. Implement by delegating to the common methods.
    public LinkedList<E> enqueueBack (E elem) {
        try {
            return this.insertAt(this.length(), elem);
        } catch (EmptyListE emptyListE) {
            emptyListE.printStackTrace();
            return null;
        }
    }

    public LinkedList<E> dequeueFront () throws EmptyListE {
        return this.removeAt(0);
    }

    public LinkedList<E> enqueueFront (E elem) {
        try {
            return this.insertAt(0, elem);
        } catch (EmptyListE emptyListE) {
            emptyListE.printStackTrace();
            return null;
        }
    }

    public LinkedList<E> dequeueBack () throws EmptyListE {
        // Remove the element at the last index
        return this.removeAt(this.length() - 1);
    }

    // front shared with queue

    public E back () throws EmptyListE {
        // Get the element at the last index
        return this.getAt(this.length() - 1);
    }
}

// -------------------------------------------------------------

class EmptyList<E> extends LinkedList<E> {

    protected LinkedList<E> getRest() throws EmptyListE {
        throw new EmptyListE();
    }

    protected int length() {
        return 0;
    }

    public LinkedList<E> insertAt(int i, E elem) throws EmptyListE {
        // Edge case where negative index is given
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        }
        else if (i > 0) {
            throw new EmptyListE();
        }
        else {
            return new NonEmptyList<>(elem, this);
        }
    }

    public LinkedList<E> removeAt(int i) throws EmptyListE {
        throw new EmptyListE();
    }

    public E getAt(int i) throws EmptyListE {
        throw new EmptyListE();
    }

    public String toString () { return "#"; }
}

// -------------------------------------------------------------

class NonEmptyList<E> extends LinkedList<E> {
    private E front;
    private LinkedList<E> rest;

    NonEmptyList (E front, LinkedList<E> rest) {
        this.front = front;
        this.rest = rest;
    }

    protected LinkedList<E> getRest() {
        return this.rest;
    }

    protected int length() {
        return rest.length() + 1;
    }

    public LinkedList<E> insertAt(int i, E elem) throws EmptyListE {
        // Handle the edge case where a negative index is given
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (i > 0) {
            return new NonEmptyList<>(this.front, rest.insertAt(i - 1, elem));
        }
        else {
            return new NonEmptyList<>(elem, this);
        }
    }

    public LinkedList<E> removeAt(int i) throws EmptyListE {
        // Edge case
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (i > 0) {
            return new NonEmptyList<>(this.front, rest.removeAt(i - 1));
        }
        else {
            return this.rest;
        }
    }

    public E getAt(int i) throws EmptyListE {
        // Edge case
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (i > 0) {
            return rest.getAt(i - 1);
        }
        else {
            return front;
        }
    }

    public String toString () {
        return front + " : " + rest.toString();
    }
}

// -------------------------------------------------------------
// -------------------------------------------------------------


