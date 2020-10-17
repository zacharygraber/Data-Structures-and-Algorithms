import org.junit.jupiter.api.Test;

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
}