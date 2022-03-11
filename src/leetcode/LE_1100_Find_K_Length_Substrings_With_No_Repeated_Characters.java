package leetcode;

import java.util.HashSet;
import java.util.Set;

public class LE_1100_Find_K_Length_Substrings_With_No_Repeated_Characters {
    /**
     * Given a string S, return the number of substrings of length K with no repeated characters.
     *
     * Example 1:
     * Input: S = "havefunonleetcode", K = 5
     * Output: 6
     * Explanation:
     * There are 6 substrings they are : 'havef','avefu','vefun','efuno','etcod','tcode'.
     *
     * Example 2:
     * Input: S = "home", K = 5
     * Output: 0
     * Explanation:
     * Notice K can be larger than the length of S. In this case is not possible to find any substring.
     *
     * Note:
     * 1 <= S.length <= 10^4
     * All characters of S are lowercase English letters.
     * 1 <= K <= 10^4
     *
     * Medium
     */

    /**
     * Sliding window with array counter, standard moving a fixed length sliding window
     */
    class Solution1 {
        public int numKLenSubstrNoRepeats(String S, int K) {
            if (S == null || S.length() == 0) return 0;

            int[] count = new int[256];
            int total = 0;
            int res = 0;

            for (int i = 0; i < S.length(); i++) {
                int r = S.charAt(i) - 'a';
                if (count[r] == 0) {
                    total++;
                }
                count[r]++;

                if (i >= K - 1) {
                    if (total == K) {
                        res++;
                    }

                    /**
                     * !!!
                     */
                    int l = S.charAt(i - K + 1) - 'a';
                    count[l]--;
                    if (count[l] == 0) {
                        total--;
                    }
                }
            }

            return res;
        }
    }

    /**
     * Variable length sliding window, use set to ensure current sliding window has no
     * duplicate char. Then check the length of current sliding window.
     *
     * K = 3
     * a b c d e
     * |___|
     *     +1
     *      +1
     *        +1
     *
     * a b c c c d e
     * |___|
     *     +1
     *       |
     *         |___|
     *             +1
     *
     * The lazy delete/sliding policy is very cool!
     * The counting policy here counts substrings even longer than length K so long they have unique characters.
     * But this is okay because even if we delete and slide every time we crossed length K (eagerly), we would
     * still get substrings with unique characters only (as that invariant is still asserted every time). Now,
     * if/when a duplicate character does arrive, we start shrinking the window from left till the previous
     * occurrence of this character is excluded - a lazy delete/slide policy. While doing this, we also came
     * across other starting-points of valid substrings, which we ignore because we have already counted them
     * before coming to this index (as part of the larger than K-length substrings). So nothing is missed.
     */
    class Solution2 {
        public int numKLenSubstrNoRepeats(String S, int K) {
            if (S == null || S.length() == 0) return 0;

            Set<Character> set = new HashSet<>();

            int res = 0;
            for (int i = 0, j = 0; i < S.length(); i++) {
                while (set.contains(S.charAt(i))) {
                    set.remove(S.charAt(j));
                    j++;
                }
                set.add(S.charAt(i));
                res += i - j + 1 >= K ? 1 : 0;
            }

            return res;
        }
    }
}
