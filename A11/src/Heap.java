import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        // DEBUGGING //////////////////////////////////
//        System.out.println("STARTING TREE");
//        TreePrinter.print(nodes.get(0));
//        System.out.println();
        ///////////////////////////////////////////////

        for (int i = (nodes.size() / 2) - 1; i >= 0; i--) {

            // DEBUGGING /////////////////////////////////
//            System.out.print("Array index " + i + ": ");
//            System.out.print("[");
//            for (Node n : nodes) {
//                System.out.print(n.getValue() + ", ");
//            }
//            System.out.print("]\n");
//            TreePrinter.print(nodes.get(0));
            //////////////////////////////////////////////

            this.moveDown(nodes.get(i));
        }
    }

    Node getMin () { return nodes.get(0); }

    /**
     * The next few methods are self-explanatory. They either
     * return the appropriate node of empty if the request takes
     * you outside the array bounds.
     */
    Optional<Node> getParent (Node n) {
        // If this is already the root, return empty
        if (n.getHeapIndex() == 0) {
            return Optional.empty();
        }
        return Optional.of(nodes.get((n.getHeapIndex() - 1) / 2));
    }

    Optional<Node> getLeftChild (Node n) {
        try {
            return Optional.of(nodes.get((n.getHeapIndex() * 2) + 1));
        }
        catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    Optional<Node> getRightChild (Node n) {
        try {
            return Optional.of(nodes.get((n.getHeapIndex() * 2) + 2));
        }
        catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    Optional<Node> getMinChild (Node n) {
        Optional<Node> left = this.getLeftChild(n);
        Optional<Node> right = this.getRightChild(n);

        // Case 1: both are empty - just return one of them
        if (left.isEmpty() && right.isEmpty()) {
            return left;
        }
        // Case 2: left is empty and right is not - return right
        else if (left.isEmpty()) {
            return right;
        }
        // Case 3: right is empty and left is not - return left
        else if (right.isEmpty()) {
            return left;
        }
        // Case 4: both are present - return the minimum value
        else {
            return left.get().getValue() < right.get().getValue() ? left : right;
        }
    }

    /**
     * Simply swap the two nodes in the array.
     */
    void swap (Node n1, Node n2) {
        // Update their positions in the ArrayList
        Collections.swap(nodes, n1.getHeapIndex(), n2.getHeapIndex());

        // Update their heap indices stored
        int temp = n1.getHeapIndex();
        n1.setHeapIndex(n2.getHeapIndex());
        n2.setHeapIndex(temp);
    }

    /**
     * If the minimum child is smaller than the current node, swap them, and continue
     * moving down recursively.
     *
     */
    void moveDown (Node n) {
        Optional<Node> minChildOpt = this.getMinChild(n);

        // Protect against the case where this Node n has no children
        if (minChildOpt.isPresent()) {
            Node minChild = minChildOpt.get();
            if (minChild.getValue() < n.getValue()) {
                this.swap(minChild, n);
                this.moveDown(n);
            }
        }
    }

    /**
     * If the current node is smaller than its parent, swap them, and continue
     * moving up recursively.
     */
    void moveUp (Node n) {
        Optional<Node> parentOpt = this.getParent(n);

        // Protect against the case where this is already the root (has no parent)
        if (parentOpt.isPresent()) {
            Node parent = parentOpt.get();
            if (n.getValue() < parent.getValue()) {
                this.swap(n, parent);
                this.moveUp(n);
            }
        }
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
        if (this.size == 1) {
            Node min = nodes.get(0);
            nodes.clear();
            this.size = 0;
            return min;
        }

        Node min = nodes.get(0);

        // Move the last node to the first position
        nodes.set(0, nodes.get(nodes.size() - 1));
        nodes.get(0).setHeapIndex(0);

        // Trim the ArrayList to get rid of the duplicate
        nodes.remove(nodes.size() - 1);
        this.size -= 1;

        // Move the new root down appropriately
        this.moveDown(nodes.get(0));

        return min;
    }
}
