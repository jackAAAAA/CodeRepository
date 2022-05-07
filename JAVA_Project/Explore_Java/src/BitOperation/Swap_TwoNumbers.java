package BitOperation;

public class Swap_TwoNumbers {

    public static void main(String[] args) {
        int a = 3, b = 5;
//        使用变量c
//        int c = (a ^ b);
//        System.out.println("a = " + (a ^ c));
//        System.out.println("b = " + (b ^ c));
        a += b;
        b = a - b;
        a -= b;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
