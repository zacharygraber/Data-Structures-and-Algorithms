import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Board {
    private final Tile[][] tiles;
    private final int boardSize;
    private final Words dict;

    public Board(Tile[][] tiles, Words dict) {
        this.tiles = tiles;
        this.boardSize = tiles.length;
        this.dict = dict;
    }

    public int getBoardSize () { return boardSize; }

    public Tile get(int row, int col) {
        return tiles[row][col];
    }

    /**
     * The method returns a list of neighboring cells
     * that have not been visited.
     */
    public List<Tile> getFreshNeighbors (int r, int c) {
        return null; // TODOList<Tile> result = new ArrayList<>();
    }

    /**
     * Starting from the given position (r,c) and the string s
     * found so far, the method performs the following actions:
     *   - marks the tile (r,c) as visited
     *   - extends s with the character at (r,c); if that
     *     new string is a word, add it to the result to be
     *     returned
     *   - if the extended string is not a valid prefix of
     *     any word, mark the tile (r,c) is unvisited
     *     and return
     *   - otherwise visit all fresh neighbors recursively
     */
    public HashSet<String> findWordsFromPos(int r, int c, String s) {
        return null; // TODO
    }

    public HashSet<String> findWords() {
        HashSet<String> result = new HashSet<>();
        for (int r=0; r<boardSize; r++) {
            for (int c=0; c<boardSize; c++) {
                result.addAll(findWordsFromPos(r,c,""));
            }
        }
        return result;
    }

    public void paint(Graphics2D g2, int offset, Dimension dim) {
        Rectangle2D.Double tileBox;
        int tileSize = dim.width / boardSize;

        FontMetrics fm = g2.getFontMetrics();
        int scaleFactor = dim.width / (boardSize * 10 * fm.stringWidth("J"));

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                tileBox = new Rectangle2D.Double(
                        offset + c * tileSize,
                        offset + r * tileSize,
                        tileSize,
                        tileSize);
                tiles[r][c].paint(g2, tileBox, scaleFactor);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                sb.append(get(r,c));
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

