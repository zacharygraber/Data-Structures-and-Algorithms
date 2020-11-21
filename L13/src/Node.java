import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * The classes Heap and Node are mutually defined. The heap is a
 * collection of nodes ordered by their values (min at the root).
 * Each node has a pointer back to its heap and it also maintains
 * a private variable specifying its index in the heap.
 *
 * When calculating shortest paths, the value field maintains the
 * best distance from the distance that has been computed so far.
 * A node maintains a few other private variables that are
 * used in the calculation of shortest paths: a flag
 * 'visited' that determines if the node has already been
 * visited in this graph traversal, and a variable 'previous'
 * that points to the previous node along the current
 * best known shortest path.
 *
 * There is only one method to write in this class: followPrevious.
 *
 */
public class Node implements Comparable<Node>, TreePrinter.PrintableNode {
    private final String id;
    private boolean visited;
    private int value;
    private ArrayList<Node> previous;
    private Heap heap;
    private int heapIndex;

    Node (String id) {
        this.id = id;
        reset();
    }

    void reset () {
        this.visited = false;
        this.value = Integer.MAX_VALUE;
        this.previous = new ArrayList<>();
        this.heap = null;
        this.heapIndex = -1;
    }

    boolean isVisited () { return visited; }

    void setVisited () { visited = true; }

    void setHeap (Heap heap) { this.heap = heap; }

    int getValue() { return value; }

    void setValue(int distance) { this.value = distance; }

    /**
     *  Adds a node ON TOP OF the existing previous ones. This is used when a new path is discovered with equal
     *  cost to the known minimum.
     */
    public void addPrevious(Node n) {
        this.previous.add(n);
    }

    /**
     *  Clears the previous nodes list, then adds this node into the now empty ArrayList.
     *  This is used when a new path is discovered that is shorter than the known minimum.
     */
    public void replacePrevious(Node n) {
        this.previous.clear();
        this.previous.add(n);
    }

    int getHeapIndex () { return heapIndex; }

    void setHeapIndex (int index) { this.heapIndex = index; }

    /**
     *  Each Node has an ArrayList of previous Node references. You can think of these like the destination as the root of
     *  a tree. In order to count the number of paths, we count the leaves in the tree.
     */
    int countPaths (Node source) {
        // Base case
        if (this.equals(source)) {
            return 1;
        }

        // Recursive step
        int sum = 0;
        for (Node previousNode : this.previous) {
            sum += previousNode.countPaths(source);
        }
        return sum;
    }

    /**
     *  Gets an ArrayList of all the different possible shortest paths, each represented as an ArrayList of Nodes
     */
    ArrayList<ArrayList<Node>> getAllPaths(Node source) {
        // Base case
        if (this.equals(source)) {
            ArrayList<ArrayList<Node>> result = new ArrayList<>();
            result.add(new ArrayList<>(Collections.singletonList(this)));
            return result;
        }

        ArrayList<ArrayList<Node>> possiblePaths = new ArrayList<>(); // Start each node back up the chain with an empty list of paths
        for (Node previous : this.previous) { // For each previous child of this Node..
            ArrayList<ArrayList<Node>> input = previous.getAllPaths(source); // Make a recursive call on the child to get an "input", which is a list of paths
            for (ArrayList<Node> path : input) { // For each path in the input...
                path.add(this); // Append this node to the end
                possiblePaths.add(path); // Add the path to be returned back up the Stack.
            }
        }
        return possiblePaths;
    }

    public int compareTo (Node other) {
        return Integer.compare(value,other.value);
    }

    public boolean equals (Object o) {
        if (o instanceof Node) {
            Node other = (Node)o;
            return id.equals(other.id);
        }
        return false;
    }

    public String toString () { return id; }

    // static methods

    static Node min (Node a, Node b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    // Printable

    public TreePrinter.PrintableNode getLeft() {
        return heap.getLeftChild(this).orElse(null);
    }

    public TreePrinter.PrintableNode getRight() {
        return heap.getRightChild(this).orElse(null);
    }

    public String getText() {
        return String.format("%s[%d]", id, value);
    }
}
