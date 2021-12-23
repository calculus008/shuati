package leetcode;

import java.util.*;
import java.util.stream.*;

public class LE_1272_Remove_Interval {
    /**
     * A set of real numbers can be represented as the union of several disjoint intervals, where each interval is in
     * the form [a, b). A real number x is in the set if one of its intervals [a, b) contains x (i.e. a <= x < b).
     *
     * You are given a sorted list of disjoint intervals intervals representing a set of real numbers as described above,
     * where intervals[i] = [ai, bi] represents the interval [ai, bi). You are also given another interval toBeRemoved.
     *
     * Return the set of real numbers with the interval toBeRemoved removed from intervals. In other words, return the
     * set of real numbers such that every x in the set is in intervals but not in toBeRemoved. Your answer should be a
     * sorted list of disjoint intervals as described above.
     *
     * Example 1:
     * Input: intervals = [[0,2],[3,4],[5,7]], toBeRemoved = [1,6]
     * Output: [[0,1],[6,7]]
     *
     * Example 2:
     * Input: intervals = [[0,5]], toBeRemoved = [2,3]
     * Output: [[0,2],[3,5]]
     *
     * Example 3:
     * Input: intervals = [[-5,-4],[-3,-2],[1,2],[3,5],[8,9]], toBeRemoved = [-1,4]
     * Output: [[-5,-4],[-3,-2],[4,5],[8,9]]
     *
     * Constraints:
     * 1 <= intervals.length <= 104
     * -109 <= ai < bi <= 109
     *
     * Medium
     *
     * https://leetcode.com/problems/remove-interval/
     */

    /**
     * Sweep Line - check overlap
     *
     * Time : O(n)
     * Space : if not considering the space for output, it is O(1)
     */
    class Solution {
        public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
            List<List<Integer>> ans = new ArrayList<>();
            for (int[] i : intervals) {
                if (i[1] <= toBeRemoved[0] || i[0] >= toBeRemoved[1]) { // no overlap.
                    ans.add(Arrays.asList(i[0], i[1]));
                } else { // there's overlap, i[1] > toBeRemoved[0] && i[0] < toBeRemoved[1].
                    if (i[0] < toBeRemoved[0]) {// left end no overlap.
                        ans.add(Arrays.asList(i[0], toBeRemoved[0]));
                    }

                    if (i[1] > toBeRemoved[1]) {// right end no overlap.
                        ans.add(Arrays.asList(toBeRemoved[1], i[1]));
                    }
                }
            }
            return ans;
        }
    }

    /**
     * Same as the one above, it can be simplified to be the first one.
     */
    class Solution_Mine {
        public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
            List<List<Integer>> res = new ArrayList<>();

            for (int[] i : intervals) {
                if (i[1] <= toBeRemoved[0] || i[0] >= toBeRemoved[1]) {//#1
                    res.add(Arrays.asList(i[0], i[1]));
                } else if (i[0] < toBeRemoved[0]) {
                    res.add(Arrays.asList(i[0], toBeRemoved[0]));//#2

                    if (i[1] > toBeRemoved[1]) {//#3
                        res.add(Arrays.asList(toBeRemoved[1], i[1]));
                    }
                } else if (i[1] > toBeRemoved[1]) {//#4
                    res.add(Arrays.asList(toBeRemoved[1], i[1]));
                }
            }

            return res;
        }
    }
}
