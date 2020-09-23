import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;

// -------------------------------------------------------

/**
 *
 * Before implementing the hash tables proper, we will
 * implement a small class hierarchy of hash tables entries. There are
 * three kinds of entries: an entry that contains an key-value pair, an
 * entry that is empty and hence available, and an entry that is
 * marked as deleted. A deleted entry is available for insertions but
 * cannot be marked as empty. See the book for details.
 *
 */
abstract class Entry<K,V> {
    abstract boolean available ();
}

class Empty<K,V> extends Entry<K,V> {
    boolean available () { return true; }
    public String toString () { return ""; }
}

class Deleted<K,V> extends Entry<K,V> {
    boolean available () { return true; }
    public String toString () { return "X"; }
}

class KeyValue<K,V> extends Entry<K,V> {
    private K key;
    private V value;
    KeyValue (K key, V value) {
        this.key = key;
        this.value = value;
    }
    K getKey () { return this.key; }
    V getValue () { return this.value; }
    boolean available () { return false; }
    public String toString () {
        return String.valueOf(this.key) + "," + String.valueOf(this.value);
    }
}

// -------------------------------------------------------

/**
 *
 * This class, although abstract, will have a constructor and an
 * implementation of each of the three methods: insert, delete, and
 * search. It will also have an implementation of rehash().
 *
 * The constructor should take two arguments: an
 * argument 'basicF' of type HashFunction, and an argument 'adjustF'
 * of type BiFunction<K,Integer,Integer>.  The subclasses for
 * linear probing, quadratic probing, and double hashing, will each
 * pass an appropriate function 'adjustF' to control the
 * auxiliary function.
 *
 * The constructor should make an indexed hash functions from these
 * two arguments and store it as an instance variable 'f'. The constructor
 * should also create an ArrayList of the same size as the bound
 * for 'basicF' and initialize each slot in that array with an Empty
 * entry.
 *
 * Each of the methods insert, delete, and search takes a 'key'
 * to process. Each of the methods will involve a loop that iterates
 * over all the positions in the ArrayList. At iteration 'i', an
 * integer position is calculated using:
 *
 *   int h = f.apply(key,i)
 *
 * For insert: if the position 'h' is available then the key-value pair
 * is stored at that position. It is possible for the array to be full
 * and in that case, we rehash and try to insert again.
 *
 * For delete: if position 'h' has an entry of kind 'KeyValue' and if the
 * stored key is identical to the parameter 'key' then the entry is marked
 * as deleted.
 *
 * For search: if position 'h' is Empty then the item is not found. If
 * position 'h' has an entry of kind 'KeyValue' and if the stored key
 * is identical to the parameter 'key' then the item is found.
 *
 */
abstract class HashTable<K,V> {
    private int capacity;
    private HashFunctionIndexed<K> f;
    private ArrayList<Entry<K,V>> slots;

    HashTable (HashFunction<K> basicF, BiFunction<K,Integer,Integer> adjustF) {
        this.f = new HashFunctionIndexed<>(basicF, adjustF);

        this.capacity = basicF.getBound();

        // Create and initialize the ArrayList of slots
        this.slots = new ArrayList<>(this.capacity);
        for (int i = 0; i < this.capacity; i++) {
            this.slots.add(new Empty<>());
        }
    }

    int getCapacity () { return capacity; }

    void insert (K key, V value) {
        int startingHash = f.apply(key, 0);
        int thisHash;
        int i = 0;
        while (true) {
            thisHash = f.apply(key, i);
            if (this.slots.get(thisHash).available()) {
                this.slots.set(thisHash, new KeyValue<>(key, value));
                return;
            }
            // If we have completed an entire cycle around the ArrayList to no avail, then we consider the HashTable to be full.
            else if (i != 0 && thisHash == startingHash) {
                this.rehash();
                this.insert(key, value);
                return;
            }
            i++;
        }
    }

