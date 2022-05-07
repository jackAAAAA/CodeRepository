package Sort_Set;

import java.util.Arrays;

public class InsertSort {

    static int[] nums = {2, 5, 9};
//    public static void main(String[] args) {
////        要插入有序数组的元素为num
//        int num = 3;
//        new InsertSort().insertSort(num);
//        for (int e : nums) {
//            System.out.print(e + " ");
//        }
//    }

    //    void insertSort(int num) {
//        int len = nums.length;
//        nums = Arrays.copyOf(nums, len + 1);
//        int i = len;
//        for (; i > 0 && num < nums[i - 1]; --i) {
//            nums[i] = nums[i - 1];
//        }
//        nums[i] = num;
//    }
    void insertSort(int num) {
        int len = nums.length;
        nums = Arrays.copyOf(nums, len + 1);
        int i = len;
        for (; i > 0 && num < nums[i - 1]; --i) {
            nums[i] = nums[i - 1];
        }
        nums[i] = num;
    }

    public static void main(String[] args) {
        new InsertSort().insertSort(1);
        for (int e : nums) {
            System.out.print(e + " ");
        }
    }

}
