import java.util.function.Function;

/**
 * This class is complete.
 *
 * An edge has a source, a destination, and (optionally) a weight.
 *
 * The identity of the edge only depends on its source and destination.
 *
 */
public class Edge {
    private final Node source;
    private final Node destination;
    private int weight;

    Edge (Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    Node getSource () { return source; }
    Node getDestination() { return destination; }
    int getWeight () { return weight; }

    Edge flip () {
        return new Edge(destination,source,weight);
    }

    void updateWeight (Function<Integer,Integer> f) {
        this.weight = f.apply(weight);
    }

    public String toString () {
        return String.format("%s --%d--> %s", source, weight, destination);
    }

    public boolean equals (Object o) {
        if (o instanceof Edge) {
            Edge other = (Edge)o;
            return other.source.equals(source) && other.destination.equals(destination);
        }
        return false;
    }

    public int hashCode () {
        return source.hashCode() + 7 * destination.hashCode();
    }
}
