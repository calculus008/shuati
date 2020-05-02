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
         Could you do better than O(n ^ 2) per move() operation?

         Medium
     */


    /**
     * Related:
     * LE_348_Design_Tic_Tac_Toe
     * LE_794_Valid_Tic_Tac_Toe_State
     * LE_1275_Find_Winner_On_A_Tic_Tac_Toe_Game
     */

    /**
        https://leetcode.com/problems/design-tic-tac-toe/discuss/81898/Java-O(1)-solution-easy-to-understand

         The key observation is that in order to win Tic-Tac-Toe you must have the entire row or column.
         Thus, we don't need to keep track of an entire n^2 board. We only need to keep a count for each row and column.
         If at any time a row or column matches the size of the board then that player has won.

         To keep track of which player, I add one for Player1 and -1 for Player2.
         There are two additional variables to keep track of the count of the diagonals.
         Each time a player places a piece we just need to check the count of that row, column, diagonal and anti-diagonal.

         This algo has a limitation for this problem, it cannot tell whether it is a valid move or not because it
         does not save the points history. potentially the player can keep move on the same spot. But the question
         itself kinda guarantees that assumption, even though it is not a 'good' assumption to follow.
     */


    /**
     * Related:
     * LE_348_Design_Tic_Tac_Toe                      Design data structure, give one move, check if there's a winner
     * LE_794_Valid_Tic_Tac_Toe_State                 Given a board, check if the board is in a correct state.
     * LE_1275_Find_Winner_On_A_Tic_Tac_Toe_Game      Given sequence of moves in array, check if there's a winner
     */

    class TicTacToe {
        int[] rows;
        int[] cols;
        int diagnal;
        int antiDiagnal;
        int n;

        /** Initialize your data structure here. */
        public TicTacToe(int n) {
            rows = new int[n];
            cols = new int[n];
            this.n = n;
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

            if ((row + col) == (cols.length - 1)) {
                antiDiagnal += toAdd;
            }

            int size = rows.length;
            if (Math.abs(rows[row]) == size || Math.abs(cols[col]) == size
                    || Math.abs(diagnal) == size || Math.abs(antiDiagnal) == size) {
                return player;
            }

            return 0;
        }

        /**
         * The version without using Math.abs()
         */
        public int move1(int row, int col, int player) {
            int val = (player == 1) ? 1 : -1;
            int target = (player == 1) ? n : -n;

            if(row == col) { // diagonal
                diagnal += val;
                if(diagnal == target) return player;
            }

            if(row + col == n - 1) { // diagonal
                antiDiagnal += val;
                if(antiDiagnal == target) return player;
            }

            rows[row] += val;
            cols[col] += val;

            if(rows[row] == target || cols[col] == target) return player;

            return 0;
        }
    }

    /**
     * Follow up : extended to board size of m (m >= n)
     *
     * Inspired by LE_52_N_Queens_II.
     *
     * Use four arrays to maintain the current status of the board,
     * whenever the count of one player equals to N, then he wins.
     * And I also use the index to map to different users.
     * (0 - nobody, 1 - palyer1, 2 -player2)
     */
    class TicTacToe_FollowUp {
        private int[][] rows;
        private int[][] cols;
        private int[][] diags;
        private int[][] adiags;
        private int n; //target
        private int m; //board size

        public TicTacToe_FollowUp(int n, int m) {
            rows = new int[m][3];
            cols = new int[m][3];
            diags = new int[2 * m][3];
            adiags = new int[2 * m][3];
            this.n = n;
            this.m = m;
        }

        public int move(int row, int col, int player) {
            rows[row][player] += 1;
            cols[col][player] += 1;

            /**
             * Track diagnal and anti diagnal index:
             * "row + col"
             * "row - col + m"
             */
            diags[row + col][player] += 1;
            adiags[row - col + m][player] += 1;

            if (rows[row][player] == n || cols[col][player] == n || diags[row + col][player] == n || adiags[row - col + n][player] == n) {
                return player;
            } else {
                return 0;
            }
        }
    }
}
