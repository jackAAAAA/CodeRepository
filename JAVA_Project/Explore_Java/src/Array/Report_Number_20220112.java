package Array;

import java.util.Scanner;

/**
 * 此函数的功能：总共有n个人以1-n来编号，并且围城一个圆圈，报数到m的人相继退出圆圈，求剩下的最后一个人的编号。
 */

public class Report_Number_20220112 {
    public static void main(String[] args) {
        System.out.println("请输入圆圈的人数：");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n + 1];
        int num = 0, m = 3, dead = 0;
        for (int i = 1; i <= n; ++i) {
            arr[i] = i;
        }
        for (int i = 1; ; ++i) {
            if (i > n) {
                i %= n;
            }
            if (arr[i] > 0) {
                ++num;
            }
            if (num == m) {
                if (dead != n - 1) {
                    num = 0;
                    arr[i] = 0;
                    ++dead;
                } else {
                    System.out.println("最后一个人的编号是：" + "\n" + arr[i] + "\n" + i);
                    break;
                }
            }
        }
    }
}
