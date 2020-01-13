package leetcode;

public class LE_844_Backspace_String_Compare {
    /**
     * Given two strings S and T, return if they are equal when both
     * are typed into empty text editors. # means a backspace character.
     *
     * Example 1:
     *
     * Input: S = "ab#c", T = "ad#c"
     * Output: true
     * Explanation: Both S and T become "ac".
     * Example 2:
     *
     * Input: S = "ab##", T = "c#d#"
     * Output: true
     * Explanation: Both S and T become "".
     * Example 3:
     *
     * Input: S = "a##c", T = "#a#c"
     * Output: true
     * Explanation: Both S and T become "c".
     * Example 4:
     *
     * Input: S = "a#c", T = "b"
     * Output: false
     * Explanation: S becomes "c" while T becomes "b".
     * Note:
     *
     * 1 <= S.length <= 200
     * 1 <= T.length <= 200
     * S and T only contain lowercase letters and '#' characters.
     * Follow up:
     *
     * Can you solve it in O(N) time and O(1) space?
     *
     * Easy
     */

    /**
     * Time and Space : O(n)
     */
    class Solution1 {
        public boolean backspaceCompare(String S, String T) {
            if (S == null || T == null) return false;

            return parse(S).equals(parse(T));
        }

        private String parse(String s) {
            char[] chs = s.toCharArray();

            StringBuilder sb = new StringBuilder();
            int count = 0;
            for (int i = chs.length - 1; i >= 0; i--) {
                if (chs[i] == '#') {
                    count++;
                } else {
                    if (count == 0) {
                        sb.append(chs[i]);
                    } else {
                        count--;
                    }
                }
            }

            return sb.toString();
        }
    }

    /**
     * Solution for follow up - "solve it in O(N) time and O(1) space"
     * Time  : O(n)
     * Space : O(1)
     *
     * First, since String is immutable and we can't use extra space, therefore
     * the backforward filling solution won't work (s.charAt(i) = s.charAt(j) is not allowed).
     *
     * The solution here is not trying to get the end form for S and T, like in Solution2,
     * instead:
     * 1.it uses two pointers, going backward from the end.
     * 2.For S and T, keep going if char is '#' or count of '#' is not zero, basically it
     *   skips all chars affected by backspace.
     * 3.Then whenever we have legal char, we compare if they are equal.
     * 4."while(true" - this dictates the loop control
     */
    class Solution2 {
        public boolean backspaceCompare(String S, String T) {
            int n1 = S.length();
            int n2 = T.length();

            int i = n1 - 1, j = n2 - 1;

            while (true) {
                int cnt1 = 0, cnt2 = 0;

                while (i >= 0 && (S.charAt(i) == '#' || cnt1 > 0)) {
                    cnt1 += S.charAt(i) == '#'? 1 : -1;
                    i--;
                }

                while (j >= 0 && (T.charAt(j) == '#' || cnt2 > 0)) {
                    cnt2 += T.charAt(j) == '#'? 1 : -1;
                    j--;
                }

                /**
                 * !!!
                 * Must have "i >= 0 && j >= 0"
                 */
                if (i >= 0 && j >= 0 && S.charAt(i) == T.charAt(j)) {
                    i--;
                    j--;
                } else {
                    return i == -1 && j == -1;
                }
            }
        }
    }
}
