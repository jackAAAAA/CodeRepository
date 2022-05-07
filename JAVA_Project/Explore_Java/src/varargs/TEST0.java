package varargs;

import java.lang.reflect.Method;

public class TEST0 {
    public void methodA(String str, Integer... is) {

    }

    public void methodA(String str, String... strs) {
    }

    public static void main(String[] args) {
//        TEST0 client = new TEST0();
//        client.methodA("China", 0);
//        client.methodA("China", "People");
////        client.methodA("China");  //compile error
////        client.methodA("China", null);  //compile error
////        String[] strs = null;
//        String str = null;
//        client.methodA("China",  str);
//        Parent parent = new Child();
//        parent.print("Hello");
//
//        Child child = new Child();
//        child.print("Hello");

//        foo(new String[]{"arg1", "arg2", "arg3"}); //3
//        foo(100, new String[]{"arg1", "arg1"}); //2
//
//        foo(new Integer[]{1, 2, 3}); //3
//        foo(100, new Integer[]{1, 2, 3}); //2
//        foo(1, 2, 3); //3
//        foo(new int[]{1, 2, 3}); //1

        String[] varargs = {"arg1", "arg2"};
        try {
//            Method method = TEST0.class.getMethod("foo", String[].class);
            Method method = TEST0.class.getDeclaredMethod("foo", String[].class);
            method.setAccessible(true);
            method.invoke(null, (Object) varargs);
            method.invoke(null, new Object[]{varargs});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void foo(String... args) {
        System.out.println(args.length);
    }
}

class Parent {
    public void print(String... args) {
        System.out.println("Parent......test");
    }
}

class Child extends Parent {
    @Override
    public void print(String[] args) {
        System.out.println("Child......test");
    }
}
