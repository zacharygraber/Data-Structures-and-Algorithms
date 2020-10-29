import java.util.Iterator;
import java.util.NoSuchElementException;

//-----------------------------------------------------------------------
// Empty BST exception

class EmptyBSTE extends Exception {}

//-----------------------------------------------------------------------
// Abstract BST class

abstract class BST implements TreePrinter.PrintableNode, Iterable<Integer> {

    //--------------------------
    // Static fields and methods
    //--------------------------

    static EmptyBSTE EBSTX = new EmptyBSTE();

    static BST EBST = new EmptyBST();

    static BST BSTLeaf(int elem) {
        return new BSTNode(elem, EBST, EBST);
    }

    static AVL toAVL (BST bst) {
        AVL avl = AVL.EAVL;
        for (int d : bst) avl = avl.AVLinsert(d);
        return avl;
    }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    abstract int BSTData() throws EmptyBSTE;

    abstract BST BSTLeft() throws EmptyBSTE;

    abstract BST BSTRight() throws EmptyBSTE;

    abstract int BSTHeight();

    abstract boolean isEmpty();

    //--------------------------
    // Main methods
    //--------------------------

    abstract boolean BSTfind (int key);

    abstract BST BSTinsert(int key);

    abstract BST BSTdelete(int key) throws EmptyBSTE;

    abstract Pair<Integer, BST> BSTdeleteLeftMostLeaf() throws EmptyBSTE;
}

//-----------------------------------------------------------------------

class EmptyBST extends BST {

    //--------------------------
    // Getters and simple methods
    //--------------------------

    int BSTData() throws EmptyBSTE {
        throw EBSTX;
    }

    BST BSTLeft() throws EmptyBSTE {
        throw EBSTX;
    }

    BST BSTRight() throws EmptyBSTE {
        throw EBSTX;
    }

    int BSTHeight() {
        return 0;
    }

    boolean isEmpty () { return true; }

    //--------------------------
    // Main methods
    //--------------------------

    boolean BSTfind(int key) {
        return false;
    }

    BST BSTinsert(int key) {
        return BSTLeaf(key);
    }

    BST BSTdelete(int key) throws EmptyBSTE {
        throw EBSTX;
    }

    Pair<Integer, BST> BSTdeleteLeftMostLeaf() throws EmptyBSTE {
        throw EBSTX;
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

    //--------------------------
    // Iterable interface
    //--------------------------

    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            public boolean hasNext() {
                return false;
            }

            public Integer next() {
                throw new NoSuchElementException();
            }
        };
    }
}

//-----------------------------------------------------------------------
// Non-empty tree case

class BSTNode extends BST {
    private int data;
    private BST left, right;
    private int height;

    BSTNode(int data, BST left, BST right) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.height = 1 + Math.max(left.BSTHeight(), right.BSTHeight());
    }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    int BSTData() {
        return data;
    }

    BST BSTLeft() {
        return left;
    }

    BST BSTRight() {
        return right;
    }

    int BSTHeight() {
        return height;
    }

    boolean isEmpty () { return false; }

    //--------------------------
    // Main methods
    //--------------------------

    boolean BSTfind(int key) {
        if (this.BSTData() == key) {
            return true;
        }
        return key < this.BSTData() ? this.BSTLeft().BSTfind(key) : this.BSTRight().BSTfind(key);
    }

    BST BSTinsert(int key) {
        BSTNode temp = new BSTNode(this.BSTData(), this.BSTLeft(), this.BSTRight());
        if (key < this.BSTData()) {
            temp.left = temp.BSTLeft().BSTinsert(key);
        }
        else {
            temp.right = temp.BSTRight().BSTinsert(key);
        }
        temp.height = 1 + Math.max(temp.left.BSTHeight(), temp.right.BSTHeight());
        return temp;
    }

    BST BSTdelete(int key) throws EmptyBSTE {
        BSTNode temp = new BSTNode(this.BSTData(), this.BSTLeft(), this.BSTRight());
        if (key < temp.BSTData()) {
            temp.left = temp.BSTLeft().BSTdelete(key);
        }
        else if (key > temp.BSTData()) {
            temp.right = temp.BSTRight().BSTdelete(key);
        }
        else { // If the key is equal to the data here, then the key is found at this node
            // If this node is a leaf, we're safe to delete it outright.
            if (this.BSTHeight() == 1) {
                return EBST;
            }
            // Otherwise, if this node has a right subtree replace it with the leftmost node in its right subtree
            else if (!(this.BSTRight().isEmpty())) {
                Pair<Integer, BST> delLeftmostInRight = this.BSTRight().BSTdeleteLeftMostLeaf();
                temp.data = delLeftmostInRight.getFirst();
                temp.right = delLeftmostInRight.getSecond();
            }
            // If there is no right subtree, then replace this node with its left subtree
            else {
                temp = (BSTNode) temp.BSTLeft();
            }
        }
        temp.height = 1 + Math.max(temp.left.BSTHeight(), temp.right.BSTHeight());
        return temp;
    }

    Pair<Integer, BST> BSTdeleteLeftMostLeaf() {
        return new Pair<>(this.leftmostNodeData(), this.leftMostLeafHelper());
    }

    // Recursive helper method for BSTdeleteLeftMostLeaf()
    // Returns this BST with the leftmost node removed.
    private BST leftMostLeafHelper() {
        /* Base Case:
         *     If this node has no left subtree, then this is the leftmost node.
         *     Return an empty node up the stack                                  */
        if (this.BSTLeft().isEmpty()) {
            return EBST;
        }

        // Recursive step:
        //     Otherwise, recursively call this method on the left subtree
        else {
            BSTNode temp = new BSTNode(this.BSTData(), this.BSTLeft(), this.BSTRight());
            temp.left = ((BSTNode) BSTLeft()).leftMostLeafHelper();
            return temp;
        }
    }

    // Recursive helper method for BSTdeleteLeftMostLeaf()
    // Returns the data at the leftmost leaf of this BST
    private int leftmostNodeData() {
        /* Base Case:
         *     If this node has no left subtree, then this is the leftmost node.
         *     Return the data here                                              */
        if (this.BSTLeft().isEmpty()) {
            return this.BSTData();
        }

        // Recursive step:
        //     Otherwise, recursively call this method on the left subtree
        return ((BSTNode) this.BSTLeft()).leftmostNodeData();
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

    //--------------------------
    // Iterable interface
    //--------------------------

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>()
        {
            // Make an iterator for the left and right subtrees
            Iterator<Integer> leftIter = left.iterator();
            Iterator<Integer> rightIter = right.iterator();
            boolean thisVisited = false;

            @Override
            public boolean hasNext()
            {
                // If left, center, or right not fully visited yet, return true
                return leftIter.hasNext() || (!thisVisited) || rightIter.hasNext();
            }

            @Override
            public Integer next()
            {
                if (leftIter.hasNext()) { // If the leftIter has a next, then the left subtree has not been fully visited yet
                    return leftIter.next();
                }
                else if (!thisVisited) { // Visit the root
                    thisVisited = true;
                    return data;
                }
                else { // If the rightIter has a next, then the right subtree  has not been explored all the way
                    return rightIter.next();
                }
            }
        };
    }
}

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
