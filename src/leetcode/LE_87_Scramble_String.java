package leetcode;

/**
 * Created by yuank on 3/10/18.
 */
public class LE_87_Scramble_String {
    /**
        Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.

        Below is one possible representation of s1 = "great":

            great
           /    \
          gr    eat
         / \    /  \
        g   r  e   at
                   / \
                  a   t
        To scramble the string, we may choose any non-leaf node and swap its two children.

        For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat".

            rgeat
           /    \
          rg    eat
         / \    /  \
        r   g  e   at
                   / \
                  a   t
        We say that "rgeat" is a scrambled string of "great".

        Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".

            rgtae
           /    \
          rg    tae
         / \    /  \
        r   g  ta  e
               / \
              t   a
        We say that "rgtae" is a scrambled string of "great".

        Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.
     **/

    /**
     * Time : O(n!), Space : O(n)
     *
     * Note : can't sort 2 strings and check equal (like checking anagrams.
     *        For example :
     *        "abcd" and "bdac"
     *        "abta" and "tbaa"
     *
     *        They are anagrams, but they are not scramble String
     **/
    public static boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null) return false;

        /**
         * !!! basse case
         */
        if (s1.equals(s2)) return true;

        int n = s1.length();
        if (s2.length() != n) return false;

        int[] letters = new int[26];
        for (int i = 0; i < n; i++) {
            letters[s1.charAt(i) - 'a']++;
            letters[s2.charAt(i) - 'a']--;
        }

        for (int i = 0; i < 26; i++) {
            if (letters[i] != 0) return false;
        }

        /**
         * !!! i starts at 1
         *
         * The 1st IF is to check the LEFT child of S1 is scramble of LEFT child of S2
         * AND RIGHT child of S1 is also a scramble of RIGHT child of s2.
         *
         * When this fails, it means the left and right substrings are swapped.
         *
         * The 2nd IF statement check for the swap case with LEFT child of S1 and
         * RIGHT child of S2 AND RIGHT child of S1 and LEFT child of S2.
         */

        for (int i = 1; i < n; i++) {
            if (isScramble(s1.substring(0, i), s2.substring(0, i)) &&
                    isScramble(s1.substring(i), s2.substring(i))) return true;
            /**
              great
              tearg

              i=2
              gr eat
              tea rg (position : n - i = 5 - 2 = 3)

              !!! "s2.substring(n - i)", "s2.substring(0, n - i)"
            */
            if (isScramble(s1.substring(0, i), s2.substring(n - i)) &&
                    isScramble(s1.substring(i), s2.substring(0, n - i))) return true;
        }

        return false;
    }
}
