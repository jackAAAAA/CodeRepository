package Math.Exercise;

public class Lamp_Factor_Decomposition {
    public static void main(String[] args) {
        int res = 0;
//        序号为1的灯仅被操作一次，因此最后的状态一定是关着的
        for (int num = 2; num <= 100; ++num) {
//            如果是质数，由于仅被操作两次，因此最后的状态一定是开着的
            if (isPrimer(num)) {
                ++res;
                System.out.println(2 + " " + num);
                continue;
            }
//            每个整数必然有两个因数：1 和 它本身
            int count = 2;
            for (int i = 2; i <= (num >> 1); i++) {
                if (num % i == 0) {
                    count++;
                }
            }
            if ((count & 1) == 0) {
                ++res;
                System.out.println(count + " " + num);
            }
        }
        System.out.println(res);
    }

    public static boolean isPrimer(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

}
