package leetcode;

public class LE_1219_Path_with_Maximum_Gold {
    /**
     * In a gold mine grid of size m * n, each cell in this mine has an integer
     * representing the amount of gold in that cell, 0 if it is empty.
     *
     * Return the maximum amount of gold you can collect under the conditions:
     *
     * Every time you are located in a cell you will collect all the gold in that cell.
     * From your position you can walk one step to the left, right, up or down.
     * You can't visit the same cell more than once.
     * Never visit a cell with 0 gold.
     * You can start and stop collecting gold from any position in the grid that has some gold.
     *
     *
     * Example 1:
     * Input: grid = [[0,6,0],[5,8,7],[0,9,0]]
     * Output: 24
     * Explanation:
     * [[0,6,0],
     *  [5,8,7],
     *  [0,9,0]]
     * Path to get the maximum gold, 9 -> 8 -> 7.
     *
     * Example 2:
     * Input: grid = [[1,0,7],[2,0,6],[3,4,5],[0,3,0],[9,0,20]]
     * Output: 28
     * Explanation:
     * [[1,0,7],
     *  [2,0,6],
     *  [3,4,5],
     *  [0,3,0],
     *  [9,0,20]]
     * Path to get the maximum gold, 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7.
     *
     * Constraints:
     * 1 <= grid.length, grid[i].length <= 15
     * 0 <= grid[i][j] <= 100
     * There are at most 25 cells containing gold.
     *
     * Medium
     */

    /**
     * Each of the k gold cells can at most have 4 neighbors. Therefore:
     * Time  : O(k * 4 ^ k + m * n),
     * Space : O(m * n)
     */
    class Solution {
        public int getMaximumGold(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            int res = 0;
            for (int i = 0; i < m ;i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) continue;

                    /**
                     * Must try start from each valid cell, the path sum
                     * with each starting point may be different
                     */
                    res = Math.max(res, dfs(grid, new boolean[m][n], i, j));
                }
            }

            return res;
        }

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        private int dfs(int[][] grid, boolean visited[][], int x, int y) {
            if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == 0 || visited[x][y]) return 0;

            visited[x][y] = true;
            int res = 0;

            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                res = Math.max(res, dfs(grid, visited, nx, ny));
            }

            res += grid[x][y];
            visited[x][y] = false;

            return res;
        }
    }
}
