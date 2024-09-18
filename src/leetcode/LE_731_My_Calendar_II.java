package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class LE_731_My_Calendar_II {
    /**
     * Implement a MyCalendarTwo class to store your events. A new event
     * can be added if adding the event will not cause a triple booking.
     *
     * Your class will have one method, book(int start, int end). Formally,
     * this represents a booking on the half open interval [start, end),
     * the range of real numbers x such that start <= x < end.
     *
     * A triple booking happens when three events have some non-empty
     * intersection (ie., there is some time that is common to all 3 events.)
     *
     * For each call to the method MyCalendar.book, return true if the event
     * can be added to the calendar successfully without causing a triple booking.
     * Otherwise, return false and do not add the event to the calendar.
     *
     * Your class will be called like this:
     * MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
     *
     * Example 1:
     *
     * MyCalendar();
     * MyCalendar.book(10, 20); // returns true
     * MyCalendar.book(50, 60); // returns true
     * MyCalendar.book(10, 40); // returns true
     * MyCalendar.book(5, 15); // returns false
     * MyCalendar.book(5, 10); // returns true
     * MyCalendar.book(25, 55); // returns true
     * Explanation:
     * The first two events can be booked.  The third event can be double booked.
     * The fourth event (5, 15) can't be booked, because it would result in a triple booking.
     * The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
     * The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
     * the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.
     *
     *
     * Note:
     *
     * The number of calls to MyCalendar.book per test case will be at most 1000.
     * In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
     *
     * Medium
     */

    /**
     *          LE_252_Meeting_Rooms
     *          LE_253_Meeting_Rooms_II
     *          LE_729_My_Calendar_I
     *          LE_731_My_Calendar_II
     *          LE_732_My_Calendar_III
     *
     * 类似于LE_729_My_Calendar_I solution 2, use TreeMap to achieve log(n) for insert/delete.
     *
     * A much better solution than solution1
     *
     * Key:
     * 1.This is basically a sweep line solution, but using TreeMap, instead of a sorted Collection.
     *  TreeMap<Integer, Integer>:
     *  key - time
     *  val - number of meetings at the time, 注意，这是离散化的，就是说这个值只是在这个特定的时间点。
     *
     * 2.Unique strategy : Insert first, then detect if there is any triple booking, if yes, then
     *                     remove.
     * 3.For each booking call, insert first, then iterate through TreeMap, keys (time) are sorted,
     *   try to detect if there's case for triple booking.
     *
     * Time  : O(nlogn)
     * Space : O(n)
     */
    class MyCalendarTwo_2 {
        TreeMap<Integer, Integer> map;

        public MyCalendarTwo_2() {
            map = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            map.put(start, map.getOrDefault(start, 0) + 1);
            map.put(end, map.getOrDefault(end, 0) -1);

            int count = 0;
            for (int key : map.keySet()) {
                count += map.get(key);

                if (count > 2) {// there's triple booking
                    map.put(start, map.get(start) - 1); //undo put for start
                    if (map.get(start) == 0) {
                        map.remove(start);
                    }

                    map.put(end, map.get(end) + 1);    //undo put fo end
                    if (map.get(end) == 0) {
                        map.remove(end);
                    }

                    return false;
                }
            }

            return true;
        }
    }

    /**
     * 类似于LE_729_My_Calendar_I solution 1, 基于max(start) and min(end).
     *
     * Time  : O(n ^ 2)
     * Space : O(n)
     */
    class MyCalendarTwo_1 {
        class Interval {
            int start;
            int end;

            public Interval(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }

        List<Interval> calendar;
        List<Interval> overlap;

        public MyCalendarTwo_1() {
            calendar = new ArrayList<>();
            overlap = new ArrayList<>();
        }

        public boolean book(int start, int end) {
            for (Interval i1 : calendar) {
                int s1 = Math.max(i1.start, start);
                int e1 = Math.min(i1.end, end);

                /**
                 * !!!
                 * "<", not "<=", because "half open interval [start, end)"
                 */
                if (s1 < e1) {//there's overlap
                    for (Interval i2 : overlap) {
                        int s2 = Math.max(i2.start, s1);
                        int e2 = Math.min(i2.end, e1);

                        if (s2 < e2) {
                            /**
                             * !!!
                             * we do not need to keep the overlaps of previous book() calls,
                             * because the overlapped bookings will be stored in the calendar
                             * list anyway.
                             *
                             * We also need to clear the overlap list when we see
                             * a triple overlap caused by a booking, because the previous overlaps
                             * caused by this booking should be removed (the booking is not successful.)
                             */
                            overlap.clear();
                            return false;
                        }
                    }
                    overlap.add(new Interval(s1, e1));
                }
            }

            calendar.add(new Interval(start, end));

            return true;
        }
    }
}
