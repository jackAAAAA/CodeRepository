package Sort_Set;

import java.util.Scanner;

public class Sort {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入待排序的数据长度：");
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("数据已录入，请继续输入下一个数：");
            a[i] = in.nextInt();
            System.out.println("还需要输入：" + (n - 1 - i) + "个数");
        }
        //InsertSort
//        insertSort(a, a.length);
        //QuickSort
        quickSort(a, a.length);
        //MergeSort
//        mergeSort(a, a.length);
        int i = 0;
        while (i < n) {
            System.out.println("数据由小到大排序为：" + a[i++] + " ");
        }

    }

    /**
     * InsertSort
     *
     * @param a
     * @param n
     */
    private static void insertSort(int[] a, int n) {
        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = value;
        }
    }

    /**
     * QuickSort
     *
     * @param a
     * @param n
     */
    private static void quickSort(int[] a, int n) {
        quickSortInternally(a, 0, n - 1);
    }

    private static void quickSortInternally(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }
        int q = partition(a, p, r);
        quickSortInternally(a, p, q - 1);
        quickSortInternally(a, q + 1, r);
    }

    private static int partition(int[] a, int p, int r) {
        int pivot = a[r];
        int i = p;
        int j = p;
        for (; j < r; ++j) {
            if (a[j] < pivot) {
                if (i != j) {
                    swap(a, i, j);
                }
                ++i;
            }
        }
        swap(a, i, j);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * MergeSort
     *
     * @param a
     * @param n
     */
    public static void mergeSort(int[] a, int n) {
        mergeSortInternally(a, 0, n - 1);
    }

    private static void mergeSortInternally(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }
        int q = p + (r - p) / 2;
        mergeSortInternally(a, p, q);
        mergeSortInternally(a, q + 1, r);
        merge(a, p, q, r);
    }

    private static void merge(int[] a, int p, int q, int r) {
        int i = p;
        int j = q + 1;
        int k = 0;
        int[] temp = new int[r - p + 1];
        while (i <= q && j <= r) {
            if (a[i] <= a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        int start = i;
        int end = q;
        if (j <= r) {
            start = j;
            end = r;
        }
        while (start <= end) {
            temp[k++] = a[start++];
        }
        for (i = 0; i <= r - p; ++i) {
            a[p + i] = temp[i];
        }
    }

}
