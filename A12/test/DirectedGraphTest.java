import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    @Test
    void shortestPaths () throws NoPathE {
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

        ArrayList<Edge> path = graph.shortestPath(s,t);
        assertEquals(4,path.size());
        assertEquals(sa,path.get(0));
        assertEquals(ac,path.get(1));
        assertEquals(cf,path.get(2));
        assertEquals(ft,path.get(3));
    }

    @Test
    public void topologicalSort () {
        Node v1 = new Node("v1", 0);
        Node v2 = new Node("v2", 0);
        Node v3 = new Node("v3", 0);
        Node v4 = new Node("v4", 0);
        Node v5 = new Node("v5", 0);
        Node v6 = new Node("v6", 0);
        Node v7 = new Node("v7", 0);

        Edge v12 = new Edge(v1,v2,0);
        Edge v13 = new Edge(v1,v3,0);
        Edge v14 = new Edge(v1,v4,0);
        Edge v24 = new Edge(v2,v4,0);
        Edge v25 = new Edge(v2,v5,0);
        Edge v36 = new Edge(v3,v6,0);
        Edge v43 = new Edge(v3,v3,0);
        Edge v46 = new Edge(v4,v6,0);
        Edge v47 = new Edge(v4,v7,0);
        Edge v54 = new Edge(v5,v4,0);
        Edge v57 = new Edge(v5,v7,0);
        Edge v76 = new Edge(v7,v6,0);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(v1, new ArrayList<>(Arrays.asList(v12,v13,v14)));
        neighbors.put(v2, new ArrayList<>(Arrays.asList(v24,v25)));
        neighbors.put(v3, new ArrayList<>(Collections.singletonList(v36)));
        neighbors.put(v4, new ArrayList<>(Arrays.asList(v43,v46,v47)));
        neighbors.put(v5, new ArrayList<>(Arrays.asList(v54,v57)));
        neighbors.put(v6, new ArrayList<>());
        neighbors.put(v7, new ArrayList<>(Collections.singletonList(v76)));

        DirectedGraph graph = new DirectedGraph(neighbors);

        Queue<Node> sort = graph.topologicalSort();
        assertEquals(7, sort.size());
        assertEquals(v1,sort.poll());
        assertEquals(v2,sort.poll());
        assertEquals(v5,sort.poll());
        assertEquals(v4,sort.poll());
        assertEquals(v3,sort.poll());
        assertEquals(v7,sort.poll());
        assertEquals(v6,sort.poll());
    }

    @Test
    void maximumFlow () {
        Node s = new Node("s", 0);
        Node a = new Node("a", 0);
        Node b = new Node("b", 0);
        Node c = new Node("c", 0);
        Node d = new Node("d", 0);
        Node t = new Node("t", 0);

        Edge sa = new Edge(s,a,4);
        Edge sb = new Edge(s,b,2);
        Edge ab = new Edge(a,b,1);
        Edge ac = new Edge(a,c,2);
        Edge ad = new Edge(a,d,4);
        Edge bd = new Edge(b,d,2);
        Edge ct = new Edge(c,t,3);
        Edge dt = new Edge(d,t,3);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa,sb)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab,ac,ad)));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(bd)));
        neighbors.put(c, new ArrayList<>(Collections.singletonList(ct)));
        neighbors.put(d, new ArrayList<>(Collections.singletonList(dt)));
        neighbors.put(t, new ArrayList<>());

        DirectedGraph graph = new DirectedGraph(neighbors);

        Collection<Edge> flow = graph.maximumFlow(s,t);
        assertEquals(8,flow.size());
        assertTrue(flow.contains(new Edge(s,a,3)));
        assertTrue(flow.contains(new Edge(s,b,2)));
        assertTrue(flow.contains(new Edge(a,b,0)));
        assertTrue(flow.contains(new Edge(a,c,2)));
        assertTrue(flow.contains(new Edge(a,d,1)));
        assertTrue(flow.contains(new Edge(b,d,2)));
        assertTrue(flow.contains(new Edge(c,t,2)));
        assertTrue(flow.contains(new Edge(d,t,2)));
    }

}