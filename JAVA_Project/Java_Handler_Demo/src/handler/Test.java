package handler;

/*
 * 项目名:    Demo
 * 包名       Looper
 * 文件名:    Test
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:10
 * 描述:     TODO
 */
public class Test {
    public static Handler handler;

    public static void main(String[] args) throws InterruptedException {
//        Looper.prepare();
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message message) {
//                super.handleMessage(message);
//                System.out.println("Test：" + Thread.currentThread().getName() + "线程接收到：" + message.obj);
//            }
//        };
//        Looper.loop();
//        //睡0.5s，保证上面的线程中looper初始化好了
//        Thread.sleep(500);
//        new Thread(() -> {
//            Message message = new Message();
//            message.obj = Thread.currentThread().getName() + "发送的消息 ";
//            handler.sendMessage(message);
//        }).start();
        new Thread(() -> {
            for (; ; ) {

            }
        }).start();

    }
}
