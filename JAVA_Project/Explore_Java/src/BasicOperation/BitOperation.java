package BasicOperation;

public class BitOperation {
    public static void main(String[] args) {
        //Specified Operation
        System.out.println("以下是\"指定位置的位运算\"的操作示例");
        int x = 3;
        System.out.println("The initial value of x at present is: " + x);
        boolean flag1 = (x & 1) == 1, flag2 = (x & 1) == 0;
        System.out.println("x % 2 == 1 / (x & 1) == 1: " + flag1 + " | x % 2 == 0 / (x & 1) == 0: " + flag2);
        System.out.println();

        x = 4;
        System.out.println("The initial value of x at present is: " + x);
        System.out.println("x / 2: " + (x >> 1) + " | x * 2: " + (x << 1));
        System.out.println();

        x = 7;
        System.out.println("清零最低位的1");
        System.out.println("The initial value of x at present is: " + x);
        x &= (x - 1);
        System.out.println("x & (x - 1): " + x);
        System.out.println();

        x = 8;
        System.out.println("得到最低位的1");
        System.out.println("The initial value of x at present is: " + x);
        x &= -x;
        System.out.println("x & -x: " + x);
        System.out.println();

        x = 8;
        System.out.println("置为0（目前为止，有两种方法：1.x ^ x；2.x & ~x）");
        System.out.println("The initial value of x at present is: " + x);
        x &= ~x;
        System.out.println("x & ~x: " + x);
        System.out.println();

        x = 15;
        System.out.println("将15的第2（n）位至第0位（含）清零");
        System.out.println("The initial value of x at present is: " + x);
//        x &= (-(1 << (2 + 1))); //两种写法都行
        x &= (~((1 << (2 + 1)) - 1));
        System.out.println("x & (~((1 << (n + 1)) - 1)): " + x);
        System.out.println();

        x = 15;
        System.out.println("将15的最高位（即第3位）至第2位（含）清零");
        System.out.println("The initial value of x at present is: " + x);
        x &= ((1 << 2) - 1);
        System.out.println("x & ((1 << 2) - 1): " + x);
        System.out.println();

        x = 7;
        System.out.println("仅将7的第2位置为0");
        System.out.println("The initial value of x at present is: " + x);
        x &= (~(1 << 2));
        System.out.println("x & (~(1 << 2): " + x);
        System.out.println();

        x = 7;
        System.out.println("仅将7的第3位置为1");
        System.out.println("The initial value of x at present is: " + x);
        x |= (1 << 3);
        System.out.println("x | (1 << 3): " + x);
        System.out.println();

        x = 7;
        System.out.println("获取7的第2位的幂值（7的第3位幂值为0）");
        System.out.println("The initial value of x at present is: " + x);
//        x &= (1 << 3); //The result is —— x & (1 << 3): 0
        x &= (1 << 2); //The result is —— x & (1 << 2): 4
        System.out.println("x & (1 << 2): " + x);
        System.out.println();

        x = 7;
        System.out.println("获取7的第2位值");
        System.out.println("The initial value of x at present is: " + x);
        x = (x >> 2) & 1;
        System.out.println("(x >> 2) & 1: " + x);
        x = 7;
        System.out.println("获取7的第3位值");
        System.out.println("The initial value of x at present is: " + x);
        x = (x >> 3) & 1;
        System.out.println("(x >> 3) & 1: " + x);
        System.out.println();

        x = 7;
        System.out.println("将7最右边的3位清零");
        System.out.println("The initial value of x at present is: " + x);
        x &= (~0 << 3);
        System.out.println("x & (~0 << 3): " + x);
        System.out.println();

        //“异或”操作的示例
        System.out.println("以下是\"异或\"操作的示例：");
        System.out.println();
        System.out.println("这段程序展示的是：\"异或\"的结合律不改变最后的值！！！");
         x = 3;
        System.out.println("The initial value of x at present is: " + x);
         int y = 4, z = 5;
        System.out.println("The initial value of y and z at present are respectively: " + y + " " + z);
         int xyz1 = x ^ y ^ z;
         int xyz2 = x ^ (y ^ z);
         int xyz3 = (x ^ y) ^ z;
        System.out.println("x ^ y ^ z: " + xyz1 + "；x ^ (y ^ z): " + xyz2 + "；(x ^ y) ^ z: " + xyz3);
        System.out.println();

        x = 1;
        y = 2;
        System.out.println("这段程序的功能是：利用\"异或\"快速交换两个整数的值！！！熟记于心！！！");
        System.out.println("The initial value of x and y at present are respectively: " + x + " " + y);
        int swap = x ^ y;
        x ^= swap; y ^= swap;
        System.out.println("After swap:");
        System.out.println("x = " + x + " | "+ "y = " + y + " | " + "The swap number is: " + swap);
        System.out.println();

        x = 1;
        System.out.println("置为0（目前为止，有两种方法：1.x ^ x；2.x & ~x）");
        System.out.println("The initial value of x at present is: " + x);
        x ^= x;
        System.out.println("x ^ x: " + x);
        System.out.println();

        x = 1;
        System.out.println("The initial value of x at present is: " + x);
        x ^= (~x);
        System.out.println("x ^ ~x: " + x);
        System.out.println();

        x = 1;
        System.out.println("The initial value of x at present is: " + x);
        x ^= ~0;
        System.out.println("x ^ ~0: " + x);
        System.out.println();

        x = 1;
        System.out.println("The initial value of x at present is: " + x);
        x ^= 0;
        System.out.println("x ^ 0: " + x);
        System.out.println();

        System.out.println("~0: " + ~0);
    }
}
