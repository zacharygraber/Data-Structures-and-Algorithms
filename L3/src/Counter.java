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
        // implement inc here
    }

    public String toString () {
        return Arrays.toString(array);
    }


}
