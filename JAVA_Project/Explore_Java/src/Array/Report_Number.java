package Array;

import java.util.Scanner;

/**
 * 此函数的功能：总共有n个人以1-n来编号，并且围城一个圆圈，报数到m的人相继退出圆圈，求剩下的最后一个人的编号。
 */

public class Report_Number {
    public static void main(String arg[]) {
        System.out.println("请输入人数n：");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(), m = 3;

        int arr[] = new int[n + 1];//报数从1开始所以数组大小为n+1
        int i;
        int dead = 0;   //表示已经死了多少人
        int num = 0;    //num模拟报数

        //开始时每个人都可以报数，为了能得到最后一个人的编号，我们让初始值为i下标
        for (i = 1; i <= n; i++) {
            arr[i] = i;
        }

        for (i = 1; ; i++) {
            if (i > n) {
                //如果大于总人数，我们就从头开始
                i = i % n;
            }
            if (arr[i] > 0) {
                //如果当前这个人没有死，就报数
                num++;
            }
            if (m == num && dead != n - 1) {
                //如果当前这个人报的数等于m 并且没有已经死亡n-1个人
                num = 0;
                arr[i] = 0;
                dead++;
            } else if (m == num && dead == n - 1) {
                //如果这个人报数等于m，并且已经死了n-1个人，说明当前这个人就是最后的一个活着的了。
                System.out.print(arr[i] + "");
                break;
            }
        }
    }
}
