package leetcode;

import java.util.List;

public class LE_524_Longest_Word_In_Dictionary_Through_Deleting {
    /**
     * Given a string and a string dictionary, find the longest string in the
     * dictionary that can be formed by deleting some characters of the given
     * string. If there are more than one possible results, return the longest
     * word with the smallest lexicographical order. If there is no possible
     * result, return the empty string.
     *
     * Example 1:
     * Input:
     * s = "abpcplea", d = ["ale","apple","monkey","plea"]
     *
     * Output:
     * "apple"
     *
     *
     * Example 2:
     * Input:
     * s = "abpcplea", d = ["a","b","c"]
     *
     * Output:
     * "a"
     * Note:
     * All the strings in the input will only contain lower-case letters.
     * The size of the dictionary won't exceed 1,000.
     * The length of all the strings in the input won't exceed 1,000.
     *
     * Medium
     */

    /**
     * Key:
     * 1.Know how to check if s1 is a sub-sequence of s2.
     * 2.How to make sure the result is with the smallest lexicographical order
     *
     * Time : O(nmk)
     * where n is the length of string s, m is average length of words, k is the number of words in the dictionary.
     */
    class Solution {
        public String findLongestWord(String s, List<String> d) {
            if (s == null || s.length() == 0 || d == null || d.size() == 0) return "";

            char[] chs = s.toCharArray();
            String res = "";

            for (String word : d) {
                /**
                 * !!!
                 * Sub-sequence matching
                 */
                int i = 0;
                for (char c : chs) {
                    if (i < word.length() && c == word.charAt(i)) {
                        i++;
                    }
                }

                if (i == word.length()) {
                    /**
                     * !!!
                     * String comparison:
                     * "word.compareTo(res) < 0"
                     */
                    if (word.length() > res.length() || (word.length() == res.length() && word.compareTo(res) < 0)) {
                        res = word;
                    }
                }
            }

            return res;
        }
    }
}
