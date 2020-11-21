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
        Heap h = new Heap(nodes); // Values should automatically be set to MAX_INT by Node.reset() method in constructor
        h.update(source, 0); // The starting node has a cost of 0
        while (h.getSize() > 0) {
            // DEBUGGING///////////////////////////////////////////////////////////
//            System.out.println("VISITING NODE "+h.getMin().toString());
//            TreePrinter.print(h.getMin());
//            System.out.println();
            ///////////////////////////////////////////////////////////////////////

            Node thisNode = h.extractMin(); // This should always start as the source

            // Mark this node as visited
            thisNode.setVisited();

            // Relax each of the edges
            int newCost;
            Node otherNode;
            for (Edge e : neighbors.get(thisNode)) {
                newCost = thisNode.getValue() + e.getWeight();
                otherNode = e.getDestination();
                if (newCost < otherNode.getValue()) { // If this is a shorter path...
                    h.update(otherNode, newCost); // Update the cost of the destination Node
                    otherNode.setPrevious(thisNode); // Update the previous reference to thisNode
                }
            }
        }
        return destination.followPrevious();
    }

    public String toString () {
        return neighbors.toString();
    }
}
