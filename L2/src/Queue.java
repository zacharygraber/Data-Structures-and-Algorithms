
class EmptyQueueE extends Exception {}

public class Queue {
    private Stack s1, s2;
    // s1 is back of queue
    // s2 is front of queue
    // insert to s1
    // remove from s2

    Queue () {
        s1 = new EmptyS();
        s2 = new EmptyS();
    }

    void enqueue (int i) {
        this.s1 = this.s1.push(i);
    }

    int dequeue () throws EmptyQueueE {
        int res;

        // The case where s2 already has elements in it
        try {
            res = this.s2.top(); // Since the top of s2 will be lost when we pop(), we must save it.
            this.s2 = this.s2.pop();
            return res;
        }

        // The case where s2 is empty
        catch (EmptyStackE e) {
            try {
                // Push all elements from s1 onto s2, thereby reversing order again...
                this.copyStacks();

                // Then pop() s2
                res = this.s2.top();
                this.s2 = this.s2.pop();
                return res;
            }

            // If both are empty, there is nothing to dequeue.
            catch (EmptyStackE e2) {
                throw new EmptyQueueE();
            }
        }
    }

    void copyStacks () {
        try {
            int i = s1.top();
            s1 = s1.pop();
            s2 = s2.push(i);
            copyStacks();
        }
        catch (EmptyStackE e) {
            return;
        }
    }

    public String toString () {
        return s1.toString() + " | " + s2.toString();
    }


}
