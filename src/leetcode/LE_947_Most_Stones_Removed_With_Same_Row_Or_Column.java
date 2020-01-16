package src.leetcode;

import java.util.HashSet;
import java.util.Set;

public class LE_947_Most_Stones_Removed_With_Same_Row_Or_Column {
    /**
     * On a 2D plane, we place stones at some integer coordinate points.
     * Each coordinate point may have at most one stone.
     *
     * Now, a move consists of removing a stone that shares a column or
     * row with another stone on the grid.
     *
     * What is the largest possible number of moves we can make?
     *
     *
     *
     * Example 1:
     *
     * Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
     * Output: 5
     * Example 2:
     *
     * Input: stones = [[0,0],[0,2],[1,1],[2,0],[2,2]]
     * Output: 3
     * Example 3:
     *
     * Input: stones = [[0,0]]
     * Output: 0
     *
     *
     * Note:
     *
     * 1 <= stones.length <= 1000
     * 0 <= stones[i][j] < 10000
     *
     * Medium
     */

    /**
     * Points connected on the same row or column form a connected component,
     * each connected component will have 1 point left after "moves". so the
     * stones that can be moved is total number of stones - number of connected
     * components.
     *
     * Time : O(n ^ 2)
     */
    class Solution_DFS {
        public int removeStones(int[][] stones) {
            Set<int[]> visited = new HashSet<>();

            int count = 0;
            for (int[] s : stones) {
                if (!visited.contains(s)) {
                    dfs(stones, visited, s);
                    count++;
                }
            }

            return stones.length - count;
        }

        private void dfs(int[][] stones, Set<int[]> visited, int[] cur) {
            visited.add(cur);
            for (int[] s : stones) {
                if (visited.contains(s)) continue;

                if (s[0] == cur[0] || s[1] == cur[1]) {
                    dfs(stones, visited, s);
                }
            }
        }
    }
}
