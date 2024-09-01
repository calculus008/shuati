package leetcode;

/**
 * Created by yuank on 6/11/18.
 */
public class LE_28_Implementing_Strstr {
    /**
         Implement strStr().

         Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

         Example 1:

         Input: haystack = "hello", needle = "ll"
         Output: 2
         Example 2:

         Input: haystack = "aaaaa", needle = "bba"
         Output: -1
         Clarification:

         What should we return when needle is an empty string? This is a great question to ask during an interview.

         For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().

         https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/description/?envType=company&envId=apple&favoriteSlug=apple-six-months

         Easy
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

                if (j == needle.length()-1) {
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
