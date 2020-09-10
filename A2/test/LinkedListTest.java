import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    void linkedListStringTests () throws EmptyListE {
        LinkedList<String> testList = new EmptyList<>();
        testList = testList.insertAt(0, "foo");
        testList = testList.insertAt(1, "and");
        testList = testList.insertAt(2, "bar");
        assertEquals("foo : and : bar : #", testList.toString());
        assertEquals(3, testList.length());
        assertTrue(testList.getAt(0).equals("foo"));
        assertTrue(testList.getAt(1).equals("and"));
        assertTrue(testList.getAt(2).equals("bar"));
        testList = testList.insertAt(2, "also");
        assertEquals("foo : and : also : bar : #", testList.toString());
        assertEquals(4, testList.length());
        assertTrue(testList.getAt(0).equals("foo"));
        assertTrue(testList.getAt(1).equals("and"));
        assertTrue(testList.getAt(2).equals("also"));
        assertTrue(testList.getAt(3).equals("bar"));
        testList = testList.removeAt(1);
        assertTrue(testList.getAt(1).equals("also"));
        assertEquals(3, testList.length());
        assertEquals("foo : also : bar : #", testList.toString());
        testList = testList.removeAt(0);
        assertEquals("also : bar : #", testList.toString());
    }

    @Test
    void stackTimeTests () throws EmptyListE {
        int[] referenceArray = getIntArray(100000);
        StackI<Integer> s = new EmptyList<>();

        long[] times = new long[referenceArray.length / 100];
        Instant startTime;
        Instant endTime;

        // Test time complexity of pushing. Theoretically, this should be O(1), since we push by inserting at index 0, which should take constant time.
        for (int i = 0; i < referenceArray.length / 100; i++) {
            startTime = Instant.now();
            for (int j = 0 + (100 * i); j < 100 + (100 * i); j++) {
                s = s.push(referenceArray[j]);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toMillis();
        }
        // Assert that there is essentially 0 positive correlation between StackI size and time to enqueue
        assertTrue(leastSquaresSlope(times) < 0.01);

        // Test time complexity of popping.
        // This should also be O(1) since removing from index 0 takes constant time.
        times = new long[referenceArray.length / 100];
        for (int i = 0; i < referenceArray.length / 100; i++) {
            startTime = Instant.now();
            for (int j = 0; j < 100; j++) {
                s = s.pop();
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toMillis();
        }
        // A negative correlation would imply that, as the size decreases, the operation becomes faster. This should not be the case with constant time.
        assertTrue(leastSquaresSlope(times) > -0.01);
    }

    @Test
    void queueTimeTests () throws EmptyListE {
        int[] referenceArray = getIntArray(10000);
        QueueI<Integer> q = new EmptyList<>();

        long[] times = new long[referenceArray.length / 100];
        Instant startTime;
        Instant endTime;

        // Test time complexity of enqueueing. This should be O(N) since we enqueue at the end of the linked list.
        for (int i = 0; i < referenceArray.length / 100; i++) {
            startTime = Instant.now();
            for (int j = 0 + (100 * i); j < 100 + (100 * i); j++) {
                q = q.enqueue(referenceArray[j]);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // A slope of 0.6 would at least imply a reasonable positive correlation between queue size and enqueue times.
        assertTrue(leastSquaresSlope(times) > 0.6);


        // Test time complexity of dequeueing. This should take O(1) since removeAt(0) should be constant time.
        times = new long[referenceArray.length / 100];
        for (int i = 0; i < referenceArray.length / 100; i++) {
            startTime = Instant.now();
            for (int j = 0; j < 100; j++) {
                q = q.dequeue();
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // A negative correlation would imply that, as the size decreases, the operation becomes faster. This should not be the case with constant time.
        assertTrue(leastSquaresSlope(times) > -0.01);
    }

    /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    *  WARNING! This will cause a Stack Overflow if you do not increase the Stack size with -Xss parameter
    *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
    @Test
    void dequeueTimeTests () throws EmptyListE {
        int[] referenceArray = getIntArray(10000);
        DequeueI<Integer> q = new EmptyList<>();

        long[] times = new long[referenceArray.length / 100];
        Instant startTime;
        Instant endTime;

        // Test time complexity of enqueue back. This should be O(N) since we enqueue at the end of the linked list.
        for (int i = 0; i < referenceArray.length / 100; i++) {
            startTime = Instant.now();
            for (int j = 0 + (100 * i); j < 100 + (100 * i); j++) {
                q = q.enqueueBack(referenceArray[j]);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // A slope of 0.6 would at least imply a reasonable positive correlation between queue size and enqueue times.
        assertTrue(leastSquaresSlope(times) > 0.6);

        // Test time complexity of dequeue front. This should take O(1) since removeAt(0) should be constant time.
        times = new long[referenceArray.length / 100];
        for (int i = 0; i < referenceArray.length / 100; i++) {
            startTime = Instant.now();
            for (int j = 0; j < 100; j++) {
                q = q.dequeueFront();
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // A negative correlation would imply that, as the size decreases, the operation becomes faster. This should not be the case with constant time.
        assertTrue(leastSquaresSlope(times) > -0.01);

        // Test time complexity of enqueue front. This should be O(1) since we are simply inserting at index 0, which takes constant time.
        q = new EmptyList<>();
        times = new long[referenceArray.length / 100];
        for (int i = 0; i < referenceArray.length / 100; i++) {
            startTime = Instant.now();
            for (int j = 0 + (100 * i); j < 100 + (100 * i); j++) {
                q = q.enqueueFront(referenceArray[j]);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is no correlation between DeQueue size and time taken to enqueue front.
        assertTrue(leastSquaresSlope(times) < 0.01);

        // Test time complexity of dequeue back. This should be O(N) since we must traverse the whole linked list.
        times = new long[referenceArray.length / 100];
        for (int i = 0; i < referenceArray.length / 100; i++) {
            startTime = Instant.now();
            for (int j = 0; j < 100; j++) {
                q = q.dequeueBack();
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // This asserts a strong negative correlation, meaning that operation times decrease as the DeQueue size decreases
        assertTrue(leastSquaresSlope(times) < -0.8);
    }

    private static int[] getIntArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt();
        }
        return arr;
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