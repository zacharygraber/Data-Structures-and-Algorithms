//-----------------------------------------------------------------------
// Empty AVL exception

class EmptyAVLE extends Exception {
}

//-----------------------------------------------------------------------
// Abstract AVL class

abstract class AVL<E extends Comparable<E>> implements TreePrinter.PrintableNode {

    //--------------------------
    // Static fields and methods
    //--------------------------

    static EmptyAVLE EAVLX = new EmptyAVLE();

    //--------------------------
    // Getters and simple methods
    //--------------------------

    abstract E AVLData() throws EmptyAVLE;

    abstract AVL<E> AVLLeft() throws EmptyAVLE;

    abstract AVL<E> AVLRight() throws EmptyAVLE;

    abstract boolean isEmpty();

    abstract int AVLHeight();

    //--------------------------
    // Main methods
    //--------------------------

    abstract boolean AVLfind(E key);

    abstract AVL<E> AVLinsert(E key);

    abstract AVL<E> AVLeasyRight();

    abstract AVL<E> AVLrotateRight();

    abstract AVL<E> AVLeasyLeft();

    abstract AVL<E> AVLrotateLeft();

    /**
     * Four new methods to convert an AVL tree to a red-black one. The
     * main idea of the algorithm we will use can be captured by the
     * following invariants:
     *
     * An AVL tree of height 2n can be converted to a red tree
     *   with black height n. This is what method colorRed() should do.
     *
     * An AVL tree of height 2n can be converted to a black tree
     *   with black height (n+1). This is what method colorBlackEven
     *   should do.
     *
     * An AVL tree of height 2n+1 can be converted to a black tree
     *   with black height (n+1). This is what method colorBlackOdd
     *   should do.
     *
     * The entry point for these methods is the method toRB(). Simce
     * the root should be colored black, it calls colorBlackEven or
     * colorBlackOdd depending on whether the current AVL tree has
     * even height or odd height.
     *
     */

    abstract RedBlackT<E> toRB ();

    /**
     * When this method is called, it creates a black node whose
     * colors are calculated as follows:
     *   - if the current AVL tree has children of equal height,
     *     then the generated nodes are red
     *   - if the current AVL tree has children that differ in
     *     height, then the generated node for the shorter tree
     *     is black and the other is red
     */
    abstract RedBlackT<E> colorBlackOdd ();

    /**
     * When this method is called, it creates a black node whose children
     * will also be black. The children are generated by either colorBlackEven
     * or colorBlackOdd depending on whether the height of the AVL child
     * is even or odd.
     */
    abstract RedBlackT<E> colorBlackEven ();

    /**
     * When this method is called, it creates a red node whose children
     * must be black. The children are generated by either colorBlackEven
     * or colorBlackOdd depending on whether the height of the AVL child
     * is even or odd.
     */
    abstract RedBlackT<E> colorRed  ();
}

//-----------------------------------------------------------------------

class EmptyAVL<E extends Comparable<E>> extends AVL<E> {

    //--------------------------
    // Getters and simple methods
    //--------------------------

    E AVLData() throws EmptyAVLE {
        throw EAVLX;
    }

    AVL<E> AVLLeft() throws EmptyAVLE {
        throw EAVLX;
    }

    AVL<E> AVLRight() throws EmptyAVLE {
        throw EAVLX;
    }

    boolean isEmpty() {
        return true;
    }

    // We are changing our convention for the height of an empty tree
    // This will make it easier to state the invariant needed to
    // convert to a red-black tree
    int AVLHeight() {
        return 1;
    }

    //--------------------------
    // Main methods
    // Adapting the methods from before to this generic class
    //--------------------------

    boolean AVLfind(E key) {
        return false;
    }

    AVL<E> AVLinsert(E key) {
        return new AVLNode<>(key, new EmptyAVL<>(), new EmptyAVL<>());
    }

    AVL<E> AVLeasyRight() {
        throw new Error("Internal bug: should never call easyRight on empty tree");
    }

    AVL<E> AVLrotateRight() {
        throw new Error("Internal bug: should never call rotateRight on empty tree");
    }

    AVL<E> AVLeasyLeft() {
        throw new Error("Internal bug: should never call easyLeft on empty tree");
    }

    AVL<E> AVLrotateLeft() {
        throw new Error("Internal bug: should never call rotateLeft on empty tree");
    }

    RedBlackT<E> toRB () {
        // Coloring an empty AVL tree red and black should yield an empty RB Tree
        return new EmptyRB<>();
    }

    RedBlackT<E> colorBlackEven () {
        return new EmptyRB<>();
    }

    RedBlackT<E> colorBlackOdd () {
        return new EmptyRB<>();
    }

    RedBlackT<E> colorRed  () {
        return new EmptyRB<>();
    }

    //--------------------------
    // Override
    //--------------------------

