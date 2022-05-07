public class Homework_week9 {

    //First Unique Character in a String
    public int firstUniqChar(String s) {

        //called toCharArray() once then operated on array,
        //instead of making repeated calls to charAt() & length().
        char[] ca = s.toCharArray();
        char[] cc = new char[26];
        for (char c : ca)
            ++cc[c - 'a'];
        for (int i = 0; i < ca.length; ++i)
            if (cc[ca[i] - 'a'] == 1)
                return i;
        return -1;

    }


    //541. Reverse String II
    public String reverseStr(String s, int k) {

        char[] ca = s.toCharArray();
        int index = 0;
        int len = ca.length;
        while (index < len) {
            int endindex = Math.min(index + k - 1, len - 1);
            swap(ca, index, endindex);
            index += 2 * k;
        }
        return new String(ca);

    }

    private static void swap(char[] ca, int start, int end) {
        while (start < end) {
            char temp = ca[start];
            ca[start++] = ca[end];
            ca[end--] = temp;
        }
    }

}
