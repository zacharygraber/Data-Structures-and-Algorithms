import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

class Traversal<E> {

    static <E> List<E> inOrder(BinTree<E> tree) {
        List<E> resultList = inOrder(((TreeNode<E>) tree).getLeftT()); // Visit the left subtree first

        resultList.add(((TreeNode<E>)tree).getData()); // Visit the root

        if (!(((TreeNode<E>)tree).getRightT().isEmpty())) {
            resultList.addAll(preOrder(((TreeNode<E>) tree).getRightT())); // Visit the right subtree
        }
        return resultList;
    }

    static <E> List<E> preOrder(BinTree<E> tree){
        List<E> resultList = new LinkedList<>();

        // Consider the edge case where the tree is empty
        if (tree.isEmpty()) { return resultList; }

        resultList.add(((TreeNode<E>)tree).getData()); // Visit the root

        if (!(((TreeNode<E>)tree).getLeftT().isEmpty())) {
            resultList.addAll(preOrder(((TreeNode<E>) tree).getLeftT())); // Visit the left subtree
        }

        if (!(((TreeNode<E>)tree).getRightT().isEmpty())) {
            resultList.addAll(preOrder(((TreeNode<E>) tree).getRightT())); // Visit the right subtree
        }
        return resultList;
    }

    static <E> List<E> postOrder(BinTree<E> tree) {
        List<E> resultList = new LinkedList<>();

        // Consider the edge case where the tree is empty
        if (tree.isEmpty()) { return resultList; }

        if (!(((TreeNode<E>)tree).getLeftT().isEmpty())) {
            resultList.addAll(preOrder(((TreeNode<E>) tree).getLeftT())); // Visit the left subtree
        }

        if (!(((TreeNode<E>)tree).getRightT().isEmpty())) {
            resultList.addAll(preOrder(((TreeNode<E>) tree).getRightT())); // Visit the right subtree
        }

        resultList.add(((TreeNode<E>)tree).getData()); // Visit the root
        return resultList;
    }
}