    public boolean equals(Object o) {
        return (o instanceof EmptyAVL);
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

class AVLNode<E extends Comparable<E>> extends AVL<E> {
    private final E data;
    private final AVL<E> left;
    private final AVL<E> right;
    private final int height;

    AVLNode(E data, AVL<E> left, AVL<E> right) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.height = 1 + Math.max(left.AVLHeight(), right.AVLHeight());
    }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    E AVLData() {
        return data;
    }

    AVL<E> AVLLeft() {
        return left;
    }

    AVL<E> AVLRight() {
        return right;
    }

    boolean isEmpty() {
        return false;
    }

    int AVLHeight() {
        return height;
    }

    //--------------------------
    // Main methods
    // Adapt methods from before to this generic class
    //--------------------------

    boolean AVLfind(E key) {
        if (this.AVLData() == key) {
            return true;
        }
        return key.compareTo(this.AVLData()) < 0 ? this.AVLLeft().AVLfind(key) : this.AVLRight().AVLfind(key);
    }

    AVL<E> AVLinsert(E key) {
        if (key.compareTo(this.AVLData()) < 0) {
            AVL<E> newLeft = this.AVLLeft().AVLinsert(key); // Make a new left branch by recursively inserting there
            AVLNode<E> result = new AVLNode<>(this.AVLData(), newLeft, this.AVLRight()); // Define the resulting tree as root of this, left newLeft, and right this.right
            if (result.left.AVLHeight() > result.right.AVLHeight() + 1) {
                result = (AVLNode<E>) result.AVLrotateRight();
            }
            return result;
        }
        else {
            AVL<E> newRight = this.AVLRight().AVLinsert(key);
            AVLNode<E> result = new AVLNode<>(this.AVLData(), this.AVLLeft(), newRight);
            if (result.right.AVLHeight() > result.left.AVLHeight() + 1) {
                result = (AVLNode<E>) result.AVLrotateLeft();
            }
            return result;
        }
    }

    AVL<E> AVLeasyRight() {
        AVLNode<E> newRight;
        AVLNode<E> l = (AVLNode<E>) this.AVLLeft(); // Define l as the left subtree of the original root

        newRight = new AVLNode<>(this.data, l.AVLRight(), this.right); // Define newRight as the original root, with the left reference as the right subtree of l

        return new AVLNode<>(l.AVLData(), l.AVLLeft(), newRight); // Return the left subtree as the new root, with the right reference as newRight
    }

    AVL<E> AVLrotateRight() {
        AVLNode<E> l = (AVLNode<E>) this.left;
        if (l.AVLRight().AVLHeight() > l.AVLLeft().AVLHeight()) {
            l = (AVLNode<E>) l.AVLeasyLeft();
        }
        AVL<E> root = new AVLNode<>(this.AVLData(), l, this.AVLRight());
        return root.AVLeasyRight();
    }

    AVL<E> AVLeasyLeft() {
        AVLNode<E> newLeft;
        AVLNode<E> r = (AVLNode<E>) this.AVLRight(); // Define r as the right subtree of the original root

        newLeft = new AVLNode<>(this.data, this.left, r.AVLLeft()); // Define newLeft as the original root, with the right reference as the left subtree of r

        return new AVLNode<>(r.AVLData(), newLeft, r.AVLRight()); // Return the right subtree as the new root, with the left reference as newLeft
    }

    AVL<E> AVLrotateLeft() {
        AVLNode<E> r = (AVLNode<E>) this.right;
        if (r.AVLLeft().AVLHeight() > r.AVLRight().AVLHeight()) {
            r = (AVLNode<E>) r.AVLeasyRight();
        }
        AVL<E> root = new AVLNode<>(this.AVLData(), this.AVLLeft(), r);
        return root.AVLeasyLeft();
    }

    RedBlackT<E> toRB () {
        if (this.AVLHeight() % 2 == 0) { // If the height is even.
            return this.colorBlackEven();
        }
        return this.colorBlackOdd(); // If the height is odd.
    }

    RedBlackT<E> colorBlackEven () {
        RedBlackT<E> l, r;
        l = this.AVLLeft().toRB();
        r = this.AVLRight().toRB();
        return new RBNode<>(this.AVLData(), Color.BLACK, l, r);
    }

    RedBlackT<E> colorBlackOdd () {
        RedBlackT<E> l, r;
        if (this.AVLLeft().AVLHeight() == this.AVLRight().AVLHeight()) {
            // If the children are of equal height, generate two red nodes.
            l = this.AVLLeft().colorRed();
            r = this.AVLRight().colorRed();
        }
        else {
            // Otherwise, the shorter one is black and the taller one is red
            if (this.AVLLeft().AVLHeight() < this.AVLRight().AVLHeight()) {
                l = this.AVLLeft().toRB(); // toRB() will color black, and determine which method to call
                r = this.AVLRight().colorRed();
            }
            else {
                l = this.AVLLeft().colorRed();
                r = this.AVLRight().toRB();
            }
        }
        return new RBNode<>(this.AVLData(), Color.BLACK, l, r);
    }

    RedBlackT<E> colorRed  () {
        RedBlackT<E> l, r;
        l = this.AVLLeft().toRB();
        r = this.AVLRight().toRB();
        return new RBNode<>(this.AVLData(), Color.RED, l, r);
    }

    //--------------------------
    // Override
    //--------------------------

    public boolean equals(Object o) {
        if (o instanceof AVLNode) {
            AVLNode other = (AVLNode) o;
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
        return String.valueOf(data);
    }
}

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
