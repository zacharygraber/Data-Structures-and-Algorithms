import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    @Test
    void simpleGraph () {
        Node s = new Node("s");
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node f = new Node("f");
        Node t = new Node("t");

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

        ArrayList<Node> path = graph.shortestPath(s,t);
        assertEquals(5,path.size());
        assertEquals(s,path.get(0));
        assertEquals(a,path.get(1));
        assertEquals(c,path.get(2));
        assertEquals(f,path.get(3));
        assertEquals(t,path.get(4));

    }

    @Test
    void theSimplestCaseICanThinkOf() {
        Node start = new Node("start");
        Node end = new Node("end");

        Edge startToEnd = new Edge(start, end, 5);
        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(start, new ArrayList<>(Collections.singletonList(startToEnd)));
        neighbors.put(end, new ArrayList<>());
        DirectedGraph graph = new DirectedGraph(neighbors);

        ArrayList<Node> path = graph.shortestPath(start, end);
        assertEquals(2, path.size());
        assertEquals(start, path.get(0));
        assertEquals(end, path.get(1));
    }

    @Test
    void worksForAChain() {
        Node n0 = new Node("n0");
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Node n3 = new Node("n3");
        Node n4 = new Node("n4");
        Node n5 = new Node("n5");

        Edge e1 = new Edge(n0, n1, 5);
        Edge e2 = new Edge(n1, n2, 5);
        Edge e3 = new Edge(n2, n3, 5);
        Edge e4 = new Edge(n3, n4, 5);
        Edge e5 = new Edge(n4, n5, 5);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(n0, new ArrayList<>(Collections.singletonList(e1)));
        neighbors.put(n1, new ArrayList<>(Collections.singletonList(e2)));
        neighbors.put(n2, new ArrayList<>(Collections.singletonList(e3)));
        neighbors.put(n3, new ArrayList<>(Collections.singletonList(e4)));
        neighbors.put(n4, new ArrayList<>(Collections.singletonList(e5)));
        neighbors.put(n5, new ArrayList<>());
        DirectedGraph graph = new DirectedGraph(neighbors);

        ArrayList<Node> path = graph.shortestPath(n0, n5);
        assertEquals(6, path.size());
        assertEquals(n0, path.get(0));
        assertEquals(n1, path.get(1));
        assertEquals(n2, path.get(2));
        assertEquals(n3, path.get(3));
        assertEquals(n4, path.get(4));
        assertEquals(n5, path.get(5));
    }

    @Test
    void ignoresLoopbacks() {
        Node n0 = new Node("n0");
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");

        Edge e1 = new Edge(n0, n1, 5);
        Edge e2 = new Edge(n1, n2, 5);
        Edge e3 = new Edge(n2, n0, 5);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(n0, new ArrayList<>(Collections.singletonList(e1)));
        neighbors.put(n1, new ArrayList<>(Collections.singletonList(e2)));
        neighbors.put(n2, new ArrayList<>(Collections.singletonList(e3)));

        DirectedGraph graph = new DirectedGraph(neighbors);

        ArrayList<Node> path = graph.shortestPath(n0, n2);
        assertEquals(3, path.size());
        assertEquals(n0, path.get(0));
        assertEquals(n1, path.get(1));
        assertEquals(n2, path.get(2));
    }

    @Test
    void worksForStartAndEndSameNode() {
        Node n0 = new Node("n0");
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");

        Edge e1 = new Edge(n0, n1, 5);
        Edge e2 = new Edge(n1, n2, 5);
        Edge e3 = new Edge(n2, n0, 5);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(n0, new ArrayList<>(Collections.singletonList(e1)));
        neighbors.put(n1, new ArrayList<>(Collections.singletonList(e2)));
        neighbors.put(n2, new ArrayList<>(Collections.singletonList(e3)));

        DirectedGraph graph = new DirectedGraph(neighbors);

        ArrayList<Node> path = graph.shortestPath(n0, n0);
        assertEquals(1, path.size());
        assertEquals(n0, path.get(0));
    }
}