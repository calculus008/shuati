package lintcode;

import common.Interval;

import java.util.ArrayList;
import java.util.List;

public class LI_206_Interval_Sum {
    /**
     * Given an integer array (index from 0 to n-1, where n is the size of this array),
     * and an query list. Each query has two integers [start, end]. For each query,
     * calculate the sum number between index start and end in the given array,
     * return the result list.
     *
     * Example
     * Example 1:
     * Input: array = [1,2,7,8,5],  queries = [(0,4),(1,2),(2,4)]
     * Output: [23,9,20]
     *
     * Example 2:
     * Input: array : [4,3,1,2],  queries : [(1,2),(0,2)]
     * Output: [4,8]
     *
     * Challenge
     * O(logN) time for each query (??)
     *
     * Medium
     */

    public class Solution1 {
        public List<Long> intervalSum(int[] A, List<Interval> queries) {
            long[] sums = new long[A.length];
            List<Long> res = new ArrayList<>();

            sums[0] = A[0];
            for (int i = 1; i < A.length; i++) {
                sums[i] = sums[i - 1] + A[i];
            }

            for (Interval query : queries) {
                int s = query.start;
                int e = query.end;

                if (s == 0) {
                    res.add(sums[e]);
                } else {
                    res.add(sums[e] - sums[s - 1]);
                }
            }

            return res;
        }
    }
}
