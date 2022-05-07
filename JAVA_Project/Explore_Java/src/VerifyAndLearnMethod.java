import Interview.BM_test;

public  class VerifyAndLearnMethod {

    public static void main(String[] args) {
//        parseInt()
//        System.out.println(Integer.parseInt("10", 16));
//        Integer.valueOf()
//        System.out.println(Integer.valueOf("3332"));
//        test: call other methods in other packages
//        BM_test bm_test = new BM_test();
//        String res = bm_test.multiply("34", "2");
//        System.out.println(res);
//        test: member class in InnerClass
        VerifyAndLearnMethod Ver = new VerifyAndLearnMethod();
        VerifyAndLearnMethod.member mem = Ver.new member();
        mem.print();
    }

    private int val = 0;

    class member{
        private int val = 1;
        public void print() {
            System.out.println(this.val + " " + VerifyAndLearnMethod.this.val);
        }
    }

}
