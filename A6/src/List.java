import java.util.function.BiFunction;

class EmptyListE extends Exception {}

abstract class List<E> {
    abstract E getFirst() throws EmptyListE;
    abstract List<E> getRest() throws EmptyListE;
    abstract int length();
    abstract <R> R reduce (R base, BiFunction<E,R,R> acc);
}

class Empty<E> extends List<E> {
    E getFirst() throws EmptyListE {
        throw new EmptyListE();
    }

    List<E> getRest() throws EmptyListE {
        throw new EmptyListE();
    }

    int length() {
        return 0;
    }

    <R> R reduce(R base, BiFunction<E, R, R> acc) {
        return base;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Empty;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "_";
    }
}

class Node<E> extends List<E> {
    private E data;
    private List<E> rest;
    private int hash;

    Node(E data, List<E> rest) {
        this.data = data;
        this.rest = rest;
        hash = data.hashCode() + 31 * rest.hashCode();
    }

    E getFirst() {
        return data;
    }

    List<E> getRest() {
        return rest;
    }

    int length() {
        return 1 + rest.length();
    }

    <R> R reduce(R base, BiFunction<E, R, R> acc) {
        return acc.apply(data,rest.reduce(base,acc));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node)) return false;
        else {
            Node<E> that = (Node<E>) o;
            return data.equals(that.data) && rest.equals(that.rest);
        }
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public String toString() {
        return data + "," + rest;
    }
}

