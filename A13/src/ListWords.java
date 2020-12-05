import java.util.List;

public class ListWords implements Words {
    private final List<String> words;

    ListWords (List<String> words) {
        this.words = words;
    }

    public boolean contains(String w) {
        return words.contains(w);
    }

    /**
     * Checks if the string w is a prefix of at least one
     * words in the current list.
     */
    public boolean possiblePrefix(String w) {
        return false; // TODO
    }
}
