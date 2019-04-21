package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 8/15/18.
 */
public class LE_438_Find_All_Anagrams_In_A_String {
    /**
         Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.

         Strings consists of lowercase English letters only and the length of both strings s and p
         will not be larger than 20,100.

         The order of output does not matter.

         Example 1:

         Input:
         s: "cbaebabacd" p: "abc"

         Output:
         [0, 6]

         Explanation:
         The substring with start index = 0 is "cba", which is an anagram of "abc".
         The substring with start index = 6 is "bac", which is an anagram of "abc".

         Example 2:

         Input:
         s: "abab" p: "ab"

         Output:
         [0, 1, 2]

         Explanation:
         The substring with start index = 0 is "ab", which is an anagram of "ab".
         The substring with start index = 1 is "ba", which is an anagram of "ab".
         The substring with start index = 2 is "ab", which is an anagram of "ab".

         Medium

         Same as LE_567_Permutation_In_String
     */

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int l1 = s.length();
        int l2 = p.length();
        if (l1 < l2) return res;

        int[] m1 = new int[26];
        int[] m2 = new int[26];
        for (char c : p.toCharArray()) {
            m2[c - 'a']++;
        }

        /**
         * Sliding window, each time move one out of the window and add one to the window
         */
        for (int i = 0; i < l1; i++) {
            if (i >= l2) {
                /**
                 * move the first one in window out
                 * "i >= l2" : defines a point when we should
                 *             remove left most element to move
                 *             the sliding window. It should
                 *             start at the 2nd valid window.
                 */
                m1[s.charAt(i - l2) - 'a']--;
            }
            /**
             * add new one to window
             */
            m1[s.charAt(i) - 'a']++;

            if (Arrays.equals(m1, m2)) {//!!! "Arrays.equals"
                /**
                 * 是要加开始的下标！！！
                 */
                res.add(i - l2 + 1);
            }
        }

        return res;
    }
}
