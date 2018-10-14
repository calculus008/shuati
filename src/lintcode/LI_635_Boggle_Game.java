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

    /**
     * 用Trie + DFS找每一个word的每一个字母。如果当前字母是word的最后一个单词，
     * 则我们找到了一个完整的字母，那么在当前占用状态下重扫board查找更多单词；
     * 否则我们继续寻找下一个字母。
     *
     * 这类题目是要在search()中做特殊处理，所以不能用原封不动地用Trie class.
     * 需要的是借用root和insert(),主要的工作实际上是根据题意在search的逻辑
     * 上做文章。
     *
     * 关键 ：
     * 1.每次找到一个word, 从root位置开始递归搜索，这时，没有把访问过的位置设置为FALSE，
     *   也就意味着，这些位置是被“站住“了。从此往下的递归就不能再考虑这些位置了，也就是
     *   满足题意“the words can not overlap in the same position”。
     * 2.每次从找到word的分支返回时，要将当前下一层找到的词去掉，因为反回后执行“visited[i][j] = false“，
     *   被去掉的词正是在这些位置，所以要更新res以反应当前的正确状态。
     * 3.也因此，res在运行中是动态增长和减少的，需要打擂台记录其最大size,这才是本题要的答案。
     *
     * Example :
     *           [
     *            ['a', 'b', 'c'],
     *            ['d', 'e', 'f'],
     *            ['g', 'h', 'i']
     *           ]
     *
     *           {"abc","defi","gh"}
     *
             set visited[0][0] as TRUE
             set visited[0][1] as TRUE
             set visited[0][2] as TRUE
             [abc]
             set visited[1][0] as TRUE
             set visited[1][1] as TRUE
             set visited[1][2] as TRUE
             set visited[2][2] as TRUE
             [abc, defi]
             set visited[2][0] as TRUE
             set visited[2][1] as TRUE
             [abc, defi, gh]
             return [abc, defi]
             set visited[2][0] as FALSE
             return [abc]
             set visited[1][2] as FALSE
             set visited[1][1] as FALSE
             set visited[1][0] as FALSE
             set visited[2][0] as TRUE
             set visited[2][0] as FALSE
             return []
             set visited[0][1] as FALSE
             set visited[0][0] as FALSE

             set visited[1][0] as TRUE
             set visited[1][1] as TRUE
             set visited[1][2] as TRUE
             set visited[2][2] as TRUE
             [defi]
             set visited[0][0] as TRUE
             set visited[0][1] as TRUE
             set visited[0][2] as TRUE
             [defi, abc]
             set visited[2][0] as TRUE
             set visited[2][1] as TRUE
             [defi, abc, gh]
             return [defi, abc]
             set visited[2][0] as FALSE
             return [defi]
             set visited[0][1] as FALSE
             set visited[0][0] as FALSE
             set visited[2][0] as TRUE
             set visited[2][0] as FALSE
             return []
             set visited[1][2] as FALSE
             set visited[1][1] as FALSE ...
     *
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
