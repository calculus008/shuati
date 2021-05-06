package leetcode;

public interface LE_1254_Number_Of_Closed_Islands {
    /**
     * Given a 2D grid consists of 0s (land) and 1s (water).  An island is a maximal 4-directionally connected group of 0s and a closed island is an island totally (all left, top, right, bottom) surrounded by 1s.
     *
     * Return the number of closed islands.
     *
     * Example 1:
     * Input: grid =
     * [[1,1,1,1,1,1,1,0],
     *  [1,0,0,0,0,1,1,0],
     *  [1,0,1,0,1,1,1,0],
     *  [1,0,0,0,0,1,0,1],
     *  [1,1,1,1,1,1,1,0]]
     *
     * Output: 2
     * Explanation:
     * Islands in gray are closed because they are completely surrounded by water (group of 1s).
     *
     * Example 2:
     * Input: grid =
     * [[0,0,1,0,0],
     *  [0,1,0,1,0],
     *  [0,1,1,1,0]]
     * Output: 1
     * Example 3:
     *
     * Input: grid = [[1,1,1,1,1,1,1],
     *                [1,0,0,0,0,0,1],
     *                [1,0,1,1,1,0,1],
     *                [1,0,1,0,1,0,1],
     *                [1,0,1,1,1,0,1],
     *                [1,0,0,0,0,0,1],
     *                [1,1,1,1,1,1,1]]
     * Output: 2
     *
     * Constraints:
     * 1 <= grid.length, grid[0].length <= 100
     * 0 <= grid[i][j] <=1
     *
     * Medium
     */

    /**
     * Similar problem: LE_1020_Number_Of_Enclaves
     */
    class Solution {
        public int closedIsland(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            /**
             * first, for 0 at the edge, run dfs to fill all cells related it to 1
             * because they are not part of "enclosed island".
             */
            for (int i = 0; i < m ; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                        if (grid[i][j] == 0) {
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
            for (int i = 0; i < m ; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) {
                        helper(grid, i, j);
                        res++;
                    }
                }
            }
            return res;
        }

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        private void helper(int[][] grid, int x, int y) {
            if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length || grid[x][y] == 1) return;

            grid[x][y] = 1;
            for (int[] dir : dirs) {
                helper(grid, x + dir[0], y + dir[1]);
            }
        }
    }
}
