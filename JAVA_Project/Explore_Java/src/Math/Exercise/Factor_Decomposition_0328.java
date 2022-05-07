package Math.Exercise;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class Factor_Decomposition_0328 {
    public static void main(String[] args) {
        int num = 100;
        var res1 = func1(num);
        var res2 = func2(num);
        System.out.println("Ways1: Key = " + res1.getKey() + "\nKey Size: " + res1.getKey().size());
//        字符串100的长度是3，而不是1
        System.out.println("Ways2: Value = " + res2.getValue() + "\nValue Length: " + res2.getValue().length());
    }

    private static Pair<Set<Integer>, String> func1(int num) {
        Set<Integer> set = new HashSet<>();
        var sb = new StringBuilder(num + " = ");
        int i = 2;
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
                set.add(i);
                num /= i;
            }
            if (num == 1) {
                break;
            }
        }
        var res = new Pair<>(set, sb.substring(0, sb.length() - 3));
        return res;
    }
}
