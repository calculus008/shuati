package leetcode;

public class LE_1131_Maximum_Of_Absolute_Value_Expression {
    /**
     * Given two arrays of integers with equal lengths, return the maximum value of:
     *
     * |arr1[i] - arr1[j]| + |arr2[i] - arr2[j]| + |i - j|
     *
     * where the maximum is taken over all 0 <= i, j < arr1.length.
     *
     * Example 1:
     * Input: arr1 = [1,2,3,4], arr2 = [-1,4,5,6]
     * Output: 13
     *
     * Example 2:
     * Input: arr1 = [1,-2,-5,0,10], arr2 = [0,-2,-1,-7,-4]
     * Output: 20
     *
     *
     * Constraints:
     * 2 <= arr1.length == arr2.length <= 40000
     * -10^6 <= arr1[i], arr2[i] <= 10^6
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/maximum-of-absolute-value-expression/discuss/340075/c%2B%2B-beats-100-(both-time-and-memory)-with-algorithm-and-image
     *
     * What the question means:
     * Giving two arrays, find a index i from arr1 and index j from arr2, index i and index j can be the same,
     * and make sure that the formula has the largest value,
     *
     * Since we have three | |, so for each one we have 2 options, then 8 options in total when we remove | |.
     * and since index i can equal to index j, so option 1 equals option 8, and so on.
     * (1 & 8 ; 2 & 7; 3 & 6; 4 & 5 are practically the same)
     * then all we need to do is make the subtrahend larger and minuend smaller.
     *
     * So problem reduces to finding max of (1,2,3,4).
     * And in each 1,2,3,4, values in both brackets are same, so we simply find max(value in bracket) - min(value in bracket) for each.
     * Then find max of values obtained from (1,2,3,4)
     *
     * 1.  arr1[i] - arr1[j] + arr2[i] - arr2[j] + i - j  => (arr1[i] + arr2[i] + i) - (arr1[j] + arr2[j] + j)
     * 2.  arr1[i] - arr1[j] + arr2[i] - arr2[j] - i + j  => (arr1[i] + arr2[i] - i) - (arr1[j] + arr2[j] - j)
     * 3.  arr1[i] - arr1[j] - arr2[i] + arr2[j] + i - j  => (arr1[i] - arr2[i] + i) - (arr1[j] - arr2[j] + j)
     * 4.  arr1[i] - arr1[j] - arr2[i] + arr2[j] - i + j  => (arr1[i] - arr2[i] - i) - (arr1[j] - arr2[j] - j)
     * 5.- arr1[i] + arr1[j] + arr2[i] - arr2[j] + i - j  => - (arr1[i] - arr2[i] - i) + (arr1[j] + arr2[j] + j)
     * 6.- arr1[i] + arr1[j] + arr2[i] - arr2[j] - i + j  => - (arr1[i] - arr2[i] + i) + (arr1[j] + arr2[j] + j)
     * 7.- arr1[i] + arr1[j] - arr2[i] + arr2[j] + i - j  => - (arr1[i] + arr2[i] - i) + (arr1[j] + arr2[j] - j)
     * 8.- arr1[i] + arr1[j] - arr2[i] + arr2[j] - i + j  => - (arr1[i] + arr2[i] + i) + (arr1[j] + arr2[j] + j)
     *
     * The index does not matter here, no matter it's i or j, it just represents an index that shared by both arr1
     * and arr2 and the value on that index, the index j can be replaced by i and vice versa.
     **/
    class Solution1 {
        public int maxAbsValExpr(int[] arr1, int[] arr2) {
            int max1 = Integer.MIN_VALUE;
            int max2 = Integer.MIN_VALUE;
            int max3 = Integer.MIN_VALUE;
            int max4 = Integer.MIN_VALUE;
            int min1 = Integer.MAX_VALUE;
            int min2 = Integer.MAX_VALUE;
            int min3 = Integer.MAX_VALUE;
            int min4 = Integer.MAX_VALUE;
            int n = arr1.length;

            for (int i = 0; i < n; i++) {
                // 1st scenario arr1[i] + arr2[i] + i
                max1 = Integer.max(arr1[i] + arr2[i] + i, max1);
                min1 = Integer.min(arr1[i] + arr2[i] + i, min1);
                // 2nd scenario arr1[i] + arr2[i] - i
                max2 = Integer.max(arr1[i] + arr2[i] - i, max2);
                min2 = Integer.min(arr1[i] + arr2[i] - i, min2);
                // 3rd scenario arr1[i] - arr2[i] + i
                max4 = Integer.max(arr1[i] - arr2[i] + i, max4);
                min4 = Integer.min(arr1[i] - arr2[i] + i, min4);
                // 4th scenario arr1[i] - arr2[i] - i
                max3 = Integer.max(arr1[i] - arr2[i] - i, max3);
                min3 = Integer.min(arr1[i] - arr2[i] - i, min3);
            }

            int diff1 = max1 - min1;
            int diff2 = max2 - min2;
            int diff3 = max3 - min3;
            int diff4 = max4 - min4;

            return Integer.max(Integer.max(diff1, diff2), Integer.max(diff3, diff4));
        }
    }

    /**
     * Same idea as Solution1, just code with for loop instead of using 4 paris of max and min values.
     *
     * https://leetcode.com/problems/maximum-of-absolute-value-expression/discuss/958258/Clean-n-Concise-JAVA-Solution
     * 1.Since abs(A)+abs(B) = max(a+b, a-b, -a+b, -a-b) (!!!)
     *   abs(A) + abs(B) + abs(C) = max {
     *  									 (a + b + c),
     *  									 (a + b - c),
     *  									 (a - b + c),
     *  									 (a - b - c),
     *  									 (-a + b + c),
     *  									 (-a + b - c),
     *  									 (-a - b + c),
     *  									 (-a - b - c)
     *                                   }
     * 2.If we replace a, b and c, with the respective given terms :
     * 	a = (arr1[i] - arr2[i])
     * 	b = (arr1[j] - arr2[j])
     * 	c = i - j
     *
     * 3.We'll again find similar 8 combinations as discussed above in the form         :
     * 		{(+/-) arr1[i]  (+/-) arr2[i] (+/-) i }         -
     * 		{(+/-) arr1[j]  (+/-) arr2[j] (+/-) j }
     *
     * 4.Hence from this observation, we can infer that for each of the above combination (expression), we need to find maximum value - minimum value.
     * As, can be seen in this post , we only need to calculate results for 4 expressions.
     */
    class Solution2 {
        public int maxAbsValExpr(int[] arr1, int[] arr2) {
            int len = arr1.length;
            int[] first_util = {1, 1 , 1, 1};
            int[] sec_util =   {1, 1 ,-1,-1};
            int[] third_util = {1, -1, 1,-1};
            int ans = 0;
            for(int i = 0; i < 4; i++) {
                int f = first_util[i], s = sec_util[i], t = third_util[i];
                int max_val = Integer.MIN_VALUE, min_val = Integer.MAX_VALUE;
                for(int j = 0; j < len; j++) {
                    int val = arr1[j]*f + arr2[j]*s + j*t;
                    max_val = Math.max(max_val, val);
                    min_val = Math.min(min_val, val);
                }
                ans = Math.max(ans, max_val - min_val);
            }
            return ans;
        }
    }
}
