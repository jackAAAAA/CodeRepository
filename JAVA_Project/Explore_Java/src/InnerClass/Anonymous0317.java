package InnerClass;

public class Anonymous0317 {
    public static void main(String[] args) {
//        final String test = "test";
        String test = "test";
        Object obj = new Object() {
            @Override
            public String toString() {
                System.out.println("Hello World! " + test);
                return null;
            }
        };
        obj.toString();
    }
}

