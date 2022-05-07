package InnerClass;

public class Member {
//    Duplicate variance
    private int val = 0;
    class member {
//        Duplicate variances
        private int val = 1;

        public void print() {
            System.out.println(this.val + " " + Member.this.val);
        }
    }

    public static void main(String[] args) {
        Member mem = new Member();
        Member.member m = mem.new member();
        m.print();
    }
}
