package leetcode;

/**
 * Created by yuank on 3/15/18.
 */
public class LE_130_Surrounded_Regions {
    /*
        Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.

        A region is captured by flipping all 'O's into 'X's in that surrounded region.

        For example,
        X X X X
        X O O X
        X X O X
        X O X X
        After running your function, the board should be:

        X X X X
        X X X X
        X X X X
        X O X X
     */

    //Space and Time : O(m * n)

    public static void solve(char[][] board) {
        if (board == null || board.length == 0) return;

        int m = board.length;
        int n = board[0].length;

        //'O'只可能在四条边上才不能被'X'包围，而且所有和这个边上的'O'相邻的‘O'也不是被‘X'包围的。
        //所以:
        //1.遍历每一条边，找'O'，然后以它为起点进行DFS，把所有找到的相邻的'O'设置为'1'.
        //2.遍历整个矩阵，把所有还存在的'O'设置为'X',把'1'还原成'O'

        for (int i = 0; i < n; i++) {
            if (board[0][i] == 'O') {
                helper(board, 0, i);
            }
        }

        for (int i = 0; i < n; i++) {
            if (board[m - 1][i] == 'O') {
                helper(board, m - 1, i);
            }
        }

        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                helper(board, i, 0);
            }
        }

        for (int i = 0; i < m; i++) {
            if (board[i][n - 1] == 'O') {
                helper(board, i, n - 1);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == '1') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public static void helper(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != 'O') return;

        board[i][j] = '1';

        helper(board, i + 1, j);
        helper(board, i - 1, j);
        helper(board, i, j + 1);
        helper(board, i, j - 1);
    }
}
