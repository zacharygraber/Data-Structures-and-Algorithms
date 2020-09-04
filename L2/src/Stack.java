
class EmptyStackE extends Exception {}

public abstract class Stack {
    abstract Stack push (int i);
    abstract Stack pop () throws EmptyStackE;
    abstract int top () throws EmptyStackE;
}

class EmptyS extends Stack {
    Stack push(int i) {
        return new NonEmptyS(i,this);
    }

    Stack pop() throws EmptyStackE {
        throw new EmptyStackE();
    }

    int top() throws EmptyStackE {
        throw new EmptyStackE();
    }

    public String toString () { return "#"; }
}

class NonEmptyS extends Stack {
    private int top;
    private Stack rest;

    NonEmptyS (int i, Stack rest) {
        this.top = i;
        this.rest = rest;
    }

    Stack push(int i) {
        return new NonEmptyS(i,this);
    }

    Stack pop() {
        return rest;
    }

    int top() {
        return this.top;
    }

    public String toString () {
        return top + " : " + rest.toString();
    }
}
