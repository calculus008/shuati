package lintcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 6/11/18.
 */
public class LI_627_LongestPalindrome {
    /**
     Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.

     This is case sensitive, for example "Aa" is not considered a palindrome here.

     Example
     Given s = "abccccdd" return 7

     One longest palindrome that can be built is "dccaccd", whose length is 7.
     */

    /**
     * @param s: a string which consists of lowercase or uppercase letters
     * @return: the length of the longest palindromes that can be built
     */
    public int longestPalindrome(String s) {
        if (s == null || s.length() == 0) return 0;

        Set<Character> set = new HashSet<>();

        for (char c : s.toCharArray()) {
            //!!! if else
            if(set.contains(c)) {
                set.remove(c);
            } else {
                set.add(c);
            }
        }

        int num = set.size();

        int res = s.length();
        if (num > 0) {
            num -= 1; //!!!if we have odd number of chars, we can at least use one of them in the middle of palindromes, therefore, minus 1 here. (num is the number to be removed from the string)
        }

        res -= num;
        return res;
    }
}
