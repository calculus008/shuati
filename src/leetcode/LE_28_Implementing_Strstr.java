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

         Easy
     */

    public int strStr(String source, String target) {
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
}
