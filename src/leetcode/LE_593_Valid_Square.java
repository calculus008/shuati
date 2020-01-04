package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_593_Valid_Square {
    /**
     * Given the coordinates of four points in 2D space, return whether the four points could construct a square.
     *
     * The coordinate (x,y) of a point is represented by an integer array with two integers.
     *
     * Example:
     *
     * Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
     * Output: True
     *
     *
     * Note:
     *
     * All the input integers are in the range [-10000, 10000].
     * A valid square has four equal sides with positive length and four equal angles (90-degree angles).
     * Input points have no order.
     *
     * Medium
     */

    /**
     * For any 4 points, there are 6 distances, calculate all it,
     * use HashMap to get frequency of the distance values.
     *
     * For square, there are only two values in HashMap and diagonal distance (max)
     * has frequency of 2.
     */
    class Solution {
        public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
            int[] distances = {dist(p1, p2), dist(p1, p3), dist(p1, p4), dist(p2, p4), dist(p3, p4), dist(p2, p3)};

            Map<Integer, Integer> map = new HashMap<>();
            int max = 0;

            for (int d : distances) {
                max = Math.max(max, d);
                map.put(d, map.getOrDefault(d, 0) + 1);
            }

            return map.get(max) == 2 && map.size() == 2;
        }

        private int dist(int[] p1, int[] p2) {
            return (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
        }
    }
}
