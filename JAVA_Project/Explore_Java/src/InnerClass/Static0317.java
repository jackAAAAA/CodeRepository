package InnerClass;

public class Static0317 {
    private String g1 = "non-static";
    private static String g2 = "Tencent";

    public static void main(String[] args) {
        Job job = new Job();
        job.print();

        Life life = new Life();
        life.print();
    }

    static class Job {
        void print() {
            System.out.println("I will go to " + g2 + " finally.");
        }
    }

    static class Life {
        void print() {
            System.out.println("I like ya ping" + " and ya ping will like me,too.");
        }
    }
}
