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

        Medium
     **/

    /**
     * http://zxi.mytechroad.com/blog/leetcode/leetcode-79-word-search/
     *
     * DFS
     *
     * Time  : O(m * n * 4 ^ l), l is length of word, also the depth of recursion, each call to helper : O(4 ^ l)
     * Space : O(m * n + l)
     *
     */
    class Solution1 {
        public boolean exist(char[][] board, String word) {
            if (board == null || board.length == 0 || board[0].length == 0 || word == null || word.length() == 0)
                return false;

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
                /**
                 * !!!
                 * Set '#' to visited position in original 2D array, save the space for visited[][],
                 * also, once it is set to '#', checking if the position is visited can be combined into
                 * "if (board[i][j] == word.charAt(idx))".
                 */
                board[i][j] = '#';

                idx++;
                boolean res = helper(board, word, i + 1, j, idx)
                        || helper(board, word, i - 1, j, idx)
                        || helper(board, word, i, j + 1, idx)
                        || helper(board, word, i, j - 1, idx);

                board[i][j] = c;
                return res;
            }

            return false;
        }
    }


    class Solution2 {
        public boolean exist(char[][] board, String word) {
            if (null == board || board.length == 0 || null == word || word.length() == 0) {
                return false;
            }

            int m = board.length;
            int n = board[0].length;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (helper(board, word.toCharArray(), m, n, i, j, 0)) {
                        return true;
                    }
                }
            }

            return false;
        }

        private boolean helper(char[][] board, char[] word, int m , int n, int x, int y, int pos) {
            if (pos == word.length) {
                return true;
            }

            if (x < 0 || x >= m || y < 0 || y >= n || board[x][y] != word[pos]) {
                return false;
            }

            char temp = board[x][y];
            board[x][y] = '*';

            /**
             * !!!
             * Or of the results of recursion to 4 directions.
             */
            boolean res = helper(board, word, m, n, x + 1, y, pos + 1)
                    || helper(board, word, m, n, x - 1, y, pos + 1)
                    || helper(board, word, m, n, x, y + 1, pos + 1)
                    || helper(board, word, m, n, x, y - 1, pos + 1);

            board[x][y] = temp;

            return res;
        }
    }
}
