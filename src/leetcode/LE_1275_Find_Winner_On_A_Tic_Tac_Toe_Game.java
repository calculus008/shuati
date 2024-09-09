package leetcode;

public class LE_1275_Find_Winner_On_A_Tic_Tac_Toe_Game {
    /**
     * Tic-tac-toe is played by two players A and B on a 3 x 3 grid.
     *
     * Here are the rules of Tic-Tac-Toe:
     *
     * Players take turns placing characters into empty squares (" ").
     * The first player A always places "X" characters, while the second player B always places "O" characters.
     * "X" and "O" characters are always placed into empty squares, never on filled ones.
     * The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
     * The game also ends if all squares are non-empty.
     * No more moves can be played if the game is over.
     * Given an array moves where each element is another array of size 2 corresponding to the row
     * and column of the grid where they mark their respective character in the order in which A and B play.
     *
     * Return the winner of the game if it exists (A or B), in case the game ends in a draw return
     * "Draw", if there are still movements to play return "Pending".
     *
     * You can assume that moves is valid (It follows the rules of Tic-Tac-Toe), the grid is initially
     * empty and A will play first.
     *
     * Example 1:
     * Input: moves = [[0,0],[2,0],[1,1],[2,1],[2,2]]
     * Output: "A"
     * Explanation: "A" wins, he always plays first.
     * "X  "    "X  "    "X  "    "X  "    "X  "
     * "   " -> "   " -> " X " -> " X " -> " X "
     * "   "    "O  "    "O  "    "OO "    "OOX"
     *
     * Example 2:
     * Input: moves = [[0,0],[1,1],[0,1],[0,2],[1,0],[2,0]]
     * Output: "B"
     * Explanation: "B" wins.
     * "X  "    "X  "    "XX "    "XXO"    "XXO"    "XXO"
     * "   " -> " O " -> " O " -> " O " -> "XO " -> "XO "
     * "   "    "   "    "   "    "   "    "   "    "O  "
     *
     * Example 3:
     * Input: moves = [[0,0],[1,1],[2,0],[1,0],[1,2],[2,1],[0,1],[0,2],[2,2]]
     * Output: "Draw"
     * Explanation: The game ends in a draw since there are no moves to make.
     * "XXO"
     * "OOX"
     * "XOX"
     * Example 4:
     *
     * Input: moves = [[0,0],[1,1]]
     * Output: "Pending"
     * Explanation: The game has not finished yet.
     * "X  "
     * " O "
     * "   "
     *
     *
     * Constraints:
     * 1 <= moves.length <= 9
     * moves[i].length == 2
     * 0 <= moves[i][j] <= 2
     * There are no repeated elements on moves.
     * moves follow the rules of tic tac toe.
     *
     * Easy
     *
     * https://leetcode.com/problems/find-winner-on-a-tic-tac-toe-game
     */

    /**
     * Related:
     * LE_348_Design_Tic_Tac_Toe                      Design data structure, give one move, check if there's a winner
     * LE_794_Valid_Tic_Tac_Toe_State                 Given a board, check if the board is in a correct state.
     * LE_1275_Find_Winner_On_A_Tic_Tac_Toe_Game      Given sequence of moves in array, check if there's a winner
     */

    /**
     * Counting
     *
     * For each player, there are 8 possible winning pattern, we count them on each step.
     *
     * 8 winning pattern : 3 rows, 3 cols, 2 diagonals
     *
     * Time: O(m)
     * Space: O(n)
     */
    class Solution {
        public String tictactoe(int[][] moves) {
            int[] aRow = new int[3], aCol = new int[3], bRow = new int[3], bCol = new int[3];
            int aD1 = 0, aD2 = 0;
            int bD1 = 0, bD2 = 0;

            for (int i = 0; i < moves.length; i++) {
                int r = moves[i][0];
                int c = moves[i][1];

                /**
                 * !!!
                 * For reverse diagonal, r + c == 2 , NOT 3
                 */
                if (i % 2 == 0) {
                    if (++aRow[r] == 3 || ++aCol[c] == 3 || (r + c == 2 && ++aD1 == 3) || (r == c && ++aD2 == 3)) return "A";
                } else {
                    if (++bRow[r] == 3 || ++bCol[c] == 3 || (r + c == 2 && ++bD1 == 3) || (r == c && ++bD2 == 3)) return "B";
                }
            }

            return moves.length == 9 ? "Draw" : "Pending";
        }
    }
}
