package lintcode;

/**
 * Created by yuank on 10/2/18.
 */
public class LI_45_Maximum_Subarray_Difference {
    /**
         Given an array with integers.

         Find two non-overlapping subarrays A and B, which |SUM(A) - SUM(B)| is the largest.

         Return the largest difference.

         Example
         For [1, 2, -3, 1], return 6.

         Challenge
         O(n) time and O(n) space.

         Notice
         The subarray should contain at least one number
     */

    /**
         大概的思路是
         定义四个数组
         leftMaxs, leftMins
         rightMaxs, rightMinx

         1.先从左往右找，把每个index的左边的最大和左边的最小找出来存到 leftMaxs, leftMins
         2.再从右往左找，把每个index的右边的最大和右边的最小找出来存到 rightMaxs, rightMins
         3.然后再最终找一遍, 把每个index的 abs(leftMaxs[index] - rightMins[index])
           和 abs(rightMaxs[index] - leftMins[index])求最大,
           最后一遍遍历下来就可以找到最大的不交叉连续数组的最大差了
     */
    public int maxDiffSubArrays(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int[] leftMaxs = new int[nums.length];
        int[] leftMins = new int[nums.length];
        int[] rightMaxs = new int[nums.length];
        int[] rightMins = new int[nums.length];
        leftMaxs[0] = nums[0];
        leftMins[0] = nums[0];
        rightMaxs[nums.length - 1] = nums[nums.length - 1];
        rightMins[nums.length - 1] = nums[nums.length - 1];

        int leftSum = nums[0];
        int rightSum = nums[nums.length - 1];

        int leftMin = 0;
        int leftMax = 0;
        int rightMin = 0;
        int rightMax = 0;

        for (int i = 1; i < nums.length; i++) {
            leftMin = Math.min(leftMin, leftSum);
            leftMax = Math.max(leftMax, leftSum);
            leftSum += nums[i];
            leftMaxs[i] = Math.max(leftMaxs[i - 1], leftSum - leftMin);
            leftMins[i] = Math.min(leftMins[i - 1], leftSum - leftMax);
        }

        for (int i = nums.length - 2; i >= 0; i--) {
            rightMin = Math.min(rightMin, rightSum);
            rightMax = Math.max(rightMax, rightSum);
            rightSum += nums[i];
            rightMaxs[i] = Math.max(rightMaxs[i + 1], rightSum - rightMin);
            rightMins[i] = Math.min(rightMins[i + 1], rightSum - rightMax);
        }

        int largest = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length - 1; i++) {
            largest = Math.max(largest, Math.max(Math.abs(rightMaxs[i + 1] - leftMins[i]), Math.abs(leftMaxs[i] - rightMins[i + 1])));
        }

        return largest;
    }
}
