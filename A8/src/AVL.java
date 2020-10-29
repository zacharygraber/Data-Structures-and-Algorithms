//-----------------------------------------------------------------------
// Empty AVL exception

class EmptyAVLE extends Exception {}

//-----------------------------------------------------------------------
// Abstract AVL class

abstract class AVL implements TreePrinter.PrintableNode {

    //--------------------------
    // Static fields and methods
    //--------------------------

    static EmptyAVLE EAVLX = new EmptyAVLE();

    static AVL EAVL = new EmptyAVL();

    static AVL AVLLeaf(int elem) {
        return new AVLNode(elem, EAVL, EAVL);
    }

    static BST toBST (AVL avl) {
        try {
            int data = avl.AVLData();
            AVL left = avl.AVLLeft();
            AVL right = avl.AVLRight();
            return new BSTNode(data, toBST(left), toBST(right));
        }
        catch (EmptyAVLE e) {
            return BST.EBST;
        }
    }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    abstract int AVLData() throws EmptyAVLE;

    abstract AVL AVLLeft() throws EmptyAVLE;

    abstract AVL AVLRight() throws EmptyAVLE;

    abstract int AVLHeight();

    abstract boolean isEmpty ();

    //--------------------------
    // Main methods
    //--------------------------

    abstract boolean AVLfind (int key);

    abstract AVL AVLinsert(int key);

    abstract AVL AVLeasyRight();

    abstract AVL AVLrotateRight();

    abstract AVL AVLeasyLeft();

    abstract AVL AVLrotateLeft();

    abstract AVL AVLdelete(int key) throws EmptyAVLE;

    abstract Pair<Integer, AVL> AVLshrink() throws EmptyAVLE;
}

//-----------------------------------------------------------------------

class EmptyAVL extends AVL {

    //--------------------------
    // Getters and simple methods
    //--------------------------

    int AVLData() throws EmptyAVLE {
        throw EAVLX;
    }

    AVL AVLLeft() throws EmptyAVLE {
        throw EAVLX;
    }

    AVL AVLRight() throws EmptyAVLE {
        throw EAVLX;
    }

    int AVLHeight() {
        return 0;
    }

    boolean isEmpty () { return true; }

    //--------------------------
    // Main methods
    //--------------------------

    boolean AVLfind (int key) {
        return false;
    }

    AVL AVLinsert(int key) {
        return AVLLeaf(key);
    }

    AVL AVLeasyRight() {
        throw new Error("Internal bug: should never call easyRight on empty tree");
    }

    AVL AVLrotateRight() {
        throw new Error("Internal bug: should never call rotateRight on empty tree");
    }

    AVL AVLeasyLeft() {
        throw new Error("Internal bug: should never call easyLeft on empty tree");
    }

    AVL AVLrotateLeft() {
        throw new Error("Internal bug: should never call rotateLeft on empty tree");
    }

    AVL AVLdelete(int key) throws EmptyAVLE {
        throw EAVLX;
    }

    Pair<Integer, AVL> AVLshrink() throws EmptyAVLE {
        throw EAVLX;
    }

    //--------------------------
    // Override
    //--------------------------

