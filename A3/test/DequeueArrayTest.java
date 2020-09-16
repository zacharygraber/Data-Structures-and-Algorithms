import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void correctnessTestsWithStrings() throws NoSuchElementE {
        final int SIZE = 1000;
        DequeueArray<String> dqDouble = new DequeueArrayDouble<>(1); // Initializing with capacity 1 guarantees grow() calls
        DequeueArray<String> dq1AndAHalf = new DequeueArrayOneAndHalf<>(1);
        DequeueArray<String> dqAdd1 = new DequeueArrayPlusOne<>(1);

        // Tests consistency when adding to back and removing from front.
        String[] testStrings = randomStringsArray(SIZE);
        for (String s : testStrings) {
            dqDouble.addLast(s);
            dq1AndAHalf.addLast(s);
            dqAdd1.addLast(s);
        }
        assertEquals(testStrings.length, dqDouble.size());
        assertEquals(testStrings.length, dq1AndAHalf.size());
        assertEquals(testStrings.length, dqAdd1.size());
        for (String s : testStrings) {
            assertTrue(s.equals(dqDouble.removeFirst()));
            assertTrue(s.equals(dq1AndAHalf.removeFirst()));
            assertTrue(s.equals(dqAdd1.removeFirst()));
        }

        // Test consistency when adding to front and removing from front.
        testStrings = randomStringsArray(SIZE);
        for (String s : testStrings) {
            dqDouble.addFirst(s);
            dq1AndAHalf.addFirst(s);
            dqAdd1.addFirst(s);
        }
        assertEquals(testStrings.length, dqDouble.size());
        assertEquals(testStrings.length, dq1AndAHalf.size());
        assertEquals(testStrings.length, dqAdd1.size());
        for (int i = testStrings.length - 1; i >= 0; i--) {
            assertTrue(testStrings[i].equals(dqDouble.removeFirst()));
            assertTrue(testStrings[i].equals(dq1AndAHalf.removeFirst()));
            assertTrue(testStrings[i].equals(dqAdd1.removeFirst()));
        }

        // Test consistency when adding to back and removing from back.
        testStrings = randomStringsArray(SIZE);
        for (String s : testStrings) {
            dqDouble.addLast(s);
            dq1AndAHalf.addLast(s);
            dqAdd1.addLast(s);
        }
        assertEquals(testStrings.length, dqDouble.size());
        assertEquals(testStrings.length, dq1AndAHalf.size());
        assertEquals(testStrings.length, dqAdd1.size());
        for (int i = testStrings.length - 1; i >= 0; i--) {
            assertTrue(testStrings[i].equals(dqDouble.removeLast()));
            assertTrue(testStrings[i].equals(dq1AndAHalf.removeLast()));
            assertTrue(testStrings[i].equals(dqAdd1.removeLast()));
        }

        // Test consistency when adding to front and removing from back
        testStrings = randomStringsArray(SIZE);
        for (String s : testStrings) {
            dqDouble.addFirst(s);
            dq1AndAHalf.addFirst(s);
            dqAdd1.addFirst(s);
        }
        assertEquals(testStrings.length, dqDouble.size());
        assertEquals(testStrings.length, dq1AndAHalf.size());
        assertEquals(testStrings.length, dqAdd1.size());
        for (String s : testStrings) {
            assertTrue(s.equals(dqDouble.removeLast()));
            assertTrue(s.equals(dq1AndAHalf.removeLast()));
            assertTrue(s.equals(dqAdd1.removeLast()));
        }
    }

    @Test
    public void timeTests() throws NoSuchElementE {
        final int SIZE = 10000;
        String[] s = randomStringsArray(SIZE);
        Instant start;
        Instant stop;
        long doubleTime, oneAndAHalfTime, addOneTime;

        DequeueArray<String> dqDouble = new DequeueArrayDouble<>(1);
        start = Instant.now();
        for (String string : s) {
            dqDouble.addLast(string);
        }
        stop = Instant.now();
        doubleTime = Duration.between(start, stop).toMillis();
        System.out.println(doubleTime);

        DequeueArray<String> dqOneAndAHalf = new DequeueArrayOneAndHalf<>(1);
        start = Instant.now();
        for (String string : s) {
            dqOneAndAHalf.addLast(string);
        }
        stop = Instant.now();
        oneAndAHalfTime = Duration.between(start, stop).toMillis();
        System.out.println(oneAndAHalfTime);

        DequeueArray<String> dqAdd1 = new DequeueArrayPlusOne<>(1);
        start = Instant.now();
        for (String string : s) {
            dqAdd1.addLast(string);
        }
        stop = Instant.now();
        addOneTime = Duration.between(start, stop).toMillis();
        System.out.println(addOneTime);

        assertTrue(doubleTime < addOneTime);
        assertTrue(oneAndAHalfTime < addOneTime);
    }

    /**
     * This method should provide an 8-character String of random capital letters
     * @param size the number of Strings
     * @return a new String[] with "size" random Strings
     */
    private static String[] randomStringsArray(int size) {
        String[] s = new String[size];
        Random rand = new Random();
        String thisS = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 8; j++) {
                thisS += ((char) rand.nextInt(32) + 101);
            }
            s[i] = thisS;
        }
        return s;
    }
}
