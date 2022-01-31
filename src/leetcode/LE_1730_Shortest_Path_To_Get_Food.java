package leetcode;

import java.util.*;

public class LE_1730_Shortest_Path_To_Get_Food {
    /**
     * You are starving and you want to eat food as quickly as possible. You want to find the shortest path to arrive at any food cell.
     *
     * You are given an m x n character matrix, grid, of these different types of cells:
     *
     * '*' is your location. There is exactly one '*' cell.
     * '#' is a food cell. There may be multiple food cells.
     * 'O' is free space, and you can travel through these cells.
     * 'X' is an obstacle, and you cannot travel through these cells.
     * You can travel to any adjacent cell north, east, south, or west of your current location if there is not an obstacle.
     *
     * Return the length of the shortest path for you to reach any food cell. If there is no path for you to reach food, return -1.
     *
     * Example 1:
     * Input: grid = [["X","X","X","X","X","X"],
     *                ["X","*","O","O","O","X"],
     *                ["X","O","O","#","O","X"],
     *                ["X","X","X","X","X","X"]]
     * Output: 3
     * Explanation: It takes 3 steps to reach the food.
     *
     * Example 2:
     * Input: grid = [["X","X","X","X","X"],
     *                ["X","*","X","O","X"],
     *                ["X","O","X","#","X"],
     *                ["X","X","X","X","X"]]
     * Output: -1
     * Explanation: It is not possible to reach the food.
     *
     * Example 3:
     * Input: grid = [["X","X","X","X","X","X","X","X"],
     *                ["X","*","O","X","O","#","O","X"],
     *                ["X","O","O","X","O","O","X","X"],
     *                ["X","O","O","O","O","#","O","X"],
     *                ["X","X","X","X","X","X","X","X"]]
     * Output: 6
     * Explanation: There can be multiple food cells. It only takes 6 steps to reach the bottom food.
     *
     *
     * Constraints:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 200
     * grid[row][col] is '*', 'X', 'O', or '#'.
     * The grid contains exactly one '*'.
     *
     * Medium
     *
     * https://leetcode.com/problems/shortest-path-to-get-food/
     */

    /**
     * Shortest Path -> BFS
     */
    class Solution {
        public int getFood(char[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            int[] start = new int[2];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '*') {
                        start[0] = i;
                        start[1] = j;
                        break;
                    }
                }
            }

            Queue<int[]> q = new LinkedList<>();
            q.offer(start);

            /**
             * !!!
             * Use boolean[][] to save visited info, NOT using SET!!!
             */
            boolean[][] visited = new boolean[m][n];
            visited[start[0]][start[1]] = true;

            int res = 1;
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();
                    int x = cur[0];
                    int y = cur[1];

                    for (int[] dir : dirs) {
                        int nx = x + dir[0];
                        int ny = y + dir[1];
                        int[] next = {nx, ny};

                        if (nx < 0 || nx >= m || ny < 0 || ny >= n || grid[nx][ny] == 'X' || visited[nx][ny]) continue;

                        if (grid[nx][ny] == '#') return res;

                        q.offer(next);
                        visited[nx][ny] = true;
                    }
                }

                res++;
            }

            return -1;
        }
    }
}
