package leetcode;

public class LE_780_Reaching_Points {
    /**
     * Given four integers sx, sy, tx, and ty, return true if it is possible to convert the point (sx, sy) to the
     * point (tx, ty) through some operations, or false otherwise.
     *
     * The allowed operation on some point (x, y) is to convert it to either (x, x + y) or (x + y, y).
     *
     * Example 1:
     * Input: sx = 1, sy = 1, tx = 3, ty = 5
     * Output: true
     * Explanation:
     * One series of moves that transforms the starting point to the target is:
     * (1, 1) -> (1, 2)
     * (1, 2) -> (3, 2)
     * (3, 2) -> (3, 5)
     *
     * Example 2:
     * Input: sx = 1, sy = 1, tx = 2, ty = 2
     * Output: false
     *
     * Example 3:
     * Input: sx = 1, sy = 1, tx = 1, ty = 1
     * Output: true
     *
     * Constraints:
     * 1 <= sx, sy, tx, ty <= 109
     *
     * Hard
     *
     * https://leetcode.com/problems/reaching-points/
     */

    /**
     * Math
     *
     * https://leetcode.com/problems/reaching-points/discuss/375429/Detailed-explanation.-or-full-through-process-or-Java-100-beat
     *
     * If you observe that you have two actions to take at any moment for (x,y)
     *
     * (x , x+y)
     * (x+y, y)
     *
     * That means, we can have two choices at (x,y) which forms it like a binary tree. And one of the leaf node probably
     * consist the (tx, ty)
     *
     * Top Down:
     * Do same as ask, run from top to bottom of binary tree. Find the leaf node which satisfy the condition.
     * Example:
     * sx=1, sy=1, tx=3, ty=5
     *
     *             (1, 1)
     *              /  \
     *         (1, 2)  (2, 1)
     *         /  \     ....
     *   (1, 3)  (3, 2)
     *   ...      /  \
     *        (3, 5)
     *
     * Complexity:
     * The height of binary tree depends on tx,ty. All the values which are either greater then tx or ty will be discarded
     * as from that we can't reach the tx,ty. Hence, the height of tree would be Max(tx,ty)= N ..total complexity O(2^N)
     */

    class Solution_Top_Down {
        public static boolean reachingPoints(int sx, int sy, int tx, int ty) {
            if (sx == tx && sy == ty) return true;

            if (sx > tx || sy > ty) return false;

            return (reachingPoints(sx + sy, sy, tx, ty) || reachingPoints(sx, sx + sy, tx, ty));
        }
    }

    /**
     * In above approach, we need to drill down till the tx or ty. If you see, for each child, there is only 1 way to reach
     * parent (eventually root) in binary tree. Which means, instead of starting from (sx,sy) and go down, we can start from
     * (tx,ty) and go up till you hit one of the condition like sx >= tx or sy>= ty {revers of top down condition} then you
     * from that point you can simply check does it is possible to reach or not.
     */
    class Solution_Bottom_Up {
        public boolean reachingPoints(int sx, int sy, int tx, int ty) {
            if (sx == tx && sy == ty) return true;

            /**
             * Only one way to go up until the condition does not hold
             */
            while (tx > sx && ty > sy) {
                if (tx > ty) {
                    tx %= ty;
                } else {
                    ty %= tx;
                }
            }

            if (sx == tx && ty >= sy && (ty - sy) % sx == 0) return true;

            if (sy == ty && tx >= sx && (tx - sx) % sy == 0) return true;

            return false;
        }
    }

}
