import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListWords implements Words {
    private final List<String> words;

    ListWords (List<String> words) {
        this.words = words.stream().map(String::toLowerCase).collect(Collectors.toList()); // Makes sure all words are lowercase
    }

    public boolean contains(String w) {
        return words.contains(w);
    }

    /**
     * Checks if the string w is a prefix of at least one
     * words in the current list.
     */
    public boolean possiblePrefix(String w) {
        for (String word : words) {
            if (word.length() > w.length()) { // We can rule out anything that is shorter or the same length
                if (word.startsWith(w)) {
                    return true;
                }
            }
        }
        return false;
    }
}
