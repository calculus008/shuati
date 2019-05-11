package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 11/30/18.
 */
public class LE_409_Longest_Palindrome {
    /**
         Given a string which consists of lowercase or uppercase letters,
         find the length of the longest palindromes that can be built with those letters.

         This is case sensitive, for example "Aa" is not considered a palindrome here.

         Note:
         Assume the length of given string will not exceed 1,010.

         Example:

         Input:
         "abccccdd"

         Output:
         7

         Explanation:
         One longest palindrome that can be built is "dccaccd", whose length is 7.

         Easy
     */

    /**
     * http://zxi.mytechroad.com/blog/greedy/leetcode-409-longest-palindrome/
     *
     * Time and Space : O(n)
     *
     * Slower version
     * 23 ms
     */
    public class Solution {
        public int longestPalindrome1(String s) {
            if (null == s)
                return 0;

            if (s.length() == 0)
                return 0;

            Map<Character, Integer> map = new HashMap<>();

            for (char c : s.toCharArray()) {
                if (!map.containsKey(c)) {
                    map.put(c, 1);
                } else {
                    map.put(c, map.get(c) + 1);
                }
            }

            int x = 0;
            int maxOdd = 0;
            for (int i : map.values()) {
                if (i % 2 == 0) {
                    x = x + i;
                } else {
                    x = x + i - 1;
                    if (i > maxOdd) {
                        maxOdd = i;
                    }
                }
            }

            return x + (maxOdd > 0 ? 1 : 0);
        }
    }

    /**
     * Concise and faster version
     * 1.Use int[] of size 128 instead of HashMap
     * 2.bit operation
     *
     * 4 ms
     */
    public int longestPalindrome2(String s) {
        if(null == s || s.length() == 0)
            return 0;

        int[] map = new int[128];
        for (char c : s.toCharArray()) {
            map[c]++;
        }

        int res = 0;
        int odd = 0;

        for (int freq : map) {
            /**
             * equal to "res += freq / 2 * 2", set last bit to 0
             */
            res += freq & (~1);
            odd |= freq & 1;
        }

        return res + odd;
    }
}
