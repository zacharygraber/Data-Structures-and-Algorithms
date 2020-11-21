import java.util.ArrayList;
import java.util.function.Function;

/**
 * This is class is complete.
 *
 * A node has a unique id which is used for hashing and testing for equality.
 *
 * A node is typically part of a heap. It maintains a pointer to that heap,
 * its position in the heap. The heap uses the node's value to determine its
 * position.
 *
 * During graph traversals, a node is marked as visited and its previous pointer
 * may be updated.
 *
 * Nodes should be reset() before any traversal.
 *
 */
public class Node implements Comparable<Node>, TreePrinter.PrintableNode {
    private final String id;
    private boolean visited;
    private int value;
    private Edge previousEdge;
    private Heap heap;
    private int heapIndex;

    Node (String id, int value) {
        this.id = id;
        reset(value);
    }

    void reset (int value) {
        this.visited = false;
        this.value = value;
        this.previousEdge = null;
        this.heap = null;
        this.heapIndex = -1;
    }

    boolean isVisited () { return visited; }

    void setVisited () { visited = true; }

    void setHeap (Heap heap) { this.heap = heap; }

    int getValue() { return value; }

    void setValue(int distance) { this.value = distance; }

    void updateValue (Function<Integer,Integer> f) {
        this.value = f.apply(value);
    }

    Edge getPreviousEdge() { return previousEdge; }

    void setPreviousEdge(Edge edge) { this.previousEdge = edge; }

    int getHeapIndex () { return heapIndex; }

    void setHeapIndex (int index) { this.heapIndex = index; }

    ArrayList<Edge> followPreviousEdge () {
        if (previousEdge == null) return new ArrayList<>();
        else {
            ArrayList<Edge> path = previousEdge.getSource().followPreviousEdge();
            path.add(previousEdge);
            return path;
        }
    }

    public int compareTo (Node other) {
        return Integer.compare(value,other.value);
    }

    public String toString () { return id; }

    public boolean equals (Object o) {
        if (o instanceof Node) {
            Node other = (Node)o;
            return id.equals(other.id);
        }
        return false;
    }

    public int hashCode () {
        return id.hashCode();
    }

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
