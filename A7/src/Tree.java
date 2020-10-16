import java.util.function.Function;

@FunctionalInterface
interface TriFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}

class WrongTreeE extends Exception {}

abstract class BinTree<N, L> implements TreePrinter.PrintableNode {

    abstract boolean isLeaf();
    abstract L getLeafData() throws WrongTreeE;
    abstract N getNodeData() throws WrongTreeE;
    abstract BinTree<N, L> getLeftTree() throws WrongTreeE;
    abstract BinTree<N, L> getRightTree() throws WrongTreeE;
    abstract <R, S> BinTree<R, S> map(Function<N, R> fnode, Function<L, S> fleaf);
    abstract <R> R reduce(Function<L, R> base, TriFunction<N, R, R, R> f);

}

//-----------------------------------------------------------------------

class Leaf<N, L> extends BinTree<N, L> {
    private L data;

    Leaf(L data) {
        this.data = data;
    }

    boolean isLeaf() {
        return true;
    }

    L getLeafData() {
        return data;
    }

    N getNodeData() throws WrongTreeE {
        throw new WrongTreeE();
    }

    BinTree<N, L> getLeftTree() throws WrongTreeE {
        throw new WrongTreeE();
    }

    BinTree<N, L> getRightTree() throws WrongTreeE {
        throw new WrongTreeE();
    }

    <R, S> BinTree<R, S> map(Function<N, R> fnode, Function<L, S> fleaf) {
        return new Leaf<>(fleaf.apply(data));
    }

    <R> R reduce(Function<L, R> base, TriFunction<N, R, R, R> f) {
        return base.apply(data);
    }

    public TreePrinter.PrintableNode getLeft() {
        return null;
    }

    public TreePrinter.PrintableNode getRight() {
        return null;
    }

    public String getText() {
        return data.toString();
    }
}

//-----------------------------------------------------------------------

class Node<N, L> extends BinTree<N, L> {
    private N data;
    private BinTree<N, L> left, right;

    Node(N data, BinTree<N, L> left, BinTree<N, L> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    boolean isLeaf() {
        return false;
    }

    L getLeafData() throws WrongTreeE {
        throw new WrongTreeE();
    }

    N getNodeData() {
        return data;
    }

    BinTree<N, L> getLeftTree() {
        return left;
    }

    BinTree<N, L> getRightTree() {
        return right;
    }

    <R, S> BinTree<R, S> map(Function<N, R> fnode, Function<L, S> fleaf) {
        return new Node<>(fnode.apply(data),
                left.map(fnode,fleaf),
                right.map(fnode,fleaf));
    }

    <R> R reduce(Function<L, R> base, TriFunction<N, R, R, R> f) {
        return f.apply(data, left.reduce(base,f), right.reduce(base,f));
    }

    public TreePrinter.PrintableNode getLeft() {
        return left;
    }

    public TreePrinter.PrintableNode getRight() {
        return right;
    }

    public String getText() {
        return data.toString();
    }
}

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
