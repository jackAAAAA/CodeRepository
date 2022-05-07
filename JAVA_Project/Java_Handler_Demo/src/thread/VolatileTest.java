package thread;

/*
 * 项目名:    Demo
 * 包名       thread
 * 文件名:    VolatileTest
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:19
 * 描述:     TODO
 */
public class VolatileTest {
   volatile int a = 0;

    public void addNum() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a++;
    }

    public static void main(String[] args) {
        final VolatileTest volatileDemo = new VolatileTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    volatileDemo.addNum();
                    System.out.println("num=" + volatileDemo.a);
                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    volatileDemo.addNum();
                    System.out.println("num=" + volatileDemo.a);
                }

            }
        }).start();
    }
}
