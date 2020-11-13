import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Array;
import java.util.*;

public class DirectedGraph {
    private final Hashtable<Integer,ArrayList<Edge>> neighbors;
    private final Set<Integer> nodes;

    DirectedGraph(Hashtable<Integer,ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
        this.nodes = neighbors.keySet();
    }

    ArrayList<Integer> BFS (int source) {
        // To avoid writing a Queue class, since I'm lazy, I'll use a LinkedList as one
        //    by inserting in the back and removing from the front.
        LinkedList<Integer> q = new LinkedList<>();
        ArrayList<Integer> visited = new ArrayList<>();
        q.add(source);
        int thisNode;
        while (q.peek() != null) { // Loop until the queue is empty
            thisNode = q.removeFirst(); // Get the oldest thing added
            if (!visited.contains(thisNode)) {
                visited.add(thisNode); // Start by enqueuing the source node

                // Add each neighbor of this Node to the queue, if it hasn't been visited yet.
                for (Edge e : this.neighbors.get(thisNode)) {
                    q.addLast(e.getDestination());
                }
            }
        }
        return visited;
    }

    ArrayList<Integer> DFS (int source) {
        // This is almost identical to BFS, just with a stack.
        LinkedList<Integer> s = new LinkedList<>();
        ArrayList<Integer> visited = new ArrayList<>();
        s.push(source);
        int thisNode;
        while (s.peek() != null) { // Loop until the queue is empty
            thisNode = s.pop(); // Get the oldest thing added
            if (!visited.contains(thisNode)) {
                visited.add(thisNode); // Start by enqueuing the source node

                // Add each neighbor of this Node to the queue, if it hasn't been visited yet.
                for (Edge e : this.neighbors.get(thisNode)) {
                    s.push(e.getDestination());
                }
            }
        }
        return visited;
    }

    public String toString () {
        return neighbors.toString();
    }
}
