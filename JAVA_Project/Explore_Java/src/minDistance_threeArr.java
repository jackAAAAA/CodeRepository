import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class minDistance_threeArr {


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("请先后输入数组a，b，c的长度：");
        int n1 = Integer.parseInt(br.readLine());
        int n2 = Integer.parseInt(br.readLine());
        int n3 = Integer.parseInt(br.readLine());

        int[] a = new int[n1];
        int[] b = new int[n2];
        int[] c = new int[n3];

        System.out.println("请按照由小到大的顺序输入数组a的元素：");
        String[] in = br.readLine().split(",");
        for (int i = 0; i < n1; i++) {
            a[i] = Integer.parseInt(in[i]);
        }
        System.out.println("a输入完毕");

        System.out.println("请按照由小到大的顺序输入数组b的元素：");
        in = br.readLine().split(",");
        for (int i = 0; i < n2; i++) {
            b[i] = Integer.parseInt(in[i]);
        }
        System.out.println("b输入完毕");

        System.out.println("请按照由小到大的顺序输入数组c的元素：");
        in = br.readLine().split(",");
        for (int i = 0; i < n3; i++) {
            c[i] = Integer.parseInt(in[i]);
        }
        System.out.println("c输入完毕");

        minDistance(a, b, c);

    }

    public static void minDistance(int[] a, int[] b, int[] c) {

        //Initial
        int minDis = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        int k = 0;

        int al = 0;
        int bl = 0;
        int cl = 0;

        while (i < a.length && j < b.length && k < c.length) {

            //curDis
            int dij = Math.abs(a[i] - b[j]);
            int djk = Math.abs(b[j] - c[k]);
            int dki = Math.abs(c[k] - a[i]);
            int curDis = dij + djk + dki;

            //Update
            if (curDis < minDis) {
                minDis = curDis;
            }

            //Select
            int min = min(a[i], b[j], c[k]);
            if (min == a[i]) {
                ++i;
                if (i == a.length) {
                    al = a[i-1];
                }else {
                    al = a[i];
                }
            } else if (min == b[j]) {
                ++j;
                if (j == b.length) {
                    bl = b[j-1];
                }else {
                    bl = b[j];
                }
            } else {
                ++k;
                if (k == c.length) {
                    cl = a[k-1];
                }else {
                    cl = c[k];
                }
            }
        }
        System.out.println("三元组的最小距离为：" + minDis + " a[i] = " + al + " b[j] = " + bl + " c[k] = " + cl);
    }

    private static int min(int a, int b, int c) {
        int min = a < b ? a : b;
        min = min < c ? min : c;
        return min;
    }

}
