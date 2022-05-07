package Array;

public class LeetCode_363 {

    public static void main(String[] args) {
        int m = 1, n = 3;
        getRecNum(m, n);
        System.out.println("Ways_2: The rectangle number of " + m + " X " + n + " Matrix is "
                + getRect(m + 1, n + 1));
    }

    /**
     * 方式1：获取矩阵中矩形的个数的通用函数
     * @param m >= 1
     * @param n >= 1
     */
    public static void getRecNum(int m, int n) {
        int rec_cnt = 0;
        for (int j = 0; j < n; ++j) {
            for (int i = j; i < n; ++i) {
                for (int r = 0; r < m; ++r) {
                    for (int a = r; a < m; ++a) {
                        ++rec_cnt;
                    }
                }
            }
        }
        System.out.println("Ways_1: The rectangle number of " + m + " X " + n + " Matrix is " + rec_cnt);
    }

    /**
     * 方式2：方便人理解的获取矩阵中矩形个数的函数
     * @param m > 1
     * @param n > 1
     * 矩形由四个顶点而确定 => 横向选两点 & 纵向选两点 => 一个矩形
     * 横向选法：m行 => (m + 1)个点选两点，不考虑顺序
     * 纵向选法：n列 => (n + 1)个点选两点，不考虑顺序
     * m X n 矩阵所能确定的矩形个数：C(m + 1, 2) * C(n + 1, 2)
     * @return
     */
    public static int getRect(int m, int n)
    {
        int result = 0;
        if (m > 1 && n > 1)
        {
            result = m * (m - 1) * n * (n - 1) / 4;
        }
        return result;
    }

}
