package Sort_Set;

// Java program for implementation of Heap Sort
public class HeapSort {

    public void sort(int[] nums) {
        int len = nums.length;
        for (int i = (len >> 1) - 1; i >= 0; --i) {
            heapify(nums, len, i);
        }
        for (int i = len - 1; i >= 0; --i) {
            swap(nums, i, 0);
            // 自底向上逐个堆化
            heapify(nums, i, 0);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void heapify(int[] nums, int len, int i) {
        int largest = i;
        int left = 2 * i + 1, right = 2 * i + 2;
        if (left < len && nums[largest] < nums[left]) {
            largest = left;
        }
        if (right < len && nums[largest] < nums[right]) {
            largest = right;
        }
        if (largest != i) {
            swap(nums, largest, i);
            // 自顶向下堆化
            heapify(nums, len, largest);
        }
    }

    private static void printArray(int[] nums) {
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // Driver code
    public static void main(String args[])
    {
        int arr[] = { 12, 11, 13, 5, 6, 7 };

        HeapSort ob = new HeapSort();
        ob.sort(arr);

        System.out.println("Sorted array is");
        printArray(arr);
    }
}



