package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class LE_1368_Minimum_Cost_To_Make_At_Least_One_Valid_Path_In_A_Grid {
    /**
     * Given a m x n grid. Each cell of the grid has a sign pointing to the next cell you should visit if you are
     * currently in this cell. The sign of grid[i][j] can be:
     * 1 which means go to the cell to the right. (i.e go from grid[i][j] to grid[i][j + 1])
     * 2 which means go to the cell to the left. (i.e go from grid[i][j] to grid[i][j - 1])
     * 3 which means go to the lower cell. (i.e go from grid[i][j] to grid[i + 1][j])
     * 4 which means go to the upper cell. (i.e go from grid[i][j] to grid[i - 1][j])
     * Notice that there could be some invalid signs on the cells of the grid which points outside the grid.
     *
     * You will initially start at the upper left cell (0,0). A valid path in the grid is a path which starts from the
     * upper left cell (0,0) and ends at the bottom-right cell (m - 1, n - 1) following the signs on the grid. The valid
     * path doesn't have to be the shortest.
     *
     * You can modify the sign on a cell with cost = 1. You can modify the sign on a cell one time only.
     *
     * Return the minimum cost to make the grid have at least one valid path.
     *
     * Example 1:
     * Input: grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
     * Output: 3
     * Explanation: You will start at point (0, 0).
     * The path to (3, 3) is as follows. (0, 0) --> (0, 1) --> (0, 2) --> (0, 3) change the arrow to down with
     * cost = 1 --> (1, 3) --> (1, 2) --> (1, 1) --> (1, 0) change the arrow to down with cost = 1 --> (2, 0) --> (2, 1)
     * --> (2, 2) --> (2, 3) change the arrow to down with cost = 1 --> (3, 3)
     * The total cost = 3.
     *
     * Example 2:
     * Input: grid = [[1,1,3],[3,2,2],[1,1,4]]
     * Output: 0
     * Explanation: You can follow the path from (0, 0) to (2, 2).
     *
     * Example 3:
     * Input: grid = [[1,2],[4,3]]
     * Output: 1
     *
     * Example 4:
     * Input: grid = [[2,2,2],[2,2,2]]
     * Output: 3
     *
     * Example 5:
     * Input: grid = [[4]]
     * Output: 0
     *
     * Constraints:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     *
     * Hard
     */

    /**
     * BFS + DFS
     *
     * A good problem that uses both BFS and DFS
     *
     * "Return the minimum cost to make the grid have at least one valid path" -> min cost or shorted path suggests a BFS solution.
     *
     * From (0, 0), for all the cells that can be reached (without changing existing direction value in each cell), the cost is 0.
     * So the starting point of our BFS is not a single cell, but all the cells that we can each in existing grid. To find all
     * reachable cells, we use DFS, in DFS, we put each reachable cell in a queue which will be used in BFS.
     *
     * In BFS, we will change existing directions and try the other 3 directions by, again, using DFS, adding newly reachable cells into queue. Each
     * iteration of the while loop means we changed one cell direction, hence, cost plus 1. Then in the end, if we reach (m - 1, n - 1)
     * , the cost must be the minimum one. It is the answer we want.
     *
     * Tricks used:
     * 1.For directions, index 0, 1, 2, 3 in dirs[][] correspond to 1, 2, 3, 4 (dir values in given cell), so we can get direction
     *   index for dirs by doing "grid[x][y] - 1"
     * 2.dp[][] is used for the purposes of both recording the cost and marking if it is visited.
     * 3.For changing directions, we still call dfs() for 4 directions, then in dfs(), if the cell has already been visited (by checking
     *   if dp[x][y] is still its init value (max value)), we just return (by pass that direction).
     *
     */
    class Solution {
        //right, left, down, up
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        public int minCost(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            int[][] dp = new int[m][n];
            for (int[] arr : dp) {
                Arrays.fill(arr, Integer.MAX_VALUE);
            }

            Queue<int[]> q = new LinkedList<>();
            dfs(grid, 0, 0, q, dp, 0);

            int cost = 0;
            while (!q.isEmpty()) {
                cost++;
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();

                    if (cur[0] == m - 1 && cur[1] == n - 1) return dp[m - 1][n - 1];

                    for (int j = 0; j < 4; j++) {
                        dfs(grid, cur[0] + dirs[j][0], cur[1] + dirs[j][1], q, dp, cost);
                    }
                }
            }

            return dp[m - 1][n - 1];
        }

        private void dfs(int[][] grid, int x, int y, Queue<int[]> q, int[][] dp, int cost) {
            if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || dp[x][y] != Integer.MAX_VALUE) return;

            q.offer(new int[]{x, y});
            dp[x][y] = cost;
            int next = grid[x][y] - 1;

            dfs(grid, x + dirs[next][0], y + dirs[next][1], q, dp, cost);
        }
    }
}
