package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_980_Unique_Paths_III {
    /**
     * On a 2-dimensional grid, there are 4 types of squares:
     *
     * 1 represents the starting square.  There is exactly one starting square.
     * 2 represents the ending square.  There is exactly one ending square.
     * 0 represents empty squares we can walk over.
     * -1 represents obstacles that we cannot walk over.
     * Return the number of 4-directional walks from the starting square to the ending square,
     * that walk over every non-obstacle square exactly once.
     *
     *
     *
     * Example 1:
     *
     * Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
     * Output: 2
     * Explanation: We have the following two paths:
     * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
     * 2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
     * Example 2:
     *
     * Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
     * Output: 4
     * Explanation: We have the following four paths:
     * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
     * 2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
     * 3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
     * 4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)
     * Example 3:
     *
     * Input: [[0,1],[2,0]]
     * Output: 0
     * Explanation:
     * There is no path that walks over every empty square exactly once.
     * Note that the starting and ending square can be anywhere in the grid.
     *
     *
     * Note:
     *
     * 1 <= grid.length * grid[0].length <= 20
     *
     * Hard
     */

    /**
     * Time  : O(4^{R*C})
     * Space : O(R*C)
     */
    class Solution {
        public int uniquePathsIII(int[][] grid) {
            if (null == grid || grid.length == 0) return 0;

            List<List<int[]>> res = new ArrayList<>();

            int count = 0;
            int x = 0, y = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] != -1) {
                        count++;
                    }

                    if (grid[i][j] == 1) {
                        x = i;
                        y = j;
                    }
                }
            }

            dfs(grid, new boolean[grid.length][grid[0].length], res, new ArrayList<>(), count, x, y);

            return res.size();
        }

        public void dfs(int[][] grid, boolean[][] visited, List<List<int[]>> res, List<int[]> temp, int count, int x, int y) {
            if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) return;
            if (grid[x][y] == -1 || visited[x][y]) return;

            if (grid[x][y] == 2) {
                if (count == 1) {
                    temp.add(new int[]{x, y});
                    res.add(new ArrayList<>(temp));
                    temp.remove(temp.size() - 1);
                    visited[x][y] = false;
                }
                return;
            }

            temp.add(new int[]{x, y});
            visited[x][y] = true;
            count--;

            int[][] dirs = new int[][]{{1, 0},{-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                dfs(grid, visited, res, temp, count, nx, ny);
            }

            temp.remove(temp.size() - 1);
            visited[x][y] = false;
            count++;
        }
    }

    public void dfs1(int[][] grid, boolean[][] visited, List<List<int[]>> res, List<int[]> temp, int count, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) return;
        if (grid[x][y] == -1 || visited[x][y]) return;

        temp.add(new int[]{x, y});
        visited[x][y] = true;
        count--;

        if (grid[x][y] == 2) {
            if (count == 0) {
                res.add(new ArrayList<>(temp));
            }

            temp.remove(temp.size() - 1);
            visited[x][y] = false;
            return;
        }

        dfs1(grid, visited, res, temp, count, x + 1, y);
        dfs1(grid, visited, res, temp, count, x - 1, y);
        dfs1(grid, visited, res, temp, count, x, y + 1);
        dfs1(grid, visited, res, temp, count, x, y - 1);

        temp.remove(temp.size() - 1);
        visited[x][y] = false;

        /**
         * count is local to the current level recursion, so no need to
         * recover it here and when we hit destination
         */
//        count++;
    }
}
