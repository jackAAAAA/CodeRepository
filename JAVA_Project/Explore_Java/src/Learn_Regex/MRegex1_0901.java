package Learn_Regex;

import java.util.List;

public class MRegex1_0901 {
    public static void main(String[] args) {
        String reg = "\\d{3,4}-\\d{7,8}";
        for (String s : List.of("010-12345678", "098-3456789", "020-8999999")) {
            if (!s.matches(reg)) {
                System.out.println("测试失败！");
                return;
            }
        }
        for (String s : List.of("020-123456789", "090 345678900", "1234-333333333")) {
            if (s.matches(reg)) {
                System.out.println("测试失败！");
                return;
            }
        }
        System.out.println("测试成功！");
    }
}
