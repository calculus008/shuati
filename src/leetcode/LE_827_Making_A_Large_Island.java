package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LE_827_Making_A_Large_Island {
    /**
         In a 2D grid of 0s and 1s, we change at most one 0 to a 1.

         After, what is the size of the largest island? (An island is
         a 4-directionally connected group of 1s).

         Example 1:
         Input: [[1, 0], [0, 1]]
         Output: 3
         Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.

         Example 2:
         Input: [[1, 1], [1, 0]]
         Output: 4
         Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.

         Example 3:
         Input: [[1, 1], [1, 1]]
         Output: 4
         Explanation: Can't change any 0 to 1, only one island with area = 4.

         Notes:
         1 <= grid.length = grid[0].length <= 50.
         0 <= grid[i][j] <= 1.

         Hard
     */

    /**
     * http://zxi.mytechroad.com/blog/graph/leetcode-827-making-a-large-island/
     *
     * DFS + coloring
     *
     * 1.DFS to find and color island, calculate area
     * 2.Iterate each 0 cell, calculate the area of the biggest land by set it to 1
     *
     * Time  : O(m * n)
     * Space : O(m * n)
     */
    class Solution {
        int[][] dirs = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int largestIsland(int[][] grid) {
            if (null == grid || grid.length == 0) {
                return 0;
            }

            int m = grid.length;
            int n = grid[0].length;
            int id = 2;
            int res = 0;

            Map<Integer, Integer> islands = new HashMap<>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        int area = dfs(grid, m, n, i, j, id);
                        /**
                         * !!!
                         * find the island with the max area for the cases:
                         * 1.Any connected islands by one cell is smaller than the max island
                         * 2.If there's only 1 island
                         */
                        res = Math.max(res, area);
                        islands.put(id, area);
                        id++;
                    }
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) {
                        int cur = getArea(grid, m, n, islands, i, j);
                        res = Math.max(res, cur);
                    }
                }
            }

            return res;
        }

        /**
         * DFS
         * Find a island starting from a "1" cell, color it and calculate/return area
         */
        private int dfs(int[][] grid, int m, int n, int x, int y, int id) {
            if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] != 1) {
                return 0;
            }

            grid[x][y] = id;

            int res = 1;//!!!
            for (int i = 0; i < 4; i++) {
                int nx = x + dirs[i][0];
                int ny = y + dirs[i][1];
                res += dfs(grid, m, n, nx, ny, id);
            }

            return res;
        }

        private int getArea(int[][] grid, int m, int n, Map<Integer, Integer> islands, int x, int y) {
            int res = 1;
            //!!!
            Set<Integer> visited = new HashSet<>();

            for (int i = 0; i < 4; i++) {
                int nx = x + dirs[i][0];
                int ny = y + dirs[i][1];

                /**
                 * !!!
                 * Never forget to validate a new coordinate after it is generated using dirs[][]
                 */
                if (nx < 0 || nx >= m || ny < 0 || ny >= n || grid[nx][ny] == 0) {
                    continue;
                }

                int island = grid[nx][ny];
                /**
                 * !!!
                 * Only add island area if it is NOT visited
                 */
                if (visited.contains(island)) {
                    continue;
                }

                if (island > 1) {
                    res += islands.get(island);
                    visited.add(island);
                }
            }

            return res;
        }
    }

    class Solution_Practice {
        public int largestIsland(int[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int res = 0;
            int m = grid.length;
            int n = grid[0].length;
            int id = 2;
            Map<Integer, Integer> map = new HashMap<>();
            int max = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        int area = getArea(grid, m, n, i, j, id);
                        map.put(id, area);
                        id++;
                        max = Math.max(max, area);
                    }
                }
            }

            /**
             * If there's no "0" in grid
             */
            if (max == m * n) return max;

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) {
                        int x = 1;
                        Set<Integer> set = new HashSet<>();

                        for (int k = 0; k < 4; k++) {
                            int nx = i + dirs[k][0];
                            int ny = j + dirs[k][1];

                            if (nx < 0 || nx >= m || ny < 0 || ny >= n || grid[nx][ny] < 2) {
                                continue;
                            }

                            if (set.add(grid[nx][ny])) {
                                x += map.get(grid[nx][ny]);
                            }
                        }
                        res = Math.max(res, x);
                    }
                }
            }

            return res;
        }

        private int getArea(int[][] grid, int m, int n, int x, int y, int id) {
            if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] != 1) return 0;//!!! return 0

            grid[x][y] = id;

            int res = 1;
            res += getArea(grid, m, n, x + 1, y, id);
            res += getArea(grid, m, n, x - 1, y, id);
            res += getArea(grid, m, n, x, y + 1, id);
            res += getArea(grid, m, n, x, y - 1, id);

            return res;
        }
    }
}