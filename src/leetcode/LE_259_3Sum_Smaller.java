package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 4/11/18.
 */
public class LE_259_3Sum_Smaller {
    /**
        Given an array of n integers nums and a target, find the number of index triplets i, j, k with 0 <= i < j < k < n
        that satisfy the condition nums[i] + nums[j] + nums[k] < target.

        For example, given nums = [-2, 0, 1, 3], and target = 2.

        Return 2. Because there are two triplets which sums are less than 2:

        [-2, 0, 1]
        [-2, 0, 3]
        Follow up:
        Could you solve it in O(n ^ 2) runtime?
     */

    /**
     *  Time : O(n ^ 2), Space : O(1)
     *
     *  3sum questions : LE_15, LE_16
     */

    public int threeSumSmaller(int[] nums, int target) {
        int res = 0;
        Arrays.sort(nums);

        for (int i = 0; i < nums.length -2; i++) {
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                if (nums[i] + nums[left] + nums[right] < target) {
                    /**
                     * !!!
                     * nums[i] is fixed in each for loop,so for numbers :
                     *
                     * i, left, right
                     *
                     * Since nums is sorted, all numbers after right is not bigger than nums[right],
                     * therefore, nums[i] + nums[left] + nums[k] (k <= right) must be smaller
                     * than target, the number of triples is right - left.
                     */
                    res += right - left;

                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }
}
