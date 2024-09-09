package leetcode;

/**
 * Created by yuank on 6/11/18.
 */
public class LE_28_Find_The_Index_Of_The_First_Occurrence_In_A_String {
    /**
     Given two strings needle and haystack, return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

     Example 1:
     Input: haystack = "sadbutsad", needle = "sad"
     Output: 0
     Explanation: "sad" occurs at index 0 and 6.
     The first occurrence is at index 0, so we return 0.

     Example 2:
     Input: haystack = "leetcode", needle = "leeto"
     Output: -1
     Explanation: "leeto" did not occur in "leetcode", so we return -1.


     Constraints:

     1 <= haystack.length, needle.length <= 104
     haystack and needle consist of only lowercase English characters.

     Easy

     https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string
     */

    /**
     * Brutal Force
     * Time : O(mn)
     * Space : O(1)
     *
     * O(n) solution with RK and KMP algorithm, details in Editorial
     */
    public int strStr1(String haystack, String needle) {
        // empty needle appears everywhere, first appears at 0 index
        if (needle.length() == 0)
            return 0;
        if (haystack.length() == 0)
            return -1;


        for (int i = 0; i < haystack.length(); i++) {
            // not enough places for needle after i
            if (i + needle.length() > haystack.length()) break;

            for (int j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i+j) != needle.charAt(j)) break;

                if (j == needle.length() - 1) {
                    return i;
                }
            }
        }

        return -1;
    }

    public int strStr2(String source, String target) {
        if (target == null || source == null) return -1;
        if (target.length() == 0) return 0;

        //!!! "<="
        for (int i = 0; i <= source.length() - target.length(); i++) {
            if ((source.substring(i, i + target.length())).equals(target)) {
                return i;
            }
        }

        return -1;
    }

    public int strStr3(String haystack, String needle) {
        if(haystack.contains(needle)){
            return haystack.indexOf(needle);
        }
        return -1;
    }
}
