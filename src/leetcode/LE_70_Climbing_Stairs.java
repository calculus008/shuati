package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_70_Climbing_Stairs {
    /**
        You are climbing a staircase. It takes n steps to reach to the top.

        Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

        Note: Given n will be a positive integer.


        Example 1:

        Input: 2
        Output:  2
        Explanation:  There are two ways to climb to the top.

        1. 1 step + 1 step
        2. 2 steps
        Example 2:

        Input: 3
        Output:  3
        Explanation:  There are three ways to climb to the top.

        1. 1 step + 1 step + 1 step
        2. 1 step + 2 steps
        3. 2 steps + 1 step
     */

    //Solution 1 : 迭代
    //n = 5:
    //i = 2, s1 = 1, s2 = 1, res = 2
    //i = 3, s1 = 2, s2 = 1, res = 3
    //i = 4, s1 = 3, s2 = 2, res = 5
    //i = 5, s1 = 5, s2 = 3, res = 8
     public static int climbStairs1(int n) {
         if (n <= 1) return 1;
         //number of possible ways
         //s1 : one step and one step, one way of climbing to 2
         //s2 : two steps one time, one way of climbing to 2
         int s1 = 1, s2 = 1;
         int res = 0;
         for (int i = 2; i <= n; i++) {
             res = s1 + s2;
             s2 = s1;
             s1 = res;
         }

         return res;
     }

    /**
     * Solution 2 : 递归 with memorization
     * n = 5
     * (4) + (3) = (3) + (2) + (2) + (1) = (2) + (1) + (2) + (2) + (1) = 2 + 1 + 2 + 2 + 1 = 8
     *
     * Time : O(2 ^ n)
     */

    public int climbStairs2(int n) {
        //!!! n+1
        int[] mem = new int[n+1];
        return helper(mem, n);
    }

    public static int helper(int[] mem, int n) {
        if (n <= 2) {
            return n;
        } else if(mem[n] != 0) {
            return mem[n];
        }else {
            int sum = helper(mem, n - 1) + helper(mem, n-2);
            mem[n] = sum;
            return sum;
        }
    }

}
