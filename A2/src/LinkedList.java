
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
        return null; // TODO
    }

    public LinkedList<E> pop () throws EmptyListE {
        return null; // TODO
    }

    public E top () throws EmptyListE {
        return null; // TODO
    };

    // Linked List
    abstract LinkedList<E> insertAt (int i, E elem) throws EmptyListE;
    abstract LinkedList<E> removeAt (int i) throws EmptyListE;
    abstract E getAt (int i) throws EmptyListE;

    // Queue. Implement by delegating to the common methods.
    public LinkedList<E> enqueue (E elem) {
        return null; // TODO
    }

    public LinkedList<E> dequeue () throws EmptyListE {
        return null; // TODO
    }

    public E front () throws EmptyListE {
        return null; // TODO
    }

    // Dequeue. Implement by delegating to the common methods.
    public LinkedList<E> enqueueBack (E elem) {
        return null; // TODO
    }

    public LinkedList<E> dequeueFront () throws EmptyListE {
        return null; // TODO
    }

    public LinkedList<E> enqueueFront (E elem) {
        return null; // TODO
    }

    public LinkedList<E> dequeueBack () throws EmptyListE {
        return null; // TODO
    }

    // front shared with queue

    public E back () throws EmptyListE {
        return null; // TODO
    }
}

// -------------------------------------------------------------

class EmptyList<E> extends LinkedList<E> {

    protected LinkedList<E> getRest() throws EmptyListE {
        return null; // TODO
    }

    protected int length() {
        return 0; // TODO
    }

    public LinkedList<E> insertAt(int i, E elem) throws EmptyListE {
        return null; // TODO
    }

    public LinkedList<E> removeAt(int i) throws EmptyListE {
        return null; // TODO
    }

    public E getAt(int i) throws EmptyListE {
        return null; // TODO
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
        return null; // TODO
    }

    protected int length() {
        return 0; // TODO
    }

    public LinkedList<E> insertAt(int i, E elem) throws EmptyListE {
        return null; // TODO
    }

    public LinkedList<E> removeAt(int i) throws EmptyListE {
        return null; // TODO
    }

    public E getAt(int i) throws EmptyListE {
        return null; // TODO
    }

    public String toString () {
        return front + " : " + rest.toString();
    }
}

// -------------------------------------------------------------
// -------------------------------------------------------------