    public boolean equals (Object o) {
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

class AVLNode extends AVL {
    private int data;
    private AVL left, right;
    private int height;

    AVLNode(int data, AVL left, AVL right) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.height = 1 + Math.max(left.AVLHeight(), right.AVLHeight());
    }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    int AVLData() {
        return data;
    }

    AVL AVLLeft() {
        return left;
    }

    AVL AVLRight() {
        return right;
    }

    int AVLHeight() {
        return height;
    }

    boolean isEmpty () { return false; }

    //--------------------------
    // Main methods
    //--------------------------

    boolean AVLfind(int key) {
        if (this.AVLData() == key) {
            return true;
        }
        return key < this.AVLData() ? this.AVLLeft().AVLfind(key) : this.AVLRight().AVLfind(key);
    }

    AVL AVLinsert(int key) {
        if (key < this.AVLData()) {
            AVL newLeft = this.AVLLeft().AVLinsert(key); // Make a new left branch by recursively inserting there
            AVLNode result = new AVLNode(this.AVLData(), newLeft, this.AVLRight()); // Define the resulting tree as root of this, left newLeft, and right this.right
            if (result.left.AVLHeight() > result.right.AVLHeight() + 1) {
                result = (AVLNode) result.AVLrotateRight();
            }
            return result;
        }
        else {
            AVL newRight = this.AVLRight().AVLinsert(key);
            AVLNode result = new AVLNode(this.AVLData(), this.AVLLeft(), newRight);
            if (result.right.AVLHeight() > result.left.AVLHeight() + 1) {
                result = (AVLNode) result.AVLrotateLeft();
            }
            return result;
        }
    }

    AVL AVLeasyRight() {
        AVLNode newRight;
        AVLNode l = (AVLNode) this.AVLLeft(); // Define l as the left subtree of the original root

        newRight = new AVLNode(this.data, l.AVLRight(), this.right); // Define newRight as the original root, with the left reference as the right subtree of l

        return new AVLNode(l.AVLData(), l.AVLLeft(), newRight); // Return the left subtree as the new root, with the right reference as newRight
    }

    AVL AVLrotateRight() {
        AVLNode l = (AVLNode) this.left;
        if (l.AVLRight().AVLHeight() > l.AVLLeft().AVLHeight()) {
            l = (AVLNode) l.AVLeasyLeft();
        }
        AVL root = new AVLNode(this.AVLData(), l, this.AVLRight());
        return root.AVLeasyRight();
    }

    AVL AVLeasyLeft() {
        AVLNode newLeft;
        AVLNode r = (AVLNode) this.AVLRight(); // Define r as the right subtree of the original root

        newLeft = new AVLNode(this.data, this.left, r.AVLLeft()); // Define newLeft as the original root, with the right reference as the left subtree of r

        return new AVLNode(r.AVLData(), newLeft, r.AVLRight()); // Return the right subtree as the new root, with the left reference as newLeft
    }

    AVL AVLrotateLeft() {
        AVLNode r = (AVLNode) this.right;
        if (r.AVLLeft().AVLHeight() > r.AVLRight().AVLHeight()) {
            r = (AVLNode) r.AVLeasyRight();
        }
        AVL root = new AVLNode(this.AVLData(), this.AVLLeft(), r);
        return root.AVLeasyLeft();
    }

    AVL AVLdelete(int key) throws EmptyAVLE {
        if (key < this.AVLData()) { // If the key is less, recursively delete from left subtree
            AVL newLeft = this.AVLLeft().AVLdelete(key);

            AVLNode result = new AVLNode(this.AVLData(), newLeft, this.AVLRight());

            // Deleting from the left subtree might make the right larger and unbalance the tree
            if (result.AVLRight().AVLHeight() > result.AVLLeft().AVLHeight() + 1) {
                result = (AVLNode) result.AVLrotateLeft();
            }

            return result;
        }
        else if (key > this.AVLData()) { // If the key is greater, go to the right
            AVL newRight = this.AVLRight().AVLdelete(key);

            AVLNode result = new AVLNode(this.AVLData(), this.AVLLeft(), newRight);

            // Deleting from right may make left larger.
            if (result.AVLLeft().AVLHeight() > result.AVLRight().AVLHeight() + 1) {
                result = (AVLNode) result.AVLrotateRight();
            }
            return result;
        }
        else { // If the key is here at this tree...
            // Case 1: this node is a leaf, and we're safe to just delete it.
            if (this.AVLHeight() == 1) {
                return EAVL;
            }
            // Case 2: this node has no left subtree, but has a right one.
            // Solution: replace this node with its right child
            else if (this.AVLLeft().isEmpty()) {
                return this.AVLRight();
            }
            else { // Case 3: We are at a node with 2 subtrees and trying to delete the root.
                Pair<Integer, AVL> shrink = this.AVLLeft().AVLshrink(); // Call shrink on the left subtree
                AVLNode result = new AVLNode(shrink.getFirst(), shrink.getSecond(), this.AVLRight());
                return result;
            }
        }
    }

    Pair<Integer, AVL> AVLshrink() {
        int val = this.valOfLargest(); // Get the value of the largest thing in the tree
        try {
            return new Pair<>(val, this.AVLdelete(val));
        } catch (EmptyAVLE emptyAVLE) {
            emptyAVLE.printStackTrace();
            return new Pair<>(-999999, EAVL);
        }
    }

    private int valOfLargest() {
        if (this.AVLRight().isEmpty()) {
            return this.AVLData();
        }
        return ((AVLNode) this.AVLRight()).valOfLargest();
    }

    //--------------------------
    // Override
    //--------------------------

    public boolean equals (Object o) {
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
