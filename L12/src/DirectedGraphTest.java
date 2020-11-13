import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    @org.junit.jupiter.api.Test
    void BFS() {
        Hashtable<Integer, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(0, new ArrayList<>(Arrays.asList(new Edge(0,1),new Edge(0,2))));
        neighbors.put(1, new ArrayList<>(Collections.singletonList(new Edge(1,2))));
        neighbors.put(2, new ArrayList<>(Arrays.asList(new Edge(2,0),new Edge(2,3))));
        neighbors.put(3, new ArrayList<>(Collections.singletonList(new Edge(3,3))));
        DirectedGraph g = new DirectedGraph(neighbors);

        ArrayList<Integer> actual = g.BFS(0);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(0,1,2,3));
        assertEquals(expected, actual);

        actual = g.BFS(2);
        expected = new ArrayList<>(Arrays.asList(2,0,3,1));
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void DFS() {
        Hashtable<Integer, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(0, new ArrayList<>(Arrays.asList(new Edge(0,1),new Edge(0,2))));
        neighbors.put(1, new ArrayList<>(Collections.singletonList(new Edge(1,2))));
        neighbors.put(2, new ArrayList<>(Arrays.asList(new Edge(2,0),new Edge(2,3))));
        neighbors.put(3, new ArrayList<>(Collections.singletonList(new Edge(3,3))));
        DirectedGraph g = new DirectedGraph(neighbors);

        ArrayList<Integer> actual = g.DFS(0);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(0,2,3,1));
        assertEquals(expected, actual);

        actual = g.DFS(2);
        expected = new ArrayList<>(Arrays.asList(2,3,0,1));
        assertEquals(expected, actual);
    }
}