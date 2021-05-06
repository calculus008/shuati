package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_1293_Shortest_Path_In_A_Grid_With_Obstacles_Elimination {
    /**
     * Given a m * n grid, where each cell is either 0 (empty) or 1 (obstacle). In one step, you can move up, down,
     * left or right from and to an empty cell.
     *
     * Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m-1, n-1)
     * given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.
     *
     *
     * Example 1:
     * Input:
     * grid =
     * [[0,0,0],
     *  [1,1,0],
     *  [0,0,0],
     *  [0,1,1],
     *  [0,0,0]],
     * k = 1
     * Output: 6
     * Explanation:
     * The shortest path without eliminating any obstacle is 10.
     * The shortest path with one obstacle elimination at position (3,2) is 6. Such path is
     * (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
     *
     *
     * Example 2:
     * Input:
     * grid =
     * [[0,1,1],
     *  [1,1,1],
     *  [1,0,0]],
     * k = 1
     * Output: -1
     * Explanation:
     * We need to eliminate at least two obstacles to find such a walk.
     *
     *
     * Constraints:
     * grid.length == m
     * grid[0].length == n
     * 1 <= m, n <= 40
     * 1 <= k <= m*n
     * grid[i][j] == 0 or 1
     * grid[0][0] == grid[m-1][n-1] == 0
     *
     * Hard
     */

    /**
     * Greedy + BFS
     *
     * Key lead : "Return the minimum number of steps to walk" -> shortest path -> BFS
     *
     * A variation of BFS, if no obstacle condition, then it's just a straight forward BFS.
     * For each step, to tell if we have visited the current state, we just need to know its
     * coordinates (x and y). With obstacles, the current state has a 3rd dimension - the number
     * of obstacles we have eliminated at a coordinate (x and y). Therefore visited should be a 3D
     * boolean array and the state info should be saved in an array with length of 3 (x, y, obstacles).
     *
     * We eliminate a obstacles whenever we run into one and we haven't reached k. Therefore, it is
     * greedy.
     *
     * Similar Problems:
     * LE_752_Open_The_Lock
     */
    class Solution {
        public int shortestPath(int[][] grid, int k) {
            int m = grid.length;
            int n = grid[0].length;

            boolean[][][] visited = new boolean[m][n][k + 1];
            Queue<int[]> q = new LinkedList<>();

            /**
             * Given condition "grid[0][0] == grid[m-1][n-1] == 0",
             * so the obstacle number of grid[0][0] is 0.
             */
            q.offer(new int[]{0, 0, 0});
            visited[0][0][0] = true;

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int steps = 0;

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();
                    int x = cur[0];
                    int y = cur[1];
                    int curk = cur[2];

                    /**
                     * Validate if target is hit after we remove node from queue,
                     * not when put node into the queue.
                     */
                    if (x == m - 1 && y == n - 1) {
                        return steps;
                    }

                    for (int[] dir : dirs) {
                        int nx = x + dir[0];
                        int ny = y + dir[1];
                        int nk = curk;

                        if (nx < 0 || ny < 0 || nx >= m || ny >= n) continue;

                        if (grid[nx][ny] == 1) {
                            nk++;
                        }

                        if (nk > k || visited[nx][ny][nk]) continue;

                        visited[nx][ny][nk] = true;
                        q.offer(new int[]{nx, ny, nk});
                    }
                }

                /**
                 * !!!
                 * Each step means one level in BFS tree structure.
                 * Only after we process all nodes in current level,
                 * we increase steps by one.
                 */
                steps++;
            }

            return -1;
        }
    }
}
