package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 11/29/18.
 */
public class LE_720_Longest_Word_In_Dictionary {
    /**
         Given a list of strings words representing an English Dictionary,
         find the longest word in words that can be built one character at a time by other words in words.
         If there is more than one possible answer, return the longest word with the smallest lexicographical order.

         If there is no answer, return the empty string.
         Example 1:
         Input:
         words = ["w","wo","wor","worl", "world"]
         Output: "world"
         Explanation:
         The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".

         Example 2:
         Input:
         words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
         Output: "apple"
         Explanation:
         Both "apply" and "apple" can be built from other words in the dictionary.
         However, "apple" is lexicographically smaller than "apply".

         Note:
         All the strings in the input will only contain lowercase letters.
         The length of words will be in the range [1, 1000].
         The length of words[i] will be in the range [1, 30].

         Easy
     */

    /**
     * http://zxi.mytechroad.com/blog/string/leetcode-720-longest-word-in-dictionary/
     *
     * Brutal Force
     * 17 ms
     *
     * Each word, you need to get all prefixes of a word and check in HashMap or (set):
     * O(1) + O(2) + O(3) ... + O(l) -> O(w ^ 2), total : O(sum(w ^ 2))
     *
     * For Trie, for each new prefix, you just need to move to the next level in Trie:
     * O(1) + O(1)...+ O(1) -> O(w), total : O(sum(w))
     *
     * This is where Trie Solution is better in time complexity.
     *
     * Time  : O(sum(w ^ 2))
     * Space : O(n * w)
     */
    class Solution1 {
        public String longestWord(String[] words) {
            String res = "";

            Set<String> set = new HashSet<>();
            for (String word : words) {
                set.add(word);
            }

            for (String word : words) {
                if (word.length() < res.length() ||
                        (word.length() == res.length() && word.compareTo(res) > 0) ) {
                    continue;
                }

                char[] c = word.toCharArray();
                String prefix = "";
                boolean valid = true;
                for (int i = 0; i < c.length; i++) {
                    prefix += c[i];
                    if (!set.contains(prefix)) {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    res = word;
                }
            }

            return res;
        }
    }

    /**
     * Trie + Pruning
     *
     * Time  : O(sum(W))
     * Space : O(26 * W * n)
     *
     * 10 ms
     */
    class Solution2 {
        public class Trie {
            class TrieNode {
                TrieNode[] children;
                boolean isWord;

                public TrieNode() {
                    children = new TrieNode[26];
                    isWord = false;
                }
            }

            TrieNode root;

            public Trie() {
                root = new TrieNode();
            }

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
            }

            /**
             * Check if a word and all of its prefix strings are valid words
             */
            public boolean hasAllPrefix(String word) {
                if (word == null || word.length() == 0) return false;

                TrieNode cur = root;
                for (int i = 0; i < word.length(); i++) {
                    int idx = word.charAt(i) - 'a';
                    if (cur.children[idx] == null) {
                        return false;
                    }
                    cur = cur.children[idx];
                    if (!cur.isWord) {
                        return false;
                    }
                }

                return true;
            }
        }


        Trie trie;
        public String longestWord(String[] words) {
            String res = "";
            trie = new Trie();

            for (String word : words) {
                trie.insert(word);
            }

            for (String word : words) {
                /**
                 * Pruning
                 */
                if (word.length() < res.length() ||
                        (word.length() == res.length() && word.compareTo(res) > 0) ) {
                    continue;
                }

                if (trie.hasAllPrefix(word)) {
                    res = word;
                }
            }

            /**
                //Or sort words by its length, then in for loop, once we find a valid word, we can break right away.

                 Arrays.sort(words, (a, b) -> a.length() == b.length() ? a.compareTo(b) : b.length() - a.length());
                 for (String word : words) {
                     if (trie.hasAllPrefix(word)) {
                         res = word;
                         break;
                     }
                 }
             */

            return res;
        }
    }
}
