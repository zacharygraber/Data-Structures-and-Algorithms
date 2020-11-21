import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UndirectedGraphTest {

    @Test
    void simpleGraph() {
        int inf = Integer.MAX_VALUE;
        Node s = new Node("s", inf);
        Node a = new Node("a", inf);
        Node b = new Node("b", inf);
        Node c = new Node("c", inf);
        Node d = new Node("d", inf);
        Node t = new Node("t", inf);

        Edge sa = new Edge(s, a, 7);
        Edge sc = new Edge(s, c, 8);
        Edge ab = new Edge(a, b, 6);
        Edge ac = new Edge(a, c, 3);
        Edge cb = new Edge(c, b, 4);
        Edge cd = new Edge(c, d, 3);
        Edge bd = new Edge(b, d, 2);
        Edge bt = new Edge(b, t, 5);
        Edge dt = new Edge(d, t, 2);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa, sc)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ac, ab, sa.flip())));
        neighbors.put(b, new ArrayList<>(Arrays.asList(bd,bt,ab.flip(),cb.flip())));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cb, cd, ac.flip(), sc.flip())));
        neighbors.put(d, new ArrayList<>(Arrays.asList(dt,bd.flip(),cd.flip())));
        neighbors.put(t, new ArrayList<>(Arrays.asList(bt.flip(),dt.flip())));

        UndirectedGraph graph = new UndirectedGraph(neighbors);

        Set<Edge> tree = graph.minimumSpanningTree(s);
        assertEquals(5, tree.size());
        assertTrue(tree.contains(sa));
        assertTrue(tree.contains(ac));
        assertTrue(tree.contains(cd));
        assertTrue(tree.contains(bd.flip()));
        assertTrue(tree.contains(dt));
    }
}