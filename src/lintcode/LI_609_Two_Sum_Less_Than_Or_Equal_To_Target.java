package lintcode;

import java.util.Arrays;

/**
 * Created by yuank on 7/10/18.
 */
public class LI_609_Two_Sum_Less_Than_Or_Equal_To_Target {
    /**
         Given an array of integers, find how many pairs in the array such that their sum is
         less than or equal to a specific target number. Please return the number of pairs.

         Medium
     */

    //Time : O(nlogn), Space : O(1)
    public int twoSum5(int[] nums, int target) {
        Arrays.sort(nums);

        int res = 0;
        int start = 0;
        int end = nums.length - 1;

        while (start < end) {
            int sum = nums[start] + nums[end];
            if (sum <= target) {
                res += end - start;
                start++;
            } else {
                end--;
            }
        }

        return res;
    }
}
