package leetcode;

import java.util.Stack;

public class LE_1047_Remove_All_Adjacent_Duplicates_In_String {
    /**
     * Given a string S of lowercase letters, a duplicate removal consists of choosing two adjacent and equal letters, and removing them.
     *
     * We repeatedly make duplicate removals on S until we no longer can.
     *
     * Return the final string after all such duplicate removals have been made.  It is guaranteed the answer is unique.
     *
     * Example 1:
     * Input: "abbaca"
     * Output: "ca"
     * Explanation:
     * For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible move.
     * The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is "ca".
     *
     *
     * Note:
     * 1 <= S.length <= 20000
     * S consists only of English lowercase letters.
     *
     * Easy
     */

    /**
     * stack solution
     * Time and Space : O(n)
     */
    class Solution1 {
        public String removeDuplicates(String S) {
            Stack<Character> stack = new Stack<>();
            for (char c : S.toCharArray()) {
                if (!stack.isEmpty() && stack.peek() == c) {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }

            StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty()) {
                sb.append(stack.pop());
            }

            return sb.reverse().toString();
        }
    }

    /**
     * developed from Solution1, use StringBuilder itself as a "stack"
     * Time and Space : O(n)
     */
    class Solution2 {
        public String removeDuplicates(String S) {
            StringBuilder sb = new StringBuilder();

            for (char c : S.toCharArray()) {
                int len = sb.length();
                if (len > 0 && sb.charAt(len - 1) == c) {
                    sb.setLength(len - 1);
                } else {
                    sb.append(c);
                }

            }

            return sb.toString();
        }
    }

    /**
     * two pointers to simulate stack operation.
     *
     * i is the running pointer, j can be seen as the pointer to the stack. chars[j - 1] is like "peep()"
     *
     * a b b a c a
     *   i
     *   j
     *
     * a b b a c a
     *     i
     *     j
     *
     * a b b a c a
     *       i
     *   j
     *
     * a b b a c a
     *         i
     * j
     *
     * c b b a c a
     *           i
     *   j
     *
     * c a b a c a
     *             i
     *     j
     */
    class Solution3 {
        public String removeDuplicates(String S) {
            char[] chars = S.toCharArray();

            int j = 0;
            for (int i = 0; i < chars.length; i++) {
                if (j == 0 || chars[i] != chars[j - 1]) {//peep()
                    chars[j] = chars[i];//stack push
                    j++;
                } else {
                    j--;//stack pop
                }
            }

            return new String(chars, 0, j);
        }
    }

    class Solution4 {
        public String removeDuplicates(String S) {
            char[] chars = S.toCharArray();
            int j = 0;

            for (int i = 0; i < chars.length; i++, j++) {
                chars[j] = chars[i];
                if (j > 0 && chars[j] ==  chars[j - 1]) {
                    j -= 2;
                }
            }

            return new String(chars, 0, j);
        }
    }
}
