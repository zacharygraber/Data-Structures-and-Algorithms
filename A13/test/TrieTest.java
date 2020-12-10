import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
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

    @Test
    public void quickSanityCheck() {
        Trie trie = new Trie();
        trie.insert("test");
        System.out.println(trie.toString());
        trie.insert("tent");
        System.out.println(trie.toString());
    }

    @Test
    public void findAllWordsTest () throws IOException {
        List<String> words = Files.readAllLines(Paths.get("commonwords.txt"));
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }
        Tile a = new Tile('a', 0, 0);
        Tile e = new Tile('e', 0, 1);
        Tile c = new Tile('c', 1, 0);
        Tile d = new Tile('d', 1, 1);
        Tile[][] tiles = {{a,e},
                          {c,d}};
        Board board = new Board(tiles,trie);
        System.out.println(board.toString());
        assertTrue(trie.contains("ace"));
        assertTrue(trie.contains("aced"));
        assertTrue(trie.contains("dace"));
        assertTrue(trie.contains("cad"));
        assertTrue(trie.contains("cade"));

        HashSet<String> allWordsFound = board.findWords();
        assertTrue(allWordsFound.contains("ace"));
        assertTrue(allWordsFound.contains("aced"));
        assertTrue(allWordsFound.contains("dace"));
        assertTrue(allWordsFound.contains("cad"));
        assertTrue(allWordsFound.contains("cade"));
    }
}