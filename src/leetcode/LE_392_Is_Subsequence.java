package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LE_392_Is_Subsequence {
    /**
     * Given a string s and a string t, check if s is subsequence of t.
     *
     * You may assume that there is only lower case English letters in
     * both s and t. t is potentially a very long (length ~= 500,000)
     * string, and s is a short string (<=100).
     *
     * A subsequence of a string is a new string which is formed from
     * the original string by deleting some (can be none) of the characters
     * without disturbing the relative positions of the remaining characters.
     * (ie, "ace" is a subsequence of "abcde" while "aec" is not).
     *
     * Example 1:
     * s = "abc", t = "ahbgdc"
     *
     * Return true.
     *
     * Example 2:
     * s = "axc", t = "ahbgdc"
     *
     * Return false.
     *
     * Follow up:
     * If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B,
     * and you want to check one by one to see if T has its subsequence.
     * In this scenario, how would you change your code?
     */
    public class Solution {
        public boolean isSubsequence(String s, String t) {
            if (s.length() == 0) return true;

            int indexS = 0, indexT = 0;
            while (indexT < t.length()) {
                if (t.charAt(indexT) == s.charAt(indexS)) {
                    indexS++;
                    if (indexS == s.length()) {
                        return true;
                    }
                }
                indexT++;
            }
            return false;
        }
    }

    class Solution1 {
        public boolean isSubsequence(String s, String t) {
            if (s == null || t == null ) return false;

            if (s.length() == 0) return true;

            int i = 0, j = 0;

            while (i < t.length()) {
                if (t.charAt(i) == s.charAt(j)) {
                    j++;
                    if (j == s.length()) {
                        return true;
                    }
                }

                i++;
            }

            return false;
        }
    }

    public class Solution_Follow_Up {
        /**
         * Optimize for online case :
         * Pre-process t, create a map, mapping each char in t to the list
         * of indices that it appears in t. Since we scan it from start to end
         * to build the map, the indices list is in sorted order. So for each
         * char in s, binary search it in map.
         *
         * Pre-processing : O(length of t)
         * Each call to isSubsequences() : O(L * logk), L is length of s,
         * k is the average size of each list for a char
         */
        // Follow-up: O(N) time for pre-processing, O(Mlog?) for each S.
        // Eg-1. s="abc", t="bahbgdca"
        // idx=[a={1,7}, b={0,3}, c={6}]
        //             prev=0
        //  i=0 ('a'): prev=1
        //  i=1 ('b'): prev=4
        //  i=2 ('c'): prev=6 (return true)
        // Eg-2. s="abc", t="bahgdcb"
        // idx=[a={1}, b={0,6}, c={5}]
        //  i=0 ('a'): prev=1
        //  i=1 ('b'): prev=7
        //  i=2 ('c'): prev=? (return false)
        public boolean isSubsequence(String s, String t) {
            List<Integer>[] idx = new List[256]; // Just for clarity
            for (int i = 0; i < t.length(); i++) {
                if (idx[t.charAt(i)] == null) {
                    idx[t.charAt(i)] = new ArrayList<>();
                }
                idx[t.charAt(i)].add(i);
            }

            int prev = 0;
            for (int i = 0; i < s.length(); i++) {
                if (idx[s.charAt(i)] == null) {
                    return false; // Note: char of S does NOT exist in T causing NPE
                }

                /**
                 * Once we find a matching char between t and s, set prev as the next char's index in t,
                 * it is the key we want to do binar search on indices list for current char
                 */
                int j = Collections.binarySearch(idx[s.charAt(i)], prev);

                if (j < 0) {
                    j = -j - 1;
                }

                if (j == idx[s.charAt(i)].size()) {
                    return false;
                }

                prev = idx[s.charAt(i)].get(j) + 1;
            }

            return true;
        }
    }
}
