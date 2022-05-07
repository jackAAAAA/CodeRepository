package Template_Memorial_Writing;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QS1_QS2 {

    /*
        func1:find Kth bigger number of array
        func2:print out the Kth bigger numbers of array
        func3:quicksort array
    */

    public static void main(String[] args) {
        Integer[] nums = {6, 3, 2, 8, 1, 100, 1000, 2000, 3000, 1, 3, 4, 5, 60};
        int len = nums.length;
        System.out.println("Before Sort, the sequence is: ");
        for (int i = 0; i < len; ++i) {
            System.out.print(nums[i] + " ");
        }
////        func1
        int Kth = 4;
        System.out.println("\nThe Kth is: " + Kth + "\nThe Kth bigger number of the array is: ");
        System.out.print(quickSelect(nums, 0, len - 1, len - Kth));
////        func2
        System.out.println("\nThe Kth bigger numbers of the array are respectively: ");
        for (int i = len - 1; i >= len - Kth; --i) {
            System.out.print(nums[i] + " ");
        }
        System.out.println("\nAfter quickSort, the sequence is: ");
        quickSort(nums, 0, len - 1);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
        List<Integer> Output = Arrays.asList(nums);
//        Output.forEach(System.out::print);
        Output.forEach((num) -> System.out.print(num + " "));

//        2021.07.08
    /*
        func1:find Kth bigger/smaller number of array
        func2:print out the Kth bigger/smaller numbers of array
        func3:quicksort array
    */
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please input the sequence：");
//        Integer[] nums = new Integer[5];
//        int i = 0;
//        while (i < nums.length) {
//            nums[i++] = scanner.nextInt();
//        }
//        System.out.println("Before Sort, the sequence is: ");
//        Arrays.asList(nums).forEach(num -> System.out.print(num + " "));
//        System.out.println();
//        System.out.println("Please input the Kth：");
//        int Kth = scanner.nextInt();
//        System.out.println("The Kth is: " + Kth + "\nThe Kth bigger number of the array is: ");
//        System.out.println(quickSelect(nums, 0, nums.length - 1, nums.length - Kth));
//        System.out.println("The Kth is: " + Kth + "\nThe Kth smaller number of the array is: ");
//        System.out.println(quickSelect(nums, 0, nums.length - 1, Kth - 1));
//        System.out.println("The Kth bigger numbers of the array are respectively: ");
//        for (int j = nums.length - 1; j >= nums.length - Kth; --j) {
//            System.out.print(nums[j] + " ");
//        }
//        System.out.println("The Kth smaller numbers of the array are respectively: ");
//        for (int j = 0; j < Kth; ++j) {
//            System.out.print(nums[j] + " ");
//        }
//        quickSort(nums, 0, nums.length - 1);
//        System.out.println("\nAfter quickSort, the sequence is: ");
//        Arrays.asList(nums).forEach(Integer -> System.out.print(Integer + " "));
//        Arrays.asList(nums).forEach(System.out::print);
//        scanner.close();

//        2021.06.23
    /*
        func1:find Kth smaller number of array
        func2:print out the Kth smaller numbers of array
        func3:quicksort array
    */
//        Arrays.asList(nums).forEach(System.out::print);
//        Arrays.asList(nums).forEach(Integer -> System.out.print(Integer + " "));
//        System.out.println("Before Sort, the sequence is: ");
//        Arrays.asList(nums).forEach(n -> System.out.print(n + " "));
//        QS1_QS2 q2 = new QS1_QS2();
//        int Kth = 4;
//        System.out.println("\nThe Kth is: " + Kth + "\nThe Kth smaller number of the array is: ");
//        System.out.print(q2.quickSelect(nums, 0, len - 1, Kth - 1));
//        System.out.println("\nThe Kth smaller numbers of the array are respectively: ");
//        for (int i = 0; i < Kth; ++i) {
//            System.out.print(nums[i] + " ");
//        }
//        System.out.println("\nAfter quickSort, the sequence is: ");
//        q2.quickSort(nums, 0, len - 1);
//        Arrays.asList(nums).forEach(Integer -> System.out.print(Integer + " "));
    }
//  Original Template
//    Note!!!: k can replace different meanings
    private static int quickSelect(Integer[] nums, int begin, int end, int k) {
        if (end <= begin) {
            return nums[begin];
        }
        int pivot = partition2(nums, begin, end);
        if (pivot == k) {
            return nums[pivot];
        } else if (pivot > k) {
            return quickSelect(nums, begin, pivot - 1, k);
        } else {
            return quickSelect(nums, pivot + 1, end, k);
        }
    }

    private static void quickSort(Integer[] nums, int begin, int end) {
        if (end <= begin) {
            return;
        }
        int pivot = partition1(nums, begin, end);
        quickSort(nums, begin, pivot - 1);
        quickSort(nums, pivot + 1, end);
    }

    private static int partition2(Integer[] nums, int begin, int end) {
        int pivot = nums[(int) Math.random() * (end - begin + 1) + begin];
        int i = begin, j = end;
        while (i < j) {
            while (i < j && nums[i] < pivot) {
                ++i;
            }
            while (i < j && nums[j] > pivot) {
                --j;
            }
            if (i < j) {
                if (nums[i] != nums[j]) {
                    swap(nums, i, j);
                } else {
                    ++i;
                }
            }
        }
        return i;
    }

    private static int partition1(Integer[] nums, int begin, int end) {
        int pivot = end, counter = begin;
        for (int i = begin; i < end; ++i) {
            if (nums[i] < nums[pivot]) {
                swap(nums, counter++, i);
            }
        }
        swap(nums, pivot, counter);
        return counter;
    }

    private static void swap(Integer[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

//    2021.07.08
//    static int quickSelect(Integer[] nums, int begin, int end, int k) {
//        if (end <= begin) {
//            return nums[begin];
//        }
//        int pivot = partition2(nums, begin, end);
//        if (pivot == k) {
//            return nums[pivot];
//        } else if (pivot < k) {
//            return quickSelect(nums, pivot + 1, end, k);
//        } else {
//            return quickSelect(nums, begin, pivot - 1, k);
//        }
//    }
//
//    static void quickSort(Integer[] nums, int begin, int end) {
//        if (end <= begin) {
//            return;
//        }
//        int pivot = partition1(nums, begin, end);
//        quickSort(nums, begin, pivot - 1);
//        quickSort(nums, pivot + 1, end);
//    }
//
//    static int partition2(Integer[] nums, int begin, int end) {
//        int pivot = nums[(int) (Math.random() * (end - begin + 1)) + begin];
//        int i = begin, j = end;
//        while (i < j) {
//            while (i < j && nums[i] < pivot) {
//                ++i;
//            }
//            while (i < j && nums[j] > pivot) {
//                --j;
//            }
//            if (i < j) {
//                if (nums[i] != nums[j]) {
//                    swap(nums, i, j);
//                } else {
//                    ++i;
//                }
//            }
//        }
//        return i;
//    }
//
//    static int partition1(Integer[] nums, int begin, int end) {
//        int pivot = end, counter = begin;
//        for (int i = begin; i < end; ++i) {
//            if (nums[i] < nums[pivot]) {
//                swap(nums, counter++, i);
//            }
//        }
//        swap(nums, pivot, counter);
//        return counter;
//    }
//
//    static void swap(Integer[] nums, int i, int j) {
//        Integer temp = nums[i];
//        nums[i] = nums[j];
//        nums[j] = temp;
//    }

//    2021.06.23
//    private int quickSelect(Integer[] nums, int start, int end, int k) {
//        if (end <= start) {
//            return nums[start];
//        }
//        int pivot = partition2(nums, start, end);
//        if (pivot == k) {
//            return nums[pivot];
//        } else if (pivot < k) {
//            return quickSelect(nums, pivot + 1, end, k);
//        } else {
//            return quickSelect(nums, start, pivot - 1, k);
//        }
//    }
//
//    private void quickSort(Integer[] nums, int start, int end) {
//        if (end <= start) {
//            return;
//        }
//        int pivot = partition1(nums, start, end);
//        quickSort(nums, start, pivot - 1);
//        quickSort(nums, pivot + 1, end);
//    }
//
//    private int partition1(Integer[] nums, int start, int end) {
//        int pivot = end, counter = start;
//        for (int i = start; i < end; ++i) {
//            if (nums[i] < nums[pivot]) {
//                swap(nums, counter++, i);
//            }
//        }
//        swap(nums, pivot, counter);
//        return counter;
//    }
//
//    private int partition2(Integer[] nums, int start, int end) {
//        int pivot = nums[(int) (Math.random() * (end - start + 1)) + start];
//        int i = start, j = end;
//        while (i < j) {
//            while (i < j && nums[i] < pivot) {
//                ++i;
//            }
//            while (i < j && nums[j] > pivot) {
//                --j;
//            }
//            if (i < j) {
//                if (nums[i] != nums[j]) {
//                    swap(nums, i, j);
//                } else {
//                    ++i;
//                }
//            }
//        }
//        return i;
//    }
//
//    private void swap(Integer[] nums, int i, int j) {
//        int temp = nums[i];
//        nums[i] = nums[j];
//        nums[j] = temp;
//    }

//  2021.06.09
//    public static int quickSelect(int[] nums, int begin, int end, int k) {
//        if (end <= begin) {
//            return nums[begin];
//        }
//        int pivot = partition2(nums, begin, end);
//        if (pivot == k) {
//            return nums[pivot];
//        } else if (pivot < k) {
//            return quickSelect(nums, pivot + 1, end, k);
//        } else {
//            return quickSelect(nums, begin, pivot - 1, k);
//        }
//    }
//
//    public static void quickSort(int[] nums, int begin, int end) {
//        if (end <= begin) {
//            return;
//        }
//        int pivot = partition1(nums, begin, end);
//        quickSort(nums, begin, pivot - 1);
//        quickSort(nums, pivot + 1, end);
//    }
//
//    public static int partition2(int[] nums, int begin, int end) {
//        int pivot = nums[(int) (Math.random() * (end - begin + 1) + begin)];
//        int i = begin, j = end;
//        while (i < j) {
//            while (i < j && nums[i] < pivot) {
//                ++i;
//            }
//            while (i < j && nums[j] > pivot) {
//                --j;
//            }
//            if (i < j) {
//                if (nums[i] != nums[j]) {
//                    swap(nums, i, j);
//                } else {
//                    ++i;
//                }
//            }
//        }
//        return i;
//    }
//
//    public static int partition1(int[] nums, int begin, int end) {
//        int pivot = end, counter = begin;
//        for (int i = begin; i < end; ++i) {
//            if (nums[i] < nums[pivot]) {
//                swap(nums, counter++, i);
//            }
//        }
//        swap(nums, pivot, counter);
//        return counter;
//    }
//
//    public static void swap(int[] nums, int i, int j) {
//        int temp = nums[i];
//        nums[i] = nums[j];
//        nums[j] = temp;
//    }

//    2021.06.08
//    public static int quickSelect1(int[] nums, int begin, int end, int k) {
//        if (end <= begin) {
//            return nums[begin];
//        }
//        int pivot = partition4(nums, begin, end);
//        if (pivot == k) {
//            return nums[pivot];
//        } else if (pivot < k) {
//            return quickSelect1(nums, pivot + 1, end, k);
//        } else {
//            return quickSelect1(nums, begin, pivot - 1, k);
//        }
//    }
//
//    public static void quickSort1(int[] nums, int begin, int end) {
//        if (end <= begin) {
//            return;
//        }
//        int pivot = partition3(nums, begin, end);
//        quickSort1(nums, begin, pivot - 1);
//        quickSort1(nums, pivot + 1, end);
//    }
//
//    public static int partition4(int[] nums, int begin, int end) {
//        int pivot = nums[(int) (Math.random() * (end - begin + 1) + begin)];
//        int i = begin, j = end;
//        while (i < j) {
//            while (i < j && nums[i] < pivot) {
//                ++i;
//            }
//            while (i < j && nums[j] > pivot) {
//                --j;
//            }
//            if (i < j) {
//                if (nums[i] != nums[j]) {
//                    swap(nums, i, j);
//                } else {
//                    ++i;
//                }
//            }
//        }
//        return i;
//    }
//
//    public static int partition3(int[] nums, int begin, int end) {
//        int pivot = end, counter = begin;
//        for (int i = begin; i < end; ++i) {
//            if (nums[i] < nums[pivot]) {
//                swap(nums, counter++, i);
//            }
//        }
//        swap(nums, pivot, counter);
//        return counter;
//    }
//
//    public static void swap(int[] nums, int i, int j) {
//        int temp = nums[i];
//        nums[i] = nums[j];
//        nums[j] = temp;
//    }
}

