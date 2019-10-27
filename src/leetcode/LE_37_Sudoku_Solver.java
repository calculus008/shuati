package leetcode;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_37_Sudoku_Solver {
    /**
     *     Write a program to solve a Sudoku puzzle by filling the empty cells.
     *
     *     Empty cells are indicated by the character '.'.
     *
     *     You may assume that there will be only one unique solution.
     *
     *     Hard
     */

    class Solution1 {
        public void solveSudoku(char[][] board) {
            if (board == null || board.length == 0) return;
            helper(board);
        }

        private boolean helper(char[][] board) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    /**
                     * if current cel is already filled
                     */
                    if (board[i][j] == '.') {
                        for (char c = '1'; c <= '9'; c++) {
                            // board[i][j] = c; !!! can't do it here!!!

                            if (isValid(board, i, j, c)) {
                                board[i][j] = c;
                                if (helper(board)) {
                                    return true;
                                } else {
                                    board[i][j] = '.';
                                }
                            }
                        }
                        /**
                         * for an unfilled cell, after trying 1 to 9, still reaching here,
                         * meaning we can't get into the branch to return true, therefore,
                         * no valid answer, return false here.
                         */
                        return false;
                    }
                }
            }

            /**
             * reaching here, meaning there's no empty cell in board. All cells are filled with
             * valid number, therefore return true here.
             */
            return true;
        }

        private boolean isValid(char[][] board, int row, int col, char ch) {
            for (int i = 0; i < 9; i++) {
                if (board[i][col] != '.' && board[i][col] == ch) return false;
                if (board[row][i] != '.' && board[row][i] == ch) return false;

                int r = 3 * (row / 3) + i / 3;
                //!!! given a point, find the starting row and col of the sub block (3 * 3)
                int c = 3 * (col / 3) + i % 3;

                if (board[r][c] != '.' && board[r][c] == ch) return false;
            }

            return true;
        }
    }

    public class Solution_Practice_JiuZhang {
        public void solveSudoku(int[][] board) {
            if (board == null || board.length == 0) return;
            helper(board);
        }

        private boolean helper(int[][] board) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == 0) {
                        for (int k = 1; k <= 9; k++) {
                            if (isValid(board, i, j, k)) {
                                board[i][j] = k;
                                if (helper(board)) {
                                    return true;
                                } else {
                                    board[i][j] = 0;
                                }
                            }
                        }
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean isValid(int[][] board, int x, int y, int val) {
            for (int i = 0; i < 9; i++) {
                if (board[x][i] == val) return false;
                if (board[i][y] == val) return false;

                int r = (x / 3) * 3 + i /3;
                int c = (y / 3) * 3 + i % 3;
                if (board[r][c] == val) return false;
            }

            // System.out.println("return true");
            return true;
        }
    }
}
