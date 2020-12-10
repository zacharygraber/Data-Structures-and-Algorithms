import java.util.Hashtable;

public class Trie implements Words {
    private boolean endsHere;
    private final Hashtable<Character,Trie> children;

    Trie () {
        this.endsHere = false;
        this.children = new Hashtable<>();
    }

    /**
     * Inserts the string s in the current trie. Ensures that words are case-insensitive.
     */
    void insert(String s) {
        String sLower = s.toLowerCase();
        insertHelper(sLower);
    }
    private void insertHelper(String s) {
        if (s.length() == 0) {
            this.endsHere = true;
            return;
        }

        // If there isn't already a node with the first letter, create one.
        if (!children.containsKey(s.charAt(0)))
            children.put(s.charAt(0), new Trie());

        // Recursively call insert with the rest of the string (indices 1-end)
        children.get(s.charAt(0)).insertHelper(s.length() == 1 ? "" : s.substring(1)); // Can't use .substring() for length 1
    }

    /**
     * Checks whether the string s is a full word
     * stored in the current trie.
     */
    public boolean contains(String s) {
        String sLower = s.toLowerCase();
        return containsHelper(sLower);
    }
    private boolean containsHelper(String s) {
        // Base case
        if (s.length() == 0)
            return this.endsHere;
        // Return false if there is no child with the letter
        if (!children.containsKey(s.charAt(0)))
            return false;

        // The ternary operator protects against when then length is 1, since we can't use substring for that.
        return children.get(s.charAt(0)).containsHelper(s.length() == 1 ? "" : s.substring(1));
    }

    /**
     * Checks whether the string s is a prefix
     * of at least one word in the current trie.
     */
    public boolean possiblePrefix (String s) {
        String sLower = s.toLowerCase();
        return possiblePrefixHelper(sLower);
    }
    private boolean possiblePrefixHelper (String s) {
        // Base case where s is empty
        if (s.length() == 0)
            // If there are children, an empty string is a prefix of every word. return true.
            // If there are no children here, then it is *only* a full word, and not a prefix. return false.
            return !children.isEmpty();

        // If there is no child matching the first character, it's not a valid prefix
        if (!children.containsKey(s.charAt(0)))
            return false;

        // Otherwise, recursively call on the rest of the string
        return children.get(s.charAt(0)).possiblePrefixHelper(s.length() == 1 ? "" : s.substring(1));
    }

    public String toString () {
        return children.toString();
    }
}
