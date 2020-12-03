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
    Set<Edge> minimumSpanningTree (Node source) throws NoPathE {
        for (Node n : nodes) n.reset(Integer.MAX_VALUE);
        source.setValue(0);
        Heap heap = new Heap(nodes);
        assert heap.getMin().equals(source);

        while (heap.getSize() > 0) {
            Node current = heap.extractMin();

            // If we encounter a Node with a value of (basically infinity), we know that there's no way to access it.
            if (current.getValue() == Integer.MAX_VALUE) {
                throw new NoPathE();
            }
            if (current.isVisited()) break;
            current.setVisited();
            for (Edge edge : neighbors.get(current)) {
                if (!edge.getDestination().isVisited()) {
                    Node neighbor = edge.getDestination();
                    int newWeight = edge.getWeight();
                    if (newWeight < neighbor.getValue()) {
                        heap.update(neighbor, newWeight);
                        neighbor.setPreviousEdge(edge);
                    }
                }
            }
        }
        Set<Edge> result = new HashSet<>();
        for (Node n : nodes) {
            if (!n.equals(source)) {
                result.add(n.getPreviousEdge());
            }
        }
        return result;
    }

    public String toString () {
        return neighbors.toString();
    }
}
