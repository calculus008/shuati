package leetcode;

/**
 * Created by yuank on 3/27/18.
 */
public class LE_214_Shortest_Palindrome {
    /**
        Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it.
        Find and return the shortest palindrome you can find by performing this transformation.

        For example:

        Given "aacecaaa", return "aaacecaaa".

        Given "abcd", return "dcbabcd".
     */
    /**
        Time : O(n ^ 2)
        Worst Case :
        i         j
        a a b c a a
                  e
        i     j
        a a b c a a
                  e
            i j
        a a b c a a
                  e
        i       j
        a a b c a a
                e
          i   j
        a a b c a a
                e
        i     j
        a a b c a a
              e
        i   j
        a a b c a a
            e
        i j
        a a b c a a
          e

        a a c b + a a b c a a =  a a c b a a b c a a
    */

    /**
     * The key point is to find the longest palindrome starting from the first character,
     * and then reverse the remaining part as the prefix to s.
     */
    public String shortestPalindrome(String s) {
        if (s == null || s.length() < 2) return s;

        int i = 0;
        int j = s.length() - 1;
        int end = s.length() - 1;
        char[] c = s.toCharArray();

        while (i < j) {
            if (c[i] == c[j]) {
                i++;
                j--;
            } else {
                i = 0;
                end--;
                j = end;
            }
        }

        return new StringBuilder(s.substring(end + 1)).reverse().toString() + s;
    }

}
