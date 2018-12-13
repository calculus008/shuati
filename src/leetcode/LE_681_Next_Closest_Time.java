package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yuank on 8/11/18.
 */
public class LE_681_Next_Closest_Time {
    /**
         Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits.
         There is no limit on how many times a digit can be reused.

         You may assume the given input string is always valid.
         For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.

         Example 1:
         Input: "19:34"
         Output: "19:39"
         Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39,
                      which occurs 5 minutes later.  It is not 19:33, because this
                      occurs 23 hours and 59 minutes later.

         Example 2:
         Input: "23:59"
         Output: "22:22"
         Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22.
                      It may be assumed that the returned time is next day's time since
                      it is smaller than the input time numerically.

         Medium

         Variation : "find the largest smaller than current time﻿"
     */

    /**
     * http://zxi.mytechroad.com/blog/simulation/leetcode-681-next-closest-time/
     *
     * Permutation based DFS
     *
     * new time : d1d2:d3d4, d1 ~ d4 in set
     * Time : O(4 ^ 4) = O(256)
     */

    String res = "";
    int diff = Integer.MAX_VALUE;

    public String nextClosestTime(String time) {
        if (time == null || time.length() < 5) {
            return res;
        }

        int h1 = Integer.parseInt(time.substring(0, 1));
        int h2 = Integer.parseInt(time.substring(1, 2));
        int m1 = Integer.parseInt(time.substring(3, 4));
        int m2 = Integer.parseInt(time.substring(4, 5));

        int target = (h1 * 10 + h2) * 60 + (m1 * 10 + m2);

        //use set to remove duplicate digits in "time"
        Set<Integer> set = new HashSet<>();
        set.add(h1);
        set.add(h2);
        set.add(m1);
        set.add(m2);

        //11:11, not possible to create a different time, the set size is between 2 and 4
        if (set.size() == 1) return time;

        helper(set, target, 0, new ArrayList<>());
        return res;
    }

    private void helper(Set<Integer> set, int target, int pos, List<Integer> cur) {
        if (pos == 4) {
            int val = (cur.get(0) * 10 + cur.get(1)) * 60 + (cur.get(2) * 10 + cur.get(3));

            if (val == target) return;

            //is the closest by far?
            int curDiff = val > target ? val - target : 1440 - target + val;//!!!
            if (curDiff < diff) {
                res = String.valueOf(cur.get(0)) + String.valueOf(cur.get(1)) + ":" + String.valueOf(cur.get(2)) + String.valueOf(cur.get(3));
                diff = curDiff;
            }
            return;
        }

        for (int i : set) {
            //pruning
            if (pos == 0) {//first digit for hour, only valid digits : 0,1,2
                if (i > 2) return;
            }
            if (pos == 1) {//hour, valid range : 0 ~ 23
                if (cur.get(0) * 10 + i > 23) return;
            }
            if (pos == 2) {//first digit of minute, valid range : 0 ~ 5
                if (i > 5) return;
            }
            if (pos == 3) {//minutes, 0 ~ 59
                if (cur.get(2) * 10 + i > 59) return;
            }

            cur.add(i);
            helper(set, target, pos + 1, cur);
            cur.remove(cur.size() - 1);
        }
    }
}
