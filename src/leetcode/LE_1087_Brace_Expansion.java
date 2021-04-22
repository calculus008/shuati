package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LE_1087_Brace_Expansion {
    /**
     * You are given a string s representing a list of words. Each letter in the word has one or more options.
     *
     * If there is one option, the letter is represented as is.
     * If there is more than one option, then curly braces delimit the options. For example, "{a,b,c}" represents options ["a", "b", "c"].
     * For example, if s = "a{b,c}", the first character is always 'a', but the second character can be 'b' or 'c'. The original list is ["ab", "ac"].
     *
     * Return all words that can be formed in this manner, sorted in lexicographical order.
     *
     * Example 1:
     *
     * Input: s = "{a,b}c{d,e}f"
     * Output: ["acdf","acef","bcdf","bcef"]
     * Example 2:
     *
     * Input: s = "abcd"
     * Output: ["abcd"]
     *
     *
     * Constraints:
     * 1 <= s.length <= 50
     * s consists of curly brackets '{}', commas ',', and lowercase English letters.
     * s is guaranteed to be a valid input.
     * There are no nested curly brackets.
     * All characters inside a pair of consecutive opening and ending curly brackets are different.
     *
     * Medium
     */

    /**
     * DFS backtracking
     */
    class Solution {
        List<String> list = new ArrayList<>();
        public String[] expand(String s) {
            helper(s, "");
            Collections.sort(list);
            return list.toArray(new String[0]); //convert list of string to string array
        }

        private void helper(String s, String word) {
            if (s.equals("")) {
                list.add(word);
                return;
            }

            if (s.charAt(0) == '{') {
                int idx = s.indexOf("}");
                String[] options = s.substring(1, idx).split(",");
                for (String str : options) {
                    helper(s.substring(idx + 1), word + str);
                }
            } else {
                helper(s.substring(1), word + s.charAt(0));
            }
        }
    }
}
