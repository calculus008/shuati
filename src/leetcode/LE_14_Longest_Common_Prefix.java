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
            while (strs[i].indexOf(pre) != 0) {//This means pre is not the prefix in current string, hence, shorten it by one and continue
                pre = pre.substring(0, pre.length() - 1);
                if (pre.isEmpty()) {
                    return "";
                }
            }
        }

        return pre;
    }

    /**
     * Trie solution
     * Time : O(S) , where S is the sum of all characters in all strings.
     */
    public class Solution {
        public class TrieNode {
            char ch;
            TrieNode[] next = new TrieNode[256];

            public TrieNode() {
            }

            public TrieNode(char ch) {
                this.ch = ch;
            }
        }

        public TrieNode triebuilder(String[] strs) {
            TrieNode root = new TrieNode();
            for (String str : strs) {
                if (str.length() == 0) {
                    return null;
                }

                TrieNode cur = root;
                for (char c : str.toCharArray()) {
                    if (cur.next[c - 'A'] == null) {
                        cur.next[c - 'A'] = new TrieNode(c);
                    }
                    cur = cur.next[c - 'A'];
                }
            }
            return root;
        }

        public String longestCommonPrefix(String[] strs) {
            String result = "";
            if (strs == null || strs.length == 0) {
                return result;
            }

            TrieNode root = triebuilder(strs);

            if (root == null) {
                return "";
            }

            while (count(root.next) == 1) {
                for (TrieNode node : root.next) {
                    if (node != null) {
                        result += node.ch;
                        root = node;
                        break;
                    }
                }
            }
            return result;
        }

        private int count(TrieNode[] next) {
            int result = 0;
            for (TrieNode node : next) {
                if (node != null) {
                    result++;
                }
            }
            return result;
        }
    }
}
