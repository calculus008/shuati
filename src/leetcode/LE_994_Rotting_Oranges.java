package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_994_Rotting_Oranges {
    /**
     * In a given grid, each cell can have one of three values:
     *
     * the value 0 representing an empty cell;
     * the value 1 representing a fresh orange;
     * the value 2 representing a rotten orange.
     * Every minute, any fresh orange that is adjacent (4-directionally)
     * to a rotten orange becomes rotten.
     *
     * Return the minimum number of minutes that must elapse until no cell has a fresh orange.
     * If this is impossible, return -1 instead.
     *
     * Example 1:
     * Input: [[2,1,1],[1,1,0],[0,1,1]]
     * Output: 4
     *
     * Example 2:
     * Input: [[2,1,1],[0,1,1],[1,0,1]]
     * Output: -1
     * Explanation:  The orange in the bottom left corner (row 2, column 0) is never rotten,
     *               because rotting only happens 4-directionally.
     * Example 3:
     * Input: [[0,2]]
     * Output: 0
     * Explanation:  Since there are already no fresh oranges at minute 0, the answer is just 0.
     * Note:
     *
     * 1 <= grid.length <= 10
     * 1 <= grid[0].length <= 10
     * grid[i][j] is only 0, 1, or 2.
     *
     * Medium
     *
     * https://leetcode.com/problems/rotting-oranges
     */

    /**
     * BFS
     */
    class Solution {
        public int orangesRotting(int[][] grid) {
            if (grid == null || grid.length == 0) return -1;

            int m = grid.length;
            int n = grid[0].length;

            Queue<int[]> q = new LinkedList<>();

            int count = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        count++;//get total number of good oranges to be used later to tell if no good oranges left
                    } else if (grid[i][j] == 2) {
                        q.offer(new int[]{i, j});//init BFS queue
                    }
                }
            }

            if (count == 0) return 0;

            if (q.isEmpty()) return -1;

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            int days = 1;

            while (!q.isEmpty()) {
                // days++;
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();
                    int x = cur[0];
                    int y = cur[1];

                    for (int[] dir : dirs) {
                        int nx = x + dir[0];
                        int ny = y + dir[1];

                        if (nx < 0 || nx >= m || ny < 0 || ny >= n || grid[nx][ny] != 1) {
                            continue;
                        }

                        grid[nx][ny] = 2;
                        count--;

                        if (count == 0) return days;

                        /**
                         * !!!
                         * Don't forget to enqueue the newly changed cell
                         */
                        q.offer(new int[]{nx, ny});
                    }
                }

                days++;
            }

            return -1;
        }
    }
}
