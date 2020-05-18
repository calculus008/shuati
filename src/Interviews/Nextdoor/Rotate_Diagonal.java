package Interviews.Nextdoor;

public class Rotate_Diagonal {
    /**
     * rotate with k
     * 对角线方向旋转矩阵中的元素k次，其中1 <= k <= 4
     * Example:
     * [
     *  [1, 2, 3],
     *  [4, 5, 6],
     *  [7, 8, 9]
     * ]
     * -->
     * [
     *  [1, 4, 3],
     *  [8, 5, 2],
     *  [7, 6, 9]
     * ]
     * n
     * [[1,2,3,4,5],
     *
     * [6,7,8,9,10], [11,12,13,14,15], [16,17,18,19,20], [21,22,23,24,25]] -
     * ->
     * [[1,16,11,6,5], [22,7,12,9,2], [23,18,13,8,3], [24,17,14,19,4], [21,10,15,20,25]]
     *
     * LE_48_Rotate_Image 变形, check是不是diagonal
     */
    public static void rotateDiagonal(int[][] matrix, int k) {
        int n = matrix.length;

        /**
         * rotate k times
         */
        for (int s = 0; s < k; s++) {
            /**
             * 1.rotate
             */
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    /**
                     * check if current cell is on diagonal,if not, rotate.
                     */
                    if (i != j && i + j != n - 1) {
                        int temp = matrix[i][j];
                        matrix[i][j] = matrix[j][i];
                        matrix[j][i] = temp;
                    }
                }
            }

            /**
             * 2.反转
             */
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n / 2; j++) {
                    if (i != j && i + j != n - 1) {
                        int temp = matrix[i][j];
                        matrix[i][j] = matrix[i][n - 1 - j];
                        matrix[i][n - 1 - j] = temp;
                    }
                }
            }
        }
    }

    /**
     * =========================================
     */
    public static void transpose(int[][] matrix) {
        int n = matrix.length;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (i + j == n - 1) {
                    // avaoid bottom left to top right
                    continue;
                }
                int tmp = matrix[j][i];
                matrix[j][i] = matrix[i][j];
                matrix[i][j] = tmp;
            }
        }
    }

    public static void reverse(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                if (j == i || i + j == n - 1) {
                    continue;
                }
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = tmp;
            }
        }
    }

    public static void rotate_diagonal(int[][] matrix, int k) {
        for (int i = 0; i < k; i++) {
            transpose(matrix);
            peek(matrix);
            reverse(matrix);
        }
    }

    public static void peek(int[][] matrix) {
        for (int[] row : matrix) {
            for (int n : row) {
                System.out.print(n + "\t");
            }
            System.out.println("");
        }
        System.out.println(" ");
    }

    public static void main(String[] args) {
        int n = 5;
        int k = 1;
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = i * n + j + 1;
            }
        }

        peek(matrix);
        rotate_diagonal(matrix, k);
        peek(matrix);
    }
}
