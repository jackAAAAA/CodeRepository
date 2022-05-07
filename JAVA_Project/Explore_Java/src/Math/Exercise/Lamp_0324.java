package Math.Exercise;

import java.util.Arrays;

public class Lamp_0324 {
    public static void main(String[] args) {
        int num = 100;
        int res_1 = func1(num);
        int res_2 = func2(num);
        System.out.println("Way1: " + res_1 + "\n"
        + "Way2: " + res_2);
    }

    /**
     * func1 & func2: 均以 1~100 来标记灯的序号
     */

    public static int func1(int num) {
        int res = 0;
        for (int i = 2; i <= num; ++i) {
            if (isPrime(i)) {
                ++res;
//                System.out.println("i1: " + i + " res: " + res);
                continue;
            }
            int cnt = 2;
            for (int j = 2; j <= (i >> 1); ++j) {
                if (i % j == 0) {
                    ++cnt;
                }
            }
            if ((cnt & 1) == 0) {
                ++res;
//                System.out.println("i2: " + i + " res: " + res);
            }
        }
        return res;
    }

    public static boolean isPrime(int num) {
        for (int i = 2; i <= Math.sqrt(num); ++i) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int func2(int num) {
        int[] arr = new int[num];
        Arrays.fill(arr, 1);
        for (int i = 1; i <= num; ++i) {
            for (int j = 1; j <= (num / i); ++j) {
                if (arr[i * j - 1] == 0) {
                    arr[i * j - 1] = 1;
                } else {
                    arr[i * j - 1] = 0;
                }
            }
        }
        int res = 0;
        for (int a : arr) {
            if (a == 1) {
                ++res;
            }
        }
        return res;
    }
}
