package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LE_729_My_Calendar_I {
    /**
         Implement a MyCalendar class to store your events.
         A new event can be added if adding the event will not cause a double booking.

         Your class will have the method, book(int start, int end). Formally, this represents a booking
         on the half open interval [start, end), the range of real numbers x such that start <= x < end.

         A double booking happens when two events have some non-empty intersection
         (ie., there is some time that is common to both events.)

         For each call to the method MyCalendar.book, return true if the event can be added to the calendar
         successfully without causing a double booking. Otherwise, return false and do not add the event to the calendar.

         Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)

         Example 1:
         MyCalendar();
         MyCalendar.book(10, 20); // returns true
         MyCalendar.book(15, 25); // returns false
         MyCalendar.book(20, 30); // returns true

         Explanation:
         The first event can be booked.  The second can't because time 15 is already booked by another event.
         The third event can be booked, as the first event takes every time less than 20, but not including 20.

         Note:

         The number of calls to MyCalendar.book per test case will be at most 1000.
         In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].

         Medium
     */


    /**
     * TreeMap + Binary Search
     * Time  : O(nlogn)
     * Space : O(n)
     *
     * Classic usage of TreeMap - floorKey and ceilingKey
     */
    class MyCalendar2 {
        TreeMap<Integer, Integer> map;

        public MyCalendar2() {
            map = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            Integer floor = map.floorKey(start);    // !!! Integer check overlap on start side
            if (floor != null && map.get(floor) > start) {//floor != null
                return false;
            }

            Integer ceiling = map.ceilingKey(start);// !!! Integer check overlap on end side
            if (ceiling != null && ceiling < end) {//floor != null
                return false;
            }

            map.put(start, end);
            return true;
        }
    }

    /**
     * Similar Problems:
     *
     *          LE_252_Meeting_Rooms
     *          LE_253_Meeting_Rooms_II
     *          LE_729_My_Calendar_I
     *          LE_731_My_Calendar_II
     *          LE_732_My_Calendar_III
     *
     * https://zxi.mytechroad.com/blog/algorithms/binary-search/leetcode-729-my-calendar-i/
     *
     * Related Problems:
     * LeetCode.715 Range Module https://www.youtube.com/watch?v=pcpB9ux3RrQ
     * LeetCode 699. Falling Squares https://www.youtube.com/watch?v=UeuV-6Ygxs4
     * LeetCode 56. Merge Intervals https://www.youtube.com/watch?v=6tLHjei-f0I
     * LeetCode 57. Insert Interval https://www.youtube.com/watch?v=oWHWDI2eOHY﻿
     *
     * 392 题的follow up可以用792题的方法 https://www.youtube.com/watch?v=l8_vcmjQA4g
     * 预处理t然后用二分搜索 时间复杂度从(k * (|s| + |t|)) 降低到 (k*(|s| + log|t|))，空间复杂度O(|t|)。﻿
     *
     * HashMap
     * Time  : O(n ^ 2) , 这道题比较特殊，book方法会被调用n次，每次的时间是i i=1,2,3,...,n，所以总的时间复杂度是O(n^2)﻿ !!!
     * Space : O(n)
     */
    class MyCalendar1 {
        Map<Integer, Integer> map;

        public MyCalendar1() {
            map = new HashMap<>();
        }

        public boolean book(int start, int end) {
            for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
                int start1 = entry.getKey();
                int end1 = entry.getValue();

                /**
                 * !!!
                 * trick to tell if two intervals have overlap
                 */
                if (Math.max(start, start1) < Math.min(end, end1)) {
                    return false;
                }
            }

            map.put(start, end);
            return true;
        }
    }
}