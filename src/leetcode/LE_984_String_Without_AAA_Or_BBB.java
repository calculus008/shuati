package leetcode;

public class LE_984_String_Without_AAA_Or_BBB {
    /**
     * Given two integers A and B, return any string S such that:
     *
     * S has length A + B and contains exactly A 'a' letters, and exactly B 'b' letters;
     * The substring 'aaa' does not occur in S;
     * The substring 'bbb' does not occur in S.
     *
     *
     * Example 1:
     *
     * Input: A = 1, B = 2
     * Output: "abb"
     * Explanation: "abb", "bab" and "bba" are all correct answers.
     * Example 2:
     *
     * Input: A = 4, B = 1
     * Output: "aabaa"
     *
     *
     * Note:
     *
     * 0 <= A <= 100
     * 0 <= B <= 100
     * It is guaranteed such an S exists for the given A and B.
     *
     * Easy
     */

    /**
     * https://zxi.mytechroad.com/blog/greedy/leetcode-984-string-without-aaa-or-bbb/
     *
     * Greedy
     *
     * Time  : O(n)
     * Space : O(1)
     */
    class Solution {
        String strWithout3a3b(int A, int B) {
            //!!! can't do this for B > A, because the call only swaps B and A, not "a" and "b"
            // if (B > A) {
            //     return strWithout3a3b(B, A);
            // }

            char a = 'a';
            char b = 'b';

            if (B > A) {
                int temp = B;
                B = A;
                A = temp;

                char c = a;
                a = b;
                b = c;
            }

            StringBuilder sb = new StringBuilder();
            while (A > 0 || B > 0) {
                if (A > 0) {
                    sb.append(a);
                    A--;
                }
                if (A > B) {
                    sb.append(a);
                    A--;
                }
                if (B > 0) {
                    sb.append(b);
                    B--;
                }
            }

            return sb.toString();
        }
    }
}
