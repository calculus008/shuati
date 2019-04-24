package Linkedin;

import java.util.Arrays;

public class Min_Difference_Sum_In_Two_Arrays {
    /**
     * Given two arrays a[] and b[] of equal length n. The task is to pair each element of
     * array a to an element in array b, such that sum S of absolute differences of all
     * the pairs is minimum.
     *
     * Suppose, two elements a[i] and a[j] (i != j) of a are paired with elements b[p]
     * and b[q] of b respectively, then p should not be equal to q.
     *
     * Examples:
     * Input :  a[] = {3, 2, 1}
     *          b[] = {2, 1, 3}
     * Output : 0
     *
     * Input :  n = 4
     *          a[] = {4, 1, 8, 7}
     *          b[] = {2, 3, 6, 5}
     * Output : 6
     */

    /**
     * Greedy
     */
    public static long findMinSum(long a[], long b[], long n) {
        // Sort both arrays
        Arrays.sort(a);
        Arrays.sort(b);

        // Find sum of absolute differences
        long sum = 0 ;
        for (int i = 0; i < n; i++) {
            sum = sum + Math.abs(a[i] - b[i]);
        }

        return sum;
    }


    /**
     * Variation, how about a and b have different length.
     *
     * a中的每个元素都得用上(since a.length < b.length)，b的长度大于等于a，a中每个元素到b里面选
     * 一个对应的元素（必须按顺序取）使的差值的之和为最小。
     *
     * 我是举例子 :
     *
     *   math.abs(a1-b3)＋math.abs(a2-b9)+math.abs(a3-b17)为最小，
     *
     * 不能sort是因为如果sort了，a和b的顺序就被打乱了。比如上面的例子，不能取a1-b3, a2-b1这种是不合法的。
     *
     * DP :
     * dp[i][j] :
     * min pair sum for the first i number from a and first j number from b
     *
     * Transition : Take the min value between cases of pairing a[i] - b[j] and NOT paring them.
     * dp[i][j] = min(dp[i - 1][j - 1] + abs(a[i] - b[j]), dp[i - 1][j])
     *                    Pairing a[i] with b[j]        NOT Pairing a[i] with b[j]
     * Result :
     * dp[a.length][b.length] will be the result
     */

    public int minDifferenceSum(int[] a, int[] b) {
        int[][] dp = new int[a.length + 1][b.length + 1];

        for (int i = 1; i <= a.length; i++) {
            dp[i][0] = Integer.MAX_VALUE; // Need to initialize this because we need hash[i][j - 1]
        }

        for (int i = 1; i <= a.length; i++) {
            for (int j = 1; j <= b.length - (a.length - i); j++) {
                /**
                 * 保证在b上留下空间 to match a.
                 *
                 * Example:
                 * a : [1,5,2]
                 * b : [4,3,6,8,1,11,55,88,22,9]
                 *
                 * la = 3, lb = 10
                 * a[0] can choose all, b[0] ~ b[9], once a[0] choose one from b, say k,
                 * then a[1] can only choose from index > k
                 **/
                dp[i][j] = Math.min(dp[i - 1][j - 1] + Math.abs(a[i - 1] - b[j - 1]), dp[i][j - 1]);
            }
        }

        return dp[a.length][b.length];
    }

    /**
     * What if B is super long
     * This solution has better space efficiency
     *
     * Basically using rolling arrays to reduce space complexity
     **/
    public int minDifferenceSumLong(int[] a, int[] b) {
        int[] dp = new int[a.length + 1];

        for (int i = 1; i <= a.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < b.length; i++) { // Start from index i in b, b is super long
            int[] temp = new int[a.length + 1];
            for (int j = 1; j <= a.length; j++) {
                if (b.length - i - 1 < a.length - j) {
                    continue;
                }
                temp[j] = Math.min(dp[j], dp[j - 1] + Math.abs(b[i] - a[j - 1]));
            }

            dp = temp;
        }

        return dp[a.length];
    }

}
