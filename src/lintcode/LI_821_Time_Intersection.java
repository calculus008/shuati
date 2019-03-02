package lintcode;

import common.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuank on 10/22/18.
 */
public class LI_821_Time_Intersection {
    /**
         Give two users' ordered online time series, and each section records the user's login
         time point x and offline time point y. Find out the time periods when both users
         are online at the same time, and output in ascending order.

         Example
         Given seqA = [(1,2),(5,100)], seqB = [(1,6)], return [(1,2),(5,6)].

         Explanation:
         In these two time periods (1,2),(5,6), both users are online at the same time.

         Given seqA = [(1,2),(10,15)], seqB = [(3,5),(7,9)], return [].

         Explanation:
         There is no time period, both users are online at the same time.

         Notice
         We guarantee that the length of online time series meet 1 <= len <= 1e6.
         For a user's online time series, any two of its sections do not intersect.

         Medium
     */

    /**
     * Time : O(nlogn) (need sorting on pairs), not as efficient as LI_821_Time_Intersection (O(n))
     *
     */
    class Pair {
        int time;
        int flag;
        public Pair(int time, int flag) {
            this.time = time;
            this.flag = flag;
        }
    }

    public List<Interval> timeIntersection(List<Interval> seqA, List<Interval> seqB) {
        List<Interval> res = new ArrayList<>();
        if (seqA == null || seqA.size() == 0 || seqB == null || seqB.size() == 0) return res;

        List<Pair> pairs = new ArrayList<>();
        for (Interval i : seqA) {
            pairs.add(new Pair(i.start, 1));
            pairs.add(new Pair(i.end, 0));
        }

        for (Interval i : seqB) {
            pairs.add(new Pair(i.start, 1));
            pairs.add(new Pair(i.end, 0));
        }

        Collections.sort(pairs, (a, b) -> {
            int comp = 0;
            if (a.time == b.time) {
                comp = Integer.compare(a.flag, b.flag);
            } else {
                /**
                   !!! ",", NOT "-"
                 */
                comp = Integer.compare(a.time, b.time);
            }
            return comp;
        });

        int count = 0;
        int start = 0, end = 0;
        for (Pair p : pairs) {
            if (p.flag == 1) {
                count++;
                if (count == 2) {
                    start = p.time;
                }
            } else {
                count--;
                if (count == 1) {
                    end = p.time;
                    res.add(new Interval(start, end));
                }
            }
        }

        return res;
    }
}
