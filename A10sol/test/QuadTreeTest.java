import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuadTreeTest {

    @Test
    void array() {
        boolean[][] points = new boolean[64][64];
        points[0][1] = true;
        points[3][7] = true;
        points[7][2] = true;
        points[6][4] = true;
        points[10][4] = true;
        points[11][4] = true;
        points[15][4] = true;
        points[46][48] = true;
        points[61][4] = true;
        points[10][34] = true;
        points[8][8] = true;
        points[54][15] = true;
        points[41][63] = true;

        QuadTree tree = QuadTree.fromArray(64, points);
        boolean[][] points2 = QuadTree.toArray(tree);

        assertArrayEquals(points,points2);
    }

    @Test
    void hash () {
        QuadTree.hash.clear();
        QuadTree on = QuadTree.newCell(true);
        QuadTree off = QuadTree.newCell(false);
        QuadTree t1 = QuadTree.newRegion(on,on,off,off);
        QuadTree t2 = QuadTree.newRegion(on,off,off,on);
        for (int i=0; i<100; i++) {
            QuadTree.newRegion(t1,t1,t2,t2);
        }
        assertEquals(5, QuadTree.hash.size());
    }

    @Test
    void blinker () {
        QuadTree.hash.clear();
        GameQuadTree blinker = new GameQuadTree(4, 500, Games.blinker);
        blinker.step();
    }

    @Test
    void toad () {
        QuadTree.hash.clear();
        GameQuadTree toad = new GameQuadTree(8, 500, Games.toad);
        toad.step();
    }
}
