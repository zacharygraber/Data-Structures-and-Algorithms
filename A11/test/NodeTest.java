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

}