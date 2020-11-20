import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * A undirected graph represented using adjacency lists. There is one method
 * to implement in this class which computes a minimum spanning tree.
 *
 */
public class UndirectedGraph {
    private final Hashtable<Node, ArrayList<Edge>> neighbors;
    private final Set<Node> nodes;

    UndirectedGraph(Hashtable<Node,ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
        this.nodes = neighbors.keySet();
    }

    /**
     * The outline of this algorithm is quite similar to Dijkstra's
     * shortest path algorithm:
     *
     *   - Initialize the nodes values and put them in a heap
     *   - Repeatedly extract the min node from the heap and
     *     relax its outgoing edges. If the weight of an edge
     *     is less than the current value at its destination,
     *     then the destination's value is updated and its
     *     previous pointer is updated.
     *
     * The result will be the collection of all edges stored
     * in the previous pointers.
     *
     */
    Set<Edge> minimumSpanningTree (Node source) {
        return null; // TODO
    }

    public String toString () {
        return neighbors.toString();
    }
}
