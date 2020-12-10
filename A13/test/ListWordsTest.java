import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListWordsTest {

    @Test
    public void trie () {
        ListWords t = new ListWords(Arrays.asList(
                "to", "tea", "ted", "ten", "in", "inn", "A"));

        assertTrue(t.contains("ten"));
        assertTrue(t.contains("in"));
        assertTrue(t.contains("inn"));
        assertFalse(t.contains("tenn"));
        assertFalse(t.contains("te"));
    }

    @Test
    public void dict () throws IOException {
        List<String> list = Files.readAllLines(Paths.get("commonwords.txt"));
        ListWords words = new ListWords(list);

        assertTrue(words.contains("abandon"));
        assertTrue(words.contains("abandoned"));
        assertTrue(words.contains("abandonment"));
        assertFalse(words.contains("abandonmenth"));
        assertFalse(words.contains("abando"));
        assertFalse(words.contains("aband"));
        assertFalse(words.contains("aban"));
        assertFalse(words.contains("aba"));
        assertFalse(words.contains("ab"));
        assertFalse(words.contains("a"));
        assertTrue(words.possiblePrefix("abando"));
        assertTrue(words.possiblePrefix("aband"));
        assertTrue(words.possiblePrefix("aban"));
        assertTrue(words.possiblePrefix("aba"));
        assertTrue(words.possiblePrefix("ab"));
        assertTrue(words.possiblePrefix("a"));
    }

    @Test
    public void getFreshNeighbors () throws IOException {
        List<String> words = Files.readAllLines(Paths.get("commonwords.txt"));

        Tile a = new Tile('a', 0, 0);
        Tile b = new Tile('b', 0, 1);
        Tile c = new Tile('c', 0, 2);
        Tile d = new Tile('d', 1, 0);
        Tile e = new Tile('e', 1, 1);
        Tile f = new Tile('f', 1, 2);
        Tile g = new Tile('g', 2, 0);
        Tile h = new Tile('h', 2, 1);
        Tile i = new Tile('i', 2, 2);
        Tile[][] tiles = {{a, b, c},
                          {d, e, f},
                          {g, h, i}};

        Board board = new Board(tiles,new ListWords(words));
        List<Tile> aNeighbors = board.getFreshNeighbors(a.getRow(),a.getCol());
        assertEquals(3, aNeighbors.size());
        assertTrue(aNeighbors.contains(b));
        assertTrue(aNeighbors.contains(d));
        assertTrue(aNeighbors.contains(e));

        List<Tile> iNeighbors = board.getFreshNeighbors(i.getRow(), i.getCol());
        assertEquals(3, iNeighbors.size());
        assertTrue(iNeighbors.contains(e));
        assertTrue(iNeighbors.contains(f));
        assertTrue(iNeighbors.contains(h));

        List<Tile> eNeighbors = board.getFreshNeighbors(e.getRow(), e.getCol());
        assertEquals(8, eNeighbors.size());
        ArrayList<Tile> eNeighborsExpected = new ArrayList<>(Arrays.asList(a,b,c,d,f,g,h,i));
        assertTrue(eNeighbors.containsAll(eNeighborsExpected));

        b.setVisited();
        d.setVisited();
        f.setVisited();
        eNeighbors = board.getFreshNeighbors(e.getRow(), e.getCol());
        eNeighborsExpected = new ArrayList<>(Arrays.asList(a,c,g,h,i));
        assertTrue(eNeighbors.containsAll(eNeighborsExpected));
        assertFalse(eNeighbors.contains(b));
        assertFalse(eNeighbors.contains(d));
        assertFalse(eNeighbors.contains(f));
    }

    @Test
    public void findAllWordsTest () throws IOException {
        List<String> words = Files.readAllLines(Paths.get("commonwords.txt"));
        ListWords dict = new ListWords(words);
        Tile a = new Tile('a', 0, 0);
        Tile e = new Tile('e', 0, 1);
        Tile c = new Tile('c', 1, 0);
        Tile d = new Tile('d', 1, 1);
        Tile[][] tiles = {{a,e},
                          {c,d}};
        Board board = new Board(tiles,dict);
        System.out.println(board.toString());
        assertTrue(dict.contains("ace"));
        assertTrue(dict.contains("aced"));
        assertTrue(dict.contains("dace"));
        assertTrue(dict.contains("cad"));
        assertTrue(dict.contains("cade"));

        HashSet<String> allWordsFound = board.findWords();
        assertTrue(allWordsFound.contains("ace"));
        assertTrue(allWordsFound.contains("aced"));
        assertTrue(allWordsFound.contains("dace"));
        assertTrue(allWordsFound.contains("cad"));
        assertTrue(allWordsFound.contains("cade"));
    }
}