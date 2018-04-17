package leetcode;

/**
 * Created by yuank on 4/15/18.
 */
public class LE_276_Paint_Fence {
    /**
     * There is a fence with n posts, each post can be painted with one of the k colors.

     You have to paint all the posts such that no more than two adjacent fence posts have the same color.

     Return the total number of ways you can paint the fence.

     Note:
     n and k are non-negative integers.
     */

    /**
     For posts {1, 2, 3}, when we get to 3, total scenarios:
        1.2 and 3  are painted with different color, so we can use all color except the one used on 2, so the total number ways to paint : (k - 1) * total
        2.2 and 3 are painted with the same color, then 2 and 1 must be painted with different color. So we look at the ways of 1 and 2 painted different color.
     */

    public int numWays(int n, int k) {
        if (n == 0) return 0;
        if (n == 1) return k;
        int same = 0;
        int diff = k;
        int total = k;
        for (int i = 2; i <= n; i++) {
            /**
             * i and i - 1 are painted with the same color,
             * then i - 1 and i - 2 must be painted with different color.
             * So we look at the ways of 1 and 2 painted different color.
             */
            same = diff;
            /**
             * i and i - 1  are painted with different color,
             * so we can use all color except the one used on i - 1, so the total number ways to paint : (k - 1) * total
             */
            diff = (k - 1) * total;
            total = diff + same;
        }
        return total;
    }
}
