package leetcode;

import java.util.TreeMap;

public class LE_1094_Car_Pooling {
    /**
     * You are driving a vehicle that has capacity empty seats initially available for passengers.
     * The vehicle only drives east (ie. it cannot turn around and drive west.)
     *
     * Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains
     * information about the i-th trip: the number of passengers that must be picked up, and the
     * locations to pick them up and drop them off.  The locations are given as the number of
     * kilometers due east from your vehicle's initial location.
     *
     * Return true if and only if it is possible to pick up and drop off all passengers for all
     * the given trips.
     *
     * Example 1:
     * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
     * Output: false
     *
     * Example 2:
     * Input: trips = [[2,1,5],[3,3,7]], capacity = 5
     * Output: true
     *
     * Example 3:
     * Input: trips = [[2,1,5],[3,5,7]], capacity = 3
     * Output: true
     *
     * Example 4:
     * Input: trips = [[3,2,7],[3,7,9],[8,3,9]], capacity = 11
     * Output: true
     *
     * Constraints:
     * trips.length <= 1000
     * trips[i].length == 3
     * 1 <= trips[i][0] <= 100
     * 0 <= trips[i][1] < trips[i][2] <= 1000
     * 1 <= capacity <= 100000
     *
     * Medium
     */

    /**
     * An Interval Sweep Line problem in disguise
     *
     * Sweep line by TreeMap, two loops:
     * #1.Put all start/end time and count in dist (sorted by TreeMap)
     * #2.Go over the TreeMap, check if number of total passengers exceeds the capacity at any time.
     *
     * Same as :
     * LE_252_Meeting_Rooms
     * LE_253_Meeting_Rooms_II
     * LE_729_My_Calendar_I
     * LE_731_My_Calendar_II
     * LE_732_My_Calendar_III
     */
    class Solution {
        public boolean carPooling(int[][] trips, int capacity) {
            TreeMap<Integer, Integer> map = new TreeMap<>();

            for (int[] trip : trips) {
                map.put(trip[1], map.getOrDefault(trip[1], 0) + trip[0]);
                map.put(trip[2], map.getOrDefault(trip[2], 0) - trip[0]);
            }

            int count = 0;
            for (int key : map.keySet()) {
                count += map.get(key);
                if (count > capacity) return false;
            }

            return true;
        }
    }
}
