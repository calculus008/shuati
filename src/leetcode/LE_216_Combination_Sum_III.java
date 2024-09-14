package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/27/18.
 */
public class LE_216_Combination_Sum_III {
    /**
        Find all possible combinations of k numbers that add up to a number n,
        given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.

        Example 1:

        Input: k = 3, n = 7

        Output:

        [[1,2,4]]

        Example 2:

        Input: k = 3, n = 9

        Output:

        [[1,2,6], [1,3,5], [2,3,4]]


        Constraints:
        2 <= k <= 9
        1 <= n <= 60
        Medium

        https://leetcode.com/problems/combination-sum-iii
     */

    /**
     * DFS + backtracking
     *
     * Time : C(m, k) = C(9, k)
     * Space : O(k + k * number of answers), depth of recursion is k
     */
    class Solution1 {
        public List<List<Integer>> combinationSum3(int k, int n) {
            List<List<Integer>> res = new ArrayList<>();
            helper(res, new ArrayList<>(), k, n, 1);
            return res;
        }

        public void helper(List<List<Integer>> res, List<Integer> temp, int k, int n, int start) {
            if (k == 0 && n == 0) {
                res.add(new ArrayList<>(temp));
                return;
            }

            for (int i = start; i <= 9; i++) {
                temp.add(i);
                helper(res, temp, k - 1, n - i, i + 1);
                temp.remove(temp.size() - 1);
            }
        }
    }

    /**
     * bit
     *
     * Time : O(2 ^ m) = O(2 ^ 9)
     * Space : O(k + k * number of answers)
     */
    class Solution2 {
        public List<List<Integer>> combinationSum3(int k, int n) {
            List<List<Integer>> res = new ArrayList<>();

            for (int i = 0; i < (1 << 9); i++) {
                List<Integer> cur = new ArrayList<>();

                int sum = 0;
                for (int j = 1; j <= 9; j++) {
                    if ((i & (1 << (j - 1))) != 0) {
                        sum += j;
                        cur.add(j);
                    }
                }

                if (sum == n && cur.size() == k) {
                    res.add(cur);
                }
            }

            return res;
        }
    }
}
