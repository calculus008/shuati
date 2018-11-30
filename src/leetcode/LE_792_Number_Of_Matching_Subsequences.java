package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuank on 11/29/18.
 */
public class LE_792_Number_Of_Matching_Subsequences {
    /**
         Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.

         Example :
         Input:
         S = "abcde"
         words = ["a", "bb", "acd", "ace"]
         Output: 3
         Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".
         Note:

         All words in words and S will only consists of lowercase letters.
         The length of S will be in the range of [1, 50000].
         The length of words will be in the range of [1, 5000].
         The length of words[i] will be in the range of [1, 50].

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/string/leetcode-792-number-of-matching-subsequences/
     *
     * HashMap
     *
     * Solution 1
     * Brutal Force
     *
     * Time : O((S + L) * W)
     * Space : O(1)
     *
     */
    class Solution1 {
        public int numMatchingSubseq1(String S, String[] words) {
            int res = 0;
            char[] s = S.toCharArray();

            for (String word : words) {
                char[] c = word.toCharArray();
                int n = c.length;

                int cur = 0;
                for (int i = 0; i < s.length; i++) {
                    if (s[i] == c[cur]) {
                        cur++;
                    }

                    if (cur == n) {
                        res++;
                        break;
                    }
                }
            }

            return res;
        }
    }

    /**
     * HashMap + BinarySearch
     *
     * Time  : O(S + W * L * log(S))
     * Space : O(S)
     * S: length of S
     * W: number of words
     * L: length of a word
     */
    class Solution2 {
        List<List<Integer>> pos;

        public int numMatchingSubseq(String S, String[] words) {
            pos = new ArrayList<>();
            for (int i = 0; i < 26; i++) {
                pos.add(new ArrayList<Integer>());
            }

            int res = 0;
            char[] s = S.toCharArray();

            for (int i = 0; i < s.length; i++) {
                int idx = s[i] - 'a';
                pos.get(idx).add(i);
            }

            for (String word : words) {
                if (isMatch(word)) {
                    res++;
                }
            }

            return res;
        }

        private boolean isMatch(String word) {
            int l = -1;//!!!
            for (char c : word.toCharArray()) {
                List<Integer> p = pos.get(c - 'a');//!!!

                int target = Collections.binarySearch(p, l + 1);
                int index = target < 0 ? - target - 1 : target;
                if (index >= p.size()) {
                    return false;
                }

                l = p.get(index);
            }

            return true;
        }
    }
}
