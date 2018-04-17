package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 1/19/18.
 */
public class FindKthLargest {
    public static int findKthLargest(int[] nums, int k) {
        if (null == nums || nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;

        if(left == right) {
            return nums[left];
        }

        int idx = nums.length - 1 - k + 1;

        System.out.println("targetidx = " + idx);

        //Partition, left side numbers are bigger than pivot
        while (true) {
            int pivotIndex = randomPivot(left, right);
            pivotIndex = partition(nums, left, right, pivotIndex);

            System.out.println("pivotIndex="+pivotIndex + " in " + Arrays.toString(nums));

            if(idx == pivotIndex) {
                System.out.println("result = " + nums[idx]);
                return nums[idx];
            } else if(idx < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static int randomPivot(int left, int right) {
        return left + (int) Math.floor(Math.random() * (right - left + 1));
    }

    // https://discuss.leetcode.com/topic/18662/ac-clean-quickselect-java-solution-avg-o-n-time
    //!!!
    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        System.out.println("left = " + left + ", right = "+right + ", pivotIndex=" + pivotIndex);
        int pivot = nums[pivotIndex];
         System.out.println("swap 1 " + pivotIndex + " and " + right);
        swap(nums, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if(nums[i] < pivot) {
                 System.out.println("swap 2 " + i + " and " + storeIndex);
                swap(nums, i, storeIndex);
                storeIndex++;
            }
        }

         System.out.println("swap 3 " + right  + " and " + storeIndex);

        swap(nums, right, storeIndex);
        return storeIndex;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
//        int[] array = {9, 8, 7, 6, 5, 0, 1, 2, 3, 4};
        int[] array = {3,2,1,5,6,4};

//        int[] array = {-1,2,0};
        findKthLargest(array, 3);
    }
}
