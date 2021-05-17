package leetcode;

import java.util.*;

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

         LE_252_Meeting_Rooms
         LE_253_Meeting_Rooms_II
         LE_729_My_Calendar_I
         LE_731_My_Calendar_II
         LE_732_My_Calendar_III
         Number_Employees_In_Intervals
     */

    /**
     * Best solution !!!
     * Same solution for LE_731_My_Calendar_II and LE_732_My_Calendar_III
     *
     * In essence, it is using same sweep line idea, it uses TreeMap instead of sorting collections.
     * Very elegant and simple solution.
     *
     * Example: [[0, 30],[5, 10],[15, 20]],
     *
     * TreeMap:
     * 0  -> 1
     * 5  -> 1
     * 10 -> -1
     * 15 -> 1
     * 20 -> -1
     * 30 -> -1
     *
     * Iterate through TreeMap
     * Count:
     * 0  : 1
     * 5  : 2
     * 10 : 1
     * 15 : 2
     * 20 : 1
     * 20 : 0
     *
     * Max is 2
     *
     * Time  : O(nlogn)
     * Space : O(n)
     */
    class Solution {
        public int minMeetingRooms(int[][] intervals) {
            TreeMap<Integer, Integer> map = new TreeMap<>();

            for (int[] interval : intervals) {
                map.put(interval[0], map.getOrDefault(interval[0], 0) + 1);
                map.put(interval[1], map.getOrDefault(interval[1], 0) - 1);
            }

            int count = 0;
            int max = 0;

            for (int key : map.keySet()) {
                count += map.get(key);
                max = Math.max(max, count);
            }

            return max;
        }
    }

    class Solution_SweepLine_Practice {
        class Pair {
            int time;
            int type;

            public Pair(int time, int type) {
                this.time = time;
                this.type = type;
            }
        }


        public int minMeetingRooms(int[][] intervals) {
            if (intervals == null || intervals.length == 0) return 0;

            int res = 0;
            List<Pair> list = new ArrayList<>();
            for (int[] interval : intervals) {
                list.add(new Pair(interval[0], 1));
                list.add(new Pair(interval[1], 0));
            }

            /**
             * !!!
             */
            Collections.sort(list, (a, b) -> a.time != b.time ? a.time - b.time : a.type -b.type);

            int count = 0;
            for (Pair p : list) {
                if (p.type == 1) {
                    count++;
                } else {
                    count--;
                }
                res = Math.max(res, count);
            }

            return res;
        }
    }

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

     the minimal number of rooms equal to the max number of overlapping meeting in any time point.
     The code is actually counting the number of overlapping meetings throughout the timeline and
     recording the maximum.
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

    /**
     * Lintcode 919
     * Input is list of Interval
     *
     * Line Sweep
     *
     * Move line, for a given moment, count how many meetings are running in parallel?
     * Maintain the MAX count value, then it is the answer.
     *
     * In order to do it, need to dist all start/end points on a single timeline,
     * hence we need Pair Class to represent time and type of events (start or end),
     * then sort.
     *
     * Time  : O(nlogn)
     * Space : O(n)
     */
    public class Solution3 {
        class Pair {
            int x;
            int flag;

            public Pair(int x, int flag) {
                this.x = x;
                this.flag = flag;
            }
        }

        public int minMeetingRooms(List<Interval> intervals) {
            if (intervals == null || intervals.size() == 0) return 0;

            List<Pair> pairs = new ArrayList<>();
            for (Interval interval : intervals) {
                pairs.add(new Pair(interval.start, 1));
                pairs.add(new Pair(interval.end, 0));
            }

            Collections.sort(pairs, (a, b) -> {
                int comp = 0;
                if (a.x == b.x) {
                    /**
                     * !!!
                     * if time is the same, end point is ahead of start point
                     */
                    comp = Integer.compare(a.flag, b.flag);
                } else {
                    comp = Integer.compare(a.x, b.x);
                }
                return comp;
            });

            int res = Integer.MIN_VALUE;
            int count = 0;
            for (Pair p : pairs) {
                if (p.flag == 1) {
                    count++;
                } else {
                    count--;
                }

                res = Math.max(res, count);
            }

            return res;
        }
    }
}
