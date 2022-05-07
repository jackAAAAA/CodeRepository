package Others;

import java.util.*;

public class Daffodil {
    /**
     * 返回不超过1000的水仙花数
     *
     * @param Ceiling
     * @return
     */
//    public List<Integer> calculate(int Ceiling) {
//        List<Integer> res = new ArrayList<>();
//        int[] temp = new int[3];
//        for (int i = 100; i < Ceiling; ++i) {
//            temp[0] = i % 10;
//            temp[1] = (i / 10) % 10;
//            temp[2] = i / 100;
//            int cur = 0;
//            for (int j = 0; j < 3; ++j) {
//                cur += Math.pow(temp[j], 3);
//            }
//            if (i == cur) {
//                res.add(i);
//            }
//        }
//        return res;
//    }
    public List<Integer> calc_1004(int Ceiling) {
        List<Integer> res = new ArrayList<>();
        int[] temp = new int[3];
        for (int i = 100; i <= Ceiling; ++i) {
            temp[0] = i % 10;
            temp[1] = (i / 10) % 10;
            temp[2] = i / 100;
            int cur = 0;
            for (int t : temp) {
                cur += Math.pow(t, 3);
            }
            if (i != 1000 && i == cur) {
                res.add(i);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Daffodil daffodil = new Daffodil();
        int limit = 371;
        List<Integer> res = daffodil.calc_1004(limit);
        System.out.println(limit + "以内的水仙花数是：");
        res.forEach(it -> System.out.print(it + " "));
    }



    public List<Integer> calc(int Ceiling) {
        List<Integer> res = new ArrayList<>();
        int[] temp = new int[3];
        for (int i = 100; i <= Ceiling; i++) {
            temp[0] = i % 10;
            temp[1] = (i / 10) % 10;
            temp[2] = i / 100;
            int cur = 0;
            for (int j = 0; j < 3; j++) {
                cur += Math.pow(temp[j], 3);
            }
            if (i != 1000 && i == cur) {
                res.add(i);
            }
        }
        return res;
    }

//    public static void main(String[] args) {
//        Daffodil daffodil = new Daffodil();
//        int limit = 380;
//        List<Integer> res = daffodil.calc(limit);
//        System.out.println(limit + "以内的水仙花数是：");
//        res.forEach(it -> System.out.print(it + " "));
//    }
}
