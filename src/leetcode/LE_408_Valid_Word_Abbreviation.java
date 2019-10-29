package leetcode;

public class LE_408_Valid_Word_Abbreviation {
    /**
     * Given a non-empty string s and an abbreviation abbr,
     * return whether the string matches with the given abbreviation.
     *
     * A string such as "word" contains only the following valid abbreviations:
     *
     * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2",
     * "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
     * Notice that only the above abbreviations are valid abbreviations of the
     * string "word". Any other string is not a valid abbreviation of "word".
     *
     * Note:
     * Assume s contains only lowercase letters and abbr contains only lowercase letters and digits.
     *
     * Example 1:
     * Given s = "internationalization", abbr = "i12iz4n":
     *
     * Return true.
     *
     * Example 2:
     * Given s = "apple", abbr = "a2e":
     *
     * Return false.
     *
     * Easy
     */

    class Solution {
        public boolean validWordAbbreviation(String word, String abbr) {
            if (null == word || null == abbr) return false;

            int i = 0, j = 0;
            char[] s = word.toCharArray();
            char[] t = abbr.toCharArray();

            if (t.length > s.length) return false;

            while (i < s.length && j < t.length) {
                /**
                 * !!!
                 * If detect digit in abbreviation, extract the number,
                 * then "i += num" will move word pointer to the position
                 * that is right after the digit in abbreviation so that
                 * we can check if they are the same.
                 */
                if (Character.isDigit(t[j])) {
                    /**
                     * !!!
                     * '0' is invalid in abbreviation
                     */
                    if (t[j] == '0') {
                        return false;
                    }

                    int num = 0;
                    while (j < t.length && Character.isDigit(t[j])) {
                        num = num * 10 + t[j] - '0';
                        j++;
                    }

                    /**
                     * !!!
                     */
                    i += num;
                } else {
                    if (s[i] != t[j]) {
                        return false;
                    }
                    i++;
                    j++;
                }
            }

            return i == s.length && j == t.length;
        }
    }
}
