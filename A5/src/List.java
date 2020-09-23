abstract class List {
    abstract boolean isEmpty ();
    abstract int getElem ();
    abstract List getRest ();
}

class EmptyL extends List {
    boolean isEmpty () { return true; }
    int getElem () { throw new Error("Bug"); }
    List getRest () { throw new Error("Bug"); }
    public String toString () { return "*"; }
}

class NodeL extends List {
    private int elem;
    private List rest;

    NodeL (int elem, List rest) {
        this.elem = elem;
        this.rest = rest;
    }

    boolean isEmpty () { return false; }
    int getElem () { return elem; }
    List getRest () { return rest; }
    public String toString () { return elem + " : " + rest.toString(); }
}

class R {

    static List append (List xs, List ys) {
        if (xs.isEmpty()) return ys;
        else return new NodeL(xs.getElem(), append(xs.getRest(),ys));
    }

    static List rev1(List xs) {
        if (xs.isEmpty()) return new EmptyL();
        else return append(rev1(xs.getRest()),new NodeL(xs.getElem(),new EmptyL()));
    }

    //--

    static List rev2H(List xs, List ys) {
        if (xs.isEmpty()) return ys;
        else return rev2H(xs.getRest(),new NodeL(xs.getElem(),ys));
    }

    static List rev2(List xs) {
        return rev2H(xs, new EmptyL());
    }
}