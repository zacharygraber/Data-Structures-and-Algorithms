import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * The classes Heap and Node are mutually defined. The heap is a
 * collection of nodes ordered by their values (min at the root).
 * Each node has a pointer back to its heap and it also maintains
 * a private variable specifying its index in the heap.
 */
public class Heap {
    private final ArrayList<Node> nodes;
    private int size;

    Heap (Collection<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
        this.size = nodes.size();
        for (int i=0; i<size; i++) {
            this.nodes.get(i).setHeap(this);
            this.nodes.get(i).setHeapIndex(i);
        }
        heapify();
    }

    ArrayList<Node> getNodes () { return nodes; }

    int getSize () { return size; }

    // main methods

    /**
     * The method iterates over the first half of the array towards 0
     * calling moveDown on each node.
     */
    void heapify () {
        // TODO
    }

    Node getMin () { return nodes.get(0); }

    /**
     * The next few methods are self-explanatory. They either
     * return the appropriate node of empty if the request takes
     * you outside the array bounds.
     */
    Optional<Node> getParent (Node n) {
        return null; // TODO
    }

    Optional<Node> getLeftChild (Node n) {
        return null; // TODO
    }

    Optional<Node> getRightChild (Node n) {
        return null; // TODO
    }

    Optional<Node> getMinChild (Node n) {
        return null; // TODO
    }

    /**
     * Simply swap the two nodes in the array.
     */
    void swap (Node n1, Node n2) {
        // TODO
    }

    /**
     * If the minimum child is smaller than the current node, swap them, and continue
     * moving down recursively.
     *
     */
    void moveDown (Node n) {
        // TODO
    }

    /**
     * If the current node is smaller than its parent, swap them, and continue
     * moving up recursively.
     */
    void moveUp (Node n) {
        // TODO
    }

    void update (Node n, int value) {
        n.setValue(value);
        moveUp(n);
    }

    /**
     * Deletes and returns the minimum node which is located
     * at position 0.
     * To delete the minimum node, we move the last node in the
     * array to the first position, and call move down.
     */
    Node extractMin () {
        return null; // TODO
    }
}
