class Pair<A, B> {
    private A first;
    private B second;

    Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    A getFirst() {
        return first;
    }

    B getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair<?, ?> otherPair = (Pair<?, ?>) other;
            return first.equals(otherPair.first) && second.equals(otherPair.second);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public String toString() {
        return "<" + first + ", " + second + ">";
    }
}
