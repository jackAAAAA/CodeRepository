package Delegation;

public class test1 {
    public static void main(String[] args) {
        A a = new A();
        B b = new B(a);
        b.foo();
        b.bar();
    }
}

class A {
    void foo() {
        this.bar();
    }
    void bar() {
        System.out.println("a.bar");
    }
}

class B {
    private A a;
    public B(A a) {
        this.a = a;
    }
    void foo() {
        a.foo(); // call foo() on the a-instance }
    }
    void bar() {
        System.out.println("b.bar");
    }
}
