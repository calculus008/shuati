package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/7/18.
 */
public class LE_212_Word_Search_II {
    /**
        Given a 2D board and a list of words from the dictionary, find all words in the board.

        Each word must be constructed from letters of sequentially adjacent cell,
        where "adjacent" cells are those horizontally or vertically neighboring.
        The same letter cell may not be used more than once in a word.

        For example,
        Given words = ["oath","pea","eat","rain"] and board =

        [
          ['o','a','a','n'],
          ['e','t','a','e'],
          ['i','h','k','r'],
          ['i','f','l','v']
        ]
        Return ["eat","oath"].
     */

    /**
     * Solution 1
     * Trie + DFS, Time : O(m * n * TrieNode), Space : O(TrieNode)
     **/
    class TrieNode {
        TrieNode[] next = new TrieNode[26]; //!!! TrieNode数组
        String word;
    }

    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        if (board == null || board.length == 0 || words == null || words.length == 0) return res;

        TrieNode root = buildTrieTree(words);

        //Wrong !!!, this just searches word starting from 0,0.
        // helper(board, res, root, 0, 0);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                helper(board, res, root, i, j);
            }
        }

        return res;
    }

    public TrieNode buildTrieTree(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (cur.next[idx] == null) {
                    cur.next[idx] = new TrieNode();
                }
                cur = cur.next[idx];
            }
            cur.word = word;
        }
        return root;
    }

    public void helper(char[][] board, List<String> res, TrieNode p, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;

        char c = board[i][j];
        int idx = c - 'a';
        if (c == '#' || p.next[idx] == null) return;

        //!!!
        p = p.next[idx];

        if (p.word != null) {
            res.add(p.word);
            p.word = null;
            /**
             * !!!
             * 不能return, for example, find "fight", another word is "fighter", need to continue to search
             */
            // return
        }

        //!!! mark current cell as visited, so the DFS below won't come back to current cell
        board[i][j] = '#';
        helper(board, res, p, i + 1, j);
        helper(board, res, p, i - 1, j);
        helper(board, res, p, i, j + 1);
        helper(board, res, p, i, j - 1);
        //DFS, recover
        board[i][j] = c;
    }

    /**
     * Solution 2 : HashMap + DFS
     *
     * 和Solution 3 用同样的算法和数据结构。不同的是 ：
     * 在helper()中，处理当前传入的坐标处的char(board[i][j])。处理完后，设置已处理标志，再向4个方向递归。
     *
     * 而Solution 3，先设置已处理标志，然后递归，对当前坐标处char的处理，是在下一层的递归中。这样做要求
     * 每次递归前要设置访问标志，完成递归后再回复，繁琐，而且不直观。
     */
    public List<String> wordSearchII_MyVersion(char[][] board, List<String> words) {
        List<String> res = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.size() == 0) {
            return res;
        }

        int m = board.length;
        int n = board[0].length;

        /**
         * map : key - prefix or word, value : true if it is word, false if it is prefix
         */
        Map<String, Boolean> map = buildMap(words);
        Set<String> set = new HashSet<>();

        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n; j++) {
                /**
                 * !!!
                 * "i, j", NOT "0, 0"
                 */
                helper(board, set, map, i, j, "");
            }
        }
        return new ArrayList<>(set);
    }

    private void helper(char[][] board, Set<String> set, Map<String, Boolean> map, int i, int j, String cur) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length){
            return;
        }

        char c = board[i][j];
        String str = cur + c;

        if (c == '*' || !map.containsKey(str)) {
            return;
        }

        /**
         * if map.get(str) == true, then it is a word, we add it to set.
         */
        if (map.get(str)) {
            set.add(str);
        }

        board[i][j] = '*';
        helper(board, set, map, i + 1, j, str);
        helper(board, set, map, i - 1, j, str);
        helper(board, set, map, i, j + 1, str);
        helper(board, set, map, i, j - 1, str);
        board[i][j] = c;
    }

    private Map<String, Boolean> buildMap(List<String> words) {
        Map<String, Boolean> map = new HashMap<>();
        for (String word : words) {
            for (int i = 0; i < word.length() - 1; i++) {
                String prefix = word.substring(0, i + 1);
                /**
                 * Example :
                   ["abce","sfcs","adee"]
                   ["as","ab","cf","da","ee","e","adee","eeda"]


                   "ee"是个word, 当处理“eeds"时，"ee"是它的一个prefix. 所以，
                   必须先检查prefix是否已经在map中被记录为word了(value为true),
                   如果是，则不做处理。否则会覆盖掉一个合法的word。
                 **/
                if (!map.containsKey(prefix)) {
                    map.put(prefix , false);
                }
            }
            map.put(word, true);
        }
        return map;
    }

    /**
     * Solution 3 : HashMap + DFS
     */
    int[][] dir = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public List<String> wordSearchII_JiuZhang(char[][] board, List<String> words) {
        List<String> res = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.size() == 0) {
            return res;
        }

        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        Map<String, Boolean> map = buildMap(words);
        Set<String> set = new HashSet<>();

        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n; j++) {
                visited[i][j] = true;
                helper(board, set, map, visited, i, j, "" + board[i][j]);
                visited[i][j] = false;
            }
        }
        return new ArrayList<>(set);
    }

    private void helper(char[][] board, Set<String> set, Map<String, Boolean> map, boolean[][] visited, int i, int j, String cur) {
        if (!map.containsKey(cur)) {
            return;
        }

        if (map.get(cur)) {
            set.add(cur);
        }

        for (int k = 0; k < dir.length; k++) {
            int nextRow = i + dir[k][0];
            int nextCol = j + dir[k][1];

            if(!isValid(nextRow, nextCol, board.length, board[0].length)
                    || visited[nextRow][nextCol]) {
                continue;
            }

            String next = cur + board[nextRow][nextCol];
            visited[nextRow][nextCol] = true;
            helper(board, set, map, visited, nextRow, nextCol, next);
            visited[nextRow][nextCol] = false;
        }
    }

    private boolean isValid(int i, int j, int m, int n) {
        if (i < 0 || i >= m || j < 0 || j >= n){
            return false;
        }
        return true;
    }

    /**
     * Another form of Trie + DFS solution
     * Difference from Solution 1 : use "insert()" from Tire class
     * to populate Trie (instead of using "buildTrieTree()"
     */
    public class Solution {
        class TrieNode {
            TrieNode[] children;
            String word;

            public TrieNode() {
                children = new TrieNode[26];
                word = null;
            }
        }

        TrieNode root;

        private void insert(String word) {
            if (word == null || word.length() == 0) return;

            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (cur.children[idx] == null) {
                    cur.children[idx] = new TrieNode();
                }
                cur = cur.children[idx];
            }

            cur.word = word;
        }

        public List<String> wordSearchII(char[][] board, List<String> words) {
            List<String> res = new ArrayList<>();
            if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.size() == 0) return res;

            root = new TrieNode();
            for (String word : words) {
                insert(word);
            }

            for (int i = 0; i < board.length ; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    dfs(board, res, root, i, j);
                }
            }

            return res;
        }

        private void dfs(char[][] board, List<String> res, TrieNode cur, int i, int j) {
            /**
             * check if i and j is out of boundary
             */
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
                return;
            }

            /**
             * check if it is visited and if target char exists in Trie
             */
            char c = board[i][j];
            int idx = c - 'a';
            if (c == '#' || cur.children[idx] == null) {
                return;
            }

            /**
             * !!!
             * Must move to children before checking "cur.word".
             * It is the first thing to do after validating input params!!!
             *
             * The call to dfs() starts from root, if we don't move
             * to root's children here, then check cur.word will be meaningless
             * since root never stores a word. If "a" is valid word, then
             * you can't add "a" to res if you check cur.children while cur
             * is root.
             * !!!
             */
            cur = cur.children[idx];

            if (cur.word != null) {
                res.add(cur.word);
                /**
                 * Another way to prevent duplicates in res (list)
                 * Or put word in a HashSet, then convert set to list at the end
                 */
                cur.word = null;
            }

            board[i][j] = '#';
            dfs(board, res, cur, i + 1, j);
            dfs(board, res, cur, i, j + 1);
            dfs(board, res, cur, i - 1, j);
            dfs(board, res, cur, i, j - 1);
            board[i][j] = c;
        }
    }

}
