package lintcode;

import java.util.ArrayList;
import java.util.List;

public class LI_647_Find_All_Anagrams_In_A_String {
    /**
     * Given a string s and a non-empty string p,
     * find all the start indices of p's anagrams in s.
     *
     * Strings consists of lowercase English letters only and the
     * length of both strings s and p will not be larger than 40,000.
     *
     * The order of output does not matter.
     *
     * Have you met this question in a real interview?
     * Example
     * Example 1:
     *
     * Input : s =  "cbaebabacd", p = "abc"
     * Output : [0, 6]
     * Explanation :
     * The substring with start index = 0 is "cba", which is an anagram of "abc".
     * The substring with start index = 6 is "bac", which is an anagram of "abc".
     *
     * Same as LE_567_Permutation_In_String
     */

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        if (s.length() < p.length()) {
            return ans;
        }
        char[] sc = s.toCharArray();
        char[] pc = p.toCharArray();

        int[] det = new int[256];

        for (int i = 0; i < p.length(); i++) {
            det[pc[i]]--;
            det[sc[i]]++;
        }

        int absSum = 0;
        for (int item : det) {
            absSum += Math.abs(item);
        }
        if (absSum == 0) {
            ans.add(0);
        }

        for (int i = p.length(); i < s.length(); i++) {
            int r = sc[i];
            int l = sc[i - p.length()];

            /**
             * l           r
             * a  b  b  b  c
             *
             * ------------>
             *
             * det[sc[r]]++
             * det[sc[l]]--
             *
             * 每一次移动sliding window, only det[r] and det[l] change.
             *
             * Therefore, 3 steps to update absSum.
             * 1.Remove absolute value of det[r] and det[l]
             * 2.Update det[r] and det[l]
             * 3.Add absolute values of det[r] and det[l] (it is the updated value)
             *
             * Note :
             * here r and l are not index, they are the actual chars on the right and left side
             * of the window.
             */


            absSum = absSum - Math.abs(det[r]) - Math.abs(det[l]);

            det[r]++;
            det[l]--;

            absSum = absSum + Math.abs(det[r]) + Math.abs(det[l]);

            if (absSum == 0) {
                ans.add(i - p.length() + 1);
            }
        }
        return ans;
    }
}

