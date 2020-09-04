import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    @Test
    void test1 () throws EmptyQueueE {
        Queue q = new Queue();
        System.out.printf("q = %s%n", q);

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        System.out.printf("q = %s%n", q);

        System.out.printf("Dequeue %d%n", q.dequeue());
        System.out.printf("q = %s%n", q);

        System.out.printf("Dequeue %d%n", q.dequeue());
        System.out.printf("q = %s%n", q);

        System.out.printf("Dequeue %d%n", q.dequeue());
        System.out.printf("q = %s%n", q);

        System.out.printf("Dequeue %d%n", q.dequeue());
        System.out.printf("q = %s%n", q);

        System.out.printf("Dequeue %d%n", q.dequeue());
        System.out.printf("q = %s%n", q);
    }

    // create amortization tests below

}