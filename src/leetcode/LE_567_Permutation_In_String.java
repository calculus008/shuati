package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 8/15/18.
 */
public class LE_567_Permutation_In_String {
    /**
         Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1.
         In other words, one of the first string's permutations is the substring of the second string.

         Example 1:
         Input:s1 = "ab" s2 = "eidbaooo"
         Output:True
         Explanation: s2 contains one permutation of s1 ("ba").

         Example 2:
         Input:s1= "ab" s2 = "eidboaoo"
         Output: False

         Note:
         The input strings only contain lower case letters.
         The length of both given strings is in range [1, 10,000].

         Same as LE_438_Find_All_Anagrams_In_A_String

         Medium
     */

    /**
     * Time  : O(l1 + (l2 - l1) * 26) = O(l1 + l2)
     * Space : O(26 * 2) = O(1)
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() == 0 || s2.length() == 0) return true;

        int l1 = s1.length();
        int l2 = s2.length();

        if (l1 > l2) return false;

        int[] m1 = new int[26];
        int[] m2 = new int[26];

        for (char c : s1.toCharArray()) {
            m1[c - 'a']++;
        }

        //!!! Sliding window
        for (int i = 0; i < l2; ++i) {
            if (i >= l1) {
                m2[s2.charAt(i - l1) - 'a']--;
            }

            m2[s2.charAt(i) - 'a']++;

            if(Arrays.equals(m1, m2)) return true;
        }

        return false;
    }
}
