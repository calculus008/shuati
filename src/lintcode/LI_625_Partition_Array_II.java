package lintcode;

/**
 * Created by yuank on 7/12/18.
 */
public class LI_625_Partition_Array_II {
    /**
         Partition an unsorted integer array into three parts:

         The front part < low
         The middle part >= low & <= high
         The tail part > high
         Return any of the possible solutions.

         Example
         Given [4,3,4,1,2,3,1,2], and low = 2 and high = 3.

         Change to [1,1,2,3,2,3,4,4].

         ([1,1,2,2,3,3,4,4] is also a correct answer, but [1,2,1,2,3,3,4,4] is not)

         Challenge
         Do it in place.
         Do it in one pass (one loop).
     */

    public void partition2(int[] nums, int low, int high) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        int left = 0;
        int right = nums.length - 1;
        int index = 0;

        while (index <= right) {
            if (nums[index] < low) {
                swap(nums, index, left);
                left++;
                index++;
            } else if (nums[index] > high) {
                swap(nums, index, right);
                right--;
            } else {
                index++;
            }
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
