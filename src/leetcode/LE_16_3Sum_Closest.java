package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 4/11/18.
 */
public class LE_16_3Sum_Closest {
    /**
        Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target.
        Return the sum of the three integers. You may assume that each input would have exactly one solution.

            For example, given array S = {-1 2 1 -4}, and target = 1.

            The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

        LE_259
     */

    public int threeSumClosest(int[] nums, int target) {
        int res = nums[0] + nums[1] + nums[2];

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            int start = i + 1;
            int end = nums.length - 1;

            while (start < end) {
                int sum = nums[i] + nums[start] + nums[end];
                if (sum < target) {
                    start++;
                } else {
                    end--;
                }

                if (Math.abs(sum - target) < Math.abs(res - target)) {
                    res = sum;
                }
            }
        }

        return res;
    }
}
