package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 5/6/18.
 */
public class LE_317_Shortest_Distance_From_All_Buildings {
    /**
         You want to build a house on an empty land which reaches all buildings in the shortest amount of distance. You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:

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
     BFS, important
     Time  : O(m ^ 2 * n ^ 2)
     Space : O(m * n)

     Key : Starting from every building (1), update dist[][] and nums[][], then run another pass to find the min distance
     **/
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }

        int m = grid.length;
        int n = grid[0].length;
        int[][] dist = new int[m][n];
        int[][] nums = new int[m][n];
        int buildingCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    buildingCount++;//!!! find out how many builds in grid, update it in nums[][]
                    bfs(grid, dist, nums, i, j, m ,n);
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                /**
                 * Valid cells are :
                 * 1.empty (0)
                 * 2.Can be reached from all buildings
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
                    if (x >= 0 && x < m && y >=0 && y < n && !visited[x][y] && (grid[x][y] == 0)) {
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
