package src.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class LE_767_Reorganize_String {
    /**
     * Given a string S, check if the letters can be rearranged so that two characters
     * that are adjacent to each other are not the same.
     *
     * If possible, output any possible result.  If not possible, return the empty string.
     *
     * Example 1:
     *
     * Input: S = "aab"
     * Output: "aba"
     * Example 2:
     *
     * Input: S = "aaab"
     * Output: ""
     * Note:
     *
     * S will consist of lowercase letters and have length in range [1, 500].
     *
     * Medium
     */

    /**
     * Same algorithm as LE_358_Rearrange_String_K_Distance_Apart Solution3
     *
     * here k is 2
     */

    class Solution {
        public String reorganizeString(String s) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }

            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]);

            for (int i = 0; i < 26; i++) {
                if (count[i] > 0) {
                    pq.offer(new int[]{count[i], i});
                }
            }

            StringBuilder sb = new StringBuilder();
            while (!pq.isEmpty()) {
                List<Integer> used = new ArrayList<>();

                for (int i = 0; i < 2; i++) {
                    if (pq.isEmpty()) {
                        System.out.println(sb.toString());

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
