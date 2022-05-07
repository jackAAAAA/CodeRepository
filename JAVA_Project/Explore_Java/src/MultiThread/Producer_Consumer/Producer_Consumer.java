package MultiThread.Producer_Consumer;

import java.util.LinkedList;

public class Producer_Consumer {
    // 共享资源
    public static class Resource {
        private LinkedList<String> commonList;
        private int capacity = 0;

        public Resource(int capacity) {
            this.capacity = capacity;
            this.commonList = new LinkedList<>();
        }

        public void addLast(String item) {
            commonList.addLast(item);
        }

        public void removeFirst() {
            commonList.removeFirst();
        }

        public boolean isFull() {
            return commonList.size() == this.capacity;
        }

        public boolean isEmpty() {
            return commonList.size() == 0;
        }

        public int size() {
            return commonList.size();
        }
    }
    // 生产者
    public static class Producer extends Thread {

        private int      duration;
        private Resource resource;

//        duration: 生产者休眠的时间
//        resource: 共享资源
        public Producer (int duration, Resource resource) {
            this.duration = duration;
            this.resource = resource;
        }

        @Override
        public synchronized void run() {
//            while (true) {
                try {
                    if (resource.isFull()) {
                        wait();
                    } else {
                        resource.addLast("Producer_" + Thread.currentThread().getName());
                        System.out.println("当前的资源大小：" + resource.size());
                        notifyAll();
                    }
                    Thread.sleep(1000 * duration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            }
        }

//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    synchronized (resource) {
//                        if (!resource.isFull()) {
//                            resource.addLast("Producer_" + Thread.currentThread().getName());
//                            // notify/notifyAll() 方法，唤醒一个或多个正处于等待状态的线程
//                            System.out.println("当前的资源大小：" + resource.size());
//                            resource.notifyAll();
//                        } else {
//                            // wait()使当前线程阻塞，前提是必须先获得锁配合synchronized 关键字使用
//                            resource.wait();
//                        }
//                    }
//                    Thread.sleep(1000 * duration);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    // 消费者
    public static class Consumer extends Thread {

        private int      duration;
        private Resource resource;

//        duration: 生产者休眠的时间
//        resource: 共享资源
        public Consumer (int duration, Resource resource) {
            this.duration = duration;
            this.resource = resource;
        }

        @Override
        public synchronized void run() {
//            while (true) {
                try {
                    if (resource.isEmpty()) {
                        wait();
                    } else {
                        resource.removeFirst();
                        System.out.println("当前的资源大小：" + resource.size());
                        notifyAll();
                    }
                    Thread.sleep(1000 * duration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            }
        }

//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    synchronized (resource) {
//                        // 共享区有数据才能取数据
//                        if (resource.size() > 0) {
//                            resource.removeFirst();
//                            System.out.println("当前的资源大小：" + resource.size());
//                            // 消费数据后唤醒生产者
//                            resource.notifyAll();
//                        } else {
//                            // wait()使当前线程阻塞，前提是 必须先获得锁，配合synchronized 关键字使用
//                            resource.wait();
//                        }
//                    }
//                    Thread.sleep(1000 * duration);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    // 测试
    public static void main(String[] args) {
        Resource resource = new Resource(10);
        new Producer(3, resource).start();
//        new Producer(3, resource).start();
//        new Producer(3, resource).start();
//        new Consumer(1, resource).start();
        new Consumer(1, resource).start();
    }

}
