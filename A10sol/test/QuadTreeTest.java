import org.junit.jupiter.api.Test;

import java.util.Arrays;

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

    @Test
    void fromArrayBuildingRegionsCorrectly() throws WrongRegionE
    {
        boolean[][] expected = {{false,false,false,false},
                                {true, true, true, false},
                                {false,false,false,false},
                                {false,false,false,false}};

        boolean[][] expectedNW = {{false, false},
                                  {true,  true}};

        boolean[][] expectedNE = {{false, false},
                                  {true,  false}};

        boolean[][] expectedSW = {{false, false},
                                  {false, false}};
        boolean[][] expectedSE = {{false, false},
                                  {false, false}};
        QuadTree t = QuadTree.fromArray(4, expected);
        assertArrayEquals(expected, QuadTree.toArray(t));
        assertArrayEquals(expectedNW, QuadTree.toArray(t.getNW()));
        assertArrayEquals(expectedNE, QuadTree.toArray(t.getNE()));
        assertArrayEquals(expectedSW, QuadTree.toArray(t.getSW()));
        assertArrayEquals(expectedSE, QuadTree.toArray(t.getSE()));

        assertEquals(2, t.getLevel());
        assertEquals(1, t.getNE().getLevel());
        assertEquals(1, t.getNW().getLevel());
        assertEquals(1, t.getSW().getLevel());
        assertEquals(1, t.getSE().getLevel());
    }
}
