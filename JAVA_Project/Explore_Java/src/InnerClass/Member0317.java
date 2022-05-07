package InnerClass;

public class Member0317 {
    public static void main(String[] args) {
        Circle circle = new Circle();

//        获取内部类实例的第一种方法：
//        Circle.Draw draw = circle.new Draw();

//        获取内部类实例的第二种方法：
        Circle.Draw draw = circle.getDraw();
        draw.print();

        Circle.Draw1 draw1 = circle.new Draw1();
        draw1.print1();
    }
}

class Circle {
    private int radius = 1;

    private void print() {
        System.out.println("Outer_Circle: " + this.radius);
    }

    Draw draw;
    Draw getDraw() {
        if (draw == null) {
            draw = new Draw();
        }
        return draw;
    }

    /**
     * 情况一：当内部类的变量、方法分别与外部类的相同时
     */
    class Draw {
        private int radius = Circle.this.radius;
        void print() {
            Circle.this.print();
            System.out.println(++this.radius);
        }
    }

    /**
     * 情况二：当内部类的变量、方法与外部类的不同时
     */
    class Draw1 {
        private int ra = ++radius;
        void print1() {
            print();
            System.out.println(ra);
        }
    }

}
