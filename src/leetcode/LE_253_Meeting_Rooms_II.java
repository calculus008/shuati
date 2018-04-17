package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by yuank on 4/10/18.
 */
public class LE_253_Meeting_Rooms_II {
    /**
         Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
         find the minimum number of conference rooms required.

         For example,
         Given [[0, 30],[5, 10],[15, 20]],
         return 2.
     */


    //Time : O(nlogn), Space : O(n)

    /**
     [[0, 30],[5, 10],[15, 20]],

     starts : [0,   5, 15]
     ends :   [10, 20, 30]

     i
     [0,   5, 15]    res = 0
     [10, 20, 30]
     j

     i
     [0,   5, 15]    res = 1
     [10, 20, 30]
     j

     i
     [0,   5, 15]    res = 2
     [10, 20, 30]
     j

     i
     [0,   5, 15]    res = 2
     [10, 20, 30]
     j

     i
     [0,   5, 15]    res = 2
     [10, 20, 30]
     j
     */
    public int minMeetingRooms1(Interval[] intervals) {
        int res = 0;
        int n = intervals.length;
        int[] starts = new int[n];
        int[] ends = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i].start;
            ends[i] = intervals[i].end;
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        for (int i = 0, j = 0; i < n; i++) {
            if (starts[i] < ends[j]) {
                res++;
            } else {
                j++;
            }
        }

        return res;
    }

    public int minMeetingRooms2(Interval[] intervals) {
        if (intervals == null || intervals.length ==0) return 0;

        Arrays.sort(intervals, (a, b) -> a.start - b.start);
        PriorityQueue<Interval> heap = new PriorityQueue<>((a, b) -> a.end - b.end);

        heap.offer(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            Interval interval = heap.poll();
            if (intervals[i].start >= interval.end) {
                interval.end = intervals[i].end;
            } else {
                heap.offer(intervals[i]);
            }
            heap.offer(interval);
        }

        return heap.size();
    }
}
