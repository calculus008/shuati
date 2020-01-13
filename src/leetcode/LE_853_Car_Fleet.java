package leetcode;

import java.util.TreeMap;

public class LE_853_Car_Fleet {
    /**
     * N cars are going to the same destination along a one lane road.
     * The destination is target miles away.
     *
     * Each car i has a constant speed speed[i] (in miles per hour),
     * and initial position position[i] miles towards the target along
     * the road.
     *
     * A car can never pass another car ahead of it, but it can catch up
     * to it, and drive bumper to bumper at the same speed.
     *
     * The distance between these two cars is ignored - they are assumed
     * to have the same position.
     *
     * A car fleet is some non-empty set of cars driving at the same
     * position and same speed.  Note that a single car is also a car fleet.
     *
     * If a car catches up to a car fleet right at the destination point,
     * it will still be considered as one car fleet.
     *
     *
     * How many car fleets will arrive at the destination?
     *
     * Example 1:
     *
     * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
     * Output: 3
     * Explanation:
     * The cars starting at 10 and 8 become a fleet, meeting each other at 12.
     * The car starting at 0 doesn't catch up to any other car, so it is a fleet by itself.
     * The cars starting at 5 and 3 become a fleet, meeting each other at 6.
     * Note that no other cars meet these fleets before the destination, so the answer is 3.
     *
     * Note:
     *
     * 0 <= N <= 10 ^ 4
     * 0 < target <= 10 ^ 6
     * 0 < speed[i] <= 10 ^ 6
     * 0 <= position[i] < target
     *
     * All initial positions are different.
     *
     * Medium
     */

    /**
     * TreeMap
     * key - distance from start position to destination
     * val - time to get to the destination
     *
     * For a car to be caught up, it needs to be in front, hence sort by distance.
     *
     * Keep track of currently slowest one(which might block the car behind),
     * if a car can catch up current slowest one, it will not form a new group.
     * Otherwise, we count a new group and update the info of slowest
     *
     * Time  : O(nlogn)
     * Space : O(n)
     */
    class Solution {
        public int carFleet(int target, int[] position, int[] speed) {
            int n = position.length;
            TreeMap<Integer, Double> map = new TreeMap<>();

            for (int i = 0; i < n; i++) {
                int dist = target - position[i];
                double time = (double) dist / speed[i];
                map.put(dist, time);
            }

            int count = 0;
            double cur = 0;
            for (int key : map.keySet()) {
                double t = map.get(key);

                if (t > cur) {
                    count++;
                    cur = t;
                }
            }

            return count;
        }
    }
}
