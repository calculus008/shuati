package leetcode;

public class LE_686_Repeated_String_Match {
    /**
     * Given two strings A and B, find the minimum number of times A has to be
     * repeated such that B is a substring of it. If no such solution, return -1.
     *
     * For example, with A = "abcd" and B = "cdabcdab".
     *
     * Return 3, because by repeating A three times (“abcdabcdabcd”), B is a
     * substring of it; and B is not a substring of A repeated two times ("abcdabcd").
     *
     * Note:
     * The length of A and B will be between 1 and 10000.
     *
     * Easy
     */
    /**
     * https://leetcode.com/problems/repeated-string-match/solution/
     */
    class Solution {
        public int repeatedStringMatch(String A, String B) {
            StringBuilder sb = new StringBuilder(A);

            int n = 1;
            while (sb.length() < B.length()) {
                n++;
                sb.append(A);
            }

            if (sb.toString().contains(B)) return n;
            if (sb.append(A).toString().contains(B)) return n + 1;

            return -1;
        }
    }
}
