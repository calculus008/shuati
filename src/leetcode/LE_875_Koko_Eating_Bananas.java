package leetcode;

public class LE_875_Koko_Eating_Bananas {
    /**
     * Koko loves to eat bananas.  There are N piles of bananas, the i-th pile
     * has piles[i] bananas.  The guards have gone and will come back in H hours.
     *
     * Koko can decide her bananas-per-hour eating speed of K.  Each hour, she
     * chooses some pile of bananas, and eats K bananas from that pile.  If the
     * pile has less than K bananas, she eats all of them instead, and won't eat
     * any more bananas during this hour.
     *
     * Koko likes to eat slowly, but still wants to finish eating all the bananas
     * before the guards come back.
     *
     * Return the minimum integer K such that she can eat all the bananas within H hours.
     *
     * Example 1:
     * Input: piles = [3,6,7,11], H = 8
     * Output: 4
     *
     * Example 2:
     * Input: piles = [30,11,23,4,20], H = 5
     * Output: 30
     *
     * Example 3:
     * Input: piles = [30,11,23,4,20], H = 6
     * Output: 23
     *
     *
     * Note:
     * 1 <= piles.length <= 10^4
     * piles.length <= H <= 10^9
     * 1 <= piles[i] <= 10^9
     *
     * Medium
     */

    /**
     * Binary Search
     *
     * 仔细读题
     * "Each hour, she chooses some pile of bananas..."
     * " If the pile has less than K bananas, she eats
     * all of them instead, and won't eat any more bananas
     * during this hour."
     *
     * 这实际上是说，每个小时只吃一个pile ("some pile", not "piles").
     * 哪怕这个pile的数量不够k, 它也不多吃了。
     *
     * 所以，二分的range is [1, max number in a pile + 1)
     *
     * Time Complexity: O(NlogW), where N is the number of piles, and W is the maximum size of a pile.
     *
     * Space Complexity: O(1).
     */
    class Solution {
        public int minEatingSpeed(int[] piles, int H) {
            int n = piles.length;
            int l = 1;
            int r = 0;

            for (int p : piles) {
                r = Math.max(r, p);
            }
            r++;

            while (l < r) {
                int m = l + (r - l) / 2;

                if (canEatAll(piles, H, m)) {
                    r = m;
                } else {
                    l = m + 1;
                }
            }

            return l;

            // This binary search logic also works
//            int res = 0;
//            while (l <= r) {
//                int m = l + (r - l) / 2;
//
//                if (canEatAll(piles, H, m)) {
//                    res = m;
//                    r = m - 1;
//                } else {
//                    l = m + 1;
//                }
//            }
//
//            return res;

        }

        /**
         Can Koko eat all bananas in H hours with eating speed K?
         If yes, we can try to let him each more per hour.
         If no, we try to let him each less per hour.
         **/
        private boolean canEatAll(int[] piles, int H, int k) {
            int count = 0;
            for (int p : piles) {
                /**
                 * 关键是，如果每小时吃k个，如何计算吃完所有香蕉总共用的时间
                 */
                count += (p % k == 0 ? p / k : p / k + 1);
                //or
                //count += (p - 1) / k + 1;

                /**
                 * 不能用Math.ceil,这个函数是用在 double 类型上的。
                 */
                // count += Math.ceil(p / k) - 1;
            }

            return count <= H;
        }
    }
}
