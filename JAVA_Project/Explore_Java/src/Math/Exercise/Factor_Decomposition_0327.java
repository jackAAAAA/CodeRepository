package Math.Exercise;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class Factor_Decomposition_0327 {
    public static void main(String[] args) {
        int num = 100;
        var res1 = func1(num);
        var res2 = func2(num);
        System.out.println("Ways1: res1 " + res1.getValue() + "；" + "number of prime: " + res1.getKey().size());
        System.out.println("Ways1: res2 " + res2.getKey() + "；" + "number of prime: " + res2.getKey().size());
    }

    private static Pair<Set<Integer>, String> func1(int num) {
        int i = 2;
        Set<Integer> set = new HashSet<Integer>();
        var sb = new StringBuilder(num + " = ");
        while (i <= num) {
            if (num % i == 0) {
                sb.append(i).append(" * ");
                num /= i;
                set.add(i);
                i = 2;
            } else {
                ++i;
            }
        }
        var res = new Pair<>(set, sb.substring(0, sb.length() - 3));
        return res;
    }

    private static Pair<Set<Integer>, String> func2(int num) {
        Set<Integer> set = new HashSet<>();
        var sb = new StringBuilder(num + " = ");
        int temp = num;
        for (int i = 2; i <= Math.sqrt(temp); ++i) {
            while (num % i == 0) {
                sb.append(i).append(" * ");
                num /= i;
                set.add(i);
            }
        }
        var res = new Pair<>(set, sb.substring(0, sb.length() - 3));
        return res;
    }
}
