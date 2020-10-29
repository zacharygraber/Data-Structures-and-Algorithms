import com.sun.source.tree.Tree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AVLTest {
    BST bst;
    AVL avl;

    @BeforeEach
    public void setUp() {
        List<Integer> nums = IntStream.range(0, 8).boxed().collect(Collectors.toList());
        Collections.shuffle(nums);
        bst = BST.EBST;
        avl = AVL.EAVL;
        for (int i : nums) {
            bst = bst.BSTinsert(i);
            avl = avl.AVLinsert(i);
        }
    }

    @Test
    public void toAVL() {
        BST bst2 = AVL.toBST(BST.toAVL(bst));
        Iterator<Integer> bstIter = bst.iterator();
        Iterator<Integer> bst2Iter = bst2.iterator();
        while (bstIter.hasNext() && bst2Iter.hasNext()) {
            assertEquals(bstIter.next(),bst2Iter.next());
        }
    }

    @Test
    public void AVLeasyRight() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(40);
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(20);
        avl = avl.AVLinsert(10);
        avl = avl.AVLinsert(30);
        avl = avl.AVLinsert(15);

        AVL left = avl.AVLLeft();
        AVL right = avl.AVLRight();
        assertEquals(20, avl.AVLData());
        assertEquals(10,left.AVLData());
        assertEquals(15, left.AVLRight().AVLData());
        assertEquals(40,right.AVLData());
        assertEquals(30, right.AVLLeft().AVLData());
        assertEquals(50, right.AVLRight().AVLData());
    }

    @Test
    public void AVLeasyLeft() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(10);
        avl = avl.AVLinsert(20);
        avl = avl.AVLinsert(30);
        avl = avl.AVLinsert(40);
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(60);

        AVL left = avl.AVLLeft();
        AVL right = avl.AVLRight();
        assertEquals(40, avl.AVLData());
        assertEquals(20,left.AVLData());
        assertEquals(30, left.AVLRight().AVLData());
        assertEquals(10, left.AVLLeft().AVLData());
        assertEquals(50,right.AVLData());
        assertEquals(60, right.AVLRight().AVLData());
    }

    @Test
    public void AVLrotateRight() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(40);
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(20);
        avl = avl.AVLinsert(10);
        avl = avl.AVLinsert(30);
        avl = avl.AVLinsert(25);

        AVL left = avl.AVLLeft();
        AVL right = avl.AVLRight();
        assertEquals(30, avl.AVLData());
        assertEquals(20,left.AVLData());
        assertEquals(10, left.AVLLeft().AVLData());
        assertEquals(25, left.AVLRight().AVLData());
        assertEquals(40,right.AVLData());
        assertEquals(50, right.AVLRight().AVLData());
    }

    @Test
    public void AVLrotateLeft() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(30);
        avl = avl.AVLinsert(40);
        avl = avl.AVLinsert(70);
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(60);
        avl = avl.AVLinsert(55);
        AVL left = avl.AVLLeft();
        AVL right = avl.AVLRight();
        assertEquals(50, avl.AVLData());
        assertEquals(40,left.AVLData());
        assertEquals(30,left.AVLLeft().AVLData());
        assertEquals(60,right.AVLData());
        assertEquals(55,right.AVLLeft().AVLData());
        assertEquals(70,right.AVLRight().AVLData());
    }

    @Test
    public void AVLdelete() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(35);
        avl = avl.AVLinsert(20);
        avl = avl.AVLinsert(40);
        avl = avl.AVLinsert(7);
        avl = avl.AVLinsert(30);
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(10);

        AVL avl2 = avl.AVLdelete(35);
        AVL left = avl2.AVLLeft();
        AVL right = avl2.AVLRight();
        assertEquals(30, avl2.AVLData());
        assertEquals(7,left.AVLData());
        assertEquals(5, left.AVLLeft().AVLData());
        assertEquals(20, left.AVLRight().AVLData());
        assertEquals(10, left.AVLRight().AVLLeft().AVLData());
        assertEquals(40,right.AVLData());
        assertEquals(50, right.AVLRight().AVLData());
    }

    @Test
    public void FE () throws EmptyAVLE {
        AVL t = new EmptyAVL();
        t = t.AVLinsert(44);
        t = t.AVLinsert(17);
        t = t.AVLinsert(78);
        t = t.AVLinsert(32);
        t = t.AVLinsert(50);
        t = t.AVLinsert(88);
        t = t.AVLinsert(48);
        t = t.AVLinsert(62);
        TreePrinter.print(t);
        t = t.AVLdelete(32);
        TreePrinter.print(t);
    }

    @Test
    public void debugging() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(35);
        avl = avl.AVLinsert(20);
        avl = avl.AVLinsert(40);
        avl = avl.AVLinsert(7);
        avl = avl.AVLinsert(30);
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(10);
        TreePrinter.print(avl);

        TreePrinter.print(avl.AVLLeft().AVLshrink().getSecond());
    }

    @Test
    public void AVLdeleteNodeInRightSubtree () throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(35);
        avl = avl.AVLinsert(20);
        avl = avl.AVLinsert(40);
        avl = avl.AVLinsert(7);
        avl = avl.AVLinsert(30);
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(10);

        AVL avl2 = avl.AVLdelete(40);
        AVL left = avl2.AVLLeft();
        AVL right = avl2.AVLRight();
        assertEquals(20, avl2.AVLData());
        assertEquals(7,left.AVLData());
        assertEquals(5, left.AVLLeft().AVLData());
        assertEquals(10, left.AVLRight().AVLData());
        assertEquals(35,right.AVLData());
        assertEquals(30, right.AVLLeft().AVLData());
        assertEquals(50, right.AVLRight().AVLData());
    }

    @Test
    public void AVLdeleteNodeInLeftSubtree () throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(35);
        avl = avl.AVLinsert(20);
        avl = avl.AVLinsert(40);
        avl = avl.AVLinsert(7);
        avl = avl.AVLinsert(30);
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(10);

        AVL avl2 = avl.AVLdelete(20);
        AVL left = avl2.AVLLeft();
        AVL right = avl2.AVLRight();
        assertEquals(35, avl2.AVLData());
        assertEquals(10,left.AVLData());
        assertEquals(7, left.AVLLeft().AVLData());
        assertEquals(5, left.AVLLeft().AVLLeft().AVLData());
        assertEquals(30, left.AVLRight().AVLData());
        assertEquals(40,right.AVLData());
        assertEquals(50, right.AVLRight().AVLData());
    }

    @Test
    public void AVLfind () throws EmptyAVLE {
        Iterator<Integer> iter = AVL.toBST(avl).iterator();
        while (iter.hasNext()) {
            assertTrue(avl.AVLfind(iter.next()));
        }

        iter = AVL.toBST(avl).iterator();
        int i;
        while (iter.hasNext()) {
            i = iter.next();
            avl = avl.AVLdelete(i);
            assertFalse(avl.AVLfind(i));
        }
    }
}

