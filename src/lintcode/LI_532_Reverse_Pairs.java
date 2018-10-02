package lintcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 10/1/18.
 */
public class LI_532_Reverse_Pairs {
    /**
         For an array A, if i < j, and A [i] > A [j], called (A [i], A [j]) is a reverse pair.
         return total of reverse pairs in A.

         Example
         Given A = [2, 4, 1, 3, 5] , (2, 1), (4, 1), (4, 3) are reverse pairs. return 3

         Medium
     */

    /**
     * Solution 1
     * Merge Sort
     */
    public long reversePairs1(int[] A) {
        return mergeSort(A, 0, A.length - 1);
    }

    private int mergeSort(int[] A, int start, int end) {
        if (start >= end) {
            return 0;
        }

        int mid = (start + end) / 2;
        int sum = 0;
        sum += mergeSort(A, start, mid);
        sum += mergeSort(A, mid+1, end);
        sum += merge(A, start, mid, end);
        return sum;
    }

    private int merge(int[] A, int start, int mid, int end) {
        int[] temp = new int[A.length];
        int leftIndex = start;
        int rightIndex = mid + 1;
        int index = start;
        int sum = 0;

        while (leftIndex <= mid && rightIndex <= end) {
            if (A[leftIndex] <= A[rightIndex]) {
                temp[index++] = A[leftIndex++];
            } else {
                temp[index++] = A[rightIndex++];
                sum += mid - leftIndex + 1;
            }
        }
        while (leftIndex <= mid) {
            temp[index++] = A[leftIndex++];
        }
        while (rightIndex <= end) {
            temp[index++] = A[rightIndex++];
        }

        for (int i = start; i <= end; i++) {
            A[i] = temp[i];
        }

        return sum;
    }

    /**
     * Binary Indexed Tree
     */
    public long reversePairs2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        discretization(nums);

        // build bit array
        int[] bit = new int[nums.length + 1];
        int[] count = new int[nums.length];

        List<Integer> result = new ArrayList<Integer>();


        for (int i = nums.length - 1; i >= 0; i--) {
            /**
             * For example, current nums[i] is 3, meaning current value ranks 3rd,
             * so we need to find out the sum for ranks at 2.
             */
            result.add(0, getSum(bit, nums[i] - 1) );

            /**
             * 经过离散化后，已经不用考虑下标加一的问题了，这里"nums[i]"已经是rank (1 based).
             */
            update(bit, nums[i]);
        }

//        List<Integer> result = new ArrayList<Integer>();
//        for (int i = 0; i < count.length; i++) {
//            result.add(count[i]);
//        }

        long sum = 0;
        for(int n : result) {
            sum += n;
        }

        return sum;
    }

    // this is nlogn
    // sort the orignal array and mapping the number to
    // the order in the sorted array;
    private void discretization(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        for (int i = 0; i < nums.length; i++) {
            nums[i] = Arrays.binarySearch(sorted, nums[i]) + 1;
        }
    }

    private void update(int[] bit, int index) {
        for (int i = index; i < bit.length; i = i + lowbit(i)) {
            bit[i]++;
        }
    }

    private int getSum(int[] bit, int index) {
        int sum = 0;
        for (int i = index; i > 0; i = i - lowbit(i)) {
            sum += bit[i];
        }
        return sum;
    }

    private int lowbit(int x) {
        return x & (-x);
    }

}
