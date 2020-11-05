import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTTest {

    @Test
    public void test1 () {
        AVL<Character> avl = new EmptyAVL<>();
        avl = avl.AVLinsert('J');
        avl = avl.AVLinsert('F');
        avl = avl.AVLinsert('P');
        avl = avl.AVLinsert('D');
        avl = avl.AVLinsert('G');
        avl = avl.AVLinsert('L');
        avl = avl.AVLinsert('V');
        avl = avl.AVLinsert('C');
        avl = avl.AVLinsert('N');
        avl = avl.AVLinsert('S');
        avl = avl.AVLinsert('X');
        avl = avl.AVLinsert('Q');
        avl = avl.AVLinsert('U');

        TreePrinter.print(avl);
    }

    @Test
    public void test2 () {
        RedBlackT<Character> rb;

        rb = new RBNode<>(
                'J',
                Color.BLACK,
                new RBNode<>(
                        'F',
                        Color.BLACK,
                        new RBNode<>(
                                'D',
                                Color.BLACK,
                                new RBNode<>(
                                        'C',
                                        Color.RED,
                                        new EmptyRB<>(),
                                        new EmptyRB<>()
                                ),
                                new EmptyRB<>()
                        ),
                        new RBNode<>(
                                'G',
                                Color.BLACK,
                                new EmptyRB<>(),
                                new EmptyRB<>()
                        )
                ),
                new RBNode<>(
                        'P',
                        Color.BLACK,
                        new RBNode<>(
                                'L',
                                Color.BLACK,
                                new EmptyRB<>(),
                                new RBNode<>(
                                        'N',
                                        Color.RED,
                                        new EmptyRB<>(),
                                        new EmptyRB<>()
                                )
                        ),
                        new RBNode<>(
                                'V',
                                Color.RED,
                                new RBNode<>(
                                        'S',
                                        Color.BLACK,
                                        new RBNode<>(
                                                'Q',
                                                Color.RED,
                                                new EmptyRB<>(),
                                                new EmptyRB<>()
                                        ),
                                        new RBNode<>(
                                                'U',
                                                Color.RED,
                                                new EmptyRB<>(),
                                                new EmptyRB<>()
                                        )
                                ),
                                new RBNode<>(
                                        'X',
                                        Color.BLACK,
                                        new EmptyRB<>(),
                                        new EmptyRB<>()
                                )
                        )
                ));

        TreePrinter.print(rb);

        Optional<Integer> opth = rb.isValidBlackTree();
        int h = opth.orElseThrow();
        assertEquals(4,h);
    }

    @Test
    public void test3 () {
        AVL<Character> avl = new EmptyAVL<>();
        avl = avl.AVLinsert('J');
        avl = avl.AVLinsert('F');
        avl = avl.AVLinsert('P');
        avl = avl.AVLinsert('D');
        avl = avl.AVLinsert('G');
        avl = avl.AVLinsert('L');
        avl = avl.AVLinsert('V');
        avl = avl.AVLinsert('C');
        avl = avl.AVLinsert('N');
        avl = avl.AVLinsert('S');
        avl = avl.AVLinsert('X');
        avl = avl.AVLinsert('Q');
        avl = avl.AVLinsert('U');

        RedBlackT<Character> rb = avl.toRB();

        TreePrinter.print(rb);

        Optional<Integer> opth = rb.isValidBlackTree();
        int h = opth.orElseThrow();
        assertEquals(4,h);

    }

    @Test
    public void evenHeightCallsColorBlackEven () {
        AVL<Integer> avl = new EmptyAVL<>();
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(3);
        avl = avl.AVLinsert(8);
        avl = avl.AVLinsert(10);
        assertEquals(4, avl.AVLHeight());

        RedBlackT<Integer> rb = avl.toRB();
        assertEquals(avl.colorBlackEven(), rb);
        assertEquals(3, rb.isValidTree().get());
        assertFalse(avl.colorBlackOdd().isValidTree().isPresent()); // Calling odd would result in an invalid tree.
    }

    @Test
    public void oddHeightCallsColorBlackOdd () {
        AVL<Integer> avl = new EmptyAVL<>();
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(3);
        avl = avl.AVLinsert(8);
        avl = avl.AVLinsert(10);
        avl = avl.AVLinsert(12);
        avl = avl.AVLinsert(13);
        avl = avl.AVLinsert(14);
        avl = avl.AVLinsert(15);
        assertEquals(5, avl.AVLHeight());

        RedBlackT<Integer> rb = avl.toRB();
        TreePrinter.print(rb);
        assertEquals(avl.colorBlackOdd(), rb);
        assertEquals(3, rb.isValidTree().get());
        assertFalse(avl.colorBlackEven().isValidTree().isPresent()); // Calling even would result in an invalid tree.
    }
}