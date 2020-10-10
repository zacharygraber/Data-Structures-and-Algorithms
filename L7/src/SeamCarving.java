import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

public class SeamCarving {
    private int[] pixels;
    private int type, height, width;

    // Field getters

    int[] getPixels() {
        return pixels;
    }

    int getType() {
        return type;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    // Read and write images

    void readImage(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));
        type = image.getType();
        height = image.getHeight();
        width = image.getWidth();
        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    void writeImage(String filename) throws IOException {
        BufferedImage image = new BufferedImage(width, height, type);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        ImageIO.write(image, "jpg", new File(filename));
    }

    // Accessing pixels and their neighbors

    Color getColor(int h, int w) {
        int pixel = pixels[w + h * width];
        return new Color(pixel, true);
    }

    ArrayList<Position> getHVneighbors(int h, int w) {
        ArrayList<Position> neighbors = new ArrayList<>();

        if (w == 0) neighbors.add(new Position(h, w + 1));
        else if (w + 1 == width) neighbors.add(new Position(h, w - 1));
        else {
            neighbors.add(new Position(h, w - 1));
            neighbors.add(new Position(h, w + 1));
        }

        if (h == 0) neighbors.add(new Position(h + 1, w));
        else if (h + 1 == height) neighbors.add(new Position(h - 1, w));
        else {
            neighbors.add(new Position(h + 1, w));
            neighbors.add(new Position(h - 1, w));
        }
        return neighbors;
    }

    ArrayList<Position> getBelowNeighbors(int h, int w) {
        ArrayList<Position> neighbors = new ArrayList<>();
        if (h + 1 == height) return neighbors;
        neighbors.add(new Position(h + 1, w));
        if (w == 0) {
            neighbors.add(new Position(h + 1, w + 1));
            return neighbors;
        } else if (w + 1 == width) {
            neighbors.add(new Position(h + 1, w - 1));
            return neighbors;
        } else {
            neighbors.add(new Position(h + 1, w + 1));
            neighbors.add(new Position(h + 1, w - 1));
            return neighbors;
        }
    }

    // Computing energy at given pixel

    int computeEnergy(int h, int w) {
        Color c = getColor(h, w);
        Function<Integer, Integer> sq = n -> n * n;
        int energy = 0;
        for (Position p : getHVneighbors(h, w)) {
            Color nc = getColor(p.getFirst(), p.getSecond());
            energy += sq.apply(nc.getRed() - c.getRed());
            energy += sq.apply(nc.getGreen() - c.getGreen());
            energy += sq.apply(nc.getBlue() - c.getBlue());
        }
        return energy;
    }

    // Find seam starting from (h,w) going down and return list of positions and cost
    // and then pick best seam starting from some position on the first row

    Map<Position, Pair<List<Position>, Integer>> hash = new WeakHashMap<>();

    Pair<List<Position>, Integer> findSeam(int hp, int wp) {
        Position thisPos = new Position(hp, wp);
        if (hash.containsKey(thisPos)) {
            return hash.get(thisPos);
        }
        else {
            Pair<List<Position>, Integer> result;

            // Compute costs of each neighbor below.
            ArrayList<Pair<List<Position>, Integer>> neighborsBelow = new ArrayList<>();
            for (Position p : getBelowNeighbors(hp, wp)) {
                if (!hash.containsKey(p)) {
                    hash.put(p, findSeam(p.getFirst(), p.getSecond()));
                }
                neighborsBelow.add(hash.get(p));
            }

            // The base case, where we've reached the bottom of the image.
            // Return a pair with the List as just this position (there is no next), and the cost as this pixel's energy
            if (neighborsBelow.size() == 0) {
                result = new Pair<>(new Node<>(thisPos, new Empty<>()), computeEnergy(hp, wp));
                hash.put(thisPos, result);
                return result;
            }

            // Find the minimum cost of all the neighbors and return it
            Pair<List<Position>, Integer> minCostSeam = new Pair<>(null, Integer.MAX_VALUE);
            for (Pair<List<Position>, Integer> thisSeam : neighborsBelow) {
                if (thisSeam.getSecond() < minCostSeam.getSecond()) {
                    minCostSeam = thisSeam;
                }
            }

            // Make and return a new Pair to represent the optimal seam at this pixel.
            //  The List is the location of this pixel, with a *next* value of the path of the lowest-cost neighbor.
            //  The cost is the energy of this pixel, plus the cost of the rest of the seam.
            result = new Pair<List<Position>, Integer>(new Node<>(thisPos, minCostSeam.getFirst()), computeEnergy(hp, wp) + minCostSeam.getSecond());
            hash.put(thisPos, result);
            return result;
        }
    }

    Pair<List<Position>, Integer> bestSeam() {
        hash.clear();
        Pair<List<Position>, Integer> lowestCostSeam=null, thisSeam;
        int lowestCost = Integer.MAX_VALUE;
        for (int i = 0; i < this.getWidth(); i++) {
            thisSeam = findSeam(0, i);
            if (thisSeam.getSecond() < lowestCost) {
                lowestCostSeam = thisSeam;
                lowestCost = thisSeam.getSecond();
            }
        }
        return lowestCostSeam;
    }

    // Putting it all together; find best seam and copy pixels without that seam

    void cutSeam() {
        try {
            List<Position> seam = bestSeam().getFirst();
            int[] newPixels = new int[height * (width - 1)];
            for (int h = 0; h < height; h++) {
                int nw = 0;
                for (int w = 0; w < width; w++) {
                    if (seam.isEmpty()) {
                        newPixels[nw + h * (width - 1)] = pixels[w + h * width];
                    }
                    else {
                        Position p = seam.getFirst();
                        if (p.getFirst() == h && p.getSecond() == w) {
                            seam = seam.getRest();
                            nw--;
                        } else {
                            newPixels[nw + h * (width - 1)] = pixels[w + h * width];
                        }
                    }
                    nw++;
                }
            }
            width = width - 1;
            pixels = newPixels;
        } catch (EmptyListE e) {
            throw new Error("Bug");
        }
    }
}


