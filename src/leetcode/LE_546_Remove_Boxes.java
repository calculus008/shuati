package leetcode;

/**
 * Created by yuank on 11/19/18.
 */
public class LE_546_Remove_Boxes {
    /**
         Given several boxes with different colors represented by different positive numbers.
         You may experience several rounds to remove boxes until there is no box left.
         Each time you can choose some continuous boxes with the same color (composed of k boxes, k >= 1),
         remove them and get k*k points.

         Find the maximum points you can get.

         Example 1:
         Input:

         [1, 3, 2, 2, 2, 3, 4, 3, 1]
         Output:
         23

         Explanation:
         [1, 3, 2, 2, 2, 3, 4, 3, 1]
         ----> [1, 3, 3, 4, 3, 1] (3*3=9 points)
         ----> [1, 3, 3, 3, 1] (1*1=1 points)
         ----> [1, 1] (3*3=9 points)
         ----> [] (2*2=4 points)
         Note: The number of boxes n would not exceed 100.

         Hard
     */

    /**
     * https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-546-remove-boxes/
     *
     * DP
     *
     * Solution: Recursion + Memorization
     *
     * Use dp[l][r][k] to denote the max score of subarray box[l] ~ box[r] with k boxes after box[r] that have the same color as box[r]
     * box[l], box[l+1], …, box[r], box[r+1], …, box[r+k]
     *
     * e.g. “CDABACAAAAA”
     *
     * dp[2][6][4] is the max score of [ABACA] followed by [AAAA]
     * dp[2][6][3] is the max score of [ABACA] followed by [AAA]
     *
     * base case: l > r, empty array, return 0.
     *
     * Transition:
     *  dp[l][r][k] = max(dp[l][r-1][0] + (k + 1)*(k + 1),  # case 1
     *  dp[l][i][k+1] + dp[i+1][r-1][0])                    # case 2
     *
     *  Example:
     *  # "ABACA|AAAA"
     *  # case 1: dp("ABAC") + score("AAAAA") drop j and the tail.
     *  # case 2: box[i] == box[r], l <= i < r, try all break points
     *
     *  # max({dp("A|AAAAA") + dp("BAC")}, {dp("ABA|AAAAA") + dp("C")})
     *
     *  Time  : O(n^4)
     *  Space : O(n^3)
     *
     */

    class Solution1 {
        int[][][] dp;
        public int removeBoxes(int[] boxes) {
            int n = boxes.length;
            dp = new int[n][n][n];

            return dfs(boxes, 0, n - 1, 0);
        }

        private int dfs(int[] boxes, int l, int r, int k) {
            if (l > r) return 0;

            if (dp[l][r][k] > 0) {
                return dp[l][r][k];
            }

            /**
             * Optimization
             * If more elements in side l-r has the same color as r,
             * move left side.
             *
             * With optimization, run time cut from 129 ms to 14 ms
             */
            while (l < r && boxes[r - 1] == boxes[r]) {
                r--;
                k++;
            }

            dp[l][r][k] = dfs(boxes, l, r - 1, 0) + (k + 1) * (k + 1);

            for (int i = l; i < r; i++) {
                if (boxes[i] == boxes[r]) {
                    dp[l][r][k] = Math.max(dp[l][r][k],
                            dfs(boxes, l, i, k + 1) + dfs(boxes, i + 1, r - 1, 0));
                }
            }

            return dp[l][r][k];
        }
    }
}
