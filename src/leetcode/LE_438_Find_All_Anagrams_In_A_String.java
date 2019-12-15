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

    /**
     * Best solution, just use 1 count array
     *
     * s: "cbabcbacd" p: "abc"
     *
     * i = 3
     * count[0], [1], [2], [3], [4]
     *            1    -1
     * sum = 2
     *
     * i = 4
     * count[0], [1], [2], [3], [4]
     *           0     0
     * sum = 0
     */
    public List<Integer> findAnagrams1(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (null == s || null == p || s.length() == 0 || p.length() == 0
                || p.length() > s.length()) {
            return res;
        }

        char[] chars1 = s.toCharArray();
        char[] chars2 = p.toCharArray();

        int[] count = new int[256];

        for (int i = 0; i < p.length(); i++) {
            count[chars2[i]]--;
            count[chars1[i]]++;
        }

        int sum = 0;

        for (int n : count) {
            sum += Math.abs(n);
        }

        if (sum == 0) {
            res.add(0);
        }

        for (int i = p.length(); i < s.length(); i++) {
            int r = chars1[i];
            int l = chars1[i - p.length()];

            sum -= (Math.abs(count[r]) + Math.abs(count[l]));

            count[r]++;
            count[l]--;

            sum +=  (Math.abs(count[r]) + Math.abs(count[l]));

            if (sum == 0) {
                res.add(i - p.length() + 1);
            }
        }

        return res;
    }


    public List<Integer> findAnagrams_practice(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s == null || p == null || s.length() == 0 || p.length() == 0 || s.length() < p.length()) {
            return res;
        }

        int[] count = new int[256];
        int sum = 0;

        char[] chars1 = s.toCharArray();
        char[] chars2 = p.toCharArray();
        int l1 = chars1.length;
        int l2 = chars2.length;

        for (int i = 0; i < l2; i++) {
            count[chars1[i]]++;
            count[chars2[i]]--;
        }

        for (int n : count) {
            sum += Math.abs(n);
        }

        if (sum == 0) res.add(0);

        for (int i = l2; i < l1; i++) {
            int r = chars1[i];
            int l = chars1[i - l2];

            sum -= (Math.abs(count[r]) + Math.abs(count[l]));

            count[r]++;
            count[l]--;

            sum += (Math.abs(count[r]) + Math.abs(count[l]));

            if (sum == 0) {
                res.add(i - l2 + 1);
            }
        }

        return res;
    }


    public List<Integer> findAnagrams2(String s, String p) {
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

    class Solution_Practice {
        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> res = new ArrayList<>();
            if (null == s || null == p || s.length() < p.length()) return res;

            int[] count = new int[256];
            int sl = s.length();
            int pl = p.length();
            int sum = 0;
            for (int i = 0; i < pl; i++) {
                count[p.charAt(i)]--;
                count[s.charAt(i)]++;
            }

            for (int num : count) {
                sum += Math.abs(num);
            }

            if (sum == 0) {
                res.add(0);
            }

            for (int i = 1; i <= sl - pl; i++) {
                int l = i - 1;
                int r = i + pl - 1;

                sum -= (Math.abs(count[s.charAt(l)]) + Math.abs(count[s.charAt(r)]));

                count[s.charAt(l)]--;
                count[s.charAt(r)]++;

                sum += (Math.abs(count[s.charAt(l)]) + Math.abs(count[s.charAt(r)]));

                if (sum == 0) {
                    res.add(i);
                }
            }

            return res;
        }
    }
}
