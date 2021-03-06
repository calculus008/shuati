package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 3/27/18.
 */
public class LE_211_Add_And_Search_Word {
    /**
        Design a data structure that supports the following two operations:

        void addWord(word)
        bool search(word)

        search(word) can search a literal word or a regular expression string containing only
        letters a-z or .. A '.' means it can represent any one letter.

        For example:

        addWord("bad")
        addWord("dad")
        addWord("mad")
        search("pad") -> false
        search("bad") -> true
        search(".ad") -> true
        search("b..") -> true

        Note:
        You may assume that all words are consist of lowercase letters a-z.
     */

    /**
     * Time  : O(n), n is word length
     * Space : O(Number of Words * word length * 26)
     *
     * Assume words only contain lower case letter, use array for children in TrieNode
     */
    class WordDictionary1 {
        private TrieNode root;

        /**
         * Initialize your data structure here.
         */
        public WordDictionary1() {
            //注意!!!, 不是 “TrieNode root = new TrieNode();"
            root = new TrieNode();
        }

        /**
         * Adds a word into the data structure.
         */
        public void addWord(String word) {
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

        /**
         * Returns if the word is in the data structure.
         * A word could contain the dot character '.' to represent any one letter.
         */
        public boolean search(String word) {
            if (word == null || word.length() == 0) return false;

            return helper(word, 0, root);
        }

        //DFS helper
        private boolean helper(String word, int start, TrieNode cur) {
            if (start == word.length()) return cur.isWord; //or cur.word.equals(word)

            /**
             * !!!
             * 因为'.'可以代表任何char,所以需要在下一层对所有字母遍历。
             */
            if (word.charAt(start) == '.') {//
                for (TrieNode child : cur.children) {
                    if (child != null && helper(word, start + 1, child)) {
                        return true;
                    }
                }
                /**
                 * 要在这里返回false, for loop执行后仍然没有返回，必定为false.
                 */
                return false;
            } else {
                int idx = word.charAt(start) - 'a';
                TrieNode temp = cur.children[idx];
                return temp != null && helper(word, start + 1, temp);
            }
        }
    }

    /**
     * Word uses unicode, so use hashmap in TrieNode for children
     */
    class WordDictionary2 {
        class TrieNode {
            Map<Character, TrieNode> children;
            boolean isWord;

            public TrieNode() {
                children = new HashMap<>();
                isWord = false;
            }
        }

        TrieNode root;

        public WordDictionary2() {
            root = new TrieNode();
        }

        public void addWord(String word) {
            if (word == null || word.isEmpty()) return;

            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                if (!cur.children.containsKey(c)) {
                    cur.children.put(c, new TrieNode());
                }
                cur = cur.children.get(c);
            }
            cur.isWord = true;
        }

        public boolean search(String word) {
            if (word == null || word.isEmpty()) return false;

            return helper(word, 0, root);
        }

        public boolean helper(String word, int start, TrieNode cur) {
            if (start == word.length()) {
                return cur.isWord;
            }

            char c = word.charAt(start);

            if (c == '.') {
                for (Character key : cur.children.keySet()) {
                    TrieNode child = cur.children.get(key);
                    if (child != null && helper(word, start + 1, child)) {
                        return true;
                    }
                }
            } else {
                TrieNode child = cur.children.get(c);
                if (child != null && helper(word, start + 1, child)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * LintCode 473
     */
    public class WordDictionary3 {
        class TrieNode {
            TrieNode[] children;
            boolean isWord;

            public TrieNode() {
                children = new TrieNode[26];
                isWord = false;
            }
        }

        TrieNode root;

        public WordDictionary3() {
            root = new TrieNode();
        }

        public void addWord(String word) {
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
        }


        /*
         * @param word: A string
         * @return: if the word is in the trie.
         */
        public boolean search(String word) {
            if (word == null || word.length() == 0) return false;

            return helper(word, 0, root);
        }

        //DFS
        private boolean helper(String word, int start, TrieNode cur) {
            if (start == word.length()) {
                return cur.isWord;
            }

            char c = word.charAt(start);
            if (c == '.') {
                for (TrieNode child : cur.children) {
                    if (child != null && helper(word, start + 1, child)) {
                        return true;
                    }
                }
            } else {
                int idx = c - 'a';
                TrieNode child = cur.children[idx];

                if (child != null && helper(word, start + 1, child)) {
                    return true;
                }
            }

            return false;
        }
    }

}
