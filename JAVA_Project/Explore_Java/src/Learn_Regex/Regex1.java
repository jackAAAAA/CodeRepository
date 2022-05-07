package Learn_Regex;

import java.util.List;

public class Regex1 {
    public static void main(String[] args) {
        String reg = "\\d{3,4}-\\d{7,8}";
        for (String s : List.of("010-12345678", "020-4556788", "080-1234567")) {
//            注意：是s.matches(reg)而不是reg.matches(s)
            if (!s.matches(reg)) {
                System.out.println("测试失败！" + s);
                return;
            }
        }
        for (String s : List.of("010 12345678", "020 3456789", "1111111111111111")) {
            if (s.matches(reg)) {
                System.out.println("测试失败！" + s);
                return;
            }
        }
        System.out.println("测试成功！");
    }
}
