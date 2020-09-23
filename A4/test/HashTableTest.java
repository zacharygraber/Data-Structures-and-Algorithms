import org.junit.jupiter.api.Test;

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

}
