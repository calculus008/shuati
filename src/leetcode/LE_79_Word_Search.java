package leetcode;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_79_Word_Search {
    /**
        Given a 2D board and a word, find if the word exists in the grid.

        The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.

        For example,
        Given board =

        [
          ['A','B','C','E'],
          ['S','F','C','S'],
          ['A','D','E','E']
        ]
        word = "ABCCED", -> returns true,
        word = "SEE", -> returns true,
        word = "ABCB", -> returns false.
     **/

    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0 || word == null || word.length() == 0) return false;

        int m = board.length;
        int n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (helper(board, word, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean helper(char[][] board, String word, int i, int j, int idx) {
        /**!!!This line must be the first line to execute (must before the logic checking boundary of i, j).
              Otherwise, it will fail for case like "[[a]], 'a'" : first char match, then to all 4 directions, they are all
              out of boundary, so it returns false.
         **/
        if (idx >= word.length()) return true; // use ">=" also works

        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false;

        if (board[i][j] == word.charAt(idx)) {
            //!!! set current cell with '#' if matched. It saves us from using a boolean matrix to track which cells are visted
            char c = board[i][j];
            board[i][j] = '#';

            idx++;
            boolean res =  helper(board, word, i + 1, j, idx)
                    || helper(board, word, i - 1, j, idx)
                    || helper(board, word, i, j + 1, idx)
                    || helper(board, word, i, j - 1, idx);

            board[i][j] = c;
            return res;
        }

        return false;
    }
}
