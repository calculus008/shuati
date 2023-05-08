package leetcode;

public class LE_942_DI_String_Match {
    /**
     * A permutation perm of n + 1 integers of all the integers in the range [0, n] can be represented as a string s of length n where:
     *
     * s[i] == 'I' if perm[i] < perm[i + 1], and
     * s[i] == 'D' if perm[i] > perm[i + 1].
     * Given a string s, reconstruct the permutation perm and return it. If there are multiple valid permutations perm, return any of them.
     *
     * Example 1:
     * Input: s = "IDID"
     * Output: [0,4,1,3,2]
     *
     * Example 2:
     * Input: s = "III"
     * Output: [0,1,2,3]
     *
     * Example 3:
     * Input: s = "DDI"
     * Output: [3,2,0,1]
     *
     *
     * Constraints:
     * 1 <= s.length <= 105
     * s[i] is either 'I' or 'D'.
     *
     * Easy
     *
     * https://leetcode.com/problems/di-string-match/description/
     */


    /**
     * In essence, it's a greedy + two-pointers problem. Since we only need to find a solution
     * that meets the requirement, there are many ways to construct the answer, we just need to find one.
     * Don't make it too complicated.
     *
     * Think this way:
     * if s is "DDDDDD", answer should be [6,5,4,3,2,1,0]
     * if s is "IIIIII", answer should be [0,1,2,3,4,5,6]
     *
     * So iterate from left to right, we always put the largest available number at current index if it is "D" which
     * guarantees it is bigger than the next one and put the smallest available number at current index it is "I" which
     * guarantees it is smaller than the next one.
     *
     * Just don't forget to fill the last index with current high number!!!
     */
    class Solution {
        public int[] diStringMatch(String s) {
            int n = s.length();
            int[] res = new int[n + 1];

            int low  = 0, high = n, i = 0;
            for (char c : s.toCharArray()) {
                if (c == 'I')  {
                    res[i] = low;
                    low++;
                } else {
                    res[i] = high;
                    high--;
                }
                i++;
            }

            /**
             * miss by one error!!!
             */
            res[n] = high;
            return res;
        }
    }
}
