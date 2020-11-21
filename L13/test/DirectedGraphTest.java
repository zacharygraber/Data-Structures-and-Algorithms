import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class DirectedGraphTest {
    @Test
    public void countMinimumPathsTest() {
        // The graph I'm using for this test: https://cdn.discordapp.com/attachments/308431945384198146/779545993028829244/20201120_221712.jpg
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");
        Node g = new Node("g");

        Edge aToB = new Edge(a,b, 1);
        Edge bToC = new Edge(b,c, 3);
        Edge bToD = new Edge(b,d, 5);
        Edge bToE = new Edge(b,e, 10);
        Edge cToE = new Edge(c,e, 5);
        Edge dToE = new Edge(d,e, 3);
        Edge eToF = new Edge(e,f, 1);
        Edge aToF = new Edge(a,f, 10);
        Edge aToG = new Edge(a,g, 4);
        Edge gToF = new Edge(g,f, 6);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(a, new ArrayList<>(Arrays.asList(aToB, aToF, aToG)));
        neighbors.put(b, new ArrayList<>(Arrays.asList(bToC, bToD, bToE)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cToE)));
        neighbors.put(d, new ArrayList<>(Arrays.asList(dToE)));
        neighbors.put(e, new ArrayList<>(Arrays.asList(eToF)));
        neighbors.put(f, new ArrayList<>());
        neighbors.put(g, new ArrayList<>(Arrays.asList(gToF)));

        DirectedGraph graph = new DirectedGraph(neighbors);

        int paths = graph.numShortestPaths(a, f);
        assertEquals(4, paths);
    }

    @Test
    public void getAllPossiblePathsTest() {
        // The graph I'm using for this test: https://cdn.discordapp.com/attachments/308431945384198146/779545993028829244/20201120_221712.jpg
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");
        Node g = new Node("g");

        Edge aToB = new Edge(a,b, 1);
        Edge bToC = new Edge(b,c, 3);
        Edge bToD = new Edge(b,d, 5);
        Edge bToE = new Edge(b,e, 10);
        Edge cToE = new Edge(c,e, 5);
        Edge dToE = new Edge(d,e, 3);
        Edge eToF = new Edge(e,f, 1);
        Edge aToF = new Edge(a,f, 10);
        Edge aToG = new Edge(a,g, 4);
        Edge gToF = new Edge(g,f, 6);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(a, new ArrayList<>(Arrays.asList(aToB, aToF, aToG)));
        neighbors.put(b, new ArrayList<>(Arrays.asList(bToC, bToD, bToE)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cToE)));
        neighbors.put(d, new ArrayList<>(Arrays.asList(dToE)));
        neighbors.put(e, new ArrayList<>(Arrays.asList(eToF)));
        neighbors.put(f, new ArrayList<>());
        neighbors.put(g, new ArrayList<>(Arrays.asList(gToF)));

        DirectedGraph graph = new DirectedGraph(neighbors);
        graph.runDijkstras(a,f);
        for (ArrayList<Node> path : f.getAllPaths(a)) {
            System.out.print("[");
            for (Node n : path) {
                System.out.print(n.toString()+",");
            }
            System.out.print("]\n");
        }
    }

    @Test
    public void getLeastNodesPath() {
        // The graph I'm using for this test: https://cdn.discordapp.com/attachments/308431945384198146/779545993028829244/20201120_221712.jpg
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");
        Node g = new Node("g");

        Edge aToB = new Edge(a,b, 1);
        Edge bToC = new Edge(b,c, 3);
        Edge bToD = new Edge(b,d, 5);
        Edge bToE = new Edge(b,e, 10);
        Edge cToE = new Edge(c,e, 5);
        Edge dToE = new Edge(d,e, 3);
        Edge eToF = new Edge(e,f, 1);
        Edge aToF = new Edge(a,f, 10);
        Edge aToG = new Edge(a,g, 4);
        Edge gToF = new Edge(g,f, 6);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(a, new ArrayList<>(Arrays.asList(aToB, aToF, aToG)));
        neighbors.put(b, new ArrayList<>(Arrays.asList(bToC, bToD, bToE)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cToE)));
        neighbors.put(d, new ArrayList<>(Arrays.asList(dToE)));
        neighbors.put(e, new ArrayList<>(Arrays.asList(eToF)));
        neighbors.put(f, new ArrayList<>());
        neighbors.put(g, new ArrayList<>(Arrays.asList(gToF)));

        DirectedGraph graph = new DirectedGraph(neighbors);
        ArrayList<Node> expectedPath = new ArrayList<>(Arrays.asList(a,f));
        ArrayList<Node> actualPath = graph.shortestPathLeastNodes(a,f);
        for (int i = 0; i < actualPath.size(); i++) {
            assertEquals(expectedPath.get(i), actualPath.get(i));
        }
    }
}
