package Interviews;

public class Longest_Zig_Zag_Subsequence {
    /**
     * Find length of the longest subsequence of given sequence such that
     * all elements of this are alternating. If a sequence {x1, x2, .. xn}
     * is alternating sequence then its element satisfy one of the following
     * relation :
     *
     *   x1 < x2 > x3 < x4 > x5 < …. xn or
     *   x1 > x2 < x3 > x4 < x5 > …. xn
     *
     * Examples :
     *
     * Input: arr[] = {1, 5, 4}
     * Output: 3
     * The whole arrays is of the form  x1 < x2 > x3
     *
     * Input: arr[] = {1, 4, 5}
     * Output: 2
     *
     * All subsequences of length 2 are either of the form
     * x1 < x2; or x1 > x2
     *
     * Input: arr[] = {10, 22, 9, 33, 49, 50, 31, 60}
     * Output: 6
     *
     * The subsequences {10, 22, 9, 33, 31, 60} or
     * {10, 22, 9, 49, 31, 60} or {10, 22, 9, 50, 31, 60}
     * are longest Zig-Zag of length 6.
     */

    /**
     * DP
     * dp[i, 0] = maximum length subsequence ending at i such that the difference between the
     *            last two elements is positive
     * dp[i, 1] = same, but difference between the last two is negative
     *
     * Init
     * dp[i, 0] = dp[i, 1] = 1
     *
     * Transfer
     *   for j = 0 to to i - 1 do
     *     if a[i] - a[j] > 0
     *       dp[i, 0] = max(dp[j, 1] + 1, dp[i, 0])
     *     else if a[i] - a[j] < 0
     *       dp[i, 1] = max(dp[j, 0] + 1, dp[i, 1]
     */

    public static int zzis(int arr[], int n) {
    /**dp[i][0] = Length of the longest Zig-Zag subsequence ending at index i and last element is
     *            greater than its previous element
     * dp[i][1] = Length of the longest Zig-Zag subsequence ending at index i and last element is
     *            smaller than its previouselement
     * */
        int dp[][] = new int[n][2];

        /**
         *  Initialize all values from 1
         **/
        for (int i = 0; i < n; i++) {
            dp[i][0] = dp[i][1] = 1;
        }

        int res = 1; // Initialize result

        /**
         *  Compute values in bottom up manner
         * */
        for (int i = 1; i < n; i++)
        {
            // Consider all elements as
            // previous of arr[i]
            for (int j = 0; j < i; j++)
            {
//                if (arr[j] < arr[i] && dp[i][0] < dp[j][1] + 1) {
//                    dp[i][0] = dp[j][1] + 1;
//                }
//
//                if( arr[j] > arr[i] && dp[i][1] < dp[j][0] + 1) {
//                    dp[i][1] = dp[j][0] + 1;
//                }
//
                if (arr[i] - arr[j] > 0) {
                    dp[i][0] = Math.max(dp[i][0], dp[j][1] + 1);
                }
                
                if (arr[i] < arr[j]) {
                    dp[i][1] = Math.max(dp[i][1], dp[j][0] + 1);
                }
            }

            /**
             * Pick maximum of both values at index i
             **/
            if (res < Math.max(dp[i][0], dp[i][1])) {
                res = Math.max(dp[i][0], dp[i][1]);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int arr[] = { 10, 22, 9, 33, 49, 50, 31, 60 };
        int n = arr.length;
        System.out.println("Length of Longest "+
                "Zig-Zag subsequence is " +
                zzis(arr, n));
    }
}
