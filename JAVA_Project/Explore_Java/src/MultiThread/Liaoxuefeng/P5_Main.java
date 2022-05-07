package MultiThread.Liaoxuefeng;

public class P5_Main {
//    未加锁
//    static class Counter {
//        public static int count = 0;
//    }
//    static class AddThread extends Thread {
//        @Override
//        public void run() {
//            for (int i = 0; i < 10000; i++) {
//                Counter.count += 1;
//            }
//        }
//    }
//    static class DecThread extends Thread {
//        @Override
//        public void run() {
//            for (int i = 0; i < 10000; i++) {
//                Counter.count -= 1;
//            }
//        }
//    }

//    正确加锁
//    static class Counter {
//        public static final Object lock = new Object();
//        public static int count = 0;
//    }
//    static class AddThread extends Thread {
//        @Override
//        public void run() {
//            for (int i = 0; i < 10000; i++) {
//                synchronized (Counter.lock) {
//                    Counter.count += 1;
//                }
//            }
//        }
//    }
//    static class DecThread extends Thread {
//        @Override
//        public void run() {
//            for (int i = 0; i < 10000; i++) {
//                synchronized (Counter.lock) {
//                    Counter.count -= 1;
//                }
//            }
//        }
//    }

//    错误加锁1
//    static class Counter {
//        public static final Object lock1 = new Object();
//        public static final Object lock2 = new Object();
//        public static int count = 0;
//    }
//    static class AddThread extends Thread {
//        @Override
//        public void run() {
//            for (int i = 0; i < 10000; i++) {
//                synchronized (Counter.lock1) {
//                    Counter.count += 1;
//                }
//            }
//        }
//    }
//    static class DecThread extends Thread {
//        @Override
//        public void run() {
//            for (int i = 0; i < 10000; i++) {
//                synchronized (Counter.lock2) {
//                    Counter.count -= 1;
//                }
//            }
//        }
//    }

//    public static void main(String[] args) throws Exception {
//        var add = new AddThread();
//        var dec = new DecThread();
//        add.start();
//        dec.start();
//        add.join();
//        dec.join();
//        System.out.println(Counter.count);
//    }

    //    错误加锁2：正确的方法是应该分别使用两把锁
    static class Counter {
        public static final Object lock = new Object();
        public static final Object lockstudent = new Object();
        public static final Object lockteacher = new Object();
        public static int studentcount = 0;
        public static int teachercount = 0;
    }
    static class AddStudentThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                synchronized (Counter.lock) {
                    Counter.studentcount += 1;
                }
            }
        }

    }
    static class DecStudentThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                synchronized (Counter.lock) {
                    Counter.studentcount -= 1;
                }
            }
        }

    }
    static class AddTeacherThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                synchronized (Counter.lock) {
                    Counter.teachercount += 1;
                }
            }
        }

    }
    static class DecTeacherThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                synchronized (Counter.lock) {
                    Counter.teachercount -= 1;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        var ts = new Thread[]{new AddStudentThread(), new
                DecStudentThread(), new AddTeacherThread(), new
                DecTeacherThread()};
        for (var t :
                ts) {
            t.start();
        }
        for (var t :
                ts) {
            t.join();
        }
        System.out.println(Counter.studentcount);
        System.out.println(Counter.teachercount);
    }
}



