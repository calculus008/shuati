package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_830_Positions_Of_Large_Groups {
    /**
     * In a string s of lowercase letters, these letters form consecutive groups of the same character.
     *
     * For example, a string like s = "abbxxxxzyy" has the groups "a", "bb", "xxxx", "z", and "yy".
     *
     * A group is identified by an interval [start, end], where start and end denote the start and end
     * indices (inclusive) of the group. In the above example, "xxxx" has the interval [3,6].
     *
     * A group is considered large if it has 3 or more characters.
     *
     * Return the intervals of every large group sorted in increasing order by start index.
     *
     * Example 1:
     * Input: s = "abbxxxxzzy"
     * Output: [[3,6]]
     * Explanation: "xxxx" is the only large group with start index 3 and end index 6.
     *
     * Example 2:
     * Input: s = "abc"
     * Output: []
     * Explanation: We have groups "a", "b", and "c", none of which are large groups.
     *
     * Example 3:
     * Input: s = "abcdddeeeeaabbbcd"
     * Output: [[3,5],[6,9],[12,14]]
     * Explanation: The large groups are "ddd", "eeee", and "bbb".
     *
     * Example 4:
     * Input: s = "aba"
     * Output: []
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s contains lower-case English letters only.
     *
     * Easy
     */

    /**
     * The only trick case for this problem is case likeL
     *
     * "aaaa"
     * "bcaaadddddd"
     *
     * This is the "miss-the-last" error.
     *
     */
    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> res = new ArrayList<>();

        char[] chars = s.toCharArray();
        int start = 0;
        char last = chars[0];
        int count = 1;
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (c != last) {
                if (count >= 3) {
                    List<Integer> list = new ArrayList<>();
                    list.add(start);
                    list.add(i - 1);
                    res.add(list);
                }
                start = i;
                last = c;
                count = 1;
            } else {
                count++;
            }
        }

        if (count >= 3) {
            List<Integer> list = new ArrayList<>();
            list.add(start);
            list.add(chars.length - 1);
            res.add(list);
        }

        return res;
    }
}
