class EmptyListE extends Exception {}

abstract class List<E> {
    abstract E getFirst() throws EmptyListE;
    abstract List<E> getRest() throws EmptyListE;
    abstract boolean isEmpty();
    abstract int length();
    abstract E get(int index) throws EmptyListE;
}

class EmptyList<E> extends List<E> {
    E getFirst() throws EmptyListE {
        throw new EmptyListE();
    }

    List<E> getRest() throws EmptyListE {
        throw new EmptyListE();
    }

    boolean isEmpty() {
        return true;
    }

    int length() {
        return 0;
    }

    E get(int index) throws EmptyListE {
       throw new EmptyListE();
    }

    @Override
    public boolean equals(Object other) {
       return other instanceof EmptyList;
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

class NonEmptyList<E> extends List<E> {
    private E front;
    private List<E> rest;

    NonEmptyList(E front, List<E> rest) {
        this.front = front;
        this.rest = rest;
    }

    E getFirst() {
        return front;
    }

    List<E> getRest() {
        return rest;
    }

    boolean isEmpty() {
        return false;
    }

    int length() {
        return 1 + rest.length();
    }

    E get(int index) throws EmptyListE {
        return index == 0 ? front : rest.get(index - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof NonEmptyList) {
            NonEmptyList<?> otherList = (NonEmptyList<?>) other;
            return front.equals(otherList.front) && rest.equals(otherList.rest);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return front.hashCode() + rest.hashCode();
    }

    @Override
    public String toString() {
        return front + ", " + rest;
    }
}
