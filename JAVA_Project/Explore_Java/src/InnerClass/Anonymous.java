package InnerClass;

public class Anonymous {
        private static String a = "a";
        private static String b = "b";

        private static Object obj = new Object() {
            private String name = "匿名内部类";
            @Override
            public String toString() {
                return name;
            }
        };

        public static void test() {
//            Object obj = new Object() {
//                @Override
//                public String toString() {
//                    System.out.println(b);
//                    return String.valueOf(a);
//                }
//            };
            Object obj = new Object() {
                public String toString() {
                    System.out.println(b);
                    return String.valueOf(a);
                }
            };
            System.out.println(obj.toString());
        }

    public static void main(String[] args) {
        test();
        System.out.println(obj.toString());
    }
}

