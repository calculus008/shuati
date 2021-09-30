package leetcode;

import java.util.*;

public class LE_403_Frog_Jump {
    /**
     * A frog is crossing a river. The river is divided into some number of units, and at each unit, there may or may not
     * exist a stone. The frog can jump on a stone, but it must not jump into the water.
     *
     * Given a list of stones' positions (in units) in sorted ascending order, determine if the frog can cross the river
     * by landing on the last stone. Initially, the frog is on the first stone and assumes the first jump must be 1 unit.
     *
     * If the frog's last jump was k units, its next jump must be either k - 1, k, or k + 1 units. The frog can only jump
     * in the forward direction.
     *
     * Example 1:
     * Input: stones = [0,1,3,5,6,8,12,17]
     * Output: true
     * Explanation: The frog can jump to the last stone by jumping 1 unit to the 2nd stone, then 2 units to the 3rd stone,
     * then 2 units to the 4th stone, then 3 units to the 6th stone, 4 units to the 7th stone, and 5 units to the 8th stone.
     *
     * Example 2:
     * Input: stones = [0,1,2,3,4,8,9,11]
     * Output: false
     * Explanation: There is no way to jump to the last stone as the gap between the 5th and 6th stone is too large.
     *
     * Constraints:
     * 2 <= stones.length <= 2000
     * 0 <= stones[i] <= 231 - 1
     * stones[0] == 0
     * stones is sorted in a strictly increasing order.
     *
     * Hard
     *
     * https://leetcode.com/problems/frog-jump/
     */

    /**
     * DFS + memoization
     *
     * If DFS without memoization:
     * Time : O(3 ^ n), Recursion tree can grow upto 3 ^ n.
     * Space : O(n). Recursion of depth nn is used.
     *
     * With memoization:
     * Time  : O(n ^ 3), Memorization will reduce time complexity to O(n ^ 3), why?
     * https://stackoverflow.com/questions/53143539/how-does-complexity-get-reduced-to-on2-from-o2n-in-case-of-memoization
     *
     * With memoization, the function is called only as many times as you have distinct parameters: if you want to call
     * it with parameters you've used already, you save having to call it.
     *
     * Here the dfs() is called O(3 ^ n) times, but with only O(n ^ 3) different sets of parameters. Using memoization,
     * the function is called once only for each different parameter set (O(n ^ 3)) rather than each time the algorithm needs
     * the answer (O(3 ^ n)).
     *
     * Space : O(n ^ 2)
     */
    class Solution1 {
        int[] increments = {-1, 0, 1};

        public boolean canCross(int[] stones) {
            Set<Integer> dist = new HashSet<>();
            for (int x : stones) {
                dist.add(x);
            }

            /**
             * "visited" set save string "curPos,steps", which represent a state.
             * Just remember the position itself won't work.
             */
            Set<String> visited = new HashSet<>();
            visited.add(0 + "," + 1);

            return dfs(stones, 1, 1, dist, visited);
        }

        private boolean dfs(int[] stones, int pos, int last, Set<Integer> dist, Set<String> visited) {
            if (pos == stones[stones.length - 1]) {
                return true;
            }

            /**
             * !!!
             * Important : a jump from idx i may jump over the position in stones[i + 1] or more positions.
             * Therefore, need to put all valid positions in a set and check the current position is in
             * this set.
             */
            if (!dist.contains(pos)) return false;

            boolean res = false;
            for (int i : increments) {
                int next = last + i;
                if (next <= 0) continue;

                if (visited.contains(pos + "," + next)) continue;

                visited.add(pos + "," + next);//!!!
                res = res || dfs(stones, pos + next, next, dist, visited);
            }

            return res;
        }
    }

    /**
     * DP
     *
     * let dp[i][j] denote at stone i, the frog can or cannot make jump of size j
     *
     * // Recurrence relation:
     * for any j < i,
     * dist = stones[i] - stones[j];
     * if dp[j][dist]:
     *     dp[i][dist - 1] = ture
     *     dp[i][dist] = ture
     *     dp[i][dist + 1] = ture
     *
     * Time  : O(n ^ 2)
     * Space : O(n ^ 2)
     */
    class Solution2 {
        public boolean canCross(int[] stones) {
            int N = stones.length;
            boolean[][] dp = new boolean[N][N + 1];
            dp[0][1] = true;

            for(int i = 1; i < N; ++i){
                for(int j = 0; j < i; ++j){
                    int diff = stones[i] - stones[j];

                    if(diff < 0 || diff > N || !dp[j][diff]) continue;

                    dp[i][diff] = true;
                    if(diff - 1 >= 0) dp[i][diff - 1] = true;
                    if(diff + 1 <= N) dp[i][diff + 1] = true;

                    if(i == N - 1) return true;
                }
            }

            return false;
        }
    }
}
