package Interviews.Amazon;

import java.util.LinkedList;
import java.util.Queue;

public class Shortest_Path_To_Rooms {
    /**
     * 一个矩阵A，矩阵元素可以是一个房间，一扇门，或者一个障碍物。
     * 返回一个矩阵B with the same size，B[i][j]的值如下：
     * 若A[i][j]为门，返回0；
     * 若A[i][j]为障碍物，返回-1；
     * 若A[i][j]为房间，返回这个房间离最近的门的距离。
     *
     * 1 - door
     * 0 - room
     *-1 - obstacle
     *
     */

    public class Solution1 {
        public int shortestDistance(int[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) {
                return -1;
            }

            int m = grid.length;
            int n = grid[0].length;
            int[][] dist = new int[m][n]; //For empty land, sum of dirs to all buildings it can reach
            int[][] nums = new int[m][n]; //For empty land, how many builds it can reach
            int buildingCount = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
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

            /**
             * !!!
             */
            boolean[][] visited = new boolean[m][n];

            queue.offer(new int[]{row, col});
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int distance = 0;

            while (!queue.isEmpty()) {
                int size = queue.size();
                distance++;//!!! increment dirs here

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
}
