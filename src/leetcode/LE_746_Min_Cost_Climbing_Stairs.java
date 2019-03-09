package leetcode;

/**
 * Created by yuank on 11/11/18.
 */
public class LE_746_Min_Cost_Climbing_Stairs {
    /**
         On a staircase, the i-th step has some non-negative cost cost[i] assigned (0 indexed).

         Once you pay the cost, you can either climb one or two steps. You need to find minimum
         cost to reach the top of the floor, and you can either start from the step with index 0,
         or the step with index 1.

         Example 1:
         Input: cost = [10, 15, 20]
         Output: 15
         Explanation: Cheapest is start on cost[1], pay that cost and go to the top.

         Example 2:
         Input: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
         Output: 6
         Explanation: Cheapest is start on cost[0], and only step on 1s, skipping cost[3].

         Note:
         cost will have a length in the range [2, 1000].
         Every cost[i] will be an integer in the range [0, 999].

         Easy
     */

    /**
     * Solutin 1 : DP, iterative
     * Time  : O(n)
     * Space : O(1)
     **/
    public int minCostClimbingStairs1(int[] cost) {
        int dp1 = 0; //f(n-1)
        int dp2 = 0; //f(n-2)

        //!!! <= !!!
        for (int i = 2; i <= cost.length; i++) {
            int dp = Math.min(dp1 + cost[i - 1], dp2 + cost[i - 2]);
            dp2 = dp1;
            dp1 = dp;
        }

        return dp1;
    }

     /**
      * Solution 2 : recursion + memorization
      *
      * f(n) : cost get to n step, exclude cost on step n.
      * f(n) = min(f(n-1) + cost(n-1), f(n-2) + cost(n-2));
      * ans = f(n)
      **/
     public int minCostClimbingStairs2(int[] cost) {
         int[] mem = new int[cost.length + 1];
         helper(cost, mem, cost.length);

         return mem[cost.length];
     }

    private int helper(int[] cost, int[] mem, int step) {
        if (step <= 1) {
            return 0;
        }

        if (mem[step] > 0) {
            return mem[step];
        }

        mem[step] = Math.min(helper(cost, mem, step - 1) + cost[step - 1],
                helper(cost, mem, step - 2) + cost[step - 2]);

        return mem[step];
    }
}

