package leetcode;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_198_House_Robber {
    /**
        You are a professional robber planning to rob houses along a street.
        Each house has a certain amount of money stashed, the only constraint
        stopping you from robbing each of them is that adjacent houses have
        security system connected and it will automatically contact the police
        if two adjacent houses were broken into on the same night.

        Given a list of non-negative integers representing the amount of money of each house,
        determine the maximum amount of money you can rob tonight without alerting the police.
     */

    //Solution 1: DP
    public int rob1(int[] nums) {
        if(null == nums || nums.length==0) {
            return 0;
        }

        int len = nums.length;
        int[] dp = new int[len];

        for(int i=0; i<len; i++) {
            dp[i] = Math.max(i<2 ? nums[i] : nums[i] + dp[i-2],
                    i<1 ? 0 : dp[i-1]);
        }

        return dp[len-1];
    }

    //Solution 2: DP with optimization on space
    public int rob2(int[] nums) {
        if(null == nums || nums.length==0) {
            return 0;
        }

        int len = nums.length;
        int dp1 = 0;
        int dp2 = 0;

        for(int i=0; i<len; i++) {
            int dp = Math.max(i<2 ? nums[i] : nums[i] + dp1,
                    i<1 ? 0 : dp2);
            dp1 = dp2;
            dp2 = dp;
        }

        return dp2;
    }

    //Solution 3: recursion with memorization
     public int rob3(int[] nums) {
         if(null == nums || nums.length==0) {
             return 0;
         }

         int len = nums.length;
         int[] mem = new int[len];
         //!!!init mem : the value here is greater or equal to zero, so must init it with -1
         for(int i=0; i<len; i++) {
             mem[i] = -1;
         }

         helper(nums, mem, len-1);
         return mem[len-1];
     }

     public int helper(int[] nums, int[] mem, int idx) {
         if(idx < 0) return 0;
         if(mem[idx] >= 0) return mem[idx];

         mem[idx] = Math.max(helper(nums, mem, idx-2) + nums[idx], helper(nums, mem, idx-1));
         return mem[idx];
     }

    /**
     * LI_392, return long to avoid overflow
     * Time  : O(n)
     * Space : O(n)
     **/
    public long houseRobber1(int[] A) {
        if (A == null || A.length == 0) return 0;

        //dp[i] : max value can be stolen for first i houses
        long[] dp = new long[A.length + 1];
        dp[0] = 0;
        dp[1] = A[0];

        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.max(dp[i - 1], A[i - 1] + dp[i - 2]);
        }

        return dp[A.length];
    }

    /**
     * 滚动数组
     * Time : O(n)
     * Space : O(1)
     */
    public long houseRobber2(int[] A) {
        // write your code here
        int n = A.length;
        if(n == 0)
            return 0;
        long []res = new long[2];


        res[0] = 0;
        res[1] = A[0];
        for(int i = 2; i <= n; i++) {
            res[i%2] = Math.max(res[(i-1)%2], res[(i-2)%2] + A[i-1]);
        }
        return res[n%2];
    }
}
