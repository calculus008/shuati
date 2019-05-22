package Interviews.DoorDash;

import java.util.LinkedList;
import java.util.Queue;

public class Largest_Block {
    /**
     * Variation of LE_695_Max_Area_Of_Island
     *
     * @param matrix, a 2-d array (list of lists) of integers
     * @return the size of the largest contiguous block (horizontally/vertically connected)
     * of numbers with the same value
     *
     * 最大岛屿问题，不是1， 0. 而是任意数
     * LC numbers of island but not just 0 and 1. it could be from 0 - 9, 求最大岛屿面积
     * 所有的都是岛，相同的数group在一起算一个岛
     *
     * int expected1 = 2;
     * int [][] matrix1 = new int[][]
     * {{1,2,3},
     * {4,1,6},
     * {4,5,1}};
     *
     * int expected2 = 4;
     * int [][] matrix2 = new int[][]
     * {{1,1,1,2,4},
     * {5,1,5,3,1},
     * {3,4,2,1,1}};
     *
     * int expected3 = 11;
     * int [][] matrix3 = new int[][]
     * {{3,3,3,3,3,1},
     * {3,4,4,4,3,4},
     * {2,4,3,3,3,4},
     * {2,4,4,4,4,4}};
     *
     * int expected4 = 24;
     * int [][] matrix4 = new int[][]{
     * {0,0,0,0,0},
     * {0,0,0,0,0},
     * {0,0,1,0,0},
     * {0,0,0,0,0},
     * {0,0,0,0,0}};
     */

    public static int largestBlock(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;

        int res = 0;

        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
//                int area = helper(matrix, m, n, i, j, matrix[i][j], new boolean[m][n]);
                if (!visited[i][j]) {
//                    int area = helper(matrix, m, n, i, j, matrix[i][j], visited);
                    int area = bfs(matrix, m, n, i, j, matrix[i][j], visited);
                    res = Math.max(res, area);
                }
            }
        }

        return res;
    }

    private static int helper(int[][] matrix, int m, int n, int x, int y, int val, boolean[][] visited) {
        if (x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] != val || matrix[x][y] < 0 || visited[x][y]) return 0;

        int area = 1;

        visited[x][y] = true;

        area += helper(matrix, m, n, x + 1, y, val, visited);
        area += helper(matrix, m, n, x - 1, y, val, visited);
        area += helper(matrix, m, n, x, y + 1, val, visited);
        area += helper(matrix, m, n, x, y - 1, val, visited);

        return area;
    }

    public static int largestBlock_practice(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                /**
                 * !!!
                 * visited[i][j], not visited[m][n]
                 */
                if (!visited[i][j]) {
                    res = Math.max(res, dfs(matrix, m, n, i, j, matrix[i][j], visited));
                }
            }
        }

        return res;
    }

    private static int dfs(int[][] matrix, int m, int n, int x, int y, int val, boolean[][] visited) {
        if (x < 0 || x >= m || y < 0 || y >= n || visited[x][y] || matrix[x][y] != val) {
            return 0;
        }

        visited[x][y] = true;

        int res = 1;

        res += helper(matrix, m, n, x + 1, y, val, visited);
        res += helper(matrix, m, n, x - 1, y, val, visited);
        res += helper(matrix, m, n, x, y + 1, val, visited);
        res += helper(matrix, m, n, x, y - 1, val, visited);

        return res;
    }

    private static int bfs(int[][] matrix, int m, int n, int x, int y, int val, boolean[][] visited) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{x, y});
        /**
         * !!! set visited when offer
         */
        visited[x][y] = true;

        int res = 0;

        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1},{ 0, -1}};
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            /**
             * add after poll
             */
            res++;

            for (int[] dir : dirs) {
                /**
                 * !!!
                 * cur[0] + dir[0]
                 */
                int nx = cur[0] + dir[0];
                int ny = cur[1] + dir[1];

                if (nx < 0 || nx >= m || ny < 0 || ny >= n || visited[nx][ny] || matrix[nx][ny] != val) {
                    continue;
                }

                /**
                 * !!!
                 */
                visited[nx][ny] = true;
                q.offer(new int[]{nx, ny});
            }

        }

        return res;
    }

    public static void main(String[] args) {
        int[][] matrix1 = new int[][]
                {{1, 2, 3},
                        {4, 1, 6},
                        {4, 5, 1}};

        if (largestBlock(matrix1) == 2) {
            System.out.println("Correct");
        } else {
            System.out.println("Wrong");
        }

        int[][] matrix2 = new int[][]
                       {{1, 1, 1, 2, 4},
                        {5, 1, 5, 3, 1},
                        {3, 4, 2, 1, 1}};
        if (largestBlock(matrix2) == 4) {
            System.out.println("Correct");
        } else {
            System.out.println("Wrong");
        }

        int [][] matrix3 = new int[][]
                {{3,3,3,3,3,1},
                        {3,4,4,4,3,4},
                        {2,4,3,3,3,4},
                        {2,4,4,4,4,4}};
        if (largestBlock(matrix3) == 11) {
            System.out.println("Correct");
        } else {
            System.out.println("Wrong");
        }

        int [][] matrix4 = new int[][]{
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,1,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}};
        int ret = largestBlock(matrix4);
        if (ret == 24) {
            System.out.println("Correct");
        } else {
            System.out.println("Wrong, " + ret);
        }
    }
}
