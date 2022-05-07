package com.learning.javacodeexercise.EarlyExercise;

import static com.learning.javacodeexercise.EarlyExercise.HeapSort.swap;

public class Heap {
    int[] a; //用数组来存储堆，下标从1开始
    int n; //堆能够存储的数据个数
    int count; //堆中已有的数据个数

    public Heap(int capacity) {
        a = new int[capacity + 1];
        n = capacity;
        count = 0;
    }

    /**
     * 向堆中插入数据，实行自下往上的堆化
     * @param data
     */
    private void insert(int data) {
        if (count >= n) {
            return; //堆满了
        }
        ++count;
        a[count] = data;
        int i = count;
        while (i / 2 > 0 && a[i] > a[i / 2]) {
            swap(a, i, i / 2);
            i /= 2;
        }
    }

    private int removeMax() {
        if (count == 0) {
            return -1;
        }
        int temp = a[1];
        a[1] = a[count];
        --count;
        heapify(a, count, 1);
        return temp;
    }

    /**
     * 自上往下堆化，数组下标从1开始
     * @param a
     * @param count 堆中最后一个元素的下标
     * @param i 自上往下开始堆化元素的下标
     */
    private void heapify(int[] a, int count, int i) {
        while (true) {
            int maxpos = i;
            if ( i * 2 <= count && a[maxpos] < a[i * 2]) {
                maxpos = i * 2;
            }
            if ( i * 2 + 1 <= count && a[maxpos] < a[i * 2 + 1]) {
                maxpos = i * 2 + 1;
            }
            if (maxpos == i) {
                break;
            }
            swap(a, i, maxpos);
            i = maxpos;
        }
    }


}
