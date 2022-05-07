package Math;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PrimeFactor_decomposition {
    public static void main(String[] args) {
//        System.out.println(resolvePrime(100).values());
//        System.out.println(resolvePrime(100).keySet());
        System.out.println(resolvePrime(100).getKey().size());
        System.out.println(resolvePrime(100));
    }

    /**
     * 分解质因数
     *
     * @param num 待分解的数字
     * @return 分解后的数字结果
     */
//    public static Map<Set<Integer>, String> resolvePrime(int num) {
    public static Pair<Set<Integer>, String> resolvePrime(int num) {

        // 定义结果字符串缓存对象，用来保存结果字符
        StringBuilder sb = new StringBuilder(num + "=");

        // 定义最小素数
        int i = 2;

//        int count = 0;
        Set<Integer> set = new HashSet<>();

        // 进行辗转相除法
//        while (i <= num) {
//
//            // 若num 能整除 i ，则i 是num 的一个因数
//            if (num % i == 0) {
//
//                // 将i 保存进sb 且 后面接上 *
//                sb.append(i).append("*");
//
//                ++count;
//
//                // 同时将 num除以i 的值赋给 num
//                num = num / i;
//
//                // 将i重新置为2
//                i = 2;
//            } else {
//
//                // 若无法整除，则i 自增
//                i++;
//            }
//        }

        int temp = num;
        for (; i < Math.sqrt(temp); ++i) {
            while (num % i == 0) {
                sb.append(i).append("*");
                num /= i;
//                ++count;
                set.add(i);
            }
            if (num == 1) {
                break;
            }
        }

        Map<Set<Integer>, String> res = new HashMap<>();
        Pair<Set<Integer>, String> res1 = new Pair<>(set, sb.substring(0, sb.length() - 1));

        // 去除字符串缓存对象最后的一个*，并将结果写回Map
//        res.put(set, sb.substring(0, sb.toString().length() - 1));

        return res1;
    }

//    检验代码

//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        StringBuilder result = new StringBuilder();
//        while (sc.hasNext()) {
//            int num = sc.nextInt();
//            int count = 0;
//            result.append(num + " " + "=");
//            int j = num;
//            for (int i = 2;i<=Math.sqrt(j);i++) {
//                while (num % i == 0) {
//                    result.append(" "+i+" "+"*");
//                    num = num/i;
//                    count++;
//                }
//                if (num == 1) break;
//            }
//            if (num != 1) result.append(" "+ num);
//            if (result.charAt(result.length()-1) == '*') {
//                result.deleteCharAt(result.length()-1);
//                result.deleteCharAt(result.length()-1);
//            }
//            System.out.println(result);
//            result.setLength(0);
//        }
//    }
}
