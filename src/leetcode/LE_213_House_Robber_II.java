package leetcode;

/**
 * Created by yuank on 3/27/18.
 */
public class LE_213_House_Robber_II {
    /**
        Note: This is an extension of House Robber (LE_198)

        After robbing those houses on that street, the thief has found himself a new place for
        his thievery so that he will not get too much attention.
        This time, all houses at this place are arranged in a circle. That means the first house
        is the neighbor of the last one. Meanwhile, the security system for these houses remain
       the same as for those in the previous street.

        Given a list of non-negative integers representing the amount of money of each house,
        determine the maximum amount of money you can rob tonight without alerting the police.

        Medium

        https://leetcode.com/problems/house-robber-ii

        LE_198_House_Robber
        LE_213_House_Robber_II
        LE_337_House_Robber_III
     */
    class Solution_clean {
        class Solution {
            public int rob(int[] nums) {
                if (nums.length == 0) return 0;

                if (nums.length == 1) return nums[0];

                int max1 = rob_simple(nums, 0, nums.length - 2);
                int max2 = rob_simple(nums, 1, nums.length - 1);

                return Math.max(max1, max2);
            }

            public int rob_simple(int[] nums, int start, int end) {
                int t1 = 0;
                int t2 = 0;

                for (int i = start; i <= end; i++) {
                    int temp = Math.max(nums[i] + t2, t1);
                    t2 = t1;
                    t1 = temp;
                }

                return t1;
            }
        }
    }
    /**
     * rob 0, not rob n-1 || rob 0, not rob n-1     ==>rob(0, nums.length-2, nums)
     * not rob 0, rob n-1 || not rob 0,not rob n-1  ==>rob(1, nums.length-1, nums)
     */
    public int rob(int[] nums) {
        if(null == nums || nums.length==0) {
            return 0;
        }

        //!!!
        if (nums.length == 1) {
            return nums[0];
        }

        return Math.max(rob1(nums, 0, nums.length - 2), rob1(nums, 1, nums.length -1));

    }

    //Directly copied from LE_198
    public int rob1(int[] nums, int lo, int hi) {
        int len = nums.length;
        int dp1 = 0;
        int dp2 = 0;

        for (int i = lo; i <= hi; i++) {
            int dp = Math.max((i < 2 ? 0 : dp1) + nums[i],
                               i < 1 ? 0 : dp2);
            dp1 = dp2;
            dp2 = dp;
        }

        return dp2;
    }
}
