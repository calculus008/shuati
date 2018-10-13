package lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 10/12/18.
 */
public class LI_778_Pacific_Atlantic_Water_Flow {
    /**
         Given an m x n matrix of non-negative integers representing the height of each
         unit cell in a continent, the "Pacific ocean" touches the left and top edges
         of the matrix and the "Atlantic ocean" touches the right and bottom edges.

         Water can only flow in four directions (up, down, left, or right) from a
         cell to another one with height equal or lower.

         Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

         Example
         Given the following 5x5 matrix:

         Pacific ~   ~   ~   ~   ~
         ~  1   2   2   3  (5) *
         ~  3   2   3  (4) (4) *
         ~  2   4  (5)  3   1  *
         ~ (6) (7)  1   4   5  *
         ~ (5)  1   1   2   4  *
         *   *   *   *   * Atlantic

         Return:
         [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).

         Notice
         1.The order of returned grid coordinates does not matter.
         2.Both m and n are less than 150.

         Medium
     */

    public class Solution {
        int m, n;

        public List<List<Integer>> pacificAtlantic(int[][] matrix) {
            List<List<Integer>> res = new ArrayList<>();
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return res;

            m = matrix.length;
            n = matrix[0].length;
            boolean[][] visited1 = new boolean[m][n];
            boolean[][] visited2 = new boolean[m][n];

            for (int i = 0; i < n; i++) {
                flow(0, i, visited1, matrix);
            }

            for (int i = 0; i < m; i++) {
                flow(i, 0, visited1, matrix);
            }

            for (int i = 0; i < m; i++) {
                flow(i, n - 1, visited2, matrix );
            }

            for (int i = 0; i < n ; i++) {
                flow(m - 1, i, visited2, matrix);
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (visited1[i][j] && visited2[i][j]) {
                        List<Integer> list = new ArrayList<>();
                        list.add(i);
                        list.add(j);
                        res.add(list);
                    }
                }
            }

            return res;
        }

        private void flow(int x, int y, boolean[][] visited, int[][] matrix) {
            visited[x][y] = true;
            int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

            for (int i = 0; i < dir.length; i++) {
                int nextX = x + dir[i][0];
                int nextY = y + dir[i][1];
                if (isValid(nextX, nextY)) {
                    if (matrix[x][y] <= matrix[nextX][nextY] && !visited[nextX][nextY]) {
                        flow(nextX, nextY, visited, matrix);
                    }
                }
            }
        }

        private boolean isValid(int x, int y) {
            return (x >= 0 && x < m && y >= 0 && y < n);
        }
    }
}
