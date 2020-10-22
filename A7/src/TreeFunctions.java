import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class TreeFunctions {

    // Every solution in this file should be written
    // using reduce.
    // In other words, every solution is essentially:
    //
    // return t.reduce(...)...
    //
    // The first one is done as an example
    //
    static <N, L> int countNodes(BinTree<N, L> t) {
        return t.reduce(d -> 1, (n, l, r) -> l + r + 1);
    }

    static <N, L> int countLeaves(BinTree<N, L> t) {
        return t.reduce(d -> 1, (n, l, r) -> l + r);
    }

    // All nodes are internal except they are leaves
    static <N, L> int countInternalNodes(BinTree<N, L> t) {
        return t.reduce(d -> 0, (n, l, r) -> l + r + 1);
    }

    // The height of a leaf is 0. The height of a node is one
    // plus the maximum of the heights of its children
    static <N, L> int height(BinTree<N, L> t) {
        return t.reduce(d -> 0, (n, l, r) -> Math.max(l, r) + 1);
    }

    // A leaf is balanced.
    // A node is balanced if its left tree is balanced,
    // its right tree is balanced, and the difference
    // in height between its left and right children
    // is no more than 1.
    static <N, L> boolean isBalanced(BinTree<N, L> t) {
        return t.reduce(d -> true, (n, l, r) -> {
            try {
                return l && r && Math.abs(height(t.getLeftTree()) - height(t.getRightTree())) <= 1;
            } catch (WrongTreeE wrongTreeE) {
                System.out.println("SOMETHING WENT VERY WRONG");
                wrongTreeE.printStackTrace();
                return false;
            }
        });
    }

    static List<Integer> preorder(BinTree<Integer, Integer> t) {
        return t.reduce(List::singleton, (n, l, r) -> List.singleton(n).append(l).append(r));
    }

    static List<Integer> inorder(BinTree<Integer, Integer> t) {
        return t.reduce(List::singleton, (n, l, r) -> l.append(List.singleton(n)).append(r));
    }

    static List<Integer> postorder(BinTree<Integer, Integer> t) {
        return t.reduce(List::singleton, (n, l, r) -> l.append(r).append(List.singleton(n)));
    }

    // The value of a leaf is itself. To evaluate a node,
    // apply the function stored at the node to the values
    // of its left and right subtrees.
    static Integer eval(BinTree<BinaryOperator<Integer>, Integer> t) {
        return t.reduce(d -> d, (n, l, r) -> n.apply(l, r));
    }

}
