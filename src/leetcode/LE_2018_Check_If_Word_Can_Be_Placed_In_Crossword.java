package leetcode;

public class LE_2018_Check_If_Word_Can_Be_Placed_In_Crossword {
    /**
     * You are given an m x n matrix board, representing the current state of a crossword puzzle. The crossword contains
     * lowercase English letters (from solved words), ' ' to represent any empty cells, and '#' to represent any blocked cells.
     *
     * A word can be placed horizontally (left to right or right to left) or vertically (top to bottom or bottom to top) in the board if:
     *
     * 1.It does not occupy a cell containing the character '#'.
     * 2.The cell each letter is placed in must either be ' ' (empty) or match the letter already on the board.
     * 3.There must not be any empty cells ' ' or other lowercase letters directly left or right of the word if the word was placed horizontally.
     * 4.There must not be any empty cells ' ' or other lowercase letters directly above or below the word if the word was placed vertically.
     * Given a string word, return true if word can be placed in board, or false otherwise.
     *
     * Example 1:
     * Input: board = [["#", " ", "#"], [" ", " ", "#"], ["#", "c", " "]], word = "abc"
     * Output: true
     * Explanation: The word "abc" can be placed as shown above (top to bottom).
     *
     * Example 2:
     * Input: board = [[" ", "#", "a"], [" ", "#", "c"], [" ", "#", "a"]], word = "ac"
     * Output: false
     * Explanation: It is impossible to place the word because there will always be a space/letter above or below it.
     *
     * Example 3:
     * Input: board = [["#", " ", "#"], [" ", " ", "#"], ["#", " ", "c"]], word = "ca"
     * Output: true
     * Explanation: The word "ca" can be placed as shown above (right to left).
     *
     * Constraints:
     * m == board.length
     * n == board[i].length
     * 1 <= m * n <= 2 * 105
     * board[i][j] will be ' ', '#', or a lowercase English letter.
     * 1 <= word.length <= max(m, n)
     * word will contain only lowercase English letters.
     *
     * Medium
     *
     * https://leetcode.com/problems/check-if-word-can-be-placed-in-crossword/
     */

    /**
     * Key:
     * 1.Processing rows and columns is the same as processing only the rows in the board and the rotated board.
     * 2.Looking for a word left to right and other direction (right to left) is same as looking for a word and looking
     *   for a reversed word in one direction.
     *
     * So we check for each row of the given board and the rotated board, with word and reversed word.
     * Checking rows on rotated board equals to checking vertical placement in the original board.
     * Checking a reversed word equals to checking word placement from right to left.
     *
     * Conditions #3 and #4 look wired, but what it actually tries to say is : a word must be placed in a row segment
     * that is either the entire row, or, a section of the row that is splited by '#'.
     */
    class Solution {
        public boolean placeWordInCrossword(char[][] board, String word) {
            for (char[][] b : new char[][][] {board, rotate(board)}) {//check board and rotated board
                for (char[] chs : b) {//check each row
                    String s = new String(chs);
                    String[] tokens = s.split("#");//find all tokens in the row

                    for (String t : tokens) {//check each token
                        for (String w : new String[] {word, new StringBuilder(word).reverse().toString()}) {//check for word and reversed word
                            if (w.length() == t.length()) {
                                if (canFit(w, t)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

            return false;
        }

        private char[][] rotate(char[][] board) {
            int m = board.length;
            int n = board[0].length;

            char[][] rotated = new char[n][m];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    rotated[j][i] = board[i][j];
                }
            }

            return rotated;
        }

        private boolean canFit(String w, String t) {
            for (int i = 0; i < w.length(); i++) {
                /**
                 * if token cell has a char and it is not the target char from the word, return false.
                 */
                if (t.charAt(i) != ' ' && t.charAt(i) != w.charAt(i)) return false;
            }

            return true;
        }
    }
}
