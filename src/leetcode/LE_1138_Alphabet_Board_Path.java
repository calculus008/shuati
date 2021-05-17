package leetcode;

import java.util.Collections;

public class LE_1138_Alphabet_Board_Path {
    /**
     * On an alphabet board, we start at position (0, 0), corresponding to character board[0][0].
     *
     * Here, board = ["abcde", "fghij", "klmno", "pqrst", "uvwxy", "z"], as shown in the diagram below.
     *
     *  a b c d e
     *  f g h i j
     *  k l m n o
     *  p q r s t
     *  u v w x y
     *  z
     *
     * We may make the following moves:
     *
     * 'U' moves our position up one row, if the position exists on the board;
     * 'D' moves our position down one row, if the position exists on the board;
     * 'L' moves our position left one column, if the position exists on the board;
     * 'R' moves our position right one column, if the position exists on the board;
     * '!' adds the character board[r][c] at our current position (r, c) to the answer.
     * (Here, the only positions that exist on the board are positions with letters on them.)
     *
     * Return a sequence of moves that makes our answer equal to target in the minimum number of moves.  You may return any path that does so.
     *
     * Example 1:
     * Input: target = "leet"
     * Output: "DDR!UURRR!!DDD!"
     *
     * Example 2:
     * Input: target = "code"
     * Output: "RR!DDRR!UUL!R!"
     *
     * Constraints:
     * 1 <= target.length <= 100
     * target consists only of English lowercase letters.
     *
     * Medium
     */

    /**
     * 1.Calculate coordination (x1, y1) of current char in the matrix, then get move steps on x and y directions.
     * 2.Use "Collections.nCopies()"
     * 3.Tricky part is "z", on the last row, you can only move UP, no LEFT and RIGHT. Therefore, you need to give
     *   priority to "U", make it the first possible move in assembling the steps. "U" > "R" and "L" > "D". For example,
     *   if you move form "o" to "z", since we put the same direction movement together, we first go L 5 times, then
     *   move DOWN 4 times, rather than move down 3 times, move left 5 times, then again move down one more time.
     *
     * Time  : O(n)
     * Space : O(1)
     */
    public String alphabetBoardPath(String target) {
        StringBuilder sb = new StringBuilder();

        int x = 0, y = 0;
        for (char c : target.toCharArray()) {
            int x1 = (c - 'a') % 5;
            int y1 = (c - 'a') /5;

            sb.append(String.join("", Collections.nCopies(Math.max(y - y1, 0), "U")) +
                      String.join("", Collections.nCopies(Math.max(x1 - x, 0), "R")) +
                      String.join("", Collections.nCopies(Math.max(x - x1, 0), "L")) +
                      String.join("", Collections.nCopies(Math.max(y1 - y, 0), "D")) +
                    "!");
            x = x1;
            y = y1;
        }

        return sb.toString();
    }
}
