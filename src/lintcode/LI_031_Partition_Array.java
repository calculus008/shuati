package lintcode;

/**
 * Created by yuank on 7/12/18.
 */
public class LI_031_Partition_Array {
    /**
         Given an array nums of integers and an int k, partition the array (i.e move the elements in "nums") such that:

         All elements < k are moved to the left
         All elements >= k are moved to the right
         Return the partitioning index, i.e the first index i nums[i] >= k.

         Example
         If nums = [3,2,2,1] and k=2, a valid answer is 1.

         Challenge
         Can you partition the array in-place and in O(n)?
     */

    //Quicksort uses it to do partition
    public int partitionArray(int[] nums, int k) {
        if(nums == null || nums.length == 0){
            return 0;
        }

        int left = 0, right = nums.length - 1;
        while (left <= right) {

            while (left <= right && nums[left] < k) {
                left++;
            }

            while (left <= right && nums[right] >= k) {
                right--;
            }

            if (left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;

                left++;
                right--;
            }
        }
        return left;
    }
}
