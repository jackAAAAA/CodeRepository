import java.util.Scanner;

public class RandomNumber {

    public static void main(String[] args) {
        double randNum = 0;

//        while (randNum < 0.5) {
//            randNum = Math.random();
//            System.out.println(randNum);
//        }
        Scanner in = new Scanner(System.in);
        System.out.println("你好，请输入待生成随机数范围的左端点：");
        int rangeStart = in.nextInt();
        System.out.println("已输入的整数是：" + rangeStart);
        System.out.println("请继续输入随机数范围的右端点：");
        int rangeEnd = in.nextInt();
        System.out.println("已输入的整数是：" + rangeEnd);
        
        for (int i = 0; i < 10; i++) {
            //确定模
            int mod = rangeEnd - rangeStart;

            //生成随机数
            randNum = Math.random();
            //num的值会在0到mod-1,也就是0到rangeEnd-rangeStart-1
            int num = ((int) (randNum * rangeEnd * 100)) % mod;

            //num加rangeStart之后，值域是0到rangeEnd-1
            num += rangeStart;

            if (num <= rangeStart) {
                num = rangeStart + 1;
            }
            //根据数据分析，这个条件不会满足，只是为了做最后的防护
            if (num >= rangeEnd) {
                num = rangeEnd - 1;
            }
            System.out.println("这次的随机数是：" + num);
        }
    }
}
