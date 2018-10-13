package lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 10/13/18.
 */
public class LI_635_Boggle_Game {
    /**
         Given a board which is a 2D matrix includes a-z and dictionary dict,
         find the largest collection of words on the board, the words can not
         overlap in the same position. return the size of largest collection.

         Example
         Give a board below

         [['a', 'b', 'c'],
         ['d', 'e', 'f'],
         ['g', 'h', 'i']]
         dict = ["abc", "cfi", "beh", "defi", "gh"]
         Return 3 // we can get the largest collection["abc", "defi", "gh"]

         Notice
         The words in the dictionary are not repeated.
         You can reuse the words in the dictionary.

         Hard
     */

    public class Solution {
        class TrieNode {
            TrieNode[] children;
            boolean isWord;
            String word;

            public TrieNode() {
                children = new TrieNode[26];
                isWord = false;
                word = null;
            }
        }

        TrieNode root;

        public void insert(String word) {
            if (word == null || word.length() == 0) return;

            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (cur.children[idx] == null) {
                    cur.children[idx] = new TrieNode();
                }
                cur = cur.children[idx];
            }

            cur.isWord = true;
            cur.word = word;
        }

        int m, n;
        int max;

        public int boggleGame(char[][] board, String[] words) {
            if (board == null || board.length == 0 || board[0].length == 0
                    || words == null || words.length == 0) {
                return 0;
            }

            root = new TrieNode();

            for (String word : words) {
                insert(word);
            }

            m = board.length;
            n = board[0].length;
            max = 0;

            List<String> res = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    dfs(board, new boolean[m][n], i, j, root, res);
                }
            }

            return max;
        }

        private void dfs(char[][] board, boolean[][] visited, int i, int j, TrieNode cur, List<String> res) {
            int idx = board[i][j] - 'a';
            if (!isValid(i, j) || visited[i][j] || cur.children[idx] == null) return;

            cur = cur.children[idx];
            visited[i][j] = true;

            if (cur.isWord) {

                res.add(cur.word);
                max = Math.max(max, res.size());

                for (int x = 0; x < m; x++) {
                    for (int y = 0; y < n; y++) {
                        //!!! pass "root", not "cur"!!!
                        dfs(board, visited, x, y, root, res);
                    }
                }

                res.remove(res.size() - 1);
                //!!!
                return;
            }

            int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

            for (int k = 0; k < dir.length; k++) {
                int nextX = i + dir[k][0];
                int nextY = j + dir[k][1];
                if (isValid(nextX, nextY)) {
                    dfs(board, visited, nextX, nextY, cur, res);
                }
            }

            visited[i][j] = false;
        }

        private boolean isValid(int x, int y) {
            return (x >= 0 && x < m && y >= 0 && y < n);
        }
    }
}
