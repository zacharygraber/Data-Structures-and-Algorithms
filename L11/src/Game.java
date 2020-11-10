import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

// --------------------------------------------------------------------------

public abstract class Game {
    private int maxSize;
    private int delay;

    Game (int maxSize, int delay) {
        this.maxSize = maxSize;
        this.delay = delay;
    }

    int getMaxSize () {
        return maxSize;
    }

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
    private HashSet<Point> livePoints;
    private Hashtable<Point,Integer> hash;

    GameLiveCells(int maxSize, int delay, ArrayList<Point> points) {
        super(maxSize,delay);
        //TODO
    }

    ArrayList<Point> neighbors (Point p) {
        //TODO
        return null;
    }

    void step () {
        //TODO
    }

    void draw (Graphics2D g2d, int dim) {
        //TODO
    }
}

// --------------------------------------------------------------------------

class GameMatrix extends Game {
    private boolean[][] points;

    GameMatrix(int maxSize, int delay, ArrayList<Point> livePoints) {
        super(maxSize,delay);
        points = new boolean[maxSize][maxSize];
        for (Point p : livePoints) {
            points[p.x][p.y] = true;
        }
    }

    int countLiveNeighbors (int x, int y) {
        int result = 0;
        if (x > 0 && y > 0 && points[x-1][y-1]) result++;
        if (x > 0 && points[x-1][y]) result++;
        if (x > 0 && y < getMaxSize()-1 && points[x-1][y+1]) result++;
        if (y > 0 && points[x][y-1]) result++;
        if (y < getMaxSize()-1 && points[x][y+1]) result++;
        if (x < getMaxSize()-1 && y > 0 && points[x+1][y-1]) result++;
        if (x < getMaxSize()-1 && points[x+1][y]) result++;
        if (x < getMaxSize()-1 && y < getMaxSize()-1 && points[x+1][y+1]) result++;
        return result;
    }

    void step() {
        boolean[][] newPoints = new boolean[getMaxSize()][getMaxSize()];
        for (int x=0; x < getMaxSize(); x++) {
            for (int y=0; y < getMaxSize(); y++) {
                int liveNeighbors = countLiveNeighbors(x,y);
                if (points[x][y] && (liveNeighbors == 2 || liveNeighbors == 3))
                    newPoints[x][y] = true;
                else if (! points[x][y] && liveNeighbors == 3)
                    newPoints[x][y] = true;
                else newPoints[x][y] = false;
            }
        }
        points = newPoints;
    }

    void draw(Graphics2D g2d, int dim) {
        int scale = dim / getMaxSize();
        for (int x = 0; x < getMaxSize(); x++) {
            for (int y=0; y < getMaxSize(); y++) {
                if (points[x][y]) {
                    g2d.drawRect(x * scale, y * scale, scale, scale);
                }
            }
        }
    }
}