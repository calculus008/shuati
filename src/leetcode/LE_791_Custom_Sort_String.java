package leetcode;

public class LE_791_Custom_Sort_String {
    /**
     * S and T are strings composed of lowercase letters. In S, no letter occurs more than once.
     *
     * S was sorted in some custom order previously. We want to permute the characters of T so that
     * they match the order that S was sorted. More specifically, if x occurs before y in S, then x
     * should occur before y in the returned string.
     *
     * Return any permutation of T (as a string) that satisfies this property.
     *
     * Example :
     * Input:
     * S = "cba"
     * T = "abcd"
     * Output: "cbad"
     * Explanation:
     * "a", "b", "c" appear in S, so the order of "a", "b", "c" should be "c", "b", and "a".
     * Since "d" does not appear in S, it can be at any position in T. "dcba", "cdba", "cbda" are also valid outputs.
     *
     *
     * Note:
     * S has length at most 26, and no character is repeated in S.
     * T has length at most 200.
     * S and T consist of lowercase letters only.
     *
     * Medium
     */

    /**
     * Time : O(len(S) + len(T))
     * Space : O(len(T))
     *
     * Key : We only need to return one valid string from all the permutations, so it is not asking you to go through
     *       all permutation, we just need to construct one valid string.
     *       The question only says that S has no duplicate chars, NOT for T. So first pass to get the count of each
     *       char in T. 2nd pass go through S, put it in sb. Last, go through count, put in the remaining chars.
     *
     * Example:
     * S = "cba"
     * T = "eaaabacbcd"
     *
     * Answer : "ccbbaaaade"
     */
    public String customSortString(String S, String T) {
        int[] count = new int[26];
        for (char c : T.toCharArray()) {
            count[c - 'a']++;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : S.toCharArray()) {
            int idx = c - 'a';
            if (count[idx] > 0) {
                for (int i = 0; i < count[idx]; i++) {
                    sb.append(c);
                }
                /**
                 * !!!
                 * reset to 0 so the current char won't be processed again.
                 */
                count[idx] = 0;
            }
        }

        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int  j = 0; j < count[i]; j++) {
                    sb.append((char)('a'  + i));
                }
            }
        }

        return sb.toString();
    }
}
