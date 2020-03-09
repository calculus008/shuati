package Interviews.Indeed.Practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Auto_Complete_Practice {
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isWord;

        public TrieNode() {
            children = new HashMap<>();
            isWord = false;
        }
    }

    /**
     * Create Trie
     * Time  : O(m * n), m is the max length of the word, n is total number of word.
     * Space : O(n * k), a node points to k children
     */
    class Trie {
        TrieNode root;

        /**
         * public Trie(), NOT public class Trie() !!!
         */
        public Trie() {
            root = new TrieNode();
        }

        /**
         * Time : O(L), L is the length of the word
         */
        public void insert(String word) {
            /**
             * !!! validation
             */
            if (word == null || word.length() == 0) return;

            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                TrieNode next = cur.children.get(c);
                if (next == null) {
                    next = new TrieNode();
                    /**
                     * cur.children.put(..) !!! "cur."
                     */
                    cur.children.put(c, next);
                }

                cur = next;
            }
            cur.isWord = true;
        }

        /**
         * Time : O(L + x), L is length of the prefix, x is the number of nodes under prefix subtree
         */
        public List<String> search(String prefix) {
            List<String> res = new ArrayList<>();
            /**
             * !!! validation
             */
            if (prefix == null || prefix.isEmpty()) return res;

            TrieNode cur = root;
            for (char c : prefix.toCharArray()) {
                TrieNode next = cur.children.get(c);
                if (next == null) return res;
                cur = next;
            }

//            if (cur.isWord) res.add(prefix);

            StringBuilder sb = new StringBuilder();
            sb.append(prefix);
            helper(cur, sb, res);

            return res;
        }

        private void helper(TrieNode cur, StringBuilder sb, List<String> res) {
            if (cur == null) return;

            if (cur.isWord) {
                res.add(sb.toString());
            }

            int n = sb.length();
            for (Character c : cur.children.keySet()) {
                helper(cur.children.get(c), sb.append(c), res);
                sb.setLength(n);
            }
        }
    }

    Trie trie;
    public Auto_Complete_Practice(String[] words) {
        trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }
    }

    public List<String> findStartWith(String prefix) {
        return trie.search(prefix);
    }
}
