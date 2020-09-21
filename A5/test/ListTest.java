import org.junit.jupiter.api.Test;

class ListTest {
    @Test
    void test () {
        List xs = new NodeL(1,
                new NodeL(2,
                        new NodeL(3,
                                new NodeL(4,
                                        new NodeL(5, new EmptyL())))));

        System.out.println(xs);
        System.out.println(R.rev1(xs));
        System.out.println(R.rev2(xs));
    }
}