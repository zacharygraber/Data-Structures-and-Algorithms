import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    public void trie () {
        Trie t = new Trie();
        t.insert("to");
        t.insert("tea");
        t.insert("ted");
        t.insert("ten");
        t.insert("in");
        t.insert("inn");
        t.insert("A");

        assertTrue(t.contains("ten"));
        assertTrue(t.contains("in"));
        assertTrue(t.contains("inn"));
        assertFalse(t.contains("tenn"));
        assertFalse(t.contains("te"));
    }

    @Test
    public void dict () throws IOException {
        Trie trie = new Trie();
        Scanner scanner = new Scanner(Paths.get("commonwords.txt"));
        while (scanner.hasNext()) {
            trie.insert(scanner.next());
        }

        assertTrue(trie.contains("abandon"));
        assertTrue(trie.contains("abandoned"));
        assertTrue(trie.contains("abandonment"));
        assertFalse(trie.contains("abandonmenth"));
        assertFalse(trie.contains("abando"));
        assertFalse(trie.contains("aband"));
        assertFalse(trie.contains("aban"));
        assertFalse(trie.contains("aba"));
        assertFalse(trie.contains("ab"));
        assertFalse(trie.contains("a"));
        assertTrue(trie.possiblePrefix("abando"));
        assertTrue(trie.possiblePrefix("aband"));
        assertTrue(trie.possiblePrefix("aban"));
        assertTrue(trie.possiblePrefix("aba"));
        assertTrue(trie.possiblePrefix("ab"));
        assertTrue(trie.possiblePrefix("a"));
    }
}