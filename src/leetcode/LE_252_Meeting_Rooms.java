package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
     */

    class Solution {
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
