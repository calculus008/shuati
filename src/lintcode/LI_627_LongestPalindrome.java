package lintcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 6/11/18.
 */
public class LI_627_LongestPalindrome {
    /**
     Given a string which consists of lowercase or uppercase letters,
     find the length of the longest palindromes that can be built with those letters.

     This is case sensitive, for example "Aa" is not considered a palindrome here.

     Example
     Given s = "abccccdd" return 7

     One longest palindrome that can be built is "dccaccd", whose length is 7.
     */

    /**
     * @param s: a string which consists of lowercase or uppercase letters
     * @return: the length of the longest palindromes that can be built
     *
     * Two kinds of chars
     * 1.appeared even number of times, all of them can be used to build palindrome
     * 2.appeared odd number of times, only one of them can be all used to build palindrome
     *   by placing one of them in the middle. For all other odd number chars, we must
     *   remove one for each of them.
     *
     * Therefore, we only need to know how many chars appeared for odd number of times, say n, then
     * the length of the max palindrome is :
     *
     * s.length - (n - 1)
     *
     * For example :
     * "abccccdd", n = 2 (a, b appeared one time)
     * res = 8 - (2 - 1) = 7
     */
    public int longestPalindrome(String s) {
        if (s == null || s.length() == 0) return 0;

        /**
         * Set!!!
         */
        Set<Character> set = new HashSet<>();

        for (char c : s.toCharArray()) {
            /**
             * this if - else finds there are how many
             * unique chars appeared for odd number of times.
             */
            if(set.contains(c)) {
                set.remove(c);
            } else {
                set.add(c);
            }
        }

        int num = set.size();

        int res = s.length();

        /**
         * !!!
         * if we have odd number of chars, we can at least use one of them
         * in the middle of palindromes, therefore, minus 1 here.
         * (num is the number to be removed from the string)
         */
        if (num > 0) {
            num -= 1;
        }

        res -= num;
        return res;
    }
}
