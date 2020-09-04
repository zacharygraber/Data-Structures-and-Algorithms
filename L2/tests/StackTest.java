import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    void test1 () throws EmptyStackE {
        Stack s = new EmptyS();
        System.out.printf("s = %s%n", s);

        System.out.println("---");

        Stack s1 = s.push(1).push(2).push(3).push(4);
        System.out.printf("s = %s%n", s);
        System.out.printf("s1 = %s%n", s1);

        System.out.println("---");

        Stack s2 = s1.push(5).push(6).push(7);
        System.out.printf("s = %s%n", s);
        System.out.printf("s1 = %s%n", s1);
        System.out.printf("s2 = %s%n", s2);

        System.out.println("---");
        Stack s3 = s2.pop().pop().pop().pop();
        System.out.printf("s = %s%n", s);
        System.out.printf("s1 = %s%n", s1);
        System.out.printf("s2 = %s%n", s2);
        System.out.printf("s3 = %s%n", s3);
    }

}