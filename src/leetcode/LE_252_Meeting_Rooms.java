package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by yuank on 4/10/18.
 */
public class LE_252_Meeting_Rooms {
    /**
         Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
         determine if a person could attend all meetings.

         For example,
         Given [[0, 30],[5, 10],[15, 20]],
         return false.

        Very Important!!!

         LE_252_Meeting_Rooms
         LE_253_Meeting_Rooms_II
         LE_729_My_Calendar_I
         LE_731_My_Calendar_II
         LE_732_My_Calendar_III
     */

    /**
     * Time  : O(nlogn)
     * Space : O(1)
     */
    class Solution1 {
        public boolean canAttendMeetings(Interval[] intervals) {
            //!!! sort , 2 params, "(intervals,"
            Arrays.sort(intervals, (a, b) -> a.start - b.start);

            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i -1].end > intervals[i].start) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Sweep line + TreeMap solution
     * Time  : O(nlogn)
     * Space : O(n)
     */
    class Solution2 {
        public boolean canAttendMeetings(int[][] intervals) {
            TreeMap<Integer, Integer> map = new TreeMap<>();

            for (int[] interval : intervals) {
                map.put(interval[0], map.getOrDefault(interval[0], 0) + 1);
                map.put(interval[1], map.getOrDefault(interval[1], 0) - 1);
            }

            int count = 0;
            for (int key : map.keySet()) {
                count += map.get(key);
                if (count > 1) return false;
            }

            return true;
        }
    }

    public class Solution_LI_920 {
        /**
         * @param intervals: an array of meeting time intervals
         * @return: if a person could attend all meetings
         */
        public boolean canAttendMeetings(List<Interval> intervals) {
            Collections.sort(intervals, (a, b) -> a.start - b.start);

            for (int i = 1; i < intervals.size(); i++) {
                if (intervals.get(i - 1).end > intervals.get(i).start) {
                    return false;
                }
            }

            return true;
        }
    }


}
