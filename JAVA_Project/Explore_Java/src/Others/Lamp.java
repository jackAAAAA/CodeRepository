package Others;

import java.util.ArrayList;
import java.util.List;

public class Lamp {
    public static void main(String[] args) {
        int count = 0;
        int num = 69;
        List<Integer> dd = new ArrayList<Integer>();
//	默认所有的灯开着
        for (int i = 0; i < num; i++) {
            dd.add(1);
        }
//		操作所有的灯
//        状态重置
        for (int k = 1; k <= num; k++) {
            //对k的倍数的灯进行操作如果是关设置成开 如果是开设置成关
            for (int t = 1; t <= num / k; t++) {
                if (dd.get((k * t) - 1) == 0) {
                    dd.set((k * t) - 1, 1);
                } else {
                    dd.set((k * t) - 1, 0);
                }
            }
        }
//	计算灭灯的灯的个数
        for (int i = 0; i < num; i++) {
            if (dd.get(i) == 0) {
                count++;
                System.out.println("剩余灭的灯的索引：" + i);
            }
        }
        System.out.println("剩余灭的灯的个数：" + count);
        System.out.println("剩余亮的灯的个数：" + (num - count));
    }
}
