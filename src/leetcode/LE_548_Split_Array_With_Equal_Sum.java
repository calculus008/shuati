package leetcode;

import java.util.HashSet;

public class LE_548_Split_Array_With_Equal_Sum {
    /**
     * Given an array with n integers, you need to find if there are triplets (i, j, k)
     * which satisfies following conditions:
     *
     * 0 < i, i + 1 < j, j + 1 < k < n - 1
     * Sum of subarrays (0, i - 1), (i + 1, j - 1), (j + 1, k - 1)
     * and (k + 1, n - 1) should be equal.
     *
     * where we define that subarray (L, R) represents a slice of the original array
     * starting from the element indexed L to the element indexed R.
     *
     * Example:
     * Input: [1,2,1,2,1,2,1]
     * Output: True
     * Explanation:
     * i = 1, j = 3, k = 5.
     * sum(0, i - 1) = sum(0, 0) = 1
     * sum(i + 1, j - 1) = sum(2, 2) = 1
     * sum(j + 1, k - 1) = sum(4, 4) = 1
     * sum(k + 1, n - 1) = sum(6, 6) = 1
     *
     * Note:
     * 1 <= n <= 2000.
     * Elements in the given array will be in range [-1,000,000, 1,000,000].
     *
     * Medium
     */

    /**
     * Prefix Sum + HashSet
     *
     * https://leetcode.com/articles/split-array-with-equal-sum/
     *
     * Cut array into four parts that have equal sum.
     *
     *            middle cut
     * |______|_______|_______|______|
     *
     *
     * We start by traversing over the possible positions for the middle cut formed by j.
     * For every jj, firstly, we find all the left cut's positions, i, that lead to
     * equalizing the sum of the first and the second part
     *(i.e. sum[i-1] = sum [j-1] - sum[i]sum[i−1]=sum[j−1]−sum[i]) and store such sums
     * in the setset (a new HashSet is formed for every jj chosen). Thus, the presence of
     * a sum in setset implies that such a sum is possible for having equal sum of the
     * first and second part for the current position of the middle cut(j).
     *
     * Then, we go for the right cut and find the position of the right cut that leads to
     * equal sum of the third and the fourth part
     * (sum[n-1]-sum[k]=sum[k-1] - sum[j]sum[n−1]−sum[k]=sum[k−1]−sum[j]),
     * for the same middle cut as chosen earlier. We also, look if the same sum exists in
     * the setset. If so, such a triplet (i, j, k)(i,j,k) exists which satisfies the required
     * criteria, otherwise not.
     *
     * 注意 ： 这道题的特殊之处，一共有3个cut points, 每一个cut point都是在一个数组的元素上，这些
     * cut points元素的值不计入任何一个group sum. 比如:
     *    cp1   cp2   cp3
     * [1, 2, 1, 2, 1, 2, 1]
     *
     * cp1 is at index 1
     * cp2 is at index 3
     * cp3 is at index 5
     *
     * 这是这道题和 LE_698_Partition_To_K_Equal_Sum_Subsets 的本质区别。
     *
     * Time complexity : O(n^2). One outer loop and two inner loops are used.
     * Space complexity : O(n).  HashSet size can go up to nn.
     */
    public class Solution {
        public boolean splitArray(int[] nums) {
            if (null == nums || nums.length < 7) return false;

            int n = nums.length;
            int[] sum = new int[n];
            sum[0] = nums[0];

            for (int i = 1; i < n; i++) {
                sum[i] = sum[i - 1] + nums[i];
            }

            /**
             * j : middle cut, we need to have the first cut to the left of middle cut
             * and each cut has at least 1 element, therefore iteration should start
             * from index 3. For same reason, j < n - 3
             *
             * idx 0 1 2 3 .... n -3, n - 2, n -1
             */
            for (int j = 3; j < n - 3; j++) {
                HashSet<Integer> set = new HashSet<>();

                /**
                 * Inside looping for j, we have a middle cut, look to its left
                 * to find all possible first cut (which cut the left half into
                 * two parts)
                 */
                for (int i = 1; i < j - 1; i++) {
                    /**
                     * If the first cut is at index i, the sum of the first group is
                     * sum[i - 1], the sum of the 2nd group is sum[j - 1] - sum[i],
                     * so here we have a possible first cut to form two groups which
                     * have equal sum.
                     */
                    if (sum[i - 1] == sum[j - 1] - sum[i]) {
                        set.add(sum[i - 1]);
                    }
                }


                /**
                 * After the first inner loop, set contains all possible equal group
                 * sum for the section left to the middle cut. Now, we just need to
                 * look to the right section, see if it is possible to have the 3rd
                 * cut which can form equal groups and group sum is in the set.
                 *
                 * Group sum to the left of the 3rd cut :
                 * sum[k - 1] - sum[j]
                 *
                 * Group sum to the right of the 3rd cut :
                 * sum[n - 1] - sum[k]
                 */
                //!!! k=j+2
                for (int k = j + 2; k < n - 1; k++) {
                    //!!! sum[n-1], not sum[n-2]
                    if ((sum[k - 1] - sum[j]) == (sum[n - 1] - sum[k])
                            && set.contains(sum[k - 1] - sum[j])) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
