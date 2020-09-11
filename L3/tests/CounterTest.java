import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CounterTest {

    @Test
    void init () {
        Counter c = new Counter(4);
        System.out.printf("array = %s%n", c);
        assertEquals(0,c.get(0));
        assertEquals(0,c.get(1));
        assertEquals(0,c.get(2));
        assertEquals(0,c.get(3));
        assertEquals(0, c.toDecimal());
    }

    @Test
    void count1 () {
        Counter c = new Counter(3);
        assertEquals(0, c.toDecimal());
        c.inc();
        assertEquals(1, c.toDecimal());
        c.inc();
        assertEquals(2, c.toDecimal());
        c.inc();
        assertEquals(3, c.toDecimal());
        c.inc();
        assertEquals(4, c.toDecimal());
        c.inc();
        assertEquals(5, c.toDecimal());
        c.inc();
        assertEquals(6, c.toDecimal());
        c.inc();
        assertEquals(7, c.toDecimal());
    }

    // create amortization tests here
    @Test
    void incrementTimeTest () {

        // This test will show that the time complexity of incrementing remains amortized constant as the Counter's decimal value increases.
        // This says that there is essentially no positive correlation between the size of the number and time taken to increment.
        assertTrue(leastSquaresSlope(timeCounterInIncrements(24, 1000)) < 0.001);
        assertTrue(leastSquaresSlope(timeCounterInIncrements(24, 100)) < 0.001);
        assertTrue(leastSquaresSlope(timeCounterInIncrements(24, 10000)) < 0.001);
    }

    private static long[] timeCounterInIncrements (int size, int dataPoints) {
        final long MAX_DEC_VAL = (long) Math.pow(2, size) - 1;

        Counter c = new Counter(size);
        long[] times = new long[dataPoints];
        Instant start, stop;

        for (int i = 0; i < dataPoints; i++) {
            start = Instant.now();
            for (int j = 0; j < MAX_DEC_VAL / dataPoints; j++) {
                c.inc();
            }
            stop = Instant.now();
            times[i] = Duration.between(start, stop).toMillis();
        }
        return times;
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