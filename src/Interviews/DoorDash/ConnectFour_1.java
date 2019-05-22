package Interviews.DoorDash;

/**
 * OOD
 * https://stackoverflow.com/questions/32770321/connect-4-check-for-a-win-algorithm
 *
 *
 */

public class ConnectFour_1 {
    int[][] board;

    public boolean is_win(int player) {
        if (getMaxLine(player) >= 4) return true;

        return false;
    }

    public int getMaxLine(int player) {
        int m = board.length;
        int n = board[0].length;

        int[][][] dp = new int[m][n][4];
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != player) {
                    continue;
                }

                for (int k = 0; k < 4; k++) {
                    dp[i][j][k] = 1;
                }

                if (i - 1 >= 0) {
                    dp[i][j][0] += dp[i - 1][j][0];
                }

                if (j - 1 >= 0) {
                    dp[i][j][1] += dp[i][j - 1][1];
                }

                if (i - 1 >= 0 && j - 1 >= 0) {
                    dp[i][j][2] += dp[i - 1][j - 1][2];
                }

                if (i - 1 >= 0 && j + 1 < n) {
                    dp[i][j][3] += dp[i - 1][j + 1][3];
                }

                res = Math.min(res, dp[i][j][0]);
                res = Math.min(res, dp[i][j][1]);
                res = Math.min(res, dp[i][j][2]);
                res = Math.min(res, dp[i][j][3]);
            }
        }

        return res;
    }

    public boolean is_win_2(int player) {
        int m = board.length;
        int n = board[0].length;

        //horizontal
        for (int i = 0; i < m; i++) {
            for (int j = 0; j + 3 < n; j++) {
                if (board[i][j] == player && board[i][j + 1] == player
                    && board[i][j + 2] == player && board[i][j + 3] == player) {
                    return true;
                }
            }
        }

        //vertical
        for (int i = 0; i + 3 < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == player && board[i + 1][j] == player
                        && board[i + 2][j] == player && board[i + 3][j] == player) {
                    return true;
                }
            }
        }

        //diagonal (descending, from upper left to lower right)
        for (int i = 3; i < m; i++) {
            for (int j = 3; j < n; j++) {
                if (board[i][j] == player && board[i - 1][j - 1] == player
                        && board[i - 2][j - 2] == player && board[i - 3][j - 3] == player) {
                    return true;
                }
            }
        }

        //diagnal (descending, from upper right to lower left)
        for (int i = 3; i < m; i++) {
            for (int j = 0; j + 3 < n; j++) {
                if (board[i][j] == player && board[i - 1][j + 1] == player
                        && board[i - 2][j + 2] == player && board[i - 3][j + 3] == player) {
                    return true;
                }
            }
        }

        return false;
    }
}
