package leetcode;

/**
 * Created by yuank on 5/19/18.
 */
public class LE_348_Design_Tic_Tac_Toe {
    /**
         Design a Tic-tac-toe game that is played between two players on a n x n grid.

         You may assume the following rules:

         A move is guaranteed to be valid and is placed on an empty block.
         Once a winning condition is reached, no more moves is allowed.
         A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
         Example:
         Given n = 3, assume that player 1 is "X" and player 2 is "O" in the board.

         TicTacToe toe = new TicTacToe(3);

         toe.move(0, 0, 1); -> Returns 0 (no one wins)
         |X| | |
         | | | |    // Player 1 makes a move at (0, 0).
         | | | |

         toe.move(0, 2, 2); -> Returns 0 (no one wins)
         |X| |O|
         | | | |    // Player 2 makes a move at (0, 2).
         | | | |

         toe.move(2, 2, 1); -> Returns 0 (no one wins)
         |X| |O|
         | | | |    // Player 1 makes a move at (2, 2).
         | | |X|

         toe.move(1, 1, 2); -> Returns 0 (no one wins)
         |X| |O|
         | |O| |    // Player 2 makes a move at (1, 1).
         | | |X|

         toe.move(2, 0, 1); -> Returns 0 (no one wins)
         |X| |O|
         | |O| |    // Player 1 makes a move at (2, 0).
         |X| |X|

         toe.move(1, 0, 2); -> Returns 0 (no one wins)
         |X| |O|
         |O|O| |    // Player 2 makes a move at (1, 0).
         |X| |X|

         toe.move(2, 1, 1); -> Returns 1 (player 1 wins)
         |X| |O|
         |O|O| |    // Player 1 makes a move at (2, 1).
         |X|X|X|

         Follow up:
         Could you do better than O(n2) per move() operation?

         Medium
     */

    /**
        https://leetcode.com/problems/design-tic-tac-toe/discuss/81898/Java-O(1)-solution-easy-to-understand

         The key observation is that in order to win Tic-Tac-Toe you must have the entire row or column.
         Thus, we don't need to keep track of an entire n^2 board. We only need to keep a count for each row and column.
         If at any time a row or column matches the size of the board then that player has won.

         To keep track of which player, I add one for Player1 and -1 for Player2.
         There are two additional variables to keep track of the count of the diagonals.
         Each time a player places a piece we just need to check the count of that row, column, diagonal and anti-diagonal.
     */
    class TicTacToe {
        int[] rows;
        int[] cols;
        int diagnal;
        int antiDiagnal;

        /** Initialize your data structure here. */
        public TicTacToe(int n) {
            rows = new int[n];
            cols = new int[n];
        }

        /** Player {player} makes a move at ({row}, {col}).
         @param row The row of the board.
         @param col The column of the board.
         @param player The player, can be either 1 or 2.
         @return The current winning condition, can be either:
         0: No one wins.
         1: Player 1 wins.
         2: Player 2 wins. */
        public int move(int row, int col, int player) {
            int toAdd = player == 1 ? 1 : -1;

            rows[row] += toAdd;
            cols[col] += toAdd;

            if (row == col) {
                diagnal += toAdd;
            }

            if ((cols.length - row - 1) == col) {
                antiDiagnal += toAdd;
            }

            int size = rows.length;
            if (Math.abs(rows[row]) == size || Math.abs(cols[col]) == size
                    || Math.abs(diagnal) == size || Math.abs(antiDiagnal) == size) {
                return player;
            }

            return 0;
        }
    }
}
