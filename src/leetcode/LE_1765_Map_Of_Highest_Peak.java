package leetcode;

import java.util.*;

public class LE_1765_Map_Of_Highest_Peak {
    /**
     * You are given an integer matrix isWater of size m x n that represents a map of land and water cells.
     *
     * If isWater[i][j] == 0, cell (i, j) is a land cell.
     * If isWater[i][j] == 1, cell (i, j) is a water cell.
     * You must assign each cell a height in a way that follows these rules:
     *
     * The height of each cell must be non-negative.
     * If the cell is a water cell, its height must be 0.
     * Any two adjacent cells must have an absolute height difference of at most 1. A cell is adjacent to another cell
     * if the former is directly north, east, south, or west of the latter (i.e., their sides are touching).
     * Find an assignment of heights such that the maximum height in the matrix is maximized.
     *
     * Return an integer matrix height of size m x n where height[i][j] is cell (i, j)'s height. If there are multiple
     * solutions, return any of them.
     *
     * Example 1:
     * Input: isWater = [[0,1],[0,0]]
     * Output: [[1,0],[2,1]]
     * Explanation: The image shows the assigned heights of each cell.
     * The blue cell is the water cell, and the green cells are the land cells.
     *
     * Example 2:
     * Input: isWater = [[0,0,1],[1,0,0],[0,0,0]]
     * Output: [[1,1,0],[0,1,1],[1,2,2]]
     * Explanation: A height of 2 is the maximum possible height of any assignment.
     * Any height assignment that has a maximum height of 2 while still meeting the rules will also be accepted.
     *
     * Constraints:
     * m == isWater.length
     * n == isWater[i].length
     * 1 <= m, n <= 1000
     * isWater[i][j] is 0 or 1.
     * There is at least one water cell.
     *
     * Medium
     */

    /**
     * BFS in disguise
     * "Starting points" of BFS is a collection of cells.
     *
     * Key condition - "Any two adjacent cells must have an absolute height difference of at most 1"
     */
    class Solution {
        public int[][] highestPeak(int[][] isWater) {
            int m = isWater.length;
            int n = isWater[0].length;

            int[][] res = new int[m][n];

            // for (int[] arr : res) {
            //     Arrays.fill(arr, -1);
            // }

            Queue<int[]> q = new LinkedList<>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (isWater[i][j] == 1) {
                        res[i][j] = 0;
                        q.offer(new int[]{i, j});
                    } else {//init none water cells in the same loop, get rid of loop line 57-59 to improve efficienty
                        res[i][j] = -1;
                    }
                }
            }

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();
                    int x = cur[0];
                    int y = cur[1];
                    int val = res[x][y];

                    for (int[] dir : dirs) {
                        int nx = x + dir[0];
                        int ny = y + dir[1];

                        if (nx < 0 || nx >= m || ny < 0 || ny >= n || res[nx][ny] >= 0) continue;

                        res[nx][ny] = val + 1;
                        q.offer(new int[]{nx, ny});
                    }
                }
            }

            return res;
        }
    }
}
