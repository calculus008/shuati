package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 11/29/18.
 */
public class LE_763_Partition_Labels {
    /**
         A string S of lowercase letters is given. We want to partition this string into as
         many parts as possible so that each letter appears in at most one part, and return
         a list of integers representing the size of these parts.

         Example 1:
         Input: S = "ababcbacadefegdehijhklij"
         Output: [9,7,8]
         Explanation:
         The partition is "ababcbaca", "defegde", "hijhklij".
         This is a partition so that each letter appears in at most one part.
         A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.

         Note:
         S will have length in range [1, 500].
         S will consist of lowercase letters ('a' to 'z') only.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/string/leetcode-763-partition-labels/
     *
     * HashMap + Greedy
     *
     * Time and Space : O(n)
     */

    public List<Integer> partitionLabels(String S) {
        List<Integer> res = new ArrayList<>();
        if (S == null || S.length() == 0) {
            return res;
        }

        int[] map = new int[26];
        char[] c = S.toCharArray();
        int start = 0;
        int end = 0;

        /**
         * !!!
         * Two for loop, the first one gets the last index of each char
         */
        for (int i = 0; i < c.length; i++) {
            map[c[i] - 'a'] = i;
        }

        for (int i = 0; i < c.length; i++) {
            /**
             * !!!
             * First thing in the 2nd for loop, find the current end index.
             * It's "end" matters!!!
             */
            end = Math.max(end, map[c[i] - 'a']);

            if (i == end) {
                res.add(end - start + 1);
                start = i + 1;
            }
        }

        return res;
    }
}
