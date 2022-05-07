package Automatic_processing_tool;

public class Remove_extra_spaces {
    public static void main(String[] args) {
        String text = "3.  14 15 92 65 35 89 79 32 38 46 26 43 38 32 79 50 28 84 19 71 69 39 93 75 10 ";
        text = res(text);
        System.out.println(text);
    }

    private static String res(String text) {
        char[] ca_t = text.toCharArray();
        int len = ca_t.length;
        for (int i = 0, start = 0; i < len; i++) {
            if (ca_t[i] != ' ') {
                ca_t[start++] = ca_t[i];
            }
        }
        return String.valueOf(ca_t).substring(0, 1);
    }

}
