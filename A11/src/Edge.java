public class Edge {
    private final Node source;
    private final Node destination;
    private final int weight;

    Edge (Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    Node getSource () { return source; }
    Node getDestination () { return destination; }
    int getWeight () { return weight; }
}
