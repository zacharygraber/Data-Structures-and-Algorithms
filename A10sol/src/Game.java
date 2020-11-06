import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

// --------------------------------------------------------------------------

public abstract class Game {
    private int size;
    private final int delay;

    Game (int size, int delay) {
        this.size = size;
        this.delay = delay;
    }

    int getSize() {
        return size;
    }

    void setSize (int size) { this.size = size; }

    /**
     * Live cells with 2 or 3 live neighbors remain alive
     * Dead cells with 3 live neighbors become alive
     * Everybody else dies
     */
    abstract void step();
    abstract void draw (Graphics2D g2d, int dim);

    public void run () {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Game of Life");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new View(this, delay));

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

// --------------------------------------------------------------------------

class GameLiveCells extends Game {
    private final HashSet<Point> livePoints;

    GameLiveCells(int size, int delay, ArrayList<Point> points) {
        super(size, delay);
        this.livePoints = new HashSet<>();
        livePoints.addAll(points);
    }

    ArrayList<Point> neighbors (Point p) {
        ArrayList<Point> result = new ArrayList<>();
        if (p.x > 0 && p.y > 0)
            result.add(new Point(p.x - 1, p.y - 1));
        if (p.x > 0)
            result.add(new Point(p.x - 1, p.y));
        if (p.x > 0 && p.y < getSize()-1)
            result.add(new Point(p.x - 1, p.y + 1));
        if (p.y > 0)
            result.add(new Point(p.x, p.y - 1));
        if (p.y < getSize()-1)
            result.add(new Point(p.x, p.y + 1));
        if (p.x < getSize()-1 && p.y > 0)
            result.add(new Point(p.x + 1, p.y - 1));
        if (p.x < getSize()-1)
            result.add(new Point(p.x + 1, p.y));
        if (p.x < getSize()-1 && p.y < getSize()-1)
            result.add(new Point(p.x + 1, p.y + 1));
        return result;
    }

    void step () {
        Hashtable<Point, Integer> hash = new Hashtable<>();
        for (Point point : livePoints) {
            hash.putIfAbsent(point,0);
            for (Point neighbor : neighbors(point)) {
                hash.merge(neighbor, 1, Integer::sum);
            }
        }
        for (Point k : hash.keySet()) {
            if (hash.get(k) < 2 || hash.get(k) > 3) livePoints.remove(k);
            if (hash.get(k) == 3) livePoints.add(k);
        }
    }

    void draw (Graphics2D g2d, int dim) {
        int scale = dim / getSize();
        for (Point point : livePoints) {
            g2d.drawRect(point.x*scale, point.y*scale, scale, scale);
        }
    }
}

// --------------------------------------------------------------------------

class GameMatrix extends Game {
    private boolean[][] points;

    GameMatrix(int size, int delay, ArrayList<Point> livePoints) {
        super(size,delay);
        points = new boolean[size][size];
        for (Point p : livePoints) points[p.x][p.y] = true;
    }

    GameMatrix(int size, int delay, boolean[][] points) {
        super(size,delay);
        this.points = points;
    }

    boolean[][] getPoints() { return points; }

    int countLiveNeighbors (int x, int y) {
        int result = 0;
        if (x > 0 && y > 0 && points[x-1][y-1]) result++;
        if (x > 0 && points[x-1][y]) result++;
        if (x > 0 && y < getSize()-1 && points[x-1][y+1]) result++;
        if (y > 0 && points[x][y-1]) result++;
        if (y < getSize()-1 && points[x][y+1]) result++;
        if (x < getSize()-1 && y > 0 && points[x+1][y-1]) result++;
        if (x < getSize()-1 && points[x+1][y]) result++;
        if (x < getSize()-1 && y < getSize()-1 && points[x+1][y+1]) result++;
        return result;
    }

    void step() {
        boolean[][] newPoints = new boolean[getSize()][getSize()];
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                int liveNeighbors = countLiveNeighbors(x,y);
                if (points[x][y] && (liveNeighbors == 2 || liveNeighbors == 3))
                    newPoints[x][y] = true;
                else newPoints[x][y] = !points[x][y] && liveNeighbors == 3;
            }
        }
        points = newPoints;
    }

    void draw(Graphics2D g2d, int dim) {
        int scale = dim / getSize();
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                if (points[x][y]) {
                    g2d.drawRect(x * scale, y * scale, scale, scale);
                }
            }
        }
    }
}

// --------------------------------------------------------------------------

class GameQuadTree extends Game {
    private QuadTree region;

    GameQuadTree(int size, int delay, ArrayList<Point> livePoints) {
        super(size,delay);
        boolean[][] points = new boolean[size][size];
        for (Point p : livePoints) points[p.x][p.y] = true;
        region = QuadTree.fromArray(size,points);
    }

    QuadTree getRegion () { return region; }

    void step() {
        try {
            while (region.getLevel() < 3 ||
                    region.getNW().liveCells() != region.getNW().getSE().getSE().liveCells() ||
                    region.getNE().liveCells() != region.getNE().getSW().getSW().liveCells() ||
                    region.getSW().liveCells() != region.getSW().getNE().getNE().liveCells() ||
                    region.getSE().liveCells() != region.getSE().getNW().getNW().liveCells())
            {
                setSize(getSize()*2);
                QuadTree empty = QuadTree.emptyQuadTree(region.getLevel()-1);
                region = QuadTree.newRegion(
                        QuadTree.newRegion(empty,empty,empty,region.getNW()),
                        QuadTree.newRegion(empty,empty,region.getNE(),empty),
                        QuadTree.newRegion(empty,region.getSW(),empty,empty),
                        QuadTree.newRegion(region.getNE(),empty,empty,empty));
            }
            region = region.step();
        }
        catch (WrongRegionE e) {
            throw new Error("Bug: step");
        }
    }

    void draw(Graphics2D g2d, int dim) {
        boolean[][] points = QuadTree.toArray(region);
        int scale = dim / getSize();
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                if (points[x][y]) {
                    g2d.drawRect(x * scale, y * scale, scale, scale);
                }
            }
        }
    }
}