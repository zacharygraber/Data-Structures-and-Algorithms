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
        s = new Stack<>();
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
        s = new Stack<>();
    }

    void setNeighbors (Hashtable<Node,ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * Just a quick function to test whether there is an edge going from Nodes source to destination
     */
    public boolean edgeBetween(Node source, Node dest) {
        ArrayList<Edge> edges = this.neighbors.get(source);
        if (edges == null) {
            return false;
        }
        for (Edge e : edges) {
            if (e.getDestination().equals(dest)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Returns the transpose of a Graph as a clean copy (all edges reversed)
     */
    public DirectedGraph transpose() {
        // Make a new Hash Table for the reversed edges and put an empty ArrayList at each Node.
        Hashtable<Node, ArrayList<Edge>> newNeighbors = new Hashtable<>();
        for (Node n : this.nodes) {
            newNeighbors.put(n, new ArrayList<>());
        }

        // Loop through all edges and put their reverses in the new neighbors
        for (ArrayList<Edge> edgeList : this.neighbors.values()) {
            for (Edge e : edgeList) {
                newNeighbors.get(e.getDestination()).add(e.flip());
            }
        }

        DirectedGraph result = new DirectedGraph(this);
        result.setNeighbors(newNeighbors);
        return result;
    }

    /**
     * Implements Kosaraju's Algorithm with a Stack and two DFSs
     */
    public ArrayList<ArrayList<Node>> kosarajus(Node source) {
        for (Node n : nodes) n.reset(0); // Set all Nodes unvisited
        Stack<Node> stack = this.DFS(source);
        DirectedGraph t = this.transpose();

        for (Node n : nodes) n.reset(0); // Again set all Node as unvisited
        ArrayList<ArrayList<Node>> resultSCCs = new ArrayList<>();
        while (!stack.isEmpty()) {
            Node v = stack.pop();
            if (!v.isVisited()) {
                ArrayList<Node> scc = new ArrayList<>();
                for (Node n :  t.DFS(v)) {
                    scc.add(n);
                }
                // DEBUG PRINTING ///////////////////////////////////////////
//                System.out.println("V: " + v.toString() + "\nSCC: ");
//                for (Node n : scc) System.out.print(n.toString() + " ");
//                System.out.println("\n\n");
                /////////////////////////////////////////////////////////////
                resultSCCs.add(scc);
            }
        }
        return resultSCCs;
    }

    /**
     *  Runs Depth-First Search on the Graph and returns a Stack representing it
     */
    private Stack<Node> s; // s needs to be shared between DFS and the recursive DFSHelper, so it's defined out here.
    private Stack<Node> DFS(Node source) {
        // Empty the stack
        while (!s.isEmpty()) s.pop();

        DFSHelper(source);
        return s;
    }
    private void DFSHelper(Node source) {
        source.setVisited(); // Set the node as visited
        for (Edge e : neighbors.get(source)) { // Check all its neighbors
            Node neighbor = e.getDestination();
            if (!neighbor.isVisited()) {
                DFSHelper(neighbor); // If the neighbor hasn't been visited, visit it first.
            }
        }
        s.push(source); // Push this node onto the stack
    }

    public String toString () {
        return neighbors.toString();
    }

}
