package leetcode;

public class LE_1014_Capacity_To_Ship_Packages_Within_D_Days {
    /**
     * A conveyor belt has packages that must be shipped from one port
     * to another within D days.
     *
     * The i-th package on the conveyor belt has a weight of weights[i].
     * Each day, we load the ship with packages on the conveyor belt
     * (in the order given by weights). We may not load more weight than
     * the maximum weight capacity of the ship.
     *
     * Return the least weight capacity of the ship that will result in
     * all the packages on the conveyor belt being shipped within D days.
     *
     * Example 1:
     * Input: weights = [1,2,3,4,5,6,7,8,9,10], D = 5
     * Output: 15
     * Explanation:
     * A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
     * 1st day: 1, 2, 3, 4, 5
     * 2nd day: 6, 7
     * 3rd day: 8
     * 4th day: 9
     * 5th day: 10
     *
     * Note that the cargo must be shipped in the order given, so using a ship of
     * capacity 14 and splitting the packages into parts like (2, 3, 4, 5), (1, 6, 7),
     * (8), (9), (10) is not allowed.
     *
     * Example 2:
     * Input: weights = [3,2,2,4,1,4], D = 3
     * Output: 6
     * Explanation:
     * A ship capacity of 6 is the minimum to ship all the packages in 3 days like this:
     * 1st day: 3, 2
     * 2nd day: 2, 4
     * 3rd day: 1, 4
     *
     * Example 3:
     * Input: weights = [1,2,3,1,1], D = 4
     * Output: 3
     * Explanation:
     * 1st day: 1
     * 2nd day: 2
     * 3rd day: 3
     * 4th day: 1, 1
     *
     * Note:
     * 1 <= D <= weights.length <= 50000
     * 1 <= weights[i] <= 500
     *
     * Medium
     */

    /**
     * LE_410_Split_Array_Largest_Sum 的另一个马甲。
     *
     * Binary Search
     *
     * Given the number of bags,
     * return the minimum capacity of each bag,
     * so that we can put items one by one into all bags.
     *
     * Similar as
     * 875. Koko Eating Bananas
     * 774. Minimize Max Distance to Gas Station
     *
     * Time : O(n + log(sum - maxWeight) * n)
     */
    class Solution {
        public int shipWithinDays(int[] weights, int D) {
            int l = 0, r = 0;
            for (int w : weights) {
                /**
                 * if we ship one item per ship, then the capacity of
                 * the ship should at least take the max weight in the
                 * array.
                 */
                l = Math.max(l, w);
                r += w;
            }

            r++;
            while (l < r) {
                int m = l + (r - l) / 2;
                if (countShip(weights, m, D)) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }

            return l;
        }

        /**
         * Time : O(n)
         */
        private boolean countShip(int[] weights, int capacity, int D) {
            /**
             * start with one ship, loading cargos
             */
            int count = 1;
            int sum = 0;
            for (int w : weights) {
                sum += w;
                if (sum > capacity) {
                    count++;
                    /**
                     * need a new ship, current cargo w should be
                     * in the new ship, therefore sum is set to w.
                     */
                    sum = w;
                }

                if (count > D) {
                    return true;
                }
            }

            return false;
        }
    }

}
