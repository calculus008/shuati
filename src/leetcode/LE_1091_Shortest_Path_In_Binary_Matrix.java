package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_1091_Shortest_Path_In_Binary_Matrix {
    /**
     * In an N by N square grid, each cell is either empty (0) or blocked (1).
     *
     * A clear path from top-left to bottom-right has length k if and only if
     * it is composed of cells C_1, C_2, ..., C_k such that:
     *
     * Adjacent cells C_i and C_{i+1} are connected 8-directionally (ie., they
     * are different and share an edge or corner)
     * C_1 is at location (0, 0) (ie. has value grid[0][0])
     * C_k is at location (N-1, N-1) (ie. has value grid[N-1][N-1])
     * If C_i is located at (r, c), then grid[r][c] is empty (ie. grid[r][c] == 0).
     * Return the length of the shortest such clear path from top-left to bottom-right.
     * If such a path does not exist, return -1.
     *
     * Meidum
     */

    /**
     * Straight forward BFS, moves to 8 directions, instead of 4.
     * !!!
     * Must set VISITED status correctly
     * Check if we get to destination when popping coordinates fro queue
     */
    class Solution {
        public int shortestPathBinaryMatrix(int[][] grid) {
            if (grid == null || grid.length == 0 || grid[0][0]!= 0) return -1;

            int n = grid.length;

//            if (n == 1) return 1;

            Queue<int[]> q = new LinkedList<>();
            q.offer(new int[]{0, 0});

            /**
             * Set start point as Visited !!!!!
             */
            grid[0][0] = 2;
            int steps = 0;

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();
                    int x = cur[0];
                    int y = cur[1];

                    /**
                     * !!!
                     */
                    if (x == n - 1 && y == n - 1) return steps + 1;

                    for (int[] dir : dirs) {
                        int nx = x + dir[0];
                        int ny = y + dir[1];

                        if (nx < 0 || nx >= n || ny < 0 || ny >= n || grid[nx][ny] != 0) continue;

                        /**
                         * set grid[nx][ny] as visited, NOT grid[x][y]!!!!!
                         */
                        grid[nx][ny] = 2;
                        q.offer(new int[]{nx, ny});
                    }
                }
                steps++;
            }

            return -1;
        }
    }
}
