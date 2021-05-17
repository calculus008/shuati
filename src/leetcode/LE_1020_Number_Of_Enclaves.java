package leetcode;

public class LE_1020_Number_Of_Enclaves {
    /**
     * You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.
     * A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking
     * off the boundary of the grid.
     *
     * Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any
     * number of moves.
     *
     * Input: grid =
     * [[0,0,0,0],
     *  [1,0,1,0],
     *  [0,1,1,0],
     *  [0,0,0,0]]
     *
     * Output: 3
     * Explanation: There are three 1s that are enclosed by 0s, and one 1 that is not enclosed because its on the boundary.
     *
     * Medium
     */

    /**
     * Similar problem : LE_1254_Number_Of_Closed_Islands
     */
    class Solution {
        public int numEnclaves(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            /**
             * first, for 0 at the edge, run dfs to fill all cells related it to 1
             * because they are not part of "enclosed island".
             */
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                        if (grid[i][j] == 1) {
                            helper(grid, i, j);
                        }
                    }
                }
            }

            /**
             * second, after the first step, we can use dfs to count valid enclosed
             * islands.
             */
            int res = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
//                        res += helper(grid, i, j);
                        /**
                         * !!!
                         * no need to do dfs here, just count the remaining "1" cells.
                         */
                        res++;
                    }
                }
            }
            return res;
        }

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        private int helper(int[][] grid, int x, int y) {
            if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length || grid[x][y] == 0) return 0;

            grid[x][y] = 0;
            int ret = 1;
            for (int[] dir : dirs) {
                ret += helper(grid, x + dir[0], y + dir[1]);
            }

            return ret;
        }
    }

}