    void delete (K key) {
        /* There are 5 cases here each loop:
         * Case 1: h is a KeyValue and matches the key
         * Case 2: h is a KeyValue, but not the key
         * Case 3: h is an Empty
         * Case 4: h is a Deleted
         * Case 5: we've completed an entire loop around the ArrayList without finding a KeyValue matching the key
         *
         * If we reach Case 1, we have found the key, and mark it as deleted.
         * If we reach Case 3 or Case 5, we know that the key does not exist in the table, so we return.
         * If we reach Case 4 or Case 2, we proceed to the next value of h                                          */

        int startingH = f.apply(key, 0);
        int h;
        int i = 0;
        while (true) {
            h = f.apply(key, i);
            // Case 1
            if (this.slots.get(h) instanceof KeyValue && ((KeyValue<K, V>) this.slots.get(h)).getKey() == key) {
                this.slots.set(h, new Deleted<>());
                return;
            }

            // Case 3
            else if (this.slots.get(h) instanceof Empty) {
                return;
            }

            // Case 5
            else if (i != 0 && h == startingH) {
                return;
            }

            // Cases 2 and 4 do nothing, just loop.
            i++;
        }
    }

    Optional<V> search (K key) {
        /* There are 5 cases here each loop:
         * Case 1: h is a KeyValue and matches the key
         * Case 2: h is a KeyValue, but not the key
         * Case 3: h is an Empty
         * Case 4: h is a Deleted
         * Case 5: we've completed an entire loop around the ArrayList without finding a KeyValue matching the key
         *
         * If we reach Case 1, we have found the key, and return a new Optional containing the value there.
         * If we reach Case 3 or Case 5, we know that the key does not exist in the table, so we return an empty Optional.
         * If we reach Case 4 or Case 2, we proceed to the next value of h                                                     */

        int startingH = f.apply(key, 0);
        int h;
        int i = 0;
        while (true) {
            h = f.apply(key, i);
            // Case 1
            if (this.slots.get(h) instanceof KeyValue && ((KeyValue<K, V>) this.slots.get(h)).getKey() == key) {
                return Optional.of(((KeyValue<K, V>) this.slots.get(h)).getValue());
            }

            // Case 3
            else if (this.slots.get(h) instanceof Empty) {
                return Optional.empty();
            }

            // Case 5
            else if (i != 0 && h == startingH) {
                return Optional.empty();
            }

            // Cases 2 and 4 do nothing, just loop.
            i++;
        }
    }

    /**
     * To rehash, we double the capacity of the array, adjust the hash
     * function with the new bound, and rehash all the values in the old
     * array
     */
    void rehash () {
        ArrayList<Entry<K, V>> oldSlots = (ArrayList<Entry<K,V>>) this.slots.clone();
        this.capacity *= 2;
        this.f.setBound(this.capacity);
        this.slots = new ArrayList<>(this.capacity);
        for (int i = 0; i < this.capacity; i++) {
            this.slots.add(new Empty<>());
        }
        Entry<K, V> thisEntry;
        for (int i = 0; i < oldSlots.size(); i++) {
            thisEntry = oldSlots.get(i);
            if (thisEntry instanceof KeyValue) {
                this.insert(((KeyValue<K, V>) thisEntry).getKey(), ((KeyValue<K, V>) thisEntry).getValue());
            }
        }
    }

    public String toString () {
        String result = "";
        for (int i = 0; i< capacity; i++) {
            result += "entry[" + i + "] = ";
            result += slots.get(i).toString();
            result += "\n";
        }
        return result;
    }
}

// -------------------------------------------------------


class HashLinearProbing<K,V> extends HashTable<K,V> {
    HashLinearProbing(HashFunction<K> hf) {
        super(hf, (key,index) -> index);
    }
}

class HashQuadProbing<K,V> extends HashTable<K,V> {
    HashQuadProbing (HashFunction<K> hf) {
        super(hf, (key,index) -> index*index);
    }
}

class HashDouble<K,V> extends HashTable<K,V> {
    HashDouble (HashFunction<K> hf, HashFunction<K> hf2) {
        super(hf, (key,index) -> index * hf2.apply(key));
    }
}

// -------------------------------------------------------
