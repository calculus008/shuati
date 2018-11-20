package leetcode;

/**
 * Created by yuank on 11/20/18.
 */
public class LE_740_Delete_And_Earn {
    /**
         Given an array nums of integers, you can perform operations on the array.

         In each operation, you pick any nums[i] and delete it to earn nums[i] points.
         After, you must delete every element equal to nums[i] - 1 or nums[i] + 1.

         You start with 0 points. Return the maximum number of points you can earn
         by applying such operations.

         Example 1:
         Input: nums = [3, 4, 2]
         Output: 6

         Explanation:
         Delete 4 to earn 4 points, consequently 3 is also deleted.
         Then, delete 2 to earn 2 points. 6 total points are earned.

         Example 2:
         Input: nums = [2, 2, 3, 3, 3, 4]
         Output: 9

         Explanation:
         Delete 3 to earn 3 points, deleting both 2's and the 4.
         Then, delete 3 again to earn 3 points, and 3 again to earn 3 points.
         9 total points are earned.

         Note:

         The length of nums is at most 20000.
         Each element nums[i] is an integer in the range [1, 10000].

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-740-delete-and-earn/
     *
     * LE_198_House_Robber 的变形题
     *
     * Reduce the problem to House Robber Problem
     * Key observations:
     *  If we take nums[i]，We can safely take all of its copies. We can’t take any of copies of nums[i – 1] and nums[i + 1]
     *
     *  This problem is reduced to 198 House Robber.
     *  Houses[i] has all the copies of num whose value is i.
     *
     *  [3 4 2] -> [0 2 3 4], rob([0 2 3 4]) = 6
     *
     *  [2, 2, 3, 3, 3, 4] -> [0 2*2 3*3 4], rob([0 2*2 3*3 4]) = 9
     *
     *  Time complexity: O(n+r) reduction + O(r) solving rob = O(n + r)
     *
     *  Space complexity: O(r)
     *  r = max(nums) – min(nums) + 1
     *
     *
     * 题意 - “ Each element nums[i] is an integer in the range [1, 10000]”
     * 所有元素为正整数，所以第一步类似于bucket sort, 把元素整合成house robber类似的数组。
     */

    /**
     * 简化版，空间 ：O(max)
     */
    class Solution1 {
        public int deleteAndEarn1(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }

            /**
             * !!!
             * 这种写法，必须单独处理只有一个元素的情况
             * 因为在下面，初始化时已经assume一定有两个以上的元素。
             */
            if (nums.length == 1) {
                return nums[0];
            }

            int max = Integer.MIN_VALUE;
            for (int num : nums) {
                max = Math.max(max, num);
            }

            int[] elements = new int[max];

            /**
             * !!!"[nums - 1]"
             */
            for (int num : nums) {
                elements[num - 1] += num;
            }

            int[] dp = new int[max];
            dp[0] = elements[0];
            dp[1] = Math.max(elements[0], elements[1]);

            for (int i = 2; i < elements.length; i++) {
                dp[i] = Math.max(dp[i - 1], elements[i] + dp[i - 2]);
            }

            return dp[max - 1];
        }

        public int deleteAndEarn2(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }

            int max = Integer.MIN_VALUE;
            for (int num : nums) {
                max = Math.max(max, num);
            }

            int[] elements = new int[max];

            for (int num : nums) {
                elements[num - 1] += num;
            }

            int len = elements.length;
            int[] dp = new int[len];
            for (int i = 0; i < len; i++) {
                /**
                 * 用这种写法不用单独处理只有一个元素的情况：
                 * i = 0, dp[0] = Math.max(elements[0], 0)
                 * i = 1, dp[1] = Math.max(elements[1], 0)
                 */
                dp[i] = Math.max(i < 2 ? elements[i] : elements[i] + dp[i - 2],
                        i < 1 ? 0 : dp[i - 1]);
            }

            return dp[len - 1];
        }

        /**
         * Space optimized : O(1)
         */
        public int deleteAndEarn3(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }

            int max = Integer.MIN_VALUE;
            for (int num : nums) {
                max = Math.max(max, num);
            }

            int[] elements = new int[max];//last index max - 1

            for (int num : nums) {
                elements[num - 1] += num;//"[num - 1]"
            }

            int len = elements.length;
            int dp1 = 0;
            int dp2 = 0;

            for (int i = 0; i < len; i++) {
                int dp = Math.max(i < 2 ? elements[i] : elements[i] + dp1,
                        i < 1 ? 0 : dp2);
                dp1 = dp2;
                dp2 = dp;
            }

            return dp2;
        }
    }

    /**
     * 最优 ：
     * Time  : O(n + r) reduction + O(r) solving rob = O(n + r)
     * Space : O(r)
     * r = max(nums) – min(nums) + 1
     */
    class Solution2 {
        public int deleteAndEarn(int[] nums) {
            if (null == nums || nums.length == 0) {
                return 0;
            }

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (int num : nums) {
                min = Math.min(min, num);
                max = Math.max(max, num);
            }

            int range = max - min + 1;
            int[] value = new int[range];

            for (int num : nums) {
                value[num - min] += num;
            }

            return helper(value);
        }

        public int helper(int[] nums) {
            if (null == nums || nums.length == 0) {
                return 0;
            }

            int len = nums.length;
            int dp1 = 0;
            int dp2 = 0;

            for (int i = 0; i < len; i++) {
                int dp = Math.max(i < 2 ? nums[i] : nums[i] + dp1,
                        i < 1 ? 0 : dp2);
                dp1 = dp2;
                dp2 = dp;
            }

            return dp2;
        }
    }
}
