import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    @Test
    void testEmptyList () {
        LinkedList<Integer> ints = new EmptyList<>();
    }

    @Test
    void testStack () throws EmptyListE {
        StackI<Integer> s = new EmptyList<Integer>().push(1).push(2).push(3);
        assertEquals(3, s.top());
        s = s.pop();
        assertEquals(2, s.top());
        s = s.pop();
        assertEquals(1, s.top());
        s = s.pop();
    }

    @Test
    void testQueue () throws EmptyListE {
        QueueI<Integer> q = new EmptyList<Integer>().enqueue(1).enqueue(2).enqueue(3);
        assertEquals(1, q.front());
        q = q.dequeue();
        assertEquals(2, q.front());
        q = q.dequeue();
        assertEquals(3, q.front());
        q = q.dequeue();
    }

    @Test
    void testDequeue () throws EmptyListE {
        DequeueI<Integer> q = new EmptyList<Integer>().
                enqueueBack(1).enqueueBack(2).enqueueBack(3);
        assertEquals(1,q.front());
        assertEquals(2,q.dequeueFront().front());
        assertEquals(3,q.dequeueFront().dequeueFront().front());

        q = q.enqueueFront(4).enqueueFront(5);
        assertEquals(3, q.back());
        assertEquals(2, q.dequeueBack().back());
        q = q.dequeueFront();
        q = q.dequeueBack();
        assertEquals(2, q.back());
        assertEquals(4, q.front());
    }


}