package Lambda_Expression;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class _01Explore {


    @Test
    public void test5() {
        Function<String, byte[]> func1 = new Function<>() {
            @Override
            public byte[] apply(String s) {
                try {
                    return _01Explore.this.sum(s).getBytes(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    return e.getMessage().getBytes();
                }
            }
        };

        _01Explore object = new _01Explore();

        Function<String, String> func2 = s -> object.sum(s);

        Function<String, String> func3 = object::sum;

        System.out.println(Arrays.toString(func1.apply("匿名内部类")));
        System.out.println(func2.apply("Lambda表达式"));
        System.out.println(func3.apply("方法引用"));
    }

    public String sum(String s) {
        return s;
    }

    @Test
    public void test4(){
//        Supplier<String> sup1 = new Supplier<String>() {
//            @Override
//            public String get() {
//                return "sup1";
//            }
//        };

        //换用lambda
        //Supplier<String> sup2 = ()-> {return "sup2";};
//        Supplier<String> sup2 = ()-> "sup2";

        Supplier<String> sup1 = new Supplier<>() {
            @Override
            public String get() {
                return "sup1";
            }
        };

        Supplier<String> sup2 = () -> "sup2";

        System.out.println(sup1.get());
        System.out.println(sup2.get());
    }

    @Test
    public void test3(){
//        Consumer<String> con1 = new Consumer<String>() {
//            @Override
//            public void accept(String o) {
//                System.out.println(o);
//            }
//        };

        //换用lambda
        //只有一个参数的时候()可以省略
        //Consumer<String> con2 = (o)->System.out.println(o);
//        Consumer<String> con2 = o->System.out.println(o);

        Consumer<String> con1 = new Consumer<>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

//        Consumer<String> con2 = s -> System.out.println(s);
        Consumer<String> con2 = System.out::println;

        con1.accept("con1");
        con2.accept("con2");
    }


    @Test
    public void test2(){
        //传统写法
        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        //换用lambda
        //Comparator<Integer> com2 = (o1, o2) -> {return o1.compareTo(o2);};
        Comparator<Integer> com2 = (o1, o2) -> o1.compareTo(o2);

        System.out.println(com1.compare(1,2));
        System.out.println(com2.compare(1,2));

//        构建数据
        Student student1 = new Student(1, "Android");
        Student student2 = new Student(2, "kotlin");
        List<Student> res = new ArrayList<>();
        res.add(student2);
        res.add(student1);

        System.out.println("未排序之前: ");

        res.forEach(System.out::println);
        System.out.println("*======================*");

//        1.用自定义类继承并实现Comparator
//        Comparator<Student> myComparator = new MyComparator1();
//        myComparator.compare(student1, student2);
//        res.sort(myComparator);

//        2.用内部类继承并实现Comparator
        Comparator<Student> myComparator1 = new Comparator<>() {
              @Override
              public int compare(Student s1, Student s2) {
                  return s1.num - s2.num;
              }
          };
        res.sort(myComparator1);

//        3.用Lambda表达式
//        Comparator<Student> myComparator1 = (s1, s2) -> {
//            return s1.num - s2.num;
//        };
//        res.sort(myComparator1);

        System.out.println("排序之后：");
        res.forEach(System.out::println);
        System.out.println("*=========================*");

        List<Integer> ans = new ArrayList<>();
        ans.add(8);
        ans.add(-2);
        ans.add(1);

        System.out.println("ans 未排序之前：");
        ans.forEach(System.out::println);
        System.out.println("*=========================*");

//        1.非Lambda
//        无法通过传参来实现 顺序《=》倒序。即此处只要传参满足数据类型即可，无论它们作为param1还是param2均可；
//        要实现 顺序《=》倒序 只能重写compare
//        Comparator<Integer> myComparator2 = new MyComparator2();
//        myComparator2.compare(ans.get(2), ans.get(1));
//        ans.sort(myComparator2);

//        2.Lambda
//        2.1顺序
//        Comparator<Integer> myComparator2 = (n1, n2) -> {
//            return n1 - n2;};
//        ans.sort(myComparator2);
//        2.2倒序
        Comparator<Integer> myComparator2 = (n2, n1) -> {
            return n1 - n2;};
        ans.sort(myComparator2);

        System.out.println("ans 排序之后：");
        ans.forEach(System.out::println);

    }

    class MyComparator1 implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.num - o2.num;
        }
    }

    class Student {
        private int num;
        private String name;

        public Student(int num, String name) {
            this.num = num;
            this.name = name;
        }

        public String toString() {
            return "num = " + this.num + "\n"
                    + "name = " + this.name;
        }
    }

    class MyComparator2 implements Comparator<Integer> {
//            自然顺序：递增
//        @Override
//        public int compare(Integer o1, Integer o2) {
//            return o1.compareTo(o2);
//        }

//        倒序：递减
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    }


    @Test
    public void test1() throws Exception{
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("传统方式的Runnable");
            }
        };
        Thread t1 = new Thread(run1);
        t1.start();
        t1.join();

        System.out.println("*****************");
        /* Runnable run2 = ()->{
            System.out.println("lambda实现的Runnable");
        }*/
        Runnable run2 = ()->System.out.println("lambda实现的Runnable");
        Thread t2 = new Thread(run2);
        t2.start();
        t2.join();

        System.out.println("******************");
        Runnable run3 = new MyRunnable();
        Thread t3 = new Thread(run3);
        t3.start();
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("通过类实现接口方式的Runnable");
        }
    }

}
