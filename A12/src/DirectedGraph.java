import java.util.*;

/**
 *
 * A directed graph represented using adjacency lists.
 *
 * There are two methods to implement in this class: topological sorting
 * maximum flow.
 *
 */
class NoPathE extends Exception {}

public class DirectedGraph {
    private final Set<Node> nodes;
    private Hashtable<Node,ArrayList<Edge>> neighbors;

    DirectedGraph(Hashtable<Node,ArrayList<Edge>> neighbors) {
        this.nodes = neighbors.keySet();
        this.neighbors = neighbors;
    }

    /**
     *
     * This constructor is used to clone the given graph creating
     * new copies of the edges. So the edges in either graph can be
     * updated without affecting the other graph.
     *
     */
    DirectedGraph(DirectedGraph graph) {
        this.nodes = graph.nodes;
        this.neighbors = new Hashtable<>();
        for (Node n : nodes) {
            ArrayList<Edge> ns = new ArrayList<>();
            for (Edge e : graph.neighbors.get(n)) {
                ns.add(new Edge(e.getSource(), e.getDestination(), e.getWeight()));
            }
            neighbors.put(n,ns);
        }
    }

    void setNeighbors (Hashtable<Node,ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     *
     * An implementation of Dijkstra's algorithm that throws an exception of the
     * destination is not reachable from the source. Otherwise it returns the list
     * of edges forming the shortest path from source to destination.
     *
     */
    ArrayList<Edge> shortestPath (Node source, Node destination) throws NoPathE {
        for (Node n : nodes) n.reset(Integer.MAX_VALUE);
        source.setValue(0);
        Heap heap = new Heap(nodes);
        assert heap.getMin().equals(source);

        while (heap.getSize() > 0) {
            Node current = heap.extractMin();
            if (current.getValue() == Integer.MAX_VALUE) {
                break;
            }
            if (current.equals(destination) && destination.getValue() != Integer.MAX_VALUE) {
                return destination.followPreviousEdge();
            }
            if (current.isVisited()) break;
            current.setVisited();
            for (Edge edge : neighbors.get(current)) {
                Node neighbor = edge.getDestination();
                int newWeight =  current.getValue() + edge.getWeight();
                if (newWeight < neighbor.getValue()) {
                    heap.update(neighbor,newWeight);
                    neighbor.setPreviousEdge(edge);
                }
            }
        }
        throw new NoPathE ();
    }

    /**
     *
     * This method has a similar structure to Dijkstra's algorithm with the
     * following differences:
     *
     *   - in the initialization phase, the value of each node is set to
     *     its in-degree (number of edges pointing to the node)
     *   - when extracting the minimum node from the heap, if the value is
     *     not 0 the algorithm throws an error as this indicates a cycle in
     *     the graph
     *   - instead of marking the extracted nodes as visited, we collect
     *     them into a queue
     *   - when relaxing an edge v --> w, the value of w is reduced by 1.
     *
     */
    public Queue<Node> topologicalSort () throws NoPathE {
        // Initialize all nodes to 0
        for (Node n : nodes) n.reset(0);
        // Loop through all edges, and increment the destination Node by 1
        for (ArrayList<Edge> list : neighbors.values()) {
            for (Edge e : list) {
                // Increment the in-degree of the destination of the Edge by 1
                e.getDestination().setValue(e.getDestination().getValue() + 1);
            }
        }

        Heap heap = new Heap(nodes);
        Queue<Node> q = new LinkedList<>();

        while (heap.getSize() > 0) {
            // Extract the min and make sure it is 0
            Node current = heap.extractMin();
            // DEBUGGING //////////////
            System.out.println("VISITING " + current.toString());
            for (Node n : nodes) {
                System.out.println(n.toString() + ": " + n.getValue());
            }
            System.out.println("\n");
            ///////////////////////////
            if (current.getValue() != 0) {
                throw new NoPathE();
            }

            if (q.contains(current)) break;
            // Collect it into the Queue
            q.add(current);

            // Relax edges
            for (Edge e : neighbors.get(current)) {
                heap.update(e.getDestination(), e.getDestination().getValue() - 1);
            }
        }
        return q;
    }

    /**
     *
     * This algorithm maintains two intermediate structures:
     *   - a Hashtable<Edge,Edge> to represent the flow graph
     *   - a DirectedGraph to represent the residual graph
     *
     * The flow graph is represented in an unusual way as
     * a Hashtable<Edge,Edge>. The key to this hashtable is an
     * edge where we only care about the identity of the edge
     * (i.e., its source and destination but not its weight).
     * The values in the hashtable are edges in which the weights
     * are significant. So if the original graph had an edge 'e'
     * between nodes A and B, the key 'e' may be associated with
     * edges from A to B with different weights at different times.
     * Initially all these edges would have weight 0. The final
     * result of the algorithm is the collection of values in the
     * hashtable.
     *
     * The residual graph is initially created by cloning the
     * current graph using the special copy constructor.
     *
     * We then start a while (true) loop that terminates when
     * Dijkstra's algorithm fails to find a path from source
     * to destination in the residual graph.
     *
     * Each iteration of the loop performs the following actions:
     *   - compute the shortest path P from source to destination
     *     in the residual graph
     *   - get the minimum weight of any edge along this path P
     *   - update the flow graph by adding that minimum weight to
     *     all edges in P
     *   - recompute the residual graph as follows:
     *       - create a new hashtable of adjacency lists which
     *         will be used to update the residual graph by calling
     *         setNeighbors.
     *       - for each edge in the original graph, compute its new
     *         weight as original weight minus its weight in the
     *         flow graph
     *       - if the new weight is not zero, add the edge with its
     *         weight to the residual graph
     *       - for each edge A->B in the flow graph whose weight is
     *         not 0, add the edge B->A to the residual graph
     *
     *
     */
    Collection<Edge> maximumFlow (Node source, Node destination) {
        return null; // TODO
    }

    public String toString () {
        return neighbors.toString();
    }
    
}
