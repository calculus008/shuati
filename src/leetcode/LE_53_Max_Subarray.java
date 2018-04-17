package leetcode;

/**
 * Created by yuank on 3/2/18.
 */
public class LE_53_Max_Subarray {
    /*
        Find the contiguous subarray within an array (containing at least one number) which has the largest sum.

        For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
        the contiguous subarray [4,-1,2,1] has the largest sum = 6.
     */

    public static int maxSubArray1(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        //!!!! 不是 res = 0 !!!
        int res = nums[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i] = nums[i] + (dp[i - 1] < 0 ? 0 : dp[i - 1]);
            res = Math.max(res, dp[i]);
        }

        return res;
    }

    public static int maxSubArray(int[] nums) {
        int res = nums[0];
        int sum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            //!!!
            sum = Math.max(nums[i], sum + nums[i]);
            res = Math.max(res, sum);
            System.out.println("i=" + i + ", nums[" + i + "]=" + nums[i] + ",sum=" + sum + ",res=" + res);
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        maxSubArray(nums);
    }
}
