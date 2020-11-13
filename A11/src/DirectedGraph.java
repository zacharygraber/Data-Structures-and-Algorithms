import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class DirectedGraph {
    private final Hashtable<Node,ArrayList<Edge>> neighbors;
    private final Set<Node> nodes;

    DirectedGraph(Hashtable<Node,ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
        this.nodes = neighbors.keySet();
    }

    /**
     * This implements Dijkstra's algorithm
     */
    ArrayList<Node> shortestPath (Node source, Node destination) {
        return null; // TODO
    }

    public String toString () {
        return neighbors.toString();
    }
}
