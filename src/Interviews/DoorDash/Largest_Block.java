package Interviews.DoorDash;

public class Largest_Block {
    /**
     * Variation of LE_695_Max_Area_Of_Island
     *
     * @param matrix, a 2-d array (list of lists) of integers
     * @return the size of the largest contiguous block (horizontally/vertically connected)
     * of numbers with the same value
     * <p>
     * 最大岛屿问题，不是1， 0. 而是任意数
     * LC numbers of island but not just 0 and 1. it could be from 0 - 9, 求最大岛屿面积
     * 所有的都是岛，相同的数group在一起算一个岛
     * <p>
     * int expected1 = 2;
     * int [][] matrix1 = new int[][]
     * {{1,2,3},
     * {4,1,6},
     * {4,5,1}};
     * <p>
     * int expected2 = 4;
     * int [][] matrix2 = new int[][]
     * {{1,1,1,2,4},
     * {5,1,5,3,1},
     * {3,4,2,1,1}};
     * <p>
     * int expected3 = 11;
     * int [][] matrix3 = new int[][]
     * {{3,3,3,3,3,1},
     * {3,4,4,4,3,4},
     * {2,4,3,3,3,4},
     * {2,4,4,4,4,4}};
     * <p>
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

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int area = helper(matrix, m, n, i, j, matrix[i][j], new boolean[m][n]);
                res = Math.max(res, area);
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
