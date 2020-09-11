import java.util.Arrays;

public class Counter {
    private int[] array;

    Counter (int size) {
        array = new int[size];
    }

    int get (int i) {
        return array[i];
    }

    int toDecimal () {
        int result = 0;
        int power = 1;
        for (int i=0; i<array.length; i++) {
            result += array[i] * power;
            power *= 2;
        }
        return result;
    }

    void inc () {
        boolean done = false;
        int i = 0; // Start with the first index
        // This will flip bits until it reaches a 0 (the 0 is flipped to a 1)
        while (!done && i < this.array.length) {
            if (this.array[i] == 0) {
                this.array[i] = 1;
                done = true;
            }
            else {
                this.array[i] = 0;
            }
            i += 1;
        }
    }

    public String toString () {
        return Arrays.toString(array);
    }


}
