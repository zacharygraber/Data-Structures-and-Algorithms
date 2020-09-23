import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashFunctionTest {

    void assertEqualsL (long i, long j) {
        assertEquals(i,j);
    }

    @Test
    public void hashModInt () {
        HashFunction<Integer> hf = new HashMod<>(13);
        assertEqualsL(5, hf.apply(5));
        assertEqualsL(5, hf.apply(18));
        assertEqualsL(5, hf.apply(5+13*9));
        assertEqualsL(0, hf.apply(13));
        assertEqualsL(1, hf.apply(27));
    }

    @Test
    public void hashModString () {
        HashFunction<String> hf = new HashMod<>(13);
        assertEqualsL(4, hf.apply("aaa"));
        assertEqualsL(9, hf.apply("bbb"));
        assertEqualsL(1, hf.apply("ccc"));
        assertEqualsL(6, hf.apply("ddd"));
        assertEqualsL(11, hf.apply("eee"));
    }

    @Test
    public void hashUniversalInt () {
        Random r = new Random(1);
        HashFunction<Integer> hf = new HashUniversal<>(r, 31);
        assertEqualsL(13, hf.apply(0));
        assertEqualsL(10, hf.apply(1));
        assertEqualsL(7, hf.apply(2));
        assertEqualsL(4, hf.apply(3));
        assertEqualsL(1, hf.apply(4));
        assertEqualsL(4, hf.apply(5));
        assertEqualsL(1, hf.apply(6));
        assertEqualsL(29, hf.apply(7));
        assertEqualsL(26, hf.apply(8));
        assertEqualsL(23, hf.apply(9));
        assertEqualsL(20, hf.apply(10));
        assertEqualsL(17, hf.apply(11));
        assertEqualsL(14, hf.apply(12));
        assertEqualsL(11, hf.apply(13));
        assertEqualsL(8, hf.apply(14));
        assertEqualsL(5, hf.apply(15));
    }

    @Test
    public void hashFunctionIndexed () {
        HashFunction<String> bf = new HashMod<>(10);
        BiFunction<String,Integer,Integer> af = (k,i) -> i;
        HashFunctionIndexed<String> h = new HashFunctionIndexed<>(bf, af);
        assertEqualsL(9, h.apply("cat", 7));
        assertEqualsL(1, h.apply("dog", 7));
    }
}