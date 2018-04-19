package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_41_First_Missing_Positive {
    /*
        Given an unsorted integer array, find the first missing positive integer.

        For example,
        Given [1,2,0] return 3,
        and [3,4,-1,1] return 2.

        Your algorithm should run in O(n) time and uses constant space.
     */

    // [3,4,-1,1]
    //



    //Bucket sorting solution : value in nums[i] should be i+1
    public static int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0)
            return 1;

        int n = nums.length;

        for (int i = 0; i < n; i++) {
            //!!!!
            //nums[i] - 1 : index that value in nums[i] supposed to be
            //nums[nums[i] - 1] == nums[i] : meaning it is in the right place

            System.out.println("i="+i+", nums :" + Arrays.toString(nums));
            while (nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
                System.out.println("After swap:" + Arrays.toString(nums));
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return n + 1;
    }

    private static void swap(int[] nums, int i, int j) {
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }


    public static int firstMissingPositive1(int[] nums) {
        if (null == nums || nums.length == 0) return 1;

        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                int temp = nums[i];
                nums[i] = nums[nums[i] - 1];
                nums[temp - 1] = temp;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return nums.length + 1;
    }

    public static void main(String [] args) {
        int[] arr = {3,1,4,-1};
        firstMissingPositive(arr);
    }
}
