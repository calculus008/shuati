package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 8/29/18.
 */
public class LE_03_Longest_Substring_Without_Repeating_Characters {
    /**
         Given a string, find the length of the longest substring without repeating characters.

         Example 1:

         Input: "abcabcbb"
         Output: 3
         Explanation: The answer is "abc", which the length is 3.
         Example 2:

         Input: "bbbbb"
         Output: 1
         Explanation: The answer is "b", with the length of 1.
         Example 3:

         Input: "pwwkew"
         Output: 3
         Explanation: The answer is "wke", with the length of 3.
         Note that the answer must be a substring, "pwke" is a subsequence and not a substring.

         Median
     */

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int start = 0;
        int end = 0;
        Set<Character> set = new HashSet<>();
        int res = 0;
        while (start < n && end < n) {
            //sliding window, when there's dup, move start (end stays the same), until no dup
            if (set.contains(s.charAt(end))) {
                set.remove(s.charAt(start++));
            } else {
                //!!! can't do "end++" here, need to calculate end -start + 1, then move end by one
                set.add(s.charAt(end));
                res = Math.max(res, end - start + 1);
                end++;
            }
        }

        return res;
    }
}
