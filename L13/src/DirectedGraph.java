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
     *  This finds the shortest path from the source to the destination, favoring paths with less nodes
     */
    ArrayList<Node> shortestPathLeastNodes (Node source, Node destination) {
        this.runDijkstras(source, destination);
        ArrayList<ArrayList<Node>> allPaths = destination.getAllPaths(source);

        // If there are no possible paths from the source to the destination, return an empty list (blank path)
        if (allPaths.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            // Classic minimum algorithm. Assume minimum is the first thing. Iterate through each thing. If it is smaller, replace the minimum with it.
            ArrayList<Node> leastNodesPath = allPaths.get(0);
            for (ArrayList<Node> path : allPaths) {
                if (path.size() < leastNodesPath.size()) {
                    leastNodesPath = path;
                }
            }
            return leastNodesPath;
        }
    }

    /**
     *  This gives the number of equal shortest paths to from the source to the destination.
     */
    int numShortestPaths (Node source, Node destination) {
        this.runDijkstras(source, destination);
        return destination.countPaths(source);
    }

    /**
     *   This method just runs the modified version of Dikjstra's algorithm to update all the values of the Nodes
     */
    public void runDijkstras (Node source, Node destination) {
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
                    otherNode.replacePrevious(thisNode); // Overwrite the previous reference to thisNode
                }
                else if (newCost == otherNode.getValue()) { // If this path is equal to the existing shortest path...
                    otherNode.addPrevious(thisNode);
                }
            }
        }
    }

    public String toString () {
        return neighbors.toString();
    }
}
