package leetcode;

import java.util.HashMap;

public class LE_358_Rearrange_String_K_Distance_Apart {
    /**
     * Given a non-empty string s and an integer k, rearrange the string such
     * that the same characters are at least distance k from each other.
     *
     * All input strings are given in lowercase letters. If it is not possible
     * to rearrange the string, return an empty string "".
     *
     * Example 1:
     * Input: s = "aabbcc", k = 3
     * Output: "abcabc"
     * Explanation: The same letters are at least distance 3 from each other.
     *
     * Example 2:
     * Input: s = "aaabc", k = 3
     * Output: ""
     * Explanation: It is not possible to rearrange the string.
     *
     * Example 3:
     * Input: s = "aaadbbcc", k = 2
     * Output: "abacabcd"
     *
     * Explanation: The same letters are at least distance 2 from each other.
     *
     * Hard
     */

    /**
     * This is a greedy problem.
     *
     * Every time we want to find the best candidate: which is the character with
     * the largest remaining count. Thus we will be having two arrays. One count
     * array to store the remaining count of every character. Another array to keep
     * track of the most left position that one character can appear. We will iterated
     * through these two array to find the best candidate for every position. Since
     * the array is fixed size, it will take constant time to do this. After we find
     * the candidate, we update two arrays.
     *
     * Time : O(n)
     */
    public class Solution1 {
        public String rearrangeString(String str, int k) {
            int length = str.length();
            int[] count = new int[26];
            int[] valid = new int[26];

            for (int i = 0; i < length; i++) {
                count[str.charAt(i) - 'a']++;
            }

            StringBuilder sb = new StringBuilder();

            for (int index = 0; index < length; index++) {
                int candidatePos = findValidMax(count, valid, index);
                if (candidatePos == -1) {
                    return "";
                }

                count[candidatePos]--;
                valid[candidatePos] = index + k;
                sb.append((char) ('a' + candidatePos));
            }
            return sb.toString();
        }

        /**
         * Since the array is fixed size(26), it will take constant time to find max
         *
         * candidatePos is actually int value for a lower case char, 0 ~ 25, pointing
         * to 'a' ~ 'z'.
         */
        private int findValidMax(int[] count, int[] valid, int index) {
            int max = Integer.MIN_VALUE;
            int candidatePos = -1;

            for (int i = 0; i < count.length; i++) {
                if (count[i] > 0 && count[i] > max && index >= valid[i]) {
                    max = count[i];
                    candidatePos = i;
                }
            }
            return candidatePos;
        }
    }

    /**
     * Same greedy solution, using hashmap instead of arrays
     **/
    class Solution2 {
        public String rearrangeString(String s, int k) {
            if (s == null || s.length() == 0 || k == 0) {
                return s;
            }

            HashMap<Character, Integer> freq = new HashMap<Character, Integer>();
            HashMap<Character, Integer> valid = new HashMap<Character, Integer>();

            for (int i = 0; i < s.length(); i++) {
                char cur = s.charAt(i);

                freq.put(cur, freq.getOrDefault(cur, 0) + 1);
                if (!valid.containsKey(cur)) {
                    valid.put(cur, 0);
                }
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < s.length(); i++) {
                char can = findValid(freq, valid, i);

                if (can == ' ') {
                    return "";
                }

                sb.append(can);
                freq.put(can, freq.get(can) - 1);
                valid.put(can, i + k);
            }

            return sb.toString();
        }

        private char findValid(HashMap<Character, Integer> freq, HashMap<Character, Integer> valid, int index) {
            int max = 0;
            char res = ' ';

            for (char item : freq.keySet()) {
                if (freq.get(item) > max && index >= valid.get(item)) {
                    max = freq.get(item);
                    res = item;
                }
            }

            return res;
        }
    }
}
