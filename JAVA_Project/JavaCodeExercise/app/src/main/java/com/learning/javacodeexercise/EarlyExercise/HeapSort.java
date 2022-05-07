package com.learning.javacodeexercise.EarlyExercise;

public class HeapSort {
    /**
     *
     * @param a
     * @param n
     */
    public static void heapsort(int[] a, int n) {
        if (n <= 1) {
            return;
        }
        buildHeap(a, n);
        int k = n - 1;
        while (true ) {
            swap(a,0, k);
            --k;
            if (k == 0) {
                break;
            }
            heapify(a, k,0);
        }
    }

    /**
     *
     * @param a
     * @param n
     */
    private static void buildHeap(int[] a, int n) {
        for (int i = (n - 1) / 2; i >= 0; i-- ) {
            heapify(a,n - 1, i);
        }
    }

    /**
     *
     * @param a
     * @param n
     * @param i
     */
    private static void heapify(int[] a, int n, int i) {
        while (true) {
            int maxpos = i;
            if (i * 2 + 1 <= n && a[maxpos] < a[i * 2 + 1]) {
                maxpos = i * 2 + 1;
            }
            if (i * 2 + 2 <= n && a[maxpos] < a[i * 2 + 2]) {
                maxpos = i * 2 + 2;
            }
            if (maxpos == i) {
                break;
            }
            swap(a, i, maxpos);
            i = maxpos;
        }
    }

    /**
     *
     * @param a
     * @param i
     * @param j
     */
    public static void swap (int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
