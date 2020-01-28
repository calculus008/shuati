package leetcode;

public class LE_925_Long_Pressed_Name {
    /**
     * Your friend is typing his name into a keyboard.  Sometimes, when typing a character c,
     * the key might get long pressed, and the character will be typed 1 or more times.
     *
     * You examine the typed characters of the keyboard.  Return True if it is possible that
     * it was your friends name, with some characters (possibly none) being long pressed.
     *
     * Example 1:
     *
     * Input: name = "alex", typed = "aaleex"
     * Output: true
     * Explanation: 'a' and 'e' in 'alex' were long pressed.
     * Example 2:
     *
     * Input: name = "saeed", typed = "ssaaedd"
     * Output: false
     * Explanation: 'e' must have been pressed twice, but it wasn't in the typed output.
     * Example 3:
     *
     * Input: name = "leelee", typed = "lleeelee"
     * Output: true
     * Example 4:
     *
     * Input: name = "laiden", typed = "laiden"
     * Output: true
     * Explanation: It's not necessary to long press any character.
     *
     *
     * Note:
     *
     * name.length <= 1000
     * typed.length <= 1000
     * The characters of name and typed are lowercase letters.
     *
     * Easy
     */

    /**
     * Use same algorithm as LE_809_Expressive_Words
     */
    class Solution1 {
        public boolean isLongPressedName(String name, String typed) {
            if (name == null || typed == null) return false;

            int i = 0;
            int j = 0;

            while (i < name.length() && j < typed.length()) {
                if (name.charAt(i) == typed.charAt(j)) {
                    int len1 = getLen(name, i);
                    int len2 = getLen(typed, j);

                    if (len1 > len2) return false;
                    i += len1;
                    j += len2;
                } else {
                    return false;
                }
            }

            /**
             * !!!
             * for case : "pyplrz"
             *            "ppyypllr"
             *
             * If we don't check i and j values here, it will return TRUE, whil it should be FALSE
             */
            return i == name.length() && j == typed.length();
        }

        private int getLen(String s, int x) {
            char c = s.charAt(x);
            int i = x;
            while (i < s.length() && c == s.charAt(i)) {
                i++;
            }
            return i - x;
        }
    }

    /**
     * A faster solution
     */
    class Solution {
        public boolean isLongPressedName(String name, String typed) {
            if (name == null || typed == null) return false;

            if (name.length() > typed.length()) return false;

            int i = 0, j = 0;
            while (i < name.length() && j < typed.length()) {
                if (name.charAt(i) == typed.charAt(j)) {
                    i++;
                    j++;
                } else {
                    if (i == 0 || typed.charAt(j) != name.charAt(i - 1)) {
                        return false;
                    }
                    j++;
                }
            }

            /**
             * It seems that the test cases does not have the one like :
             * "vtkgn"
             * "vttkgnnz"
             *
             * Typed has different char at the end.
             */
            //return i == name.length() ;

            /**
             * This logic will cover the case
             */
            while (j < typed.length()) {
                if (typed.charAt(j) != name.charAt(name.length() - 1)) return false;
                j++;
            }

            return i == name.length() && j == typed.length();
        }
    }
}
