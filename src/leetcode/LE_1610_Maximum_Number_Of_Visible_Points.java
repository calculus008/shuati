package leetcode;

import java.util.*;

public class LE_1610_Maximum_Number_Of_Visible_Points {
    /**
     * You are given an array points, an integer angle, and your location, where location = [posx, posy] and
     * points[i] = [xi, yi] both denote integral coordinates on the X-Y plane.
     *
     * Initially, you are facing directly east from your position. You cannot move from your position, but you can
     * rotate. In other words, posx and posy cannot be changed. Your field of view in degrees is represented by angle,
     * determining how wide you can see from any given view direction. Let d be the amount in degrees that you rotate
     * counterclockwise. Then, your field of view is the inclusive range of angles [d - angle/2, d + angle/2].
     *
     * You can see some set of points if, for each point, the angle formed by the point, your position, and the immediate
     * east direction from your position is in your field of view.
     *
     * There can be multiple points at one coordinate. There may be points at your location, and you can always see these
     * points regardless of your rotation. Points do not obstruct your vision to other points.
     *
     * Return the maximum number of points you can see.
     *
     * Example 1:
     * Input: points = [[2,1],[2,2],[3,3]], angle = 90, location = [1,1]
     * Output: 3
     * Explanation: The shaded region represents your field of view. All points can be made visible in your field of view,
     * including [3,3] even though [2,2] is in front and in the same line of sight.
     *
     * Example 2:
     * Input: points = [[2,1],[2,2],[3,4],[1,1]], angle = 90, location = [1,1]
     * Output: 4
     * Explanation: All points can be made visible in your field of view, including the one at your location.
     *
     * Example 3:
     * Input: points = [[1,0],[2,1]], angle = 13, location = [1,1]
     * Output: 1
     * Explanation: You can only see one of the two points, as shown above.
     *
     * Constraints:
     * 1 <= points.length <= 105
     * points[i].length == 2
     * location.length == 2
     * 0 <= angle < 360
     * 0 <= posx, posy, xi, yi <= 100
     *
     * Hard
     */

    /**
     * Sliding Window
     *
     * Time  : O(nlogn)
     * Space : O(n)
     *
     * https://zxi.mytechroad.com/blog/sliding-window/leetcode-1610-maximum-number-of-visible-points/
     */

    class Solution {
        public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
            List<Double> angels = new ArrayList<>();

            /**
             * Transform the coordination system, use location as origin
             */
            int count = 0;
            for (List<Integer> point : points) {
                int dx = point.get(0) - location.get(0);
                int dy = point.get(1) - location.get(1);

                /**
                 * count the point that is at the same coordination of given location
                 */
                if (dx == 0 && dy == 0) {
                    count++;
                    continue;
                }

                /**
                 * compute the angle of each point to new origin (location)
                 */
                double p = Math.atan2(dy, dx) * 180 / Math.PI;
                angels.add(p);
            }

            /**
             * !!! sorting
             */
            Collections.sort(angels);

            /**
             * !!!
             * Duplicate angle with 360 to handle turn-around case
             */
            List<Double> tmp = new ArrayList<>(angels);
            for (double p : angels) {
                tmp.add(p + 360);
            }

            /**
             * Sliding window
             */
            int res = count;
            for (int i = 0, j = 0; i < tmp.size(); i++) {
                while (tmp.get(i) - tmp.get(j) > angle) {
                    j++;
                }

                res = Math.max(res, count + i - j + 1);
            }

            return res;
        }
    }
}
