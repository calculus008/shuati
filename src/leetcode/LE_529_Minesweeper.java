package leetcode;

public class LE_529_Minesweeper {
    /**
     * You are given a 2D char matrix representing the game board.
     * 'M' represents an unrevealed mine, 'E' represents an unrevealed
     * empty square, 'B' represents a revealed blank square that has
     * no adjacent (above, below, left, right, and all 4 diagonals) mines,
     * digit ('1' to '8') represents how many mines are adjacent to this
     * revealed square, and finally 'X' represents a revealed mine.
     *
     * Now given the next click position (row and column indices) among
     * all the unrevealed squares ('M' or 'E'), return the board after
     * revealing this position according to the following rules:
     *
     * If a mine ('M') is revealed, then the game is over - change it to 'X'.
     *
     * If an empty square ('E') with no adjacent mines is revealed, then change
     * it to revealed blank ('B') and all of its adjacent unrevealed squares
     * should be revealed recursively.
     *
     * If an empty square ('E') with at least one adjacent mine is revealed,
     * then change it to a digit ('1' to '8') representing the number of adjacent
     * mines.
     *
     * Return the board when no more squares will be revealed.
     *
     *
     * Example 1:
     *
     * Input:
     *
     * [['E', 'E', 'E', 'E', 'E'],
     *  ['E', 'E', 'M', 'E', 'E'],
     *  ['E', 'E', 'E', 'E', 'E'],
     *  ['E', 'E', 'E', 'E', 'E']]
     *
     * Click : [3,0]
     *
     * Output:
     *
     * [['B', '1', 'E', '1', 'B'],
     *  ['B', '1', 'M', '1', 'B'],
     *  ['B', '1', '1', '1', 'B'],
     *  ['B', 'B', 'B', 'B', 'B']]
     *
     * Hard
     */

    /**
     * https://leetcode.com/problems/student-attendance-record-ii/discuss/101638/Simple-Java-O(n)-solution
     */
    class Solution {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        public char[][] updateBoard(char[][] board, int[] click) {
            int row = click[0];
            int col = click[1];

            if (board[row][col] == 'M' || board[row][col] == 'X') {
                board[row][col] = 'X';
                return board;
            }

            int count = 0;
            for (int[] dir : dirs) {
                int x = row + dir[0];
                int y = col + dir[1];

                if (x >=  0 && x < board.length && y >= 0 && y < board[0].length && board[x][y] == 'M') {
                    count++;
                }
            }

            if (count != 0) {
                board[row][col] = (char) (count + '0');
                return board;
            }

            board[row][col] = 'B';

            for (int[] dir : dirs) {
                int x = row + dir[0];
                int y = col + dir[1];

                if (x >=  0 && x < board.length && y >= 0 && y < board[0].length && board[x][y] == 'E') {
                    updateBoard(board, new int[]{x, y});
                }
            }

            return board;
        }
    }
}
