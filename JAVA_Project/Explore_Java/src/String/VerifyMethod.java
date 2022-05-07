package String;

public class VerifyMethod {
    public static void main(String[] args) {
        String s1 = new String("aaa");
        String s2 = new String("aaa");
        System.out.println(s1 == s2);           // false，指向堆内不同引用
        String s3 = s1.intern();
        String s4 = s2.intern();
        System.out.println(s3 == s4);           // true，指向字符串常量池中相同引用
        String s5 = "bbb";
        String s6 = "bbb";
        System.out.println(s5 == s6);  			// true，指向字符串常量池中相同引用
    }
}
