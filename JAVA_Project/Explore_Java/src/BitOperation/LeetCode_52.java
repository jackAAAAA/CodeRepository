package BitOperation;

public class LeetCode_52 {

    public static void main(String[] args) {
        System.out.println("4皇后的解法：" + totalNQueens(4));
    }

    private static int size;
    private static int count;
    private static void solve(int row, int ld, int rd, int n) {
        if (row == size){
            count++;
            return;
        }
        int pos = size & (~(row | ld| rd));
        while (pos != 0) {
            int p = pos & (-pos);
            System.out.println("Current p：" + p);
            pos -= p; //pos &= pos一1;
            solve(row | p, (ld | p) << 1, (rd | p) >> 1, n);
        }
    }

    public static int totalNQueens(int n) {
        count = 0;
        size = (1 << n) - 1;
        solve(0, 0, 0, n) ;
        return count ;
    }
}
