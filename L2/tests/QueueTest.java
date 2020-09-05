import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @Test
    void amortizationEnqueueTest() {
        long[] e1, e2, e3;

        Queue q1 = new Queue();
        Queue q2 = new Queue();
        Queue q3 = new Queue();

        // populates e1,e2,e3 with the times taken to enqueue each set of 100,000 elements
        e1 = enqueueTimeAll(q1, getIntList(1000000));
        e2 = enqueueTimeAll(q2, getIntList(10000000));
        e3 = enqueueTimeAll(q3, getIntList(30000000));

        // Asserts that there is essentially NO upward trend in time taken to enqueue as the queue grows larger.
        assertTrue(leastSquaresSlope(e1) < 0.01);
        assertTrue(leastSquaresSlope(e2) < 0.01);
        assertTrue(leastSquaresSlope(e3) < 0.01);
    }

    @Test
    void amortizationDequeueTest() throws EmptyQueueE {
        long[] d1, d2, d3;

        Queue q1 = new Queue();
        Queue q2 = new Queue();
        Queue q3 = new Queue();

        // populate the queues with elements
        enqueueAll(q1, getIntList(1000));
        enqueueAll(q2, getIntList(5000));
        enqueueAll(q3, getIntList(10000));

        // populates d1,d2,d3 with the time taken to dequeue each set of 100 elements
        d1 = dequeueTimeAll(q1, 1000);
        d2 = dequeueTimeAll(q2, 5000);
        d3 = dequeueTimeAll(q3, 10000);

        // Asserts that there is essentially NO upward/downward trend in time taken to dequeue as the size of the queue shrinks.
        assertTrue(leastSquaresSlope(d1) < 0.01 && leastSquaresSlope(d1) > -0.01);
        assertTrue(leastSquaresSlope(d2) < 0.01 && leastSquaresSlope(d2) > -0.01);
        assertTrue(leastSquaresSlope(d3) < 0.01 && leastSquaresSlope(d3) > -0.01);
    }

    private static void enqueueAll(Queue q, List<Integer> vals) {
        for (int val : vals) {
            q.enqueue(val);
        }
    }

    private static long[] enqueueTimeAll(Queue q, List<Integer> vals) {
        long[] times = new long[vals.size() / 100000];
        Instant startTime;
        Instant endTime;

        for (int i = 0; i < vals.size() / 100000; i++) {
            startTime = Instant.now();
            for (int j = 0 + (100000 * i); j < 100000 + (100000 * i); j++) {
                q.enqueue(vals.get(j));
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toMillis();
        }

        return times;
    }

    private static long[] dequeueTimeAll(Queue q, int size) throws EmptyQueueE {
        long[] times = new long[size / 100];
        Instant startTime;
        Instant endTime;

        for (int i = 0; i < size / 100; i++) {
            startTime = Instant.now();
            for (int j = 0; j < 100; j++) {
                q.dequeue();
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toMillis();
        }

        return times;
    }

    private static List<Integer> getIntList(int size) {
        Random rand = new Random();
        IntStream stream = rand.ints(size);
        List<Integer> list = stream.boxed().collect(Collectors.toList());
        return list;
    }

    private static double leastSquaresSlope(long[] vals) {
        long sumOfXVals = 0, sumOfXSquaredVals = 0, sumOfYVals = 0, sumOfXYProducts = 0;
        int n = vals.length;
        long x; // The size of the queue
        long y; // The time taken for these 100,000 operations
        for (int i = 0; i < n; i++) {
            x = 100000 * i;
            y = vals[i];

            sumOfXVals += x;
            sumOfYVals += y;
            sumOfXSquaredVals += Math.pow(x, 2);
            sumOfXYProducts += x * y;
        }

        // Formula for best squares method found at https://mathbitsnotebook.com/Algebra1/StatisticsReg/ST2lCalculatorBest.html
        return ((double) (n * sumOfXYProducts) - (sumOfXVals * sumOfYVals)) / ((n * sumOfXSquaredVals) - (Math.pow(sumOfXVals, 2)));
    }
}