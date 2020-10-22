import org.junit.jupiter.api.Test;

import javax.management.NotificationEmitter;
import java.util.function.BinaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class TreeFunctionsTest {

    @Test
    public void trees () {

        BinTree<Integer,Integer> t123, t456, t7;

        t123 = new Node<>(1, new Leaf<>(2), new Leaf<>(3));
        t456 = new Node<>(4, new Leaf<>(5), new Leaf<>(6));
        t7 = new Node<>(7, t123, t456);

        TreePrinter.print(t7);

        assertEquals(7, TreeFunctions.countNodes(t7));
        assertEquals(4, TreeFunctions.countLeaves(t7));
        assertEquals(3, TreeFunctions.countInternalNodes(t7));
        assertEquals(2, TreeFunctions.height(t7));
        assertTrue(TreeFunctions.isBalanced(t7));
        assertEquals(List.singleton(6).push(5).push(4).push(3).push(2).push(1).push(7),
                TreeFunctions.preorder(t7));
        assertEquals(List.singleton(6).push(4).push(5).push(7).push(3).push(1).push(2),
                TreeFunctions.inorder(t7));
        assertEquals(List.singleton(7).push(4).push(6).push(5).push(1).push(3).push(2),
                TreeFunctions.postorder(t7));
    }

    @Test
    public void eval () {
        BinTree<BinaryOperator<Integer>,Integer> exp1, exp2, exp3;
        exp1 = new Node<>(Integer::sum, new Leaf<>(2), new Leaf<>(3));
        exp2 = new Node<>((a,b) -> a-b, new Leaf<>(5), new Leaf<>(6));
        exp3 = new Node<>(Math::max, exp1, exp2);

        TreePrinter.print(exp3);

        assertEquals(5, TreeFunctions.eval(exp1));
        assertEquals(-1, TreeFunctions.eval(exp2));
        assertEquals(5, TreeFunctions.eval(exp3));
    }

    @Test
    public void countNodesWorksForLeaf() {
        // Edge case test
        BinTree<Integer, Integer> tree1 = new Leaf<>(null);
        assertEquals(1, TreeFunctions.countNodes(tree1));
    }

    @Test
    public void countNodesWorksForUnbalancedTrees() {
        BinTree<Integer, Integer> tree1 = new Node<>(2, new Node<>(3, new Node<>(7, new Leaf<>(99), new Leaf<>(null)), new Leaf<>(null)), new Leaf<>(null));
        assertEquals(7, TreeFunctions.countNodes(tree1));
    }

    @Test
    public void countNodesWorksForBalancedTrees() {
        BinTree<Integer, Integer> tree1 = new Node<>(2, new Node<>(3, new Leaf<>(0), new Leaf<>(4)), new Node<>(5, new Leaf<>(7), new Leaf<>(4)));
        assertEquals(7, TreeFunctions.countNodes(tree1));
    }

    @Test
    public void countLeavesWorksForLeaf() {
        // Edge case test
        BinTree<Integer, Integer> tree1 = new Leaf<>(null);
        assertEquals(1, TreeFunctions.countLeaves(tree1));
    }

    @Test
    public void countLeavesWorksForUnbalancedTrees() {
        BinTree<Integer, Integer> tree1 = new Node<>(2, new Node<>(3, new Node<>(7, new Leaf<>(99), new Leaf<>(null)), new Leaf<>(null)), new Leaf<>(null));
        assertEquals(4, TreeFunctions.countLeaves(tree1));
    }

    @Test
    public void countLeavesWorksForBalancedTrees() {
        BinTree<Integer, Integer> tree1 = new Node<>(2, new Node<>(3, new Leaf<>(0), new Leaf<>(4)), new Node<>(5, new Leaf<>(7), new Leaf<>(4)));
        assertEquals(4, TreeFunctions.countLeaves(tree1));
    }

    @Test
    public void countInternalNodesWorksForLeaf() {
        // Edge case test
        BinTree<Integer, Integer> tree1 = new Leaf<>(null);
        assertEquals(0, TreeFunctions.countInternalNodes(tree1));
    }

    @Test
    public void countInternalNodesWorksForUnbalancedTrees() {
        BinTree<Integer, Integer> tree1 = new Node<>(2, new Node<>(3, new Node<>(7, new Leaf<>(99), new Leaf<>(null)), new Leaf<>(null)), new Leaf<>(null));
        assertEquals(3, TreeFunctions.countInternalNodes(tree1));
    }

    @Test
    public void countInternalNodesWorksForBalancedTrees() {
        BinTree<Integer, Integer> tree1 = new Node<>(2, new Node<>(3, new Leaf<>(0), new Leaf<>(4)), new Node<>(5, new Leaf<>(7), new Leaf<>(4)));
        assertEquals(3, TreeFunctions.countInternalNodes(tree1));
    }

    @Test
    public void heightWorksForLeaf() {
        // Edge case test
        BinTree<Integer, Integer> tree1 = new Leaf<>(null);
        assertEquals(0, TreeFunctions.height(tree1));
    }

    @Test
    public void heightWorksWhenLeftTaller() {
        BinTree<Integer, Integer> tree1 = new Node<>(2, new Node<>(3, new Node<>(7, new Leaf<>(99), new Leaf<>(null)), new Leaf<>(null)), new Leaf<>(null));
        assertEquals(3, TreeFunctions.height(tree1));
    }

    @Test
    public void heightWorksWhenRightTaller() {
        BinTree<Integer, Integer> tree1 = new Node<>(2, new Leaf<>(null), new Node<>(3, new Leaf<>(null), new Leaf<>(null)));
        assertEquals(2, TreeFunctions.height(tree1));
    }

    @Test
    public void isBalancedWorksForLeaf() {
        // Edge case test
        BinTree<Integer, Integer> tree1 = new Leaf<>(null);
        assertTrue(TreeFunctions.isBalanced(tree1));
    }

    @Test
    public void isBalancedReturnsTrueForBalancedTree() {
        BinTree<Integer, Integer> tree1 = new Node<>(99, new Node<>(0, new Leaf<>(1), new Leaf<>(2)), new Node<>(0, new Leaf<>(1), new Leaf<>(2)));
        assertTrue(TreeFunctions.isBalanced(tree1));
    }

    @Test
    public void isBalancedReturnsFalseForUnbalancedTree() {
        BinTree<Integer, Integer> tree1 = new Node<>(2, new Node<>(3, new Node<>(7, new Leaf<>(99), new Leaf<>(null)), new Leaf<>(null)), new Leaf<>(null));
        assertFalse(TreeFunctions.isBalanced(tree1));
    }

    @Test
    public void preorderWorks() {
        /*
                         ----99----
                      --0--      --1--
                     0     1    10   11
         */
        BinTree<Integer, Integer> tree1 = new Node<>(99, new Node<>(0, new Leaf<>(0), new Leaf<>(1)), new Node<>(1, new Leaf<>(10), new Leaf<>(11)));
        assertEquals(List.singleton(11)
        .push(10)
        .push(1)
        .push(1)
        .push(0)
        .push(0)
        .push(99),

        TreeFunctions.preorder(tree1));
    }

    @Test
    public void inorderWorks() {
        /*
                         ----99----
                      --0--      --1--
                     0     1    10   11
         */
        BinTree<Integer, Integer> tree1 = new Node<>(99, new Node<>(0, new Leaf<>(0), new Leaf<>(1)), new Node<>(1, new Leaf<>(10), new Leaf<>(11)));
        assertEquals(List.singleton(11)
                        .push(1)
                        .push(10)
                        .push(99)
                        .push(1)
                        .push(0)
                        .push(0),

                TreeFunctions.inorder(tree1));
    }

    @Test
    public void postorderWorks() {
        /*
                         ----99----
                      --0--      --1--
                     0     1    10   11
         */
        BinTree<Integer, Integer> tree1 = new Node<>(99, new Node<>(0, new Leaf<>(0), new Leaf<>(1)), new Node<>(1, new Leaf<>(10), new Leaf<>(11)));
        assertEquals(List.singleton(99)
                        .push(1)
                        .push(11)
                        .push(10)
                        .push(0)
                        .push(1)
                        .push(0),

                TreeFunctions.postorder(tree1));
    }

    // I really would have liked to be able to evaluate expressions other than between ints. Shame.
    @Test
    public void evalWorksForOtherThings() {
        BinTree<BinaryOperator<Integer>, Integer> root, left, right;
        // sumOfAsciiVals("foo") = 324, sumOfAsciiVals("bar") = 309
        left = new Node<>(Integer::sum, new Leaf<>(sumOfAsciiVals("foo")), new Leaf<>(sumOfAsciiVals("bar"))); // Should yield 633
        right = new Node<>(Math::min, new Leaf<>(sumOfAsciiVals("Z")), new Leaf<>(sumOfAsciiVals("A"))); // Should yield 65
        root = new Node<>((s1, s2) -> reverseNumber(s1 + s2), left, right); // s1+s2 should be 698. That reversed is 896 (YES I know this doesn't work when there are trailing 0s)

        assertEquals(896, TreeFunctions.eval(root));
    }

    // Converts an int to a String, reverses the String, then parses to an int.
    private static int reverseNumber(int num) {
        String s = ""+num; // Cast to String
        String result = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            result += s.charAt(i);
        }
        return Integer.parseInt(result);
    }

    // Gets an int representation of a string by summing the ASCII val of each char in the String
    private static int sumOfAsciiVals(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += (int)s.charAt(i);
        }
        return sum;
    }
}