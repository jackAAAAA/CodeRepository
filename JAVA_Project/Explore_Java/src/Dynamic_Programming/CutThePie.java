package Dynamic_Programming;

import java.util.Scanner;

/**
 * @description: 切大饼的最多切法
 *      一张大饼切n刀，最多可以切多少块？
 * @author: jack
 * @create: 2021/12/27
 **/
public class CutThePie {
    private static int[] memo;
    public static void main(String[] args) {
//        System.out.println("请输入需要被切的最多刀数：");
//        Scanner scanner = new Scanner(System.in);
//        int count = scanner.nextInt();
//        System.out.println("需要被切的最多刀数：" + count);
        System.out.println("请输入需要被切的最多刀数：");
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        System.out.println("需要被切的最多刀数：" + count);
        memo = new int[count + 1];
        int current = 1;
        for (int i = 0; i < count + 1; i++) {
//            O(n) O(n)
            int total = cutPie(i);
            print("方式一", i, total);

//            O(1) O(1)
            int total2 = cutPie2(i);
            print("方式二", i, total2);

//            O(n) O(1)
            current = cutPie(current, i);
            print("方式三", i, current);
        }
    }

    /**
     * @Description: 打印
     * @param name
     * @param n
     * @param total
     * @Date: 2019/6/14
     * @return: void
     */
    private static void print(String name, int n, int total) {
        System.out.println(name + "切 " + n + " 刀最多得到 " + total + " 块饼");
    }

    /**
     * @param knife 切得刀数量
     * @Description: 方式一——基于记忆化搜素的递归
     * @Date: 2021/12/27
     * @return: int 当前切完的大饼块数量
     */
    private static int cutPie(int knife) {
        if (memo[knife] > 0) {
            return memo[knife];
        }
        if (knife == 0) {
            return 1;
        }
        return memo[knife] = cutPie(knife - 1) + knife;
    }

    /**
     * @param knife
     * @return int 当前切完的大饼块数量
     * @Description: 方式二——等差求和公式
     * @Date: 2021/12/27
     */
    private static int cutPie2(int knife) {
        return (((knife + 1) * knife) >> 1) + 1;
    }

    /**
     * @param before 切之前得大饼块数
     * @param knife  切的第第几刀
     * @Description: 方式三——动态规划DP
     * @Date: 2021/12/27
     * @return: int 当前切完的大饼块数量
     */
    private static int cutPie(int before, int knife) {
        return before + knife;
    }

}