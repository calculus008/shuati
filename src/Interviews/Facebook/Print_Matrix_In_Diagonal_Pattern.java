package Interviews.Facebook;

public class Print_Matrix_In_Diagonal_Pattern {
    /**
     * 按副对⻆线输出矩阵元素。⽐如 :
     *                  [[1,2,3],
     *                   [4,5,6],
     *                   [7,8,9]
     *                  ]
     *
     * 输出 [[1],[2,4],[3,5,7],[6,8],[9]]
     *
     * https://www.geeksforgeeks.org/print-the-matrix-diagonally-downwards/
     */

    public static void printMatrixDiagonal(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int row = 0;
            int col = i;

            sb.append("[");
            while (row < m && col >= 0) {
                sb.append(matrix[row][col]).append(" ");
                row++;
                col--;
            }
            sb.setLength(sb.length() - 1);
            sb.append("] ");
        }

        for (int j = 1; j < n; j++) {
            int row = j;
            int col = n - 1;

            sb.append("[");
            while (row < m && col >= 0) {
                sb.append(matrix[row][col]).append(" ");
                row++;
                col--;
            }
            sb.setLength(sb.length() - 1);
            sb.append("] ");

        }

        System.out.println(sb.toString().trim());
    }

    /**
     * Diagonal ZigZag
     *
     * https://www.geeksforgeeks.org/print-matrix-diagonal-pattern/
     */
    public static void printMatrixDiagonalZigZag(int mat[][]) {
        // n - size
        // mode - switch to derive up/down traversal
        // it - iterator count - increases until it
        // reaches n and then decreases
        int n = mat[0].length;
        int mode = 0;
        int it = 0;
        int lower = 0;

        // 2 * n - 1 will be the number of iterations
        for (int t = 0; t < (2 * n - 1); t++) {
            int t1 = t;

            if (t1 >= n) {
                mode++;
                t1 = n - 1;
                it--;
                lower++;
            } else {
                lower = 0;
                it++;
            }

            for (int i = t1; i >= lower; i--) {
                if ((t1 + mode) % 2 == 0) {
                    System.out.println(mat[i][t1 + lower - i]);
                } else {
                    System.out.println(mat[t1 + lower - i][i]);
                }
            }
        }
    }

// This code is contributed by Anant Agarwal.



    public static void main(String[] args) {
        // Initialize matrix
        int[][] matrix = {{1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {9, 10, 11, 12},
                        {13, 14, 15, 16}};

        printMatrixDiagonal(matrix);
    }

}
