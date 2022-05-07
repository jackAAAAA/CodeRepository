package Interview;

import java.util.Scanner;

class BigMultiply {
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int len1 = num1.length(), len2 = num2.length();
        int[] res = new int[len1 + len2];
        for (int i = len1 - 1; i >= 0; --i) {
            int n1 = num1.charAt(i) - '0';
            for (int j = len2 - 1; j >= 0; --j) {
                int n2 = num2.charAt(j) - '0';
                int sum = res[i + j + 1] + n1 * n2;
                res[i + j + 1] = sum % 10;
                res[i + j] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.length; ++i) {
            if (i == 0 && res[i] == 0) {
                continue;
            }
            sb.append(res[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("-----输入两个大数-----");
        Scanner scanner = new Scanner(System.in);
        String num1 = scanner.next();
        String num2 = scanner.next();
        BigMultiply bm = new BigMultiply();
        String res = bm.multiply(num1, num2);
        System.out.println("相乘结果为：" + res);
        scanner.close();
    }
}


