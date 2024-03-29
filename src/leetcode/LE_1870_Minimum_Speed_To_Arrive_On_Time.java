package leetcode;

public class LE_1870_Minimum_Speed_To_Arrive_On_Time {
    /**
     * You are given a floating-point number hour, representing the amount of time you have to reach the office.
     * To commute to the office, you must take n trains in sequential order. You are also given an integer array
     * dist of length n, where dist[i] describes the distance (in kilometers) of the ith train ride.
     *
     * Each train can only depart at an integer hour, so you may need to wait in between each train ride.
     *
     * For example, if the 1st train ride takes 1.5 hours, you must wait for an additional 0.5 hours before you can
     * depart on the 2nd train ride at the 2 hour mark.
     * Return the minimum positive integer speed (in kilometers per hour) that all the trains must travel at for you
     * to reach the office on time, or -1 if it is impossible to be on time.
     *
     * Tests are generated such that the answer will not exceed 10 ^ 7 and hour will have at most two digits after
     * the decimal point.
     *
     * Example 1:
     * Input: dist = [1,3,2], hour = 6
     * Output: 1
     * Explanation: At speed 1:
     * - The first train ride takes 1/1 = 1 hour.
     * - Since we are already at an integer hour, we depart immediately at the 1 hour mark. The second train takes 3/1 = 3 hours.
     * - Since we are already at an integer hour, we depart immediately at the 4 hour mark. The third train takes 2/1 = 2 hours.
     * - You will arrive at exactly the 6 hour mark.
     *
     * Example 2:
     * Input: dist = [1,3,2], hour = 2.7
     * Output: 3
     * Explanation: At speed 3:
     * - The first train ride takes 1/3 = 0.33333 hours.
     * - Since we are not at an integer hour, we wait until the 1 hour mark to depart. The second train ride takes 3/3 = 1 hour.
     * - Since we are already at an integer hour, we depart immediately at the 2 hour mark. The third train takes 2/3 = 0.66667 hours.
     * - You will arrive at the 2.66667 hour mark.
     *
     * Example 3:
     * Input: dist = [1,3,2], hour = 1.9
     * Output: -1
     * Explanation: It is impossible because the earliest the third train can depart is at the 2 hour mark.
     *
     * Constraints:
     * n == dist.length
     * 1 <= n <= 105
     * 1 <= dist[i] <= 105
     * 1 <= hour <= 109
     * There will be at most two digits after the decimal point in hour.
     *
     * Medium
     *
     * https://leetcode.com/problems/minimum-speed-to-arrive-on-time/
     */

    /**
     * Binary Search
     * Time O(NlogM), where M = max(A)
     * Space: O(1)
     *
     * https://leetcode.com/problems/minimum-speed-to-arrive-on-time/discuss/1226468/Binary-Answer-Cheat-sheet
     *
     * Similar problem:
     * https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/
     */
    class Solution1 {
        public int minSpeedOnTime(int[] dist, double hour) {
            int n = dist.length;

            /**
             * Since time is round to the next integer, so each train will take at least 1 hour.
             * Therefore if hour is smaller than or equal to n - 1, there's no time left for the
             * last train.
             */
            if (hour <= n - 1) return -1;

            int l = 1;
            int h = 10000000;

            while (l < h) {
                int mid = l + (h - l) / 2;
                if (isValid(dist, hour, mid)) {
                    h = mid;
                } else {
                    l = mid + 1;
                }
            }

            return l;
        }

        private boolean isValid(int[] dist, double hour, int speed) {
            double time = 0.0;
            int n = dist.length;

            /**
             * The only tricky part - when sum hours, the last road does not need to round to the next Integer,
             * since no train is after it.
             */
            for (int i = 0; i < n - 1; i++) {
                /**
                 * Math.ceil()
                 */
                time += Math.ceil((double) dist[i] / speed);
            }

            time += (double) dist[n - 1] / speed;

            return time <= hour? true : false;
        }
    }

    /**
     * Binary Search with different template
     */
    class Solution2 {
        public int minSpeedOnTime(int[] dist, double hour) {
            int n = dist.length;

            int l = 1;
            int h = 10000000;

            int res = -1;

            while (l <= h) {
                int mid = l + (h - l) / 2;
                if (isValid(dist, hour, mid)) {
                    /**
                     * remember the last valid mid value.
                     */
                    res = mid;
                    h = mid - 1;
                } else {
                    l = mid + 1;
                }
            }

            return res;
        }

        private boolean isValid(int[] dist, double hour, int speed) {
            double time = 0.0;
            int n = dist.length;

            for (int i = 0; i < n - 1; i++) {
                time += Math.ceil((double) dist[i] / speed);
            }

            time += (double) dist[n - 1] / speed;

            return time <= hour? true : false;
        }
    }
}
