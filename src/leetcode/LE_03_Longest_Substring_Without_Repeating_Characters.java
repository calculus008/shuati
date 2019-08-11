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

    /**
     *   Solution 1
         使用一个hashset和双指针配合即可。
         相当于开一个start和end的窗口，每次遍历s的字母，如果set里没有，加进去并把当前的end定在这个字母位置，得到长度。
         如果set里有了这个字母，则要移动窗口头指针，不断提出移出窗口的字母，直到i位置的字母不在set里出现，得到新窗口。
     */
    public int lengthOfLongestSubstring1(String s) {
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

    /**
     * Solution 2
     * 套用九章Sliding Window模版
     */
    public int lengthOfLongestSubstrings2(String s) {
        if (s == null || s.length() == 0) return 0;

        int res = Integer.MIN_VALUE;
        char[] map = new char[256];

        /**
         * here, i is end, j is start
         */
        for (int i = 0, j = 0; i < s.length(); i++) {
            map[s.charAt(i)]++;
            while(map[s.charAt(i)] > 1) {
                /**
                 * must first do counter minus one, then move index j
                 */
                map[s.charAt(j)]--;
                j++;
            }
            res = Math.max(res, i - j + 1);
        }

        return res;
    }
}
