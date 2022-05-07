package Array;

import java.util.Arrays;

public class Array {
    public static void main(String[] args)
    {
//        System.out.println("String array default values:");
//        String str[] = new String[5];
//        for (String s : str)
//            System.out.print(s + " ");
//
//        System.out.println("\n\nInteger array default values:");
//        int num[] = new int[5];
//        for (int val : num)
//            System.out.print(val + " ");
//
//        System.out.println("\n\nDouble array default values:");
//        double dnum[] = new double[5];
//        for (double val : dnum)
//            System.out.print(val + " ");
//
//        System.out.println("\n\nBoolean array default values:");
//        boolean bnum[] = new boolean[5];
//        for (boolean val : bnum)
//            System.out.print(val + " ");
//
//        System.out.println("\n\nReference Array.Array default values:");
//        Array.Array ademo[] = new Array.Array[5];
//        for (Array.Array val : ademo)
//            System.out.print(val + " ");

//        System.out.println("Character array default values");
//        char[] ca = new char[5];
//        for (char c : ca) {
////            ++c;
//            System.out.print("c = " + c + " ");
//        }

//        boolean flag = isAnagram("att", "ttb");
//
//        System.out.println("flag = " + flag + " alphabet = " + String.valueOf(alphabet));
//

//        int[] int_array = new int[] {1, 2, 3};
//        System.out.println("Convert int[] to String：" + String.valueOf(int_array));
        char[] char_array = new char[3];
        System.out.println("The Wrong char[]'s defaultValue is ");
        for (char c : char_array) {
            System.out.print(c + ' ');
        }
        System.out.println();
        System.out.println("The Right char[]'s defaultValue is ");
        for (int i = 0; i < 3; ++i) {
            System.out.print(char_array[i] + " ");
        }




    }

    private static int[] alphabet = new int[26];
    public static boolean isAnagram(String s, String t) {

        char[] ca_s = s.toCharArray();
        char[] ca_t = t.toCharArray();
        if (ca_s.length != ca_t.length) {
            return false;
        }

        for (int i = 0; i < ca_s.length; ++i) {
            ++alphabet[ca_s[i] - 'a'];
            --alphabet[ca_t[i] - 'a'];
        }

        for (int c : alphabet) {
            if (c != 0) {
                return false;
            }
        }

        return true;

    }

}
