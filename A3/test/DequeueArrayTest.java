import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DequeueArrayTest {

    @Test
    public void dequeDoubleNoResize() throws NoSuchElementE {
        DequeueArray<Integer> d = new DequeueArrayDouble<>(5);
        assertEquals(0, d.size());
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        assertEquals(3, (int) d.removeFirst());
        assertEquals(1, (int) d.removeLast());
        assertEquals(2, (int) d.removeLast());
        assertEquals(0, d.size());
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);
        d.addFirst(5);
        assertEquals(5, (int) d.removeFirst());
        assertEquals(3, (int) d.removeFirst());
        assertEquals(1, (int) d.removeFirst());
        assertEquals(2, (int) d.removeFirst());
        assertEquals(4, (int) d.removeFirst());
    }

    @Test
    public void dequeDoubleResize() throws NoSuchElementE {
        DequeueArray<Integer> d = new DequeueArrayDouble<>(5);
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);
        d.addFirst(5);
        d.addFirst(6);
        assertEquals(10, d.getCapacity());
        assertEquals(6, (int) d.removeFirst());
        assertEquals(5, (int) d.removeFirst());
        assertEquals(3, (int) d.removeFirst());
        assertEquals(1, (int) d.removeFirst());
        assertEquals(2, (int) d.removeFirst());
        assertEquals(4, (int) d.removeFirst());
    }
}
