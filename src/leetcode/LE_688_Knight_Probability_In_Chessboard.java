package leetcode;

/**
 * Created by yuank on 11/16/18.
 */
public class LE_688_Knight_Probability_In_Chessboard {
    /**
         On an NxN chessboard, a knight starts at the r-th row and c-th column and attempts to make exactly K moves.
         The rows and columns are 0 indexed, so the top-left square is (0, 0), and the bottom-right square is (N-1, N-1).

         A chess knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a
         cardinal direction, then one square in an orthogonal direction.

         Each time the knight is to move, it chooses one of eight possible moves uniformly at random
         (even if the piece would go off the chessboard) and moves there.

         The knight continues moving until it has made exactly K moves or has moved off the chessboard.
         Return the probability that the knight remains on the board after it has stopped moving.



         Example:

         Input: 3, 2, 0, 0
         Output: 0.0625
         Explanation:
         There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
         From each of those positions, there are also two moves that will keep the knight on the board.
         The total probability the knight stays on the board is 0.0625.


         Note:

         N will be between 1 and 25.
         K will be between 0 and 100.
         The knight always initially starts on the board.

         Medium
     */

    /**
     * DP
     * dp[k][i][j] : Number of ways of reach location (i, j) after k moves.
     * dp[0][r][c] = 1 : no move, there's 1 way.
     *
     * For knight, there are 8 moving directions. So:
     *
     * dp[k][i][j] = sum(dp[k - 1][dx][dy]), given dx and dy in valid range (can't be out of the chessboard)
     *
     * Then total number of ways of staying in chessboard after K moves:
     * Sum (dp[K][i][j])
     *
     * All moves : 8 ^ K (each move to 8 direction, K moves)
     *
     * Time  : O(K * N ^ 2), K moves, for each move, we go through all cells on chessboard
     * Space : O(K * N ^ 2)
     */

    public double knightProbability1(int N, int K, int r, int c) {
        double[][][] dp = new double[K + 1][N][N];

        dp[0][r][c] = 1.0;//!!!

        int[][] dirs = new int[][] {{2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};

        for (int k = 1; k <= K; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int d = 0; d < dirs.length; d++) {
                        int dx = i + dirs[d][0];
                        int dy = j + dirs[d][1];

                        if (dx >= 0 && dx < N && dy >= 0 && dy < N) {
                            dp[k][i][j] += dp[k - 1][dx][dy];
                        }
                    }
                }
            }
        }

        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += dp[K][i][j];
            }
        }

        return  sum / Math.pow(8, K);
    }

    /**
     * Optimize space with rolling array
     * Time  : O(K * N ^ 2)
     * Space : O( N ^ 2)
     */
    public double knightProbability2(int N, int K, int r, int c) {
        double[][] dp = new double[N][N];

        dp[r][c] = 1.0;//!!!

        int[][] dirs = new int[][] {{2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};

        double[][] temp = new double[N][N];

        for (int k = 1; k <= K; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int d = 0; d < dirs.length; d++) {
                        int dx = i + dirs[d][0];
                        int dy = j + dirs[d][1];

                        if (dx >= 0 && dx < N && dy >= 0 && dy < N) {
                            temp[i][j] += dp[dx][dy];
                        }
                    }
                }
            }

            /**
             * We have to set dp and temp explicitly here
             * in order to save space, it increases time complexity,
             * it is actually O(K * 2 * N ^ 2)
             */
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < N; y++) {
                    dp[x][y] = temp[x][y];
                    temp[x][y] = 0;
                }
            }
        }

        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += dp[i][j];
            }
        }

        return  sum / Math.pow(8, K);
    }


    /**
     * This one modified directly from Huahua's C++ version.
     * Since Java is by reference, therefore, we don't save
     * space with this solution, since very time we "dp = temp",
     * dp still references to the number in temp.
     */
//    public double knightProbability3(int N, int K, int r, int c) {
//        double[][] dp = new double[N][N];
//
//        dp[r][c] = 1.0;//!!!
//
//        int[][] dirs = new int[][] {{2, 1}, {2, -1}, {-2, 1}, {-2, -1},
//                {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};
//
//        for (int k = 1; k <= K; k++) {
//            double[][] temp = new double[N][N];
//
//            for (int i = 0; i < N; i++) {
//                for (int j = 0; j < N; j++) {
//                    for (int d = 0; d < dirs.length; d++) {
//                        int dx = i + dirs[d][0];
//                        int dy = j + dirs[d][1];
//
//                        if (dx >= 0 && dx < N && dy >= 0 && dy < N) {
//                            temp[i][j] += dp[dx][dy];
//                        }
//                    }
//                }
//            }
//
//            dp = temp;
//        }
//
//        double sum = 0.0;
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                sum += dp[i][j];
//            }
//        }
//
//        return  sum / Math.pow(8, K);
//    }
}
