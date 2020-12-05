import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    @Test
    public void edgeBetweenTest() {
        int inf = Integer.MAX_VALUE;
        Node s = new Node("s", inf);
        Node a = new Node("a", inf);
        Node b = new Node("b", inf);
        Node c = new Node("c", inf);
        Node f = new Node("f", inf);
        Node t = new Node("t", inf);

        Edge sa = new Edge(s,a,3);
        Edge sb = new Edge(s,b,4);
        Edge ab = new Edge(a,b,6);
        Edge bf = new Edge(b,f,5);
        Edge af = new Edge(a,f,7);
        Edge ac = new Edge(a,c,2);
        Edge cf = new Edge(c,f,1);
        Edge ct = new Edge(c,t,8);
        Edge ft = new Edge(f,t,4);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa,sb)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab,af,ac)));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(bf)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cf,ct)));
        neighbors.put(f, new ArrayList<>(Collections.singletonList(ft)));
        neighbors.put(t, new ArrayList<>());

        DirectedGraph graph = new DirectedGraph(neighbors);

        assertTrue(graph.edgeBetween(s,a));
        assertTrue(graph.edgeBetween(s,b));
        assertTrue(graph.edgeBetween(a,b));
        assertTrue(graph.edgeBetween(b,f));
        assertTrue(graph.edgeBetween(a,f));
        assertTrue(graph.edgeBetween(a,c));
        assertTrue(graph.edgeBetween(c,f));
        assertTrue(graph.edgeBetween(c,t));
        assertTrue(graph.edgeBetween(f,t));

        assertFalse(graph.edgeBetween(a,s));
        assertFalse(graph.edgeBetween(t,s));
        assertFalse(graph.edgeBetween(a,t));
    }

    @Test
    public void transposeTest() {
        int inf = Integer.MAX_VALUE;
        Node s = new Node("s", inf);
        Node a = new Node("a", inf);
        Node b = new Node("b", inf);
        Node c = new Node("c", inf);
        Node f = new Node("f", inf);
        Node t = new Node("t", inf);

        Edge sa = new Edge(s,a,3);
        Edge sb = new Edge(s,b,4);
        Edge ab = new Edge(a,b,6);
        Edge bf = new Edge(b,f,5);
        Edge af = new Edge(a,f,7);
        Edge ac = new Edge(a,c,2);
        Edge cf = new Edge(c,f,1);
        Edge ct = new Edge(c,t,8);
        Edge ft = new Edge(f,t,4);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa,sb)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab,af,ac)));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(bf)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cf,ct)));
        neighbors.put(f, new ArrayList<>(Collections.singletonList(ft)));
        neighbors.put(t, new ArrayList<>());

        DirectedGraph graph = new DirectedGraph(neighbors);
        graph = graph.transpose();

        assertTrue(graph.edgeBetween(a,s));
        assertTrue(graph.edgeBetween(b,s));
        assertTrue(graph.edgeBetween(b,a));
        assertTrue(graph.edgeBetween(f,b));
        assertTrue(graph.edgeBetween(f,a));
        assertTrue(graph.edgeBetween(c,a));
        assertTrue(graph.edgeBetween(f,c));
        assertTrue(graph.edgeBetween(t,c));
        assertTrue(graph.edgeBetween(t,f));

        assertFalse(graph.edgeBetween(s,a));
        assertFalse(graph.edgeBetween(t,s));
        assertFalse(graph.edgeBetween(t,a));
    }

    @Test
    public void kosarajusTest() {
        // Graph seen here: https://www.geeksforgeeks.org/strongly-connected-components/
        Node zero = new Node("0", 0);
        Node one = new Node("1", 1);
        Node two = new Node("2", 2);
        Node three = new Node("3", 3);
        Node four = new Node("4", 4);

        Edge zeroTwo = new Edge(zero, two, 1);
        Edge twoOne = new Edge(two, one, 1);
        Edge oneZero = new Edge(one, zero, 1);
        Edge zeroThree = new Edge(zero, three, 1);
        Edge threeFour = new Edge(three, four, 1);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(zero, new ArrayList<>(Arrays.asList(zeroTwo, zeroThree)));
        neighbors.put(one, new ArrayList<>(Collections.singletonList(oneZero)));
        neighbors.put(two, new ArrayList<>(Collections.singletonList(twoOne)));
        neighbors.put(three, new ArrayList<>(Collections.singletonList(threeFour)));
        neighbors.put(four, new ArrayList<>());

        DirectedGraph graph = new DirectedGraph(neighbors);
        ArrayList<ArrayList<Node>> scc = graph.kosarajus(zero);
        System.out.println(scc.toString());
        assertEquals(3, scc.size());
    }
}