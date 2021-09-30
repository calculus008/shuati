package leetcode;

import java.util.*;

public class LE_446_Arithmetic_Slices_II_Subsequence {
    /**
     * Given an integer array nums, return the number of all the arithmetic subsequences of nums.
     *
     * A sequence of numbers is called arithmetic if it consists of at least three elements and if the difference between
     * any two consecutive elements is the same.
     *
     * For example, [1, 3, 5, 7, 9], [7, 7, 7, 7], and [3, -1, -5, -9] are arithmetic sequences.
     * For example, [1, 1, 2, 5, 7] is not an arithmetic sequence.
     * A subsequence of an array is a sequence that can be formed by removing some elements (possibly none) of the array.
     *
     * For example, [2,5,10] is a subsequence of [1,2,1,2,4,1,5,10].
     * The test cases are generated so that the answer fits in 32-bit integer.
     *
     * Example 1:
     * Input: nums = [2,4,6,8,10]
     * Output: 7
     * Explanation: All arithmetic subsequence slices are:
     * [2,4,6]
     * [4,6,8]
     * [6,8,10]
     * [2,4,6,8]
     * [4,6,8,10]
     * [2,4,6,8,10]
     * [2,6,10]
     *
     * Example 2:
     * Input: nums = [7,7,7,7,7]
     * Output: 16
     * Explanation: Any subsequence of this array is arithmetic.
     *
     * Constraints:
     * 1  <= nums.length <= 1000
     * -231 <= nums[i] <= 231 - 1
     *
     * Hard
     *
     * https://leetcode.com/problems/arithmetic-slices-ii-subsequence/
     */

    /**
     * DP
     *
     * https://github.com/wisdompeak/LeetCode/tree/master/Hash/446.Arithmetic-Slices-II-Subsequence
     * https://leetcode.com/problems/arithmetic-slices-ii-subsequence/discuss/92822/Detailed-explanation-for-Java-O(n2)-solution
     *
     * Problem of the same type:
     * LE_1027_Longest_Arithmetic_Subsequence
     *
     * Time  : O(n ^ 2)
     * Space : O(n ^ 2)
     *
     *
     * Input : [2,4,6,8,10]
     * Run output:
     * ------------
     * i=1,j=0,d=2: dp[1][2]=0, dp[0][2]=0
     *                            update : dp[1][2]=1
     *                            update res : 0
     * ------------
     * i=2,j=0,d=4: dp[2][4]=0, dp[0][4]=0
     *                            update : dp[2][4]=1
     *                            update res : 0
     * i=2,j=1,d=2: dp[2][2]=0, dp[1][2]=1
     *                            update : dp[2][2]=2  ({4, 6}, {2, 4, 6})
     *                            update res : 1
     * ------------
     * i=3,j=0,d=6: dp[3][6]=0, dp[0][6]=0
     *                            update : dp[3][6]=1
     *                            update res : 1
     * i=3,j=1,d=4: dp[3][4]=0, dp[1][4]=0
     *                            update : dp[3][4]=1
     *                            update res : 1
     * i=3,j=2,d=2: dp[3][2]=0, dp[2][2]=2
     *                            update : dp[3][2]=3
     *                            update res : 3
     * ------------
     * i=4,j=0,d=8: dp[4][8]=0, dp[0][8]=0
     *                            update : dp[4][8]=1
     *                            update res : 3
     * i=4,j=1,d=6: dp[4][6]=0, dp[1][6]=0
     *                            update : dp[4][6]=1
     *                            update res : 3
     * i=4,j=2,d=4: dp[4][4]=0, dp[2][4]=1
     *                            update : dp[4][4]=2
     *                            update res : 4
     * i=4,j=3,d=2: dp[4][2]=0, dp[3][2]=3
     *                            update : dp[4][2]=4
     *                            update res : 7
     */
    class Solution {
        public int numberOfArithmeticSlices(int[] nums) {
            int res = 0;
            int n = nums.length;

            /**
             *  dp[i][diff]表示以元素i结尾、公差为diff的等差数列（长度>=2）有多少个。
             *
             *  为什么我们会这么定义“长度>=2”？假设我们在i后面能接上一个元素k并保持这个公差diff的话，对于k而言，以其为结尾的长度>=3的
             *  等差数列的个数那就是dp[i][diff]，恰好就是我们想统计的。可见这样定义dp[i][diff]能给我们带来极大的方便。
             *  当然，对于k而言，你也必须正确地更新dp[k][diff]。注意DP变量的定义，以k为结尾的长度>=2的等差数列应该有dp[i][diff]+1个，
             *  这“+1”就是代表着仅包含{i,k}两个元素的数列。
             */
            Map<Integer, Integer>[] dp = new Map[n];

            for (int i = 0; i < n; i++) {
                dp[i] = new HashMap<>();

                for (int j = 0; j < i; j++) {
                    long diff = (long)nums[i] - nums[j];
                    if (diff <= Integer.MIN_VALUE || diff > Integer.MAX_VALUE) continue;

                    int d = (int)diff;

                    /**
                     * "getOrDefault(d, 0)"
                     * 因为我们保存的是count(个数), 所以当hashmap中没有以d为key的entry时，default value应当是0。
                     */
                    int c1 = dp[i].getOrDefault(d, 0);
                    int c2 = dp[j].getOrDefault(d, 0);

                    /**
                     * c2 : dp[j][d]表示以第j个元素结尾、公差为d的等差数列（长度>=2）有多少个。
                     * 现在我们知道，第i个元素在第j个元素之后，并且保持了共差d, 所以，以第i个元素为结尾，公差为d的等差数列（长度>=3）
                     * 的个数，就是c2, 也就是需要累加进最终答案的部分。
                     */
                    res += c2;
                    dp[i].put(d, c1 + c2 + 1);
                }
            }

            return res;
        }
    }
}
