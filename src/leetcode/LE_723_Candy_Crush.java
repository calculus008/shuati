package leetcode;

public class LE_723_Candy_Crush {
    /**
     * This question is about implementing a basic elimination algorithm for Candy Crush.
     *
     * Given a 2D integer array board representing the grid of candy, different positive integers board[i][j]
     * represent different types of candies. A value of board[i][j] = 0 represents that the cell at position (i, j)
     * is empty. The given board represents the state of the game following the player's move. Now, you need to
     * restore the board to a stable state by crushing candies according to the following rules:
     *
     * If three or more candies of the same type are adjacent vertically or horizontally, "crush" them all at
     * the same time - these positions become empty.
     *
     * After crushing all candies simultaneously, if an empty space on the board has candies on top of itself,
     * then these candies will drop until they hit a candy or bottom at the same time. (No new candies will drop
     * outside the top boundary.)
     *
     * After the above steps, there may exist more candies that can be crushed. If so, you need to repeat the above steps.
     * If there does not exist more candies that can be crushed (ie. the board is stable), then return the current board.
     * You need to perform the above rules until the board becomes stable, then return the current board.
     *
     * Example:
     *
     * Input:
     * board =
     * [[110,5,112,113,114],[210,211,5,213,214],[310,311,3,313,314],[410,411,412,5,414],[5,1,512,3,3],[610,4,1,613,614],[710,1,2,713,714],[810,1,2,1,1],[1,1,2,2,2],[4,1,4,4,1014]]
     *
     * Output:
     * [[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[110,0,0,0,114],[210,0,0,0,214],[310,0,0,113,314],[410,0,0,213,414],[610,211,112,313,614],[710,311,412,613,714],[810,411,512,713,1014]]
     *
     * Note:
     *
     * The length of board will be in the range [3, 50].
     * The length of board[i] will be in the range [3, 50].
     * Each board[i][j] will initially start as an integer in the range [1, 2000].
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/candy-crush/discuss/178366/Another-Java-Solution
     *
     * Key points:
     * 1.While check each row and col for crushed value, we need to both mark the eligible cells and
     *   have a way to get its original value so that we won't miss the values that should be crushed.
     *   For example:
     *     1 2 3
     *     1 4 5
     *     1 1 1
     *
     *   If we crush col 1 we will set board[2][0] to 0, then we will miss row 3 (it becomes "0 1 1") for
     *   crushing.
     *
     *   The trick here is to set the value that should be crushed to its negative value. So we can tell
     *   a value that should be crushed depending on if it is negative, then when we want to get its original
     *   value, we just get its ABS value.
     *
     * 2.For drop, we only need to do it vertically. This is similar to LE_88_Merge_Sorted_Array. We don't need
     *   to actually set value to 0, we just start from the last element of the column, put none-negative values
     *   in each cell with a running row index, then set the rest of the cells in the column (if there's any left) to 0.
     *
     * Time : O((m * n) ^ 2), each round we check each of the m * n cell, at most, we may run m * n rounds.
     * Space : O(1)
     */
    class Solution {
        public int[][] candyCrush(int[][] board) {
            int m = board.length;
            int n = board[0].length;

            boolean shouldContinue = false;

            /**
             * Crush horizontally
             */
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n - 2; j++) {
                    int val = Math.abs(board[i][j]);

                    /**
                     * !!!
                     * val is ABS, so val must >= 0, but val == 0 means there's no candy,
                     * therefore we must check "val > 0" as the first condition to tell
                     * if there's a candy in current cell.
                     *
                     * !!!
                     * Then must also get ABS value for board[i][j + 1] and board[i][j + 2]
                     * (the next two elements in the same row) because they may be already set
                     * to be crushed (therefore it can be negative)
                     */
                    if (val > 0 && val == Math.abs(board[i][j + 1]) && val == Math.abs(board[i][j + 2])) {
                        board[i][j] = board[i][j + 1] = board[i][j + 2] = -val;
                        shouldContinue = true;
                    }
                }
            }

            /**
             * Crush vertically
             */
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < m - 2; i++) {
                    int val = Math.abs(board[i][j]);
                    if (val > 0 && val == Math.abs(board[i + 1][j]) && val == Math.abs(board[i + 2][j])) {
                        board[i][j] = board[i + 1][j] = board[i + 2][j] = -val;
                        shouldContinue = true;
                    }
                }
            }

            /**
             * Drop
             */
            for (int j = 0; j < n; j++) {
                int row = m - 1;
                for (int i = m - 1; i >= 0; i--) {
                    if (board[i][j] >= 0) {
                        board[row--][j] = board[i][j];
                    }
                }

                for (int i = row; i >= 0; i--) {
                    board[i][j] = 0;
                }
            }

            return shouldContinue ? candyCrush(board) : board;
        }
    }
}
