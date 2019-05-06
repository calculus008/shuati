package Interviews.DoorDash;

public class Unique_Path_K_Steps {
    /**
     * DoorDash
     *
     * 题目是unique path的升级版，给了起始位置和结束位置，8个方向都可以走，求问能在K步到达的path总量，刚开始写的dfs解，
     * 一开始还有点小bug；然后让写DP解，奈何这是个3维DP，不是很熟悉，只写了大概思路
     *
     * def count_paths(dims, start, target, K):
     *     """
     *     @param dims, a tuple (width, height) of the dimensions of the board
     *     @param start, a tuple (x, y) of the king's starting coordinate
     *     @param target, a tuple (x, y) of the king's destination
     *
     *
     *     [url=home.php?mod=space&uid=160137]@return[/url] the number of distinct paths there are for a king in chess (can move one square vertically, horizontally, or diagonally)
     *             to move from the start to target coordinates on the given board in K moves
     *
     *
     *
     *
     *
     *
     * if __name__ == "__main__":
     *     print "Running tests..."
     *     assert(count_paths((3, 3), (0, 0), (2, 2), 2) == 1)-baidu 1point3acres
     *     print "Passed test 1"
     *     assert(count_paths((3, 3), (0, 0), (2, 2), 3) == 6)
     *     print "Passed test 2"
     *     assert(count_paths((4, 4), (3, 2), (3, 2), 3) == 12)
     *     print "Passed test 3"
     *     assert(count_paths((4, 4), (3, 2), (1, 1), 4) == 84)
     *     print "Passed test 4"
     *     assert(count_paths((4, 6), (0, 2), (3, 4), 12) == 122529792)
     *     print "Passed test 5"
     */

    /**
     * 是 LE_688_Knight_Probability_In_Chessboard 的变形题。
     *
     * 3D DP
     *
     * dp[k][i][j] : Number of ways of reach location (i, j) after k moves.
     * dp[0][r][c] = 1 : start point, no move, there's 1 way.
     *
     * We have 8 moving directions. So:
     *
     * dp[k][i][j] = sum(dp[k - 1][dx][dy]), given dx and dy in valid range (can't be out of the matrix)
     *
     * Time  : O(K * m * n)
     * Space : O(K * m * n)
     *
     * Notes :
     * The count number could be very big, that's why we use double as return type
     */

    public double getNumberOfPaths1(int[][] matrix, int r1, int c1, int r2, int c2, int K) {
        if (null == matrix || matrix.length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;

        double[][][] dp = new double[K + 1][m][n];

        dp[0][r1][c1] = 1.0;

        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int k = 1; k <= K; k++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int h = 0; h < dirs.length; h++) {
                        int dx = i + dirs[h][0];
                        int dy = j + dirs[h][1];

                        if (dx >= 0 && dx < m && dy >= 0 && dy < n) {
                            dp[k][i][j] += dp[k - 1][dx][dy];
                        }
                    }
                }
            }
        }

        return dp[K][r2][c2];
    }

    /**
     * Optimize space with rolling array
     * Time  : O(K * m * n)
     * Space : O(m * n)
     */

    public double getNumberOfPaths2(int[][] matrix, int r1, int c1, int r2, int c2, int K) {
        if (null == matrix || matrix.length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;

        double[][] dp = new double[m][n];

        dp[r1][c1] = 1;

        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int k = 1; k <= K; k++) {
            double[][] temp = new double[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int h = 0; h < dirs.length; h++) {
                        int dx = i + dirs[h][0];
                        int dy = j + dirs[h][1];

                        if (dx >= 0 && dx < m && dy >= 0 && dy < n) {
                            temp[i][j] += dp[dx][dy];
                        }
                    }
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = temp[i][j];
                    temp[i][j] = 0;
                }
            }
        }

        return dp[r2][c2];
    }
}
