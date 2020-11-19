import com.sun.source.tree.Tree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void minChild () {
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        a.setValue(0);
        b.setValue(3);
        c.setValue(5);
        d.setValue(8);

        Heap h = new Heap(Arrays.asList(a,b,c,d));
        ArrayList<Node> nodes = h.getNodes();

        assertEquals(a,nodes.get(0));
        assertEquals(b,nodes.get(1));
        assertEquals(c,nodes.get(2));
        assertEquals(d,nodes.get(3));

        assertEquals(b,h.getLeftChild(a).get());
        assertEquals(c,h.getRightChild(a).get());
        assertEquals(d,h.getLeftChild(b).get());
        assertEquals(b,h.getMinChild(a).get());
        assertEquals(d,h.getMinChild(b).get());
    }

    @Test
    public void update () {
        ArrayList<Node> items = new ArrayList<>();

        Node n;

        n = new Node("a1");
        n.setValue(6);
        items.add(n);

        n = new Node("a2");
        n.setValue(1);
        items.add(n);

        n = new Node("a3");
        n.setValue(8);
        items.add(n);

        n = new Node("a4");
        n.setValue(2);
        items.add(n);

        n = new Node("a5");
        n.setValue(4);
        items.add(n);

        n = new Node("a6");
        n.setValue(7);
        items.add(n);

        n = new Node("a7");
        n.setValue(9);
        items.add(n);

        n = new Node("a8");
        n.setValue(3);
        items.add(n);

        n = new Node("a9");
        n.setValue(5);
        items.add(n);

        Heap h = new Heap(items);
        TreePrinter.print(h.getMin());

        h.update(h.getNodes().get(7),0);
        TreePrinter.print(h.getMin());
    }

    @Test
    public void sort () {
        ArrayList<Node> items = new ArrayList<>();

        Node n;

        n = new Node("a1");
        n.setValue(6);
        items.add(n);

        n = new Node("a2");
        n.setValue(1);
        items.add(n);

        n = new Node("a3");
        n.setValue(8);
        items.add(n);

        n = new Node("a4");
        n.setValue(2);
        items.add(n);

        n = new Node("a5");
        n.setValue(4);
        items.add(n);

        n = new Node("a6");
        n.setValue(7);
        items.add(n);

        n = new Node("a7");
        n.setValue(9);
        items.add(n);

        n = new Node("a8");
        n.setValue(3);
        items.add(n);

        n = new Node("a9");
        n.setValue(5);
        items.add(n);

        Heap h = new Heap(items);
        TreePrinter.print(h.getMin());

        for (int i = 1; i < 10; i++) assertEquals(i, h.extractMin().getValue());
    }

    @Test
    public void extractMin() {
        ArrayList<Node> items = new ArrayList<>();

        Node n;

        n = new Node("a1");
        n.setValue(6);
        items.add(n);

        n = new Node("a2");
        n.setValue(1);
        items.add(n);

        n = new Node("a3");
        n.setValue(8);
        items.add(n);

        n = new Node("a4");
        n.setValue(2);
        items.add(n);

        n = new Node("a5");
        n.setValue(4);
        items.add(n);

        n = new Node("a6");
        n.setValue(7);
        items.add(n);

        n = new Node("a7");
        n.setValue(9);
        items.add(n);

        n = new Node("a8");
        n.setValue(3);
        items.add(n);

        n = new Node("a9");
        n.setValue(5);
        items.add(n);

        Heap h = new Heap(items);
        TreePrinter.print(h.getMin());

        assertEquals(9, h.getSize());

        // First extraction out of 9 nodes
        assertEquals("a2", h.extractMin().toString());
        assertEquals(8, h.getSize());
        assertEquals("a4", h.getMin().toString());

        // Second extraction out of 9 nodes
        assertEquals("a4", h.extractMin().toString());
        assertEquals(7, h.getSize());
        assertEquals("a8", h.getMin().toString());

        // Third extraction out of 9 nodes
        assertEquals("a8", h.extractMin().toString());
        assertEquals(6, h.getSize());
        assertEquals("a5", h.getMin().toString());

        // Fourth extraction out of 9 nodes
        assertEquals("a5", h.extractMin().toString());
        assertEquals(5, h.getSize());
        assertEquals("a9", h.getMin().toString());

        // Fifth extraction out of 9 nodes
        assertEquals("a9", h.extractMin().toString());
        assertEquals(4, h.getSize());
        assertEquals("a1", h.getMin().toString());

        // Sixth extraction out of 9 nodes
        assertEquals("a1", h.extractMin().toString());
        assertEquals(3, h.getSize());
        assertEquals("a6", h.getMin().toString());

        // Seventh extraction out of 9 nodes
        assertEquals("a6", h.extractMin().toString());
        assertEquals(2, h.getSize());
        assertEquals("a3", h.getMin().toString());

        // Eighth extraction out of 9 nodes
        assertEquals("a3", h.extractMin().toString());
        assertEquals(1, h.getSize());
        assertEquals("a7", h.getMin().toString());

        // Last extraction out of 9 nodes
        assertEquals("a7", h.extractMin().toString());
        assertEquals(0, h.getSize());
    }

    @Test
    public void getParent() {
        ArrayList<Node> items = new ArrayList<>();

        Node a1,a2,a3;

        a1 = new Node("a1");
        a1.setValue(6);
        items.add(a1);

        a2 = new Node("a2");
        a2.setValue(1);
        items.add(a2);

        a3 = new Node("a3");
        a3.setValue(8);
        items.add(a3);

        Heap h = new Heap(items);
        TreePrinter.print(h.getMin());

        assertEquals(a2, h.getParent(a1).get());
        assertEquals(a2, h.getParent(a3).get());
        assertTrue(h.getParent(a2).isEmpty());
    }

    @Test
    public void getLeftChild() {
        ArrayList<Node> items = new ArrayList<>();

        Node a1,a2,a3;

        a1 = new Node("a1");
        a1.setValue(6);
        items.add(a1);

        a2 = new Node("a2");
        a2.setValue(1);
        items.add(a2);

        a3 = new Node("a3");
        a3.setValue(8);
        items.add(a3);

        Heap h = new Heap(items);
        TreePrinter.print(h.getMin());

        assertEquals(a1, h.getLeftChild(a2).get());
        assertTrue(h.getLeftChild(a1).isEmpty());
        assertTrue(h.getLeftChild(a3).isEmpty());
    }

    @Test
    public void getRightChild() {
        ArrayList<Node> items = new ArrayList<>();

        Node a1,a2,a3;

        a1 = new Node("a1");
        a1.setValue(6);
        items.add(a1);

        a2 = new Node("a2");
        a2.setValue(1);
        items.add(a2);

        a3 = new Node("a3");
        a3.setValue(8);
        items.add(a3);

        Heap h = new Heap(items);
        TreePrinter.print(h.getMin());

        assertEquals(a3, h.getRightChild(a2).get());
        assertTrue(h.getRightChild(a1).isEmpty());
        assertTrue(h.getRightChild(a3).isEmpty());
    }

}