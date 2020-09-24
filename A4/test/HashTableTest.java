import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HashTableTest {

    @Test
    public void fig511 () {
        HashFunction<Integer> hf = new HashMod<>(10);
        HashTable<Integer,String> ht = new HashLinearProbing<>(hf);
        ht.insert(89,"cat");
        ht.insert(18,"dog");
        ht.insert(49,"horse");
        ht.insert(58,"cow");
        ht.insert(69,"chicken");
        System.out.println("Fig. 5.11");
        System.out.println(ht);
    }

    @Test
    public void fig513 () {
        HashFunction<Integer> hf = new HashMod<>(10);
        HashTable<Integer,String> ht = new HashQuadProbing<>(hf);
        ht.insert(89,"cat");
        ht.insert(18,"dog");
        ht.insert(49,"horse");
        ht.insert(58,"cow");
        ht.insert(69,"chicken");
        System.out.println("Fig. 5.13");
        System.out.println(ht);
        ht.insert(26,"lion");
        ht.insert(72,"tiger");
        ht.insert(95,"cheetah");
        System.out.println("Before rehash");
        System.out.println(ht);
        ht.rehash();
        System.out.println("After rehash");
        System.out.println(ht);
    }

    @Test
    public void correctnessTests() {
        String[] testStrings = randomStringsArray(50);

        HashFunction<Integer> hf = new HashMod<>(10);
        HashTable<Integer, String> htLin = new HashLinearProbing<>(hf);
        HashTable<Integer, String> htQuad = new HashQuadProbing<>(hf);
        HashTable<Integer, String> htDouble = new HashDouble<>(hf, new HashUniversal<>(new Random(), 50));

        // No remapping up to this point
        for (int i = 0; i < 10; i++) {
            htLin.insert(i, testStrings[i]);
            htQuad.insert(i, testStrings[i]);
            htDouble.insert(i, testStrings[i]);
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(testStrings[i].equals(htLin.search(i).get()));
            assertTrue(testStrings[i].equals(htQuad.search(i).get()));
            assertTrue(testStrings[i].equals(htDouble.search(i).get()));
        }

        // Insert the rest, guaranteeing at least one remap
        for (int i = 10; i < 50; i++) {
            htLin.insert(i, testStrings[i]);
            htQuad.insert(i, testStrings[i]);
            htDouble.insert(i, testStrings[i]);
        }
        for (int i = 0; i < 50; i++) {
            assertTrue(testStrings[i].equals(htLin.search(i).get()));
            assertTrue(testStrings[i].equals(htQuad.search(i).get()));
            assertTrue(testStrings[i].equals(htDouble.search(i).get()));
        }

        // Tests going over a deleted entry to lookup existing one.
        // Also guarantees a collision.
        htLin = new HashLinearProbing<>(hf);
        htLin.insert(1, "foo");
        htLin.insert(1, "bar");
        assertTrue(("foo").equals(htLin.search(1).get()));
        htLin.delete(1);
        assertTrue(("bar").equals(htLin.search(1).get()));
    }

    @Test
    public void timeTestsLinearProbing() {
        String[] testStrings = randomStringsArray(10000);
        HashFunction<Integer> hf = new HashMod<>(10);
        HashTable<Integer, String> htLin = new HashLinearProbing<>(hf);

        // Test time complexity of inserting as the amount of things stored grows.
        Instant startTime;
        Instant endTime;
        long[] times = new long[testStrings.length / 100];
        for (int i = 0; i < testStrings.length / 100; i++) {
            startTime = Instant.now();
            for (int j = (100 * i); j < 100 + (100 * i); j++) {
                htLin.insert(j, testStrings[j]);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is no correlation between size and time taken to insert.
        assertTrue(leastSquaresSlope(times) < 0.01);

        // Test time complexity of deleting as amount of things decreases
        times = new long[testStrings.length / 100];
        for (int i = 0; i < testStrings.length / 100; i++) {
            startTime = Instant.now();
            for (int j = (100 * i); j < 100 + (100 * i); j++) {
                htLin.delete(j);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is little correlation between size and time taken to delete.
        assertTrue(leastSquaresSlope(times) > -1);

        // Test time complexity of search as amount of things stored increases
        htLin = new HashLinearProbing<>(hf);
        times = new long[testStrings.length / 100];
        for (int i = 0; i < testStrings.length / 100; i++) {
            for (int j = (100 * i); j < 100 + (100 * i); j++) {
                htLin.insert(j, testStrings[j]);
            }
            startTime = Instant.now();
            htLin.search((new Random()).nextInt((i + 1) * 100));
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is no correlation between size and time taken to insert.
        assertTrue(leastSquaresSlope(times) < 0.03);
    }

    @Test
    public void timeTestsQuadraticProbing() {
        String[] testStrings = randomStringsArray(10000);
        HashFunction<Integer> hf = new HashMod<>(10);
        HashTable<Integer, String> htQuad = new HashQuadProbing<>(hf);

        // Test time complexity of inserting as the amount of things stored grows.
        Instant startTime;
        Instant endTime;
        long[] times = new long[testStrings.length / 100];
        for (int i = 0; i < testStrings.length / 100; i++) {
            startTime = Instant.now();
            for (int j = (100 * i); j < 100 + (100 * i); j++) {
                htQuad.insert(j, testStrings[j]);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is no correlation between size and time taken to insert.
        assertTrue(leastSquaresSlope(times) < 0.01);

        // Test time complexity of deleting as amount of things decreases
        times = new long[testStrings.length / 100];
        for (int i = 0; i < testStrings.length / 100; i++) {
            startTime = Instant.now();
            for (int j = (100 * i); j < 100 + (100 * i); j++) {
                htQuad.delete(j);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is little correlation between size and time taken to delete.
        assertTrue(leastSquaresSlope(times) > -1);

        // Test time complexity of search as amount of things stored increases
        htQuad = new HashQuadProbing<>(hf);
        times = new long[testStrings.length / 100];
        for (int i = 0; i < testStrings.length / 100; i++) {
            for (int j = (100 * i); j < 100 + (100 * i); j++) {
                htQuad.insert(j, testStrings[j]);
            }
            startTime = Instant.now();
            htQuad.search((new Random()).nextInt((i + 1) * 100));
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is no correlation between size and time taken to insert.
        assertTrue(leastSquaresSlope(times) < 0.01);
    }

    @Test
    public void timeTestsDouble() {
        String[] testStrings = randomStringsArray(10000);
        HashFunction<Integer> hf = new HashMod<>(10);
        HashTable<Integer, String> htDouble = new HashDouble<>(hf, new HashUniversal<>(new Random(), 10000));
        // Test time complexity of inserting as the amount of things stored grows.
        Instant startTime;
        Instant endTime;
        long[] times = new long[testStrings.length / 100];
        for (int i = 0; i < testStrings.length / 100; i++) {
            startTime = Instant.now();
            for (int j = (100 * i); j < 100 + (100 * i); j++) {
                htDouble.insert(j, testStrings[j]);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is no correlation between size and time taken to insert.
        assertTrue(leastSquaresSlope(times) < 0.01);

        // Test time complexity of deleting as amount of things decreases
        times = new long[testStrings.length / 100];
        for (int i = 0; i < testStrings.length / 100; i++) {
            startTime = Instant.now();
            for (int j = (100 * i); j < 100 + (100 * i); j++) {
                htDouble.delete(j);
            }
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is little correlation between size and time taken to delete.
        assertTrue(leastSquaresSlope(times) > -1);

        // Test time complexity of search as amount of things stored increases
        htDouble = new HashDouble<>(hf, new HashUniversal<>(new Random(), 10000));
        times = new long[testStrings.length / 100];
        for (int i = 0; i < testStrings.length / 100; i++) {
            for (int j = (100 * i); j < 100 + (100 * i); j++) {
                htDouble.insert(j, testStrings[j]);
            }
            startTime = Instant.now();
            htDouble.search((new Random()).nextInt((i + 1) * 100));
            endTime = Instant.now();
            times[i] = Duration.between(startTime, endTime).toNanos();
        }
        // Assert that there is no correlation between size and time taken to insert.
        assertTrue(leastSquaresSlope(times) < 0.01);
    }

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
