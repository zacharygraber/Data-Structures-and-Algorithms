import org.junit.jupiter.api.Test;

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
}