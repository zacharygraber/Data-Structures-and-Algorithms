import org.junit.jupiter.api.Test;

import java.util.function.BinaryOperator;

import static org.junit.jupiter.api.Assertions.*;

public class TraversalTest{

    @Test
    public void testTraversal1(){
        TreeNode<Integer> intTree = new TreeNode<>(1,
                new TreeNode<>(2,
                        new TreeNode<>(4,
                                new EmptyTree<>(),
                                new EmptyTree<>()),
                        new TreeNode<>(5,
                                new EmptyTree<>(),
                                new EmptyTree<>())),
                new TreeNode<>(3,
                        new TreeNode<>(6,
                                new EmptyTree<>(),
                                new EmptyTree<>()),
                        new TreeNode<>(7,
                                new EmptyTree<>(),
                                new EmptyTree<>())));

        List<Integer> expectedInOrder = new Node<>(4, new Node<>(2, new Node<>(5,
                new Node<>(1, new Node<>(6, new Node<>(3, new Node<>(7, new Empty<>())))))));
        List<Integer> expectedPreOrder = new Node<>(1, new Node<>(2, new Node<>(4,
                new Node<>(5, new Node<>(3, new Node<>(6, new Node<>(7, new Empty<>())))))));
        List<Integer> expectedPostOrder = new Node<>(4, new Node<>(5, new Node<>(2,
                new Node<>(6, new Node<>(7, new Node<>(3, new Node<>(1, new Empty<>())))))));

        assertEquals(expectedInOrder,Traversal.inOrder(intTree));
        assertEquals(expectedPreOrder,Traversal.preOrder(intTree));
        assertEquals(expectedPostOrder,Traversal.postOrder(intTree));
    }

    @Test
    public void testTraversal2(){
        TreeNode<Integer> intTree = new TreeNode<>(1,
                new TreeNode<>(2,
                        new TreeNode<>(4,
                                new EmptyTree<>(),
                                new EmptyTree<>()),
                        new TreeNode<>(5,
                                new EmptyTree<>(),
                                new EmptyTree<>())),
                new TreeNode<>(3,
                        new TreeNode<>(6,
                                new EmptyTree<>(),
                                new EmptyTree<>()),
                        new TreeNode<>(7,
                                new EmptyTree<>(),
                                new EmptyTree<>())));

        TreeNode<Integer> intTree1 = new TreeNode<>(20,
                new TreeNode<>(8,
                        new EmptyTree<>(),
                        new EmptyTree<>()),
                new TreeNode<>(9,
                        new TreeNode<>(10,
                                new TreeNode<>(11,
                                        new EmptyTree<>(),
                                        intTree),
                                new EmptyTree<>()),
                        new TreeNode<>(12,
                                new EmptyTree<>(),
                                new TreeNode<>(13,
                                        new EmptyTree<>(),
                                        new EmptyTree<>()))));

        List<Integer> expectedInOrder = new Node<>(4, new Node<>(2, new Node<>(5,
                new Node<>(1, new Node<>(6, new Node<>(3, new Node<>(7, new Empty<>())))))));
        List<Integer> expectedPreOrder = new Node<>(1, new Node<>(2, new Node<>(4,
                new Node<>(5, new Node<>(3, new Node<>(6, new Node<>(7, new Empty<>())))))));
        List<Integer> expectedPostOrder = new Node<>(4, new Node<>(5, new Node<>(2,
                new Node<>(6, new Node<>(7, new Node<>(3, new Node<>(1, new Empty<>())))))));

        List<Integer> expectedInOrder1 = new Node<>(8, new Node<>(20, new Node<>(11,
                expectedInOrder))).append(new Node<>(10, new Node<>(9, new Node<>(12,
                new Node<>(13, new Empty<>())))));
        List<Integer> expectedPreOrder1 = new Node<>(20, new Node<>(8, new Node<>(9,
                new Node<>(10, new Node<>(11, expectedPreOrder))))).append(new Node<>(12,
                new Node<>(13, new Empty<>())));
        List<Integer> expectedPostOrder1 =  new Node<>(8, expectedPostOrder).append(new Node<>(11,
                new Node<>(10, new Node<>(13, new Node<>(12, new Node<>(9,
                        new Node<>(20, new Empty<>())))))));

        assertEquals(expectedInOrder1,Traversal.inOrder(intTree1));
        assertEquals(expectedPreOrder1,Traversal.preOrder(intTree1));
        assertEquals(expectedPostOrder1,Traversal.postOrder(intTree1));
    }
}