package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 4/7/18.
 */
public class LE_212_Find_Words_II {
    /*
        Given a 2D board and a list of words from the dictionary, find all words in the board.

        Each word must be constructed from letters of sequentially adjacent cell,
        where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

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

    //Trie + DFS, Time : O(m * n * leetcode.TrieNode), Space : O(leetcode.TrieNode)
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
            //!!! 不能return, for example, find "fight", another word is "fighter", need to continue to search
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
}
