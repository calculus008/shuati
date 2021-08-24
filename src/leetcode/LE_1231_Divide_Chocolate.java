package leetcode;

public class LE_1231_Divide_Chocolate {
    /**
     * You have one chocolate bar that consists of some chunks. Each chunk has its own sweetness given by the array sweetness.
     *
     * You want to share the chocolate with your k friends so you start cutting the chocolate bar into k + 1 pieces using k cuts,
     * each piece consists of some consecutive chunks.
     *
     * Being generous, you will eat the piece with the minimum total sweetness and give the other pieces to your friends.
     *
     * Find the maximum total sweetness of the piece you can get by cutting the chocolate bar optimally.
     *
     * Example 1:
     * Input: sweetness = [1,2,3,4,5,6,7,8,9], k = 5
     * Output: 6
     * Explanation: You can divide the chocolate to [1,2,3], [4,5], [6], [7], [8], [9]
     *
     * Example 2:
     * Input: sweetness = [5,6,7,8,9,1,2,3,4], k = 8
     * Output: 1
     * Explanation: There is only one way to cut the bar into 9 pieces.
     *
     * Example 3:
     * Input: sweetness = [1,2,2,1,2,2,1,2,2], k = 2
     * Output: 5
     * Explanation: You can divide the chocolate to [1,2,2], [1,2,2], [1,2,2]
     *
     * Constraints:
     * 0 <= k < sweetness.length <= 104
     * 1 <= sweetness[i] <= 105
     *
     * Hard
     */

    /**
     * Binary Search
     * In this problem we want to find: Maximum of minimum total sweetness
     * In LE_410_Split_Array_Largest_Sum, we want to find: Minimum of maximum largest sum
     *
     * In both places we do binary search on target answer, the difference is subtle but the key:
     *
     * In this when we overshoot the target, we will include that number in PREVIOUS sum, as that is how we will maintain
     * the target as the minimum number and binary search will find this optimal minimum.
     *
     * In LE_410_Split_Array_Largest_Sum when we overshoot the target, we will include the number in the NEXT sum, so we
     * can ensure all numbers are less than target - binary search does the rest of the magic.
     *
     * Regarding people's questions on why the binary search target is guaranteed to be the answer is because if there is
     * a better optimal answer binary search will keep heading to it, can prove by contradiction
     */

    public int maximizeSweetness(int[] sweetness, int k) {
        //We can get the min and max values of sweetness, from tests, it seems that it slows down the solution significantly.
        //So we don't do it. (speed 47% vs 90%)
//         int l = Integer.MAX_VALUE;
//         int r = 0;

//         for (int s : sweetness) {
//             l = Math.min(l, s);
//             r += s;
//         }

        int l = 0;
        int r = (int)1e9;

        int res = 0;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (isValid(sweetness, k, m)) {
                /**
                 * !!! max(), used for find group sum
                 */
                res = Math.max(res, m);
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return res;
    }

    private boolean isValid(int[] sweetness, int k, int x) {
        int sweetCount = 0;
        int groupCount = 0;

        for (int s : sweetness) {
            /**
             * overshoot and count in previous sum -> find Max of min
             */
            sweetCount += s;
            if (sweetCount >= x) {
                groupCount++;
                sweetCount = 0;

                if (groupCount >= k + 1) return true;
            }
        }

        return groupCount >= k + 1;
    }
}

