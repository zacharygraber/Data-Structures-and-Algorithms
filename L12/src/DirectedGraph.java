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
        //TODO
	return null;
    }

    ArrayList<Integer> DFS (int source) {
        //TODO
	return null;
    }

    public String toString () {
        return neighbors.toString();
    }
}
