package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 10/28/18.
 */
public class LE_377_Combination_Sum_IV {
    /**
         Given an integer array with all positive numbers and no duplicates,
         find the number of possible combinations that add up to a positive integer target.

         Example:

         nums = [1, 2, 3]
         target = 4

         The possible combination ways are:
         (1, 1, 1, 1)
         (1, 1, 2)
         (1, 2, 1)
         (1, 3)
         (2, 1, 1)
         (2, 2)
         (3, 1)

         Note that different sequences are counted as different combinations.

         (For different sequences are counted as the same combinations, check :
          LI_562_Backpack_IV )

         Therefore the output is 7.

         Follow up:
         What if negative numbers are allowed in the given array?
         How does it change the problem?
         What limitation we need to add to the question to allow negative numbers?

         Medium
     */

    /**
     * Key :
     *
     * 1.It's actually permutation sum
     * 2.Unbounded - each element can be used unlimited times.
     *
     * Looks similar to LE_39_Combination_Sum, but it asks to return only
     * the count of different numbers, NOT all combinations. Therefore we
     * can use DP to have better Time Complexity. Also we can't use solution
     * for LE_39_Combination_Sum, this problem is actually NOT COMBINATION,
     * but PERMUTATION instead. As example shows :
     *
     * (1, 1, 2) and (1, 2, 1) counted as different solution, this is permutation
     * !!!
     * So problem title is misleading, it should be Permutation Sum.
     *
     * This is different from LE_518_Coin_Change_II
     *
     * Here we want number of combinations, in example above,
     * {1, 1, 2} and {1, 2, 1} count as different combination.
     * But for LE_518_Coin_Change_II, they will be counted as
     * one way.
     *
     * 如果写成了完全背包，这样的写法不能区分数字一样顺序不同的那些结果
     */

    /**
     * https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation
     *
     * Solution 1
     * Recursion
     */
    class Solution1 {
        public int combinationSum4_1(int[] nums, int target) {
            if (target == 0) {
                return 1; //!!!
            }
            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                if (target >= nums[i]) {
                    res += combinationSum4_1(nums, target - nums[i]);
                }
            }
            return res;
        }
    }

    /**
     * Recursion with Mem
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-377-combination-sum-iv/
     *
     * Time  : O(sum(target / num[i]), upper bound close to O(n * target)
     * Space : O(target)
     */
    class Solution2 {
        int[] mem;
        public int combinationSum4_2(int[] nums, int target) {
            mem = new int[target + 1];
            Arrays.fill(mem, -1);
            //!!!
            mem[0] = 1;

            return helper1(nums, target);

        }

        private int helper1(int[] nums, int target) {
            if (target < 0) {
                return 0;
            }

            if (mem[target] != -1) {
                return mem[target];
            }

            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                res += helper1(nums, target - nums[i]);
            }

            mem[target] = res;
            return res;
        }
    }

    /**
     * Solution 3
     * DP top-down
     */
    class Solution3 {
        /**
         * dp[i] : number of combinations which can be summed to i
         */
        private int[] dp;

        public int combinationSum4(int[] nums, int target) {
            dp = new int[target + 1];
            Arrays.fill(dp, -1);

            /**
             * !!!!!
             */
            dp[0] = 1;

            return helper(nums, target);
        }

        private int helper(int[] nums, int target) {
            if (dp[target] != -1) {
                return dp[target];
            }
            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                if (target >= nums[i]) {
                    res += helper(nums, target - nums[i]);
                }
            }
            dp[target] = res;
            return res;
        }
    }


    /**
     * Time : O(target * |nums\)
     *
     * dp[1] += dp[1 - 1]=1
     * dp[2] += dp[2 - 1]=1
     * dp[2] += dp[2 - 2]=2
     * dp[3] += dp[3 - 1]=2
     * dp[3] += dp[3 - 2]=3
     * dp[3] += dp[3 - 3]=4
     * dp[4] += dp[4 - 1]=4
     * dp[4] += dp[4 - 2]=6
     * dp[4] += dp[4 - 3]=7
     */
    public int backPackVI(int[] nums, int target) {
        int[] dp = new int[target + 1];
        /**
         * !!!!!!
         */
        dp[0] = 1;

        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (i - num >= 0) {
                    dp[i] += dp[i - num];
                }
            }
        }

        return dp[target];
    }

    /**
     * As comparison, here is the solution for LE_518_Coin_Change_II
     *
     * dp[1] += dp[1 - 1]=1
     * dp[2] += dp[2 - 1]=1
     * dp[3] += dp[3 - 1]=1
     * dp[4] += dp[4 - 1]=1
     * dp[5] += dp[5 - 1]=1
     * dp[2] += dp[2 - 2]=2
     * dp[3] += dp[3 - 2]=2
     * dp[4] += dp[4 - 2]=3
     * dp[5] += dp[5 - 2]=3
     * dp[5] += dp[5 - 5]=4
     */
    public int change(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int num : nums) {
            for(int i = num; i <= target; i++) {
                dp[i] += dp[i - num];
            }
        }

        return dp[target];
    }

    /**
     * Follow up:
     *          What if negative numbers are allowed in the given array?
     *          How does it change the problem?
     *          What limitation we need to add to the question to allow negative numbers?
     *
     * The problem with negative numbers is that now the combinations could be potentially of
     * infinite length. Think about nums = [-1, 1] and target = 1. We can have all sequences
     * of arbitrary length that follow the patterns -1, 1, -1, 1, ..., -1, 1, 1 and 1, -1, 1,
     * -1, ..., 1, -1, 1 (there are also others, of course, just to give an example). So we
     * should limit the length of the combination sequence, so as to give a bound to the problem.
     *
     * In order to allow negative integers, the length of the combination sum needs to be restricted,
     * or the search will not stop. This is a modification from my previous solution, which also use
     * memory to avoid repeated calculations.
     */
    class Solution_FollowUp {
        /**
         * Map : Key - target values
         *       Value - Map<Integer, Integer> : key - len of the combination, val - values
         */
        Map<Integer, Map<Integer, Integer>> map2 = new HashMap<>();

        private int helper2(int[] nums, int len, int target, int MaxLen) {
            int count = 0;

            if (len > MaxLen) return 0;

            if (map2.containsKey(target) && map2.get(target).containsKey(len)) {
                return map2.get(target).get(len);
            }

            if (target == 0) {
                count++;
            }

            for (int num : nums) {
                count += helper2(nums, len + 1, target - num, MaxLen);
            }

            if (!map2.containsKey(target)) {
                map2.put(target, new HashMap<Integer, Integer>());
            }

            Map<Integer, Integer> mem = map2.get(target);
            mem.put(len, count);

            return count;
        }

        public int combinationSum42(int[] nums, int target, int MaxLen) {
            if (nums == null || nums.length == 0 || MaxLen <= 0) return 0;

            map2 = new HashMap<>();

            return helper2(nums, 0, target, MaxLen);
        }

//        int[] mem;
//        public int combinationSum4_2(int[] nums, int target, int maxLen) {
//            mem = new int[target + 1];
//            Arrays.fill(mem, -1);
//            //!!!
//            mem[0] = 1;
//
//            return helper1(nums, target, maxLen, 0);
//
//        }
//
//        private int helper1(int[] nums, int target, int maxLen, int len) {
//            if (target < 0) {
//                return 0;
//            }
//
//            if (mem[target] != -1) {
//                return mem[target];
//            }
//
//            int res = 0;
//            for (int i = 0; i < nums.length; i++) {
//                res += helper1(nums, target - nums[i]);
//            }
//
//            mem[target] = res;
//            return res;
//        }
    }
}
