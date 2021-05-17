package leetcode;

public class LE_1209_Remove_All_Adjacent_Duplicates_In_String_II {
    /**
     * You are given a string s and an integer k, a k duplicate removal consists of choosing k adjacent and equal
     * letters from s and removing them, causing the left and the right side of the deleted substring to concatenate
     * together.
     *
     * We repeatedly make k duplicate removals on s until we no longer can.
     *
     * Return the final string after all such duplicate removals have been made. It is guaranteed that the answer is unique.
     *
     * Example 1:
     * Input: s = "abcd", k = 2
     * Output: "abcd"
     * Explanation: There's nothing to delete.
     *
     *  Example 2:
     * Input: s = "deeedbbcccbdaa", k = 3
     * Output: "aa"
     * Explanation:
     * First delete "eee" and "ccc", get "ddbbbdaa"
     * Then delete "bbb", get "dddaa"
     * Finally delete "ddd", get "aa"
     *
     * Example 3:
     * Input: s = "pbbcggttciiippooaais", k = 2
     * Output: "ps"
     *
     * Constraints:
     * 1 <= s.length <= 105
     * 2 <= k <= 104
     * s only contains lower case English letters.
     *
     * Hard
     */

    /**
     * Developed from LE_1047_Remove_All_Adjacent_Duplicates_In_String Solution2
     *
     * Stack solution (using StringBuilder as Stack)
     * Use a counter array to count haw many equal chars so far.
     *
     * Use sb.delete()
     */
    class Solution1 {
        public String removeDuplicates(String s, int k) {
            StringBuilder sb = new StringBuilder();
            int[] count = new int[s.length()];

            for (char c : s.toCharArray()) {
                sb.append(c);

                int len = sb.length();
                int last = len - 1;

                if (last > 0 && sb.charAt(last) == sb.charAt(last - 1)) {
                    count[last] = 1 + count[last - 1];
                } else {
                    count[last] = 1;
                }

                if (count[last] >= k) {
                    sb.delete(len - k, len);
                    //or use setLength
                    //sb.setLength(len - k);
                }
            }

            return sb.toString();
        }
    }

    /**
     * Two pointers solution,developed from LE_1047_Remove_All_Adjacent_Duplicates_In_String Solution4
     */
    class Solution2 {
        public String removeDuplicates(String s, int k) {
            int[] count = new int[s.length()];
            char[] chars = s.toCharArray();

            int j = 0;
            for (int i = 0; i < chars.length; i++, j++) {
                chars[j] = chars[i];

                if (j > 0 && chars[j] == chars[j - 1]) {
                    count[j] = 1 + count[j - 1];
                } else {
                    count[j] = 1;
                }

                if (count[j] == k) {
                    j -= k;
                }
            }

            return new String(chars, 0, j);
        }
    }
}
