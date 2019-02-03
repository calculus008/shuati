package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 5/6/18.
 */
public class LE_317_Shortest_Distance_From_All_Buildings {
    /**
     You want to build a house on an empty land which reaches all buildings in the shortest amount of distance.
     You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:

     Each 0 marks an empty land which you can pass by freely.
     Each 1 marks a building which you cannot pass through.
     Each 2 marks an obstacle which you cannot pass through.
     For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):

     1 - 0 - 2 - 0 - 1
     |   |   |   |   |
     0 - 0 - 0 - 0 - 0
     |   |   |   |   |
     0 - 0 - 1 - 0 - 0
     The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal. So return 7.

     Note:
     There will be at least one building. If it is not possible to build such house according to the above rules, return -1.

     Hard
     */

    /**
     * Solution 1
     * BFS, important
     * Time  : O(m ^ 2 * n ^ 2)
     * Space : O(m * n)
     *
     * Key : Starting from every building (1), update dist[][] and nums[][], then run another pass to find the min distance
     *       单源点(empty land)多终点(houses)可以转化为多源点(houses)单终点(empty land)问题，终点就是所有源点都覆盖过的点，最后取距离和的最小值
     **/
    public class Solution1 {
        public int shortestDistance(int[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) {
                return -1;
            }

            int m = grid.length;
            int n = grid[0].length;
            int[][] dist = new int[m][n]; //For empty land, sum of distance to all buildings it can reach
            int[][] nums = new int[m][n]; //For empty land, how many builds it can reach
            int buildingCount = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        buildingCount++;//!!! find out how many builds in grid, update it in nums[][]
                        bfs(grid, dist, nums, i, j, m, n);
                    }
                }
            }

            int res = Integer.MAX_VALUE;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    /**
                     * Valid cells are :
                     * 1.empty (0)
                     * 2.Can be reached from all buildings !!!
                     * 3.Distance to all buildings are not zero
                     */
                    if (grid[i][j] == 0 && nums[i][j] == buildingCount && dist[i][j] != 0) {
                        res = Math.min(res, dist[i][j]);
                    }
                }
            }

            return res == Integer.MAX_VALUE ? -1 : res;
        }

        public void bfs(int[][] grid, int[][] dist, int[][] nums, int row, int col, int m, int n) {
            //!!! "<int[]>"
            Queue<int[]> queue = new LinkedList<>();
            boolean[][] visited = new boolean[m][n];
            queue.offer(new int[]{row, col});
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            /**
             * For BFS, each level means distance plus 1, so we have to start from a building, not empty land,
             * since we can't add 1 to distance when starting from empty land, we simply don't know where
             * is destination.
             */
            int distance = 0;

            while (!queue.isEmpty()) {
                int size = queue.size();
                distance++;//!!! increment distance here

                for (int i = 0; i < size; i++) {
                    int[] cur = queue.poll();
                    for (int j = 0; j < dirs.length; j++) {
                        int x = cur[0] + dirs[j][0];
                        int y = cur[1] + dirs[j][1];

                        //!!! only process valid cells
                        if (x >= 0 && x < m && y >= 0 && y < n && !visited[x][y] && (grid[x][y] == 0)) {
                            visited[x][y] = true;
                            dist[x][y] += distance;
                            nums[x][y]++;
                            queue.offer(new int[]{x, y});
                        }
                    }
                }
            }
        }
    }

    /**
     * Solution 2
     *
     * Much faster than Soluion 1 (15 ms vs 58 ms)
     *
     * Optimization :
     * Not using visited[][] as in Solution 1.
     * Instead, I walk only onto the cells that were reachable from all previous buildings.
     * From the first building, I only walk onto cells where grid is 0, and make them -1.
     * From the second building ,I only walk onto cells where grid is -1, and I make them -2.
     * And so on.
     *
     * We can do this because the post office should be built on a empty land that should be
     * able to access all buildings (value 1). So if an empty land is not accessible by the first
     * building in the loop, we are not interested in it. The trick is how to mark it so that
     * we don't need to repeatedly visit it in the following bfs. We use "walk" and keep
     * subtracting one before each bfs.
     *
     * It seems it is still O(m ^ 2 * n ^ 2), or it is O(m * n * k) (k is number of houses) ??
     */
    public class Solution2 {
        int minDist = -1;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int shortestDistance(int[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) {
                return minDist;
            }

            int m = grid.length, n = grid[0].length;
            int[][] dist = new int[m][n];
            int walk = 1;//!!!

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        walk--; //!!!
                        bfs(i, j, walk, grid, dist);
                    }
                }
            }

            return minDist;
        }

        private void bfs(int i, int j, int walk, int[][] grid, int[][] dist) {
            minDist = -1;
            Queue<int[]> q = new LinkedList<>();
            q.offer(new int[]{i, j});
            int depth = 0;

            while (!q.isEmpty()) {
                depth++;
                int len = q.size();

                for (int k = 0; k < len; k++) {
                    int[] pos = q.poll();
                    for (int d = 0; d < 4; d++) {
                        for (int l = 0; l < dirs.length; l++) {
                            int r = pos[0] + dirs[l][0];
                            int c = pos[1] + dirs[l][1];

                            if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length
                                    && grid[r][c] == walk) {
                                grid[r][c] = walk - 1;//!!!, not "walk--", we don't want to change its value yet
                                dist[r][c] += depth;

                                if (minDist < 0 || minDist > dist[r][c]) {
                                    minDist = dist[r][c];
                                }

                                q.offer(new int[]{r, c});
                            }
                        }
                    }
                }
            }
        }
    }
}
