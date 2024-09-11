package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 10/26/18.
 */
public class LE_14_Longest_Common_Prefix {
    /**
         Write a function to find the longest common prefix string amongst an array of strings.

         If there is no common prefix, return an empty string "".

         Example 1:

         Input: ["flower","flow","flight"]
         Output: "fl"

         Example 2:

         Input: ["dog","racecar","car"]
         Output: ""
         Explanation: There is no common prefix among the input strings.

         Easy
     */

    /**
     * Time : O(S) , where S is the sum of all characters in all strings.
     */
    public String longestCommonPrefix1(String[] strs) {
        if (null == strs || strs.length == 0) return "";

        String pre = strs[0]; //Take the first String as pre
        for (int i = 1; i < strs.length; i++) {
            /**
             * "indexOf": returns the first index of the occurrence of the given string
             *            if not found, returns -1
             * Here, when indexOf returns none zero value, it means pre is not the prefix in current string,
             * hence, shorten it by one and continue.
             *
             * The nice touch is that it does not check return value as '-1' since it already covered by the while
             * loop, it will return "" in the end. So it save several lines of code.
             */
            while (strs[i].indexOf(pre) != 0) {
                pre = pre.substring(0, pre.length() - 1);
                if (pre.isEmpty()) {
                    return "";
                }
            }
        }

        return pre;
    }

    /**
     * Not using String indexOf(), it's actually slower then the one using indexOf()
     */
    class Solution {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) {
                return "";
            }
            String prefix = strs[0];
            int count = strs.length;
            for (int i = 1; i < count; i++) {
                prefix = longestCommonPrefix(prefix, strs[i]);
                if (prefix.length() == 0) {
                    break;
                }
            }
            return prefix;
        }

        public String longestCommonPrefix(String str1, String str2) {
            int length = Math.min(str1.length(), str2.length());
            int index = 0;
            while (index < length && str1.charAt(index) == str2.charAt(index)) {
                index++;
            }
            return str1.substring(0, index);
        }
    }

    /**
     * TrieNode solution
     * Time : O(S) , where S is the sum of all characters in all strings.
     *
     * Key points :
     * 1.Build Trie
     * 2.Start from root of Trie Tree, for the common prefix, the current node will have only one child.
     * 3.We keep going down in Trie Tree if :
     *   a.There's only one child
     *   AND
     *   b.No complete word appears.
     *
     * For example :
     *
     * ["aa", "aab", "aabc"]
     *
     * Without the condition "b", "aab" will be the output, which is wrong, the correct output should be "aa".
     *
     */
    class Solution_Trie {
        class TrieNode {
            char ch;
            TrieNode[] next = new TrieNode[256];
            /**
             * !!!
             */
            boolean isWord = false;

            public TrieNode() {
            }

            public TrieNode(char ch) {
                this.ch = ch;
            }
        }

        public TrieNode buildTrie(String[] strs) {
            TrieNode root = new TrieNode();
            for (String str : strs) {
                /**
                 * This step is an optimization for this particular question, not the common step in creating Trie.
                 * If there is a null or empty string in strs, then common prefix can only be "".
                 */
                if (str == null || str.length() == 0) return null;

                TrieNode cur = root; // start from root each time for a new str
                for (char c : str.toCharArray()) {
                    if (cur.next[c] == null) {
                        cur.next[c] = new TrieNode(c);
                    }

                    cur = cur.next[c];
                }
                cur.isWord = true;
            }

            return root;
        }

        public String getLongestPrefix(String[] strs) {
            String res = "";
            if (strs == null || strs.length == 0) return res;

            TrieNode root = buildTrie(strs);

            if (root == null) return "";

            /**
             * Must stop when we reach the end of a word.
             * Common prefix -> count of the next level children should be 1
             */
            boolean stop = false;
            while (count(root.next) == 1 && !stop) {
                for (TrieNode node : root.next) {
                    if (node != null) {
                        res += node.ch;
                        stop = node.isWord;
                        root = node;
                        break;
                    }
                }
            }

            return res;
        }

        private int count(TrieNode[] next) {
            int res = 0;
            for (TrieNode node : next) {
                if (node != null) {
                    res++;
                }
            }

            return res;
        }

//        public static void main(String[] args) {
//            String[] strs = {"abc", "abc", "abc", "abc"};
//            // String[] strs = {"aa", "aabcd", "aab", "aabbbb"};
//            System.out.println(getLongestPrefix(strs));
//        }
    }
}
