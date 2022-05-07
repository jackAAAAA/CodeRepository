package Sort_Set.Exercise;

public class HeapSort_0401 {

    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 8, 9};
        int len = arr.length;
        /**
         * create maxHeap & naturalOrder & findMax
         */
//        int[] maxHeap = buildHeap(arr, len, true);
//        printArray(len, maxHeap);
//        System.out.println();
//        naturalOrder(maxHeap, len);
//        printArray(len, maxHeap);
//        System.out.println();
//        System.out.println(findMax(buildHeap(maxHeap, len, true), len));
        /**
         * create minHeap & descendingOrder & findMin
         */
        int[] minHeap = buildHeap(arr, len, false);
        printArray(minHeap, len);
        System.out.println();
        descendingOrder(minHeap, len);
        printArray(minHeap, len);
        System.out.println();
        System.out.println(findMin(buildHeap(minHeap, len, false), len));
    }

    public static void printArray(int[] arr, int len) {
        for (int i = 0; i < len; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    private static int[] buildHeap(int[] arr, int len, boolean maxHeap) {
        for (int i = (len >> 1) - 1; i >= 0; --i) {
            heapify(arr, len, i, maxHeap);
        }
        return arr;
    }

    /**
     * findMax & sortArray in natural order
     */
    private static int findMax(int[] maxHeapArray, int len) {
        int res = maxHeapArray[0];
        maxHeapArray[0] = maxHeapArray[len - 1];
        heapify(maxHeapArray, len - 1, 0, true);
        return res;
    }

    private static void naturalOrder(int[] maxHeapArray, int len) {
        for (int i = len - 1; i >= 0; --i) {
            int temp = maxHeapArray[0];
            maxHeapArray[0] = maxHeapArray[i];
            maxHeapArray[i] = temp;

            heapify(maxHeapArray, i, 0, true);
        }
    }

    /**
     * findMin & sortArray in descending order
     */
    private static int findMin(int[] minHeapArray, int len) {
        int res = minHeapArray[0];
        minHeapArray[0] = minHeapArray[len - 1];
        heapify(minHeapArray, len - 1, 0, false);
        return res;
    }

    private static void descendingOrder(int[] minHeapArray, int len) {
        for (int i = len - 1; i >= 0; --i) {
            int temp = minHeapArray[0];
            minHeapArray[0] = minHeapArray[i];
            minHeapArray[i] = temp;

            heapify(minHeapArray, i, 0, false);
        }
    }

    private static void heapify(int[] arr, int len, int top, boolean maxHeap) {
        if (top >= len) {
            return;
        }
        if (maxHeap) {
            int largest = top;
//            int left = (top << 1) - 1;
            int left = (top << 1) + 1;
            if (left < len && arr[left] > arr[largest]) {
                largest = left;
            }
//                int right = (top << 1) + 1;
            int right = (top << 1) + 2;
            if (right < len && arr[right] > arr[largest]) {
                largest = right;
            }
            if (largest != top) {
                swap(arr, top, largest);
                heapify(arr, len, largest, true);
            }
        } else {
            int smallest = top;
//            int left = (top << 1) - 1;
            int left = (top << 1) + 1;
            if (left < len && arr[left] < arr[smallest]) {
                smallest = left;
            }
//                int right = (top << 1) + 1;
            int right = (top << 1) + 2;
            if (right < len && arr[right] < arr[smallest]) {
                smallest = right;
            }
            if (smallest != top) {
                swap(arr, top, smallest);
                heapify(arr, len, smallest, false);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
