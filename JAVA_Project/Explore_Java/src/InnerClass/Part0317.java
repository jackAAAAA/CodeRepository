package InnerClass;

public class Part0317 {
    public static void main(String[] args) {
        Man man = new Man();
        man.printSex();

//      局部内部类Woman在此处无法被访问，其的作用域仅存在于局部方法中，即getWoman()
//        Woman woman = man.getWoman();
        People woman = man.getWoman();
        woman.printSex();
    }

//    public static void main(String[] args) {
//        int size = 0;
//        new Thread() {
//            @Override
//            public void run() {
//                System.out.println(size);
//            }
//        }.start();
//    }

//    验证方法有误：经过验证在Java中局部内部类和匿名内部类都可以访问非final变量——直接读取不会报错，要修改才会报错
//    public static void main(String[] args) {
//        int b = 20;
//        new Part0317().test(++b);
//    }
//
//    void test(int b) {
//        final int a = 10;
//        var thread = new Thread() {
//            @Override
//            public void run() {
//                System.out.println(a);
//                System.out.println(b);
//            }
//        };
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}

class People {
    String sex = "unknown";
    void printSex() {
        System.out.println(this.sex);
    }
}

class Man extends People {
    Man() {
        this.sex = "male";
    }

//    String temp = "temp";
//    final String temp1 = "temp1";

    People getWoman() {
        String temp = "temp";
        final String temp1 = "temp1";
        class Woman extends People {
            Woman() {
//                temp = "111";
                this.sex = "female";
//                this.sex = temp;
            }
        }
        return new Woman();
    }
}
