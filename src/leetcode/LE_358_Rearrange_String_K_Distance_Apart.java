package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class LE_358_Rearrange_String_K_Distance_Apart {
    /**
     * Given a non-empty string s and an integer k, rearrange the string such
     * that the same characters are at least dirs k from each other.
     *
     * All input strings are given in lowercase letters. If it is not possible
     * to rearrange the string, return an empty string "".
     *
     * Example 1:
     * Input: s = "aabbcc", k = 3
     * Output: "abcabc"
     * Explanation: The same letters are at least dirs 3 from each other.
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
     * Explanation: The same letters are at least dirs 2 from each other.
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

    class Solution1_Practice {
        public String rearrangeString(String s, int k) {
            if (k == 0 || null == s || s.length() < k) return s;

            int n = s.length();
            int[] count = new int[26];
            int[] pos = new int[26];

            char[] chs = s.toCharArray();
            for (char c : chs) {
                count[c - 'a']++;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                int next = getNext(count, pos, i);
                if (next == -1) return "";

                sb.append((char)(next + 'a'));
                /**
                 * !!!
                 */
                count[next]--;
                pos[next] = i + k;
            }
            return sb.toString();
        }

        private int getNext(int[] count, int[] pos, int idx) {
            int next = -1;
            int max = 0;

            for (int i = 0; i < 26; i++) {
                /**
                 * pos[i] : next position for char ('a' + i) must be
                 *          greater than pos[i]
                 *
                 * So given an idx, it is valid only when idx >= pos[i].
                 */
                if (count[i] > max && idx >= pos[i]) {
                    next = i;
                    max = count[i];
                }
            }
            return next;
        }
    }

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
                /**
                 * count[i] > 0 : we still have this char
                 * count[i] > max : find the max remaining chars
                 * index >= valid[i] :
                 */
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

    /**
     * PriorityQueue Solution, easier to understand.
     * "Every time we want to find the best candidate: which is the character with
     * the largest remaining count." --> use PriorityQueue
     *
     * Time : O(n), since size of the heap is bounded by 26, so it is constant.
     */
    class Solution3 {
        public String rearrangeString(String s, int k) {
            if (k == 0 || null == s || s.length() < k) return s;

            int[] count = new int[26];

            char[] chs = s.toCharArray();
            for (char c : chs) {
                count[c - 'a']++;
            }

            /**
             * Entity saved in pq:
             * First element  : int, offset of char from 'a'
             * Second element : frequency of the char.
             *
             * So in pq, if frequency is different, sort by frequency, bigger value first (it is a max heap),
             * if frequency is the same, sort by alphabetic order.
             */
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] != b[1] ? b[1] - a[1] : a[0] - b[0]);

            /**
             * max heap, size is bounded by 26 (lower case letters)
             */
            for (int i = 0; i < 26; i++) {
                if (count[i] > 0) {
                    pq.offer(new int[]{i, count[i]});
                }
            }

            StringBuilder sb = new StringBuilder();

            while (!pq.isEmpty()) {
                /**
                 * remember what k chars are used in the next for loop,
                 * then add them back to heap if necessary
                 */
                List<Integer> used = new ArrayList<>();

                /**
                 * iterate k time, every time get a char from heap,
                 * this makes sure we have unique char for each
                 * iteration to fill k positions.
                 */
                for (int i = 0; i < k; i++) {
                    if (pq.isEmpty()) {
                        /**
                         * run out of char for current k loop,
                         * so we can't have result as expected
                         */
                        if (sb.length() < s.length()) {
                            return "";
                        } else {
                            break;
                        }
                    }

                    int[] cur = pq.poll();
                    sb.append((char)('a' + cur[0]));
                    used.add(cur[0]);
                }

                for (int j : used) {
                    /**
                     * char is used, decrease its frequency, if it is not 0,
                     * add back to heap
                     */
                    count[j]--;
                    if (count[j] > 0) {
                        pq.offer(new int[]{j, count[j]});
                    }
                }
            }

            return sb.toString();
        }
    }

    class Solution3_Practice {
        public String rearrangeString(String s, int k) {
            if (k == 0 || null == s || s.length() < k) return s;

            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }

            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]);

            for (int i = 0; i < 26; i++) {
                /**
                 * !!!
                 * check count[i] > 0
                 */
                if (count[i] > 0) {
                    pq.offer(new int[]{count[i], i});
                }
            }

            StringBuilder sb = new StringBuilder();
            while (!pq.isEmpty()) {
                List<Integer> used = new ArrayList<>();

                for (int i = 0; i < k; i++) {
                    /**
                     * !!!
                     * Must first check pq empty, then processing.
                     * For example : "aabbcc, 3", if we put this section
                     * after processing, it will return "" when sb is "abc".
                     *
                     * If we do if first, pq will be refilled in the for loop
                     * next.
                     */
                    if (pq.isEmpty()) {
                        if (sb.length() < s.length()) {
                            return "";
                        } else {
                            break;
                        }
                    }

                    int[] cur = pq.poll();
                    sb.append((char)('a' + cur[1]));
                    used.add(cur[1]);
                }

                for (int idx : used) {
                    count[idx]--;
                    if (count[idx] > 0) {
                        pq.offer(new int[]{count[idx], idx});
                    }
                }
            }

            return sb.toString();
        }
    }
}
