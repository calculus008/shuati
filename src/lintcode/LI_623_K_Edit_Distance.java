package lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 10/26/18.
 */
public class LI_623_K_Edit_Distance {
    /**
         Given a set of strings which just has lower case letters and a target string,
         output all the strings for each the edit distance with the target no greater than k.

         You have the following 3 operations permitted on a word:

         Insert a character
         Delete a character
         Replace a character
         Example
         Given words = ["abc", "abd", "abcd", "adc"] and target = "ac", k = 1
         Return ["abc", "adc"]

         Hard
     */

    /**
     * Trie + DFS + DP
     *
     * dp[i]: min edit distance of TrieString[0..dfs_level] and target[0..j]
     *
     * dp[i]表示从Trie的root节点走到当前node节点形成的Prefix和target的前j个字符的最小编辑距离
     *
     * Time : O(nml), n: number of words, m: average word length, l: length of target
     *
     */
    public class Solution {

        class TrieNode {
            boolean isWord;
            String word;
            TrieNode[] children;

            public TrieNode(){
                isWord = false;
                children = new TrieNode[26];
            }
        }

        class Trie {
            public TrieNode root;//public

            public Trie() {
                root = new TrieNode();
            }

            public void insert(String word) {
                TrieNode cur = root;
                for (char c : word.toCharArray()) {
                    int idx = c - 'a';
                    /**
                     * !!! 千万不能忘记这个if
                     */
                    if (cur.children[idx] == null) {
                        cur.children[idx] = new TrieNode();
                    }
                    cur = cur.children[idx];
                }
                cur.isWord = true;
                cur.word = word;
            }
        }

        public List<String> kDistance(String[] words, String target, int k) {
            List<String> res = new ArrayList<>();
            if (words == null || target == null) return res;

            Trie trie = new Trie();
            for (String word : words) {
                trie.insert(word);
            }

            int[] dp = new int[target.length() + 1];
            for (int i = 0; i < dp.length; i++) {
                dp[i] = i;
            }

            helper(target, k, trie.root, dp, res);

            return res;
        }

        private void helper(String target, int k, TrieNode node, int[] dp, List<String> res) {
            if (node == null) return;//!!!

            int n = target.length();
            if (dp[n] <= k && node.isWord) {
                res.add(node.word);
            }

            /**
             * 实际上是把 LE_72_Edit_Distance 中的dp[][]分成一维数组：
             * dp代表这一行  ====>  [i-1,j-1],[i -1,j]
             * next代表dp的下一行   [i,j-1],  [i,j]
             **/
            int[] next = new int[n + 1];

            /**
             * 外循环iterate each char
             */
            for (int i = 0; i < 26; i++) {
                if (node.children[i] == null) continue;

                /**
                 * 内循环取target的每一个char
                 * !!!这里是match target String, 所以不是按Trie的深度d看对应target的char (target.charAt(d - 1))
                 * Trie的深度d是用来看当前word里的char, saved in node.children.
                 */
                next[0] = dp[0] + 1;//!!!相当于 LE_72_Edit_Distance 里为dp[i][0]初始化
                for (int j = 1; j <= n; j++) {
                    if (target.charAt(j - 1) - 'a' == i) {
                        next[j] = dp[j - 1];
                    } else {

                        /**
                         * 需要比较[左上，左，上]三个位置的值
                         * 分别对应[replace,delete,add]三个操作
                         **/
                        next[j] = Math.min(Math.min(dp[j], dp[j - 1]), next[j - 1]) + 1;
                    }
                }

                helper(target, k, node.children[i], next, res);
            }
        }
    }
}
