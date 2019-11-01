package leetcode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LE_753_Cracking_The_Safe {
    /**
         There is a box protected by a password. The password is n digits,
         where each letter can be one of the first k digits 0, 1, ..., k-1.

         You can keep inputting the password, the password will automatically be matched against the last n digits entered.

         For example, assuming the password is "345", I can open it when I type "012345", but I enter a total of 6 digits.

         Please return any string of minimum length that is guaranteed to open the box after the entire string is inputted.

         Example 1:
         Input: n = 1, k = 2
         Output: "01"
         Note: "10" will be accepted too.

         Example 2:
         Input: n = 2, k = 2
         Output: "00110"
         Note: "01100", "10011", "11001" will be accepted too.

         Note:
         n will be in the range [1, 4].
         k will be in the range [1, 10].
         k^n will be at most 4096.

         Hard
     */

    /**
     * http://zxi.mytechroad.com/blog/graph/leetcode-753-cracking-the-safe/
     *
     * Total number of possible passwords : k ^ n
     * Total length : n * k ^ n
     *
     * If each password can share the last n - 1 chars of the previous passwords,
     * Total length : k ^ n + (n - 1), for example:
     *  aa, ab, ba, bb -> aa, b, b, a
     *
     * Every possible password is presented in the sequence once and only once -> De Bruijin Sequence.
     * We don't need to know this to solve the problem. The solution problem is a generic DFS with backtracking.
     *
     * Time  : O(k ^ (k ^ n)) ~ O(k ^ n)
     * Space : O(k ^ n)
     */
    class Solution {
        String ans = "";

        public String crackSafe(int n, int k) {
            int len = (int)Math.pow(k, n) + n - 1;
            Set<String> visited = new HashSet<>();

            /**
             * !!!
             */
            String pwd = String.join("", Collections.nCopies(n, "0"));
            StringBuilder sb = new StringBuilder(pwd);
            visited.add(pwd);

            if (dfs(n, k, len, sb, visited)) {
                return sb.toString();
            }

            return "";
        }

        private boolean dfs(int n, int k, int len, StringBuilder sb, Set<String> visited) {
            if (sb.length() == len) {
                return true;
            }

            String temp = sb.substring(sb.length() - (n - 1));//substring of the last n-1 chars
            for (char c = '0'; c < '0' + k; c++) {
                String cur = temp + c;
                if (!visited.contains(cur)) {
                    sb.append(c);
                    visited.add(cur);

                    if (dfs(n, k, len, sb, visited)) return true;

                    visited.remove(cur);
                    /**
                     * !!!
                     * "sb.deleteCharAt()"
                     */
                    sb.deleteCharAt(sb.length() - 1);
                }
            }

            return false;
        }
    }
}