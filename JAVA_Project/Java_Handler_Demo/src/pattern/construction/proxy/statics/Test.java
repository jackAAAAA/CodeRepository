package pattern.construction.proxy.statics;

import handler.Handler;
import handler.Looper;
import handler.Message;

/**
 * 项目名:    Demo
 * 包名       pattern.construction.proxy.statics
 * 文件名:    Test
 * 创建时间:  2018/1/8 on 16:38
 * 描述:     TODO 静态代理测试类
 *
 * @author zjb
 */
public class Test {
    /**
     * 代理模式：为其他对象提供一种代理以便控制对这个对象的访问。
     * <p>
     * 可以详细控制访问某个类（对象）的方法，在调用这个方法前作的前置处理（统一的流程代码放到代理中处理）。调用这个方法后做后置处理。
     */
    /**
     * 抽象角色：指代理角色（经纪人）和真实角色（明星）对外提供的公共方法，一般为一个接口
     * <p>
     * 真实角色：需要实现抽象角色接口，定义了真实角色所要实现的业务逻辑，以便供代理角色调用。也就是真正的业务逻辑在此。
     * <p>
     * 代理角色：需要实现抽象角色接口，是真实角色的代理，通过真实角色的业务逻辑方法来实现抽象方法，并可以附加自己的操作。
     * <p>
     * 将统一的流程控制都放到代理角色中处理！
     *
     * @param args
     */
    public static void main(String[] args) {
        //真实角色
        RealStar realStar = new RealStar();
        //代理角色
        Star star = new ProxyStar(realStar);
        star.collectMoney();
        star.signContract();
        star.sing();
    }
}

//public class Test {
//    public static Handler handler;
//
//    public static void main(String[] args) throws InterruptedException {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                handler = new Handler() {
//                    @Override
//                    public void handleMessage(Message message) {
//                        super.handleMessage(message);
//                        System.out.println("Test：" + Thread.currentThread().getName() + "线程接收到：" + message.obj);
//                    }
//                };
//                Looper.loop();
//            }
//        }).start();
//        //睡0.5s，保证上面的线程中looper初始化好了
//        Thread.sleep(500);
//        new Thread(() -> {
//            Message message = new Message();
//            message.obj = Thread.currentThread().getName() + "发送的消息 ";
//            handler.sendMessage(message);
//        }).start();
//        new Thread(() -> {
//            Message message = new Message();
//            message.obj = Thread.currentThread().getName() + "发送的消息 ";
//            handler.sendMessage(message);
//        }).start();
//    }
//}

