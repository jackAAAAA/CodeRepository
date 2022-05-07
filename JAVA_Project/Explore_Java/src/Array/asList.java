package Array;

import java.util.Arrays;
import java.util.List;

public class asList {
    public static void main(String[] args) {
        List<Integer> res = Arrays.asList(2, 3, 4);
        System.out.println(res);
        res.forEach(System.out::println);
    }
}
