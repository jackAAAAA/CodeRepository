package JavaAPI;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MaxorMin_in_an_array {
    public static void main(String[] args) {
//        int[] array = new int[] { 20, 98, 12, 7, 35 };
        int[] array = IntStream.range(0, 5).toArray();
        int min = Arrays.stream(array).min().getAsInt();
        int max = Arrays.stream(array).max().getAsInt();
        System.out.print("The min of the array is " + min +"；The max of the array is " + max);
    }
}

