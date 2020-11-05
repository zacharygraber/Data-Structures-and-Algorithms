import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Red-black tree invariants:
 *
 * The root and the empty nodes of the tree are black.
 *
 * A red node has only black children.
 *
 * Every path from the root node to an empty node contains the same
 * number of black nodes.
 */

class EmptyRBE extends Exception {
}

enum Color {RED, BLACK}

abstract class RedBlackT<E> implements TreePrinter.PrintableNode {

    //--------------------------
    // Static fields and methods
    //--------------------------

    static EmptyRBE ERBX = new EmptyRBE();

    //--------------------------
    // Getters and simple methods
    //--------------------------

    abstract E RBData() throws EmptyRBE;

    abstract RedBlackT<E> RBLeft() throws EmptyRBE;

    abstract RedBlackT<E> RBRight() throws EmptyRBE;

    abstract boolean isEmpty();

    abstract int RBHeight();

    //-------------
    // Main methods
    //-------------

    // The method does two things simultaneously:
    // * it checks that the tree is a valid black tree; if not it returns "empty"
    // * if the tree is valid, it returns the black height of the node
    abstract Optional<Integer> isValidBlackTree();

    // The method does two things simultaneously:
    // * it checks that the tree is a valid red tree; if not it returns "empty"
    // * if the tree is valid, it returns the black height of the node
    abstract Optional<Integer> isValidRedTree();

    // A red-black tree is valid if the current node is black or red
    // and the rest of the tree is valid
    Optional<Integer> isValidTree() {
        Optional<Integer> b = isValidBlackTree();
        Optional<Integer> r = isValidRedTree();
        if (b.isPresent()) return b;
        return r;
    }
}

class EmptyRB<E> extends RedBlackT<E> {

    //--------------------------
    // Getters and simple methods
    //--------------------------

    E RBData() throws EmptyRBE {
        throw ERBX;
    }

    RedBlackT<E> RBLeft() throws EmptyRBE {
        throw ERBX;
    }

    RedBlackT<E> RBRight() throws EmptyRBE {
        throw ERBX;
    }

    boolean isEmpty() {
        return true;
    }

    // Note the convention. The black height of an empty tree is 1 because
    // empty trees are black
    int RBHeight() {
        return 1;
    }

    //--------------------------
    // Main methods
    //--------------------------

    Optional<Integer> isValidBlackTree() {
        return Optional.of(1);
    }

    Optional<Integer> isValidRedTree() {
        return Optional.empty();
    }

    //--------------------------
    // Override
    //--------------------------

    public boolean equals(Object o) {
        return (o instanceof EmptyRB);
    }

    //--------------------------
    // Printable interface
    //--------------------------

    public TreePrinter.PrintableNode getLeft() {
        return null;
    }

    public TreePrinter.PrintableNode getRight() {
        return null;
    }

    public String getText() {
        return "";
    }
}

//-----------------------------------------------------------------------

class RBNode<E> extends RedBlackT<E> {
    private final E data;
    private final Color color;
    private final RedBlackT<E> left;
    private final RedBlackT<E> right;
    private final int height; // this is the regular height --- not the black height

    RBNode(E data, Color color, RedBlackT<E> left, RedBlackT<E> right) {
        this.data = data;
        this.color = color;
        this.left = left;
        this.right = right;
        this.height = 1 + Math.max(left.RBHeight(), right.RBHeight());
    }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    E RBData() {
        return data;
    }

    RedBlackT<E> RBLeft() {
        return left;
    }

    RedBlackT<E> RBRight() {
        return right;
    }

    boolean isEmpty() {
        return false;
    }

    int RBHeight() {
        return height;
    }

    //--------------------------
    // Main methods
    //--------------------------

    Optional<Integer> isValidBlackTree() {
        return null; // TODO
    }

    Optional<Integer> isValidRedTree() {
        return null; // TODO
    }

    //--------------------------
    // Override
    //--------------------------

    public boolean equals(Object o) {
        if (o instanceof RBNode) {
            RBNode other = (RBNode) o;
            return data == other.data && left.equals(other.left) && right.equals(other.right);
        }
        return false;
    }

    //--------------------------
    // Printable interface
    //--------------------------

    public TreePrinter.PrintableNode getLeft() {
        return left.isEmpty() ? null : left;
    }

    public TreePrinter.PrintableNode getRight() {
        return right.isEmpty() ? null : right;
    }

    public String getText() {
        return data+"_"+color;
    }
}

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
