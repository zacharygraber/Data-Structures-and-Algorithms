import java.util.Hashtable;

public class Trie implements Words {
    private boolean endsHere;
    private final Hashtable<Character,Trie> children;

    Trie () {
        this.endsHere = false;
        this.children = new Hashtable<>();
    }

    /**
     * Inserts the string s in the current trie.
     */
    void insert(String s) {
        // TODO
    }

    /**
     * Checks whether the string s is a full word
     * stored in the current trie.
     */
    public boolean contains(String s) {
        return false; // TODO
    }

    /**
     * Checks whether the string s is a prefix
     * of at least one word in the current trie.
     */
    public boolean possiblePrefix (String s) {
        return false; // TODO
    }

    public String toString () {
        return children.toString();
    }
}
