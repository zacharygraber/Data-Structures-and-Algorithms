import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

class Games {
    static final ArrayList<Point> blinker = new ArrayList<>(Arrays.asList(
            new Point(0, 1),
            new Point(1, 1),
            new Point(2, 1)
    ));

    static final ArrayList<Point> toad = new ArrayList<>(Arrays.asList(
            new Point(4,4),
            new Point(4,5),
            new Point(4,6),
            new Point(5,3),
            new Point(5,4),
            new Point(5,5)
    ));

    static final ArrayList<Point> beacon = new ArrayList<>(Arrays.asList(
            new Point(3,3),
            new Point(3,4),
            new Point(4,3),
            new Point(4,4),
            new Point(5,5),
            new Point(5,6),
            new Point(6,5),
            new Point(6,6)
    ));

    static final ArrayList<Point> busy = new ArrayList<>(Arrays.asList(
            new Point(39,40),
            new Point(39,41),
            new Point(40,39),
            new Point(40,40),
            new Point(41,40)
    ));

    static final ArrayList<Point> glider = new ArrayList<>(Arrays.asList(
            new Point(50,180),
            new Point(51,180),
            new Point(50,181),
            new Point(51,181),
            new Point(60,180),
            new Point(60,179),
            new Point(60,181),
            new Point(61,178),
            new Point(62,177),
            new Point(63,177),
            new Point(61,182),
            new Point(62,183),
            new Point(63,183),
            new Point(65,182),
            new Point(66,181),
            new Point(66,180),
            new Point(66,179),
            new Point(65,178),
            new Point(64,180),
            new Point(67,180),
            new Point(70,181),
            new Point(70,182),
            new Point(70,183),
            new Point(71,181),
            new Point(71,182),
            new Point(71,183),
            new Point(72,180),
            new Point(72,184),
            new Point(74,180),
            new Point(74,179),
            new Point(74,184),
            new Point(74,185),
            new Point(84,182),
            new Point(84,183),
            new Point(85,182),
            new Point(85,183)
    ));

}

class BlinkerLiveCells {
    public static void main (String[] args) {
        Game blinker = new GameLiveCells(16, 500, Games.blinker);
        blinker.run();
    }
}

class BlinkerMatrix {
    public static void main (String[] args) {
        Game blinker = new GameMatrix(16, 500, Games.blinker);
        blinker.run();
    }
}

class ToadLiveCells {
    public static void main (String[] args) {
        Game toad = new GameLiveCells(10, 500, Games.toad);
        toad.run();
    }
}

class ToadMatrix {
    public static void main (String[] args) {
        Game toad = new GameMatrix(10, 500, Games.toad);
        toad.run();
    }
}

class BeaconLiveCells {
    public static void main (String[] args) {
        Game beacon = new GameLiveCells(10, 500, Games.beacon);
        beacon.run();
    }
}

class BeaconMatrix {
    public static void main (String[] args) {
        Game beacon = new GameMatrix(10, 500, Games.beacon);
        beacon.run();
    }
}

class BusyLiveCells {
    public static void main (String[] args) {
        Game busy = new GameLiveCells(80, 50, Games.busy);
        busy.run();
    }
}

class BusyMatrix {
    public static void main (String[] args) {
        Game busy = new GameMatrix(80, 50, Games.busy);
        busy.run();
    }
}

class GliderLiveCells {
    public static void main (String[] args) {
        Game glider = new GameLiveCells(200, 5, Games.glider);
        glider.run();
    }
}

class GliderMatrix {
    public static void main (String[] args) {
        Game glider = new GameMatrix(200, 5, Games.glider);
        glider.run();
    }
}

// This doesn't work!
//class GliderQuadTree {
//    public static void main(String[] args) {
//        QuadTree.hash.clear();
//        Game glider = new GameQuadTree(256, 500, Games.glider);
//        glider.run();
//    }
//}